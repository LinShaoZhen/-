package com.butterknife_compiler;


import com.butterknife_annotations.BindView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;


/**
 * @author lsz
 * @time 2018/10/10 22:26
 * 作用：
 */
@AutoService(Processor.class)
public class ButterKnifeProcessor extends AbstractProcessor {
    private Filer mFiler;
    private Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
    }

    //1.指定处理的版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    //2.给到需要处理的注解

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotation = new LinkedHashSet<>();
        //需要解析的自定义注解BingView OnClick
        annotation.add(BindView.class);
        return annotation;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //process方法表示的是，有注解就都会进来
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);

        Map<Element, List<Element>> elementMap = new LinkedHashMap<>();
        for (Element element : elements) {
            Element enclosingElement = element.getEnclosingElement();
            List<Element> viewBindElements = elementMap.get(enclosingElement);
            if (viewBindElements == null) {
                viewBindElements = new ArrayList<>();
                elementMap.put(enclosingElement, viewBindElements);
            }
            viewBindElements.add(element);
        }
        //生成代码
        for (Map.Entry<Element, List<Element>> entry : elementMap.entrySet()) {
            Element enclosingElement = entry.getKey();
            List<Element> viewBindElements = entry.getValue();
            String ativityClassNameStr = enclosingElement.getSimpleName().toString();
            ClassName activityClassName = ClassName.bestGuess(ativityClassNameStr);
            ClassName unbinderClassName = ClassName.get("com.butterknife", "Unbinder");
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(ativityClassNameStr + "_ViewBinding")
                    .addModifiers(Modifier.FINAL, Modifier.PUBLIC).addSuperinterface(unbinderClassName)
                    .addField(activityClassName, "target",Modifier.PRIVATE);
            //实现unbind方法
            ClassName callSuperClassName = ClassName.get("android.support.annotation", "CallSuper");
            MethodSpec.Builder unbindMethodBuilder = MethodSpec.methodBuilder("unbind")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addAnnotation(callSuperClassName);
            unbindMethodBuilder.addStatement("$T target=this.target",activityClassName);
            unbindMethodBuilder.addStatement("if(target==null) throw new IllegalStateException(\"Bindings already cleared.\");");

            //构造函数
            MethodSpec.Builder constructorMethodBuilder=MethodSpec.constructorBuilder()
                    .addParameter(activityClassName,"target");
            constructorMethodBuilder.addStatement("this.target=target");
            for (Element viewBindElement : viewBindElements) {
                String filedName=viewBindElement.getSimpleName().toString();
                ClassName utilsClassName=ClassName.get("com.butterknife","Utils");
                int resId=viewBindElement.getAnnotation(BindView.class).value();
                    constructorMethodBuilder.addStatement("target.$L=$T.findViewById(target,$L)",filedName,utilsClassName,resId);
                    unbindMethodBuilder.addStatement("target.$L=null",filedName);
            }

            classBuilder.addMethod(unbindMethodBuilder.build());
            classBuilder.addMethod(constructorMethodBuilder.build());
            //生成类
            try {
                //包名
                String packageName = mElementUtils.getPackageOf(enclosingElement).getQualifiedName().toString();
                JavaFile.builder(packageName, classBuilder.build())
                        .addFileComment("butterknife 自动生成")
                        .build().writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
