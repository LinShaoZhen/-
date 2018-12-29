package com.example.administrator.essayjoke;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.butterknife.ButterKnife;
import com.butterknife.Unbinder;
import com.butterknife_annotations.BindView;
import com.example.administrator.essayjoke.SelectImage.TestImageActivity;
import com.example.administrator.essayjoke.View.ViewActivity;
import com.example.administrator.essayjoke.banner.BannerAdapter;
import com.example.administrator.essayjoke.banner.BannerView;
import com.example.administrator.essayjoke.indicator.ViewPagerActivity;
import com.example.administrator.essayjoke.model.DiscoverListResult;
import com.example.baselibrary.fixBug.FixDexManager;
import com.example.baselibrary.http.HttpUtils;
import com.example.baselibrary.ioc.ViewById;
import com.example.framelibrary.BaseSkinActivity;
import com.example.framelibrary.DefaultNavigationBar;
import com.example.framelibrary.http.HttpCallBack;
import com.example.framelibrary.skin.SkinManager;
import com.example.framelibrary.skin.SkinResource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseSkinActivity {
    @BindView(R.id.tv)
    public TextView mTv;

    @ViewById(R.id.banner_view)
    private BannerView mBannerView;
    private String TAG = "MainActivity";
    private Unbinder mUnbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main11);
        mUnbinder=ButterKnife.bind(this);
        mTv.setText("11111111111111111");
    }


    @Override
    protected void initView() {
        final int imagerId[] = new int[]{
                R.drawable.welcome, R.drawable.welcome, R.drawable.welcome, R.mipmap.ic_launcher
        };
        mBannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                ImageView iv = null;
                if (convertView == null) {
                    iv = new ImageView(MainActivity.this);
                } else {
                    iv = (ImageView) convertView;
                }
                iv.setImageResource(imagerId[position]);

                return iv;
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public String getBannerDesc(int position) {
                List<String> strings = new ArrayList<>();
                strings.add("1111");
                strings.add("222");
                strings.add("333");
                strings.add("444");
                return strings.get(position);
            }
        });
        mBannerView.startRoll();

    }

    @Override
    protected void initTitle() {
        //初始化头部
        DefaultNavigationBar navigationBar = new DefaultNavigationBar
                .Builder(this)
                .setTitle("标题")
                .setRightIcon(R.mipmap.ic_launcher)
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "右边", Toast.LENGTH_SHORT).show();
                    }
                })
                .budiler();
    }


    public void skin(View view) {
        // String SkinPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "red.skin";
        //换肤
        //  int result = SkinManager.getInstance().loadSkin(SkinPath);
        Intent intent = new Intent(MainActivity.this, TestImageActivity.class);
        startActivity(intent);
    }

    public void skin1(View view) {
        //恢复默认
        int result = SkinManager.getInstance().restoreDefault();
        Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
        startActivity(intent);
    }

    public void skin2(View view) {
        //跳转
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
    }

    public void skin3(View view) {
        //跳转
        Intent intent = new Intent(MainActivity.this, ViewActivity.class);
        startActivity(intent);
    }

    @Override
    protected void initData() {
        startService(new Intent(this, MessageService.class));
        startService(new Intent(this, GuardService.class));
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            startService(new Intent(this, JobWakeUpService.class));
        }

        aliFixBug();
        /* HttpUtils.with(this).url("http://mobile.weather.com.cn/data/forecast/101010100.html?_=1381891660081").get()
                .cache(true)//读取缓存
                .execeute(new HttpCallBack<DiscoverListResult>() {
                    @Override
                    public void onSuccess(DiscoverListResult result) {
                        Log.e(TAG, result.getC().getC1());

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });*/
    }

    /**
     * 自己的修复方式
     */
    private void fixDexBug() {
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.dex");
        if (fixFile.exists()) {
            FixDexManager fixDexManager = new FixDexManager(this);
            try {
                fixDexManager.fixDex(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("lsz", e.getMessage());
                Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }


/*    @Override
    public void onClick(View v) {
       *//* AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.detail_comment_dialog)
                .setWidthAndHeight(400, 400)
                .fromButton(true)
                .addDefaultAnimation()
                .setText(R.id.comment_btn, "接收")
                .setOnClickListener(R.id.comment_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    }
                }).show();*//*

        //读取本地的一个.skin资源

        try {
            Resources superRes = getResources();
            AssetManager asset = AssetManager.class.newInstance();
            //添加本地下载好的资源皮肤
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            //反射执行方法
            method.invoke(asset, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "red.skin");
            Resources resources = new Resources(asset, superRes.getDisplayMetrics(), superRes.getConfiguration());
            //获取资源id
            int drawableId = resources.getIdentifier("welcome", "drawable", "com.example.gwb.gwb");
            Drawable drawable = resources.getDrawable(drawableId);
            test_iv.setImageDrawable(drawable);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //   IDaoSupport<Person> daoSoupport = DaoSupportFactory.getFactory().getDao(Person.class);
                    // daoSoupport.insert(new Person("LSZ",23));
                /*     List<Person> query = daoSoupport.querySupport().selection("name=?").selectionArgs("LSZ").query();
                    Log.e(TAG, query.size() + "");
                    // fixDexBug();
                   File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.apatch");
                    if (fixFile.exists()) {
                        //修复bug
                        try {
                            BaseApplication.mPatchManager.addPatch(fixFile.getAbsolutePath());
                            Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("lsz", e.getMessage().toString());
                            Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
                        }
                    }*/

                } else {
                    Toast.makeText(this, "你拒绝了这个权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void aliFixBug() {
        //检查权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请拨打电话的权限
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            //IDaoSupport<Person> daoSoupport = DaoSupportFactory.getFactory().getDao(Person.class);
            // daoSoupport.insert(new Person("LSZ",23));
            //List<Person> persons=new ArrayList<>();
       /*     for (int i=0;i<1000;i++){
                persons.add(new Person("lsz",i));
            }
            long startTime = SystemClock.currentThreadTimeMillis();
            daoSoupport.insert(persons);
            long endTime = SystemClock.currentThreadTimeMillis();
            Log.e(TAG,"总耗时："+(endTime-startTime));

            List<Person> query = daoSoupport.querySupport().selection("name=?").selectionArgs("lsz").query();
            Log.e(TAG, query.size() + "");
            //   fixDexBug();
            File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.apatch");
                if (fixFile.exists()) {
                    //修复bug
                try {
                    BaseApplication.mPatchManager.addPatch(fixFile.getAbsolutePath());
                    Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("lsz", e.getMessage().toString());
                    Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
                }
            }*/
        }
    }

    @Override
    public void changeSkin(SkinResource skinResource) {
        //做一些第三方的改变
        Toast.makeText(this, "换肤了", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        SkinManager.getInstance().unregister(this);
        super.onDestroy();
    }
}

