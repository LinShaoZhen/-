package com.example.administrator.essayjoke.SelectImage;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.administrator.essayjoke.R;
import com.example.administrator.essayjoke.indicator.ItemFragment;
import com.example.framelibrary.BaseSkinActivity;

import java.util.ArrayList;

/**
 * Created by nana on 2018/9/25.
 */

public class TestImageActivity extends BaseSkinActivity {
    private ArrayList<String> mImageList;
    private final int SELECT_IMAGE_REQUEST = 0X0011;
    private String TAG="TestImageActivity";

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_test_image);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    //选择图片
    public void selectImage(View view) {
        Intent intent = new Intent(this, SelectImageActivity.class);
        intent.putExtra(SelectImageActivity.EXTRA_SELECT_COUNT, 9);
        intent.putExtra(SelectImageActivity.EXTRA_SELECT_MODE, SelectImageActivity.MODE_MULTI);
        intent.putStringArrayListExtra(SelectImageActivity.EXTRA_DEFAULT_SELECTED_LIST, mImageList);
        intent.putExtra(SelectImageActivity.EXTRA_SHOW_CAMERA, true);
        startActivityForResult(intent,SELECT_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode==SELECT_IMAGE_REQUEST&&data!=null){
               mImageList= data.getStringArrayListExtra(SelectImageActivity.EXTRA_RESULT);
               //显示
                Log.e(TAG,mImageList.toString());
            }
        }
    }
}
