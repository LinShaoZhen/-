package com.example.administrator.essayjoke.View.QQStepView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import com.example.administrator.essayjoke.R;
import com.example.baselibrary.ioc.ViewById;

public class QQStepViewActivity extends AppCompatActivity {
    private QQStepView mStrpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqstep_view);
        mStrpView=findViewById(R.id.step_view);
        mStrpView.setStepMax(4000);

        //属性动画
        ValueAnimator valueAnimator=ObjectAnimator.ofFloat(0,3000);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentStep= (float) animation.getAnimatedValue();
                mStrpView.setCurrentStep((int) currentStep);
            }
        });
        valueAnimator.start();

    }
}
