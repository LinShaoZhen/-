package com.example.administrator.essayjoke.SelectImage;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.essayjoke.MainActivity;
import com.example.administrator.essayjoke.R;
import com.example.administrator.essayjoke.RecycleView.CommonAdapter.ItemClickListener;
import com.example.baselibrary.ioc.OnClick;
import com.example.baselibrary.ioc.ViewById;
import com.example.framelibrary.BaseSkinActivity;
import com.example.framelibrary.DefaultNavigationBar;

import java.util.ArrayList;

/**
 * Created by nana on 2018/9/21.
 */

public class SelectImageActivity extends BaseSkinActivity implements View.OnClickListener, SelectImageListener {
    private static final String TAG = "SelectImageActivity";
    //带过来的key
    //是否显示相机的EXTRA_KEY
    public static final String EXTRA_SHOW_CAMERA = "EXTRA_SHOW_CAMERA";
    //总共可以选择多少张的EXTRA_KEY
    public static final String EXTRA_SELECT_COUNT = "EXTRA_SELECT_COUNT";
    //原始图片路径的EXTRA_KEY
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "EXTRA_DEFAULT_SELECTED_LIST";
    //选择模式的EXTRA_KEY
    public static final String EXTRA_SELECT_MODE = "EXTRA_SELECT_MODE";
    //返回选择图片列表的EXTRA_KEY
    public static final String EXTRA_RESULT = "EXTRA_RESULT";
    //获取传递过来的参数
    //选择图片模式——多选
    public static final int MODE_MULTI = 0x0011;
    //选择图片模式——单选
    public static final int MODE_SINGLE = 0x0012;

    //单选或者多选，int类型的type
    private int mMode = MODE_MULTI;
    //int类型的图片张数
    private int mMaxCount = 8;
    //boolean类型的是否显示拍照按钮
    private boolean mShowCamera = true;
    //ArrayList<String> 已经选择好的图片列表
    private ArrayList<String> mResyltList;

    @ViewById(R.id.image_list_rv)
    private RecyclerView mImageListRv;
    //加载所有数据
    private static final int LOADER_TYPE = 0x0021;

    @ViewById(R.id.select_preview)
    private TextView mSelectPreview;

    @ViewById(R.id.select_num)
    private TextView mSelectNum;

    @ViewById(R.id.select_finish)
    private TextView mSelectFinish;


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_image_selector);

    }

    @Override
    protected void initTitle() {
        DefaultNavigationBar navigationBar = new DefaultNavigationBar
                .Builder(this)
                .setTitle("所有图片")
                .setRightIcon(R.mipmap.ic_launcher)
                .budiler();

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //获取上个野蛮传过来的参数
        Intent intent = getIntent();
        mMode = intent.getIntExtra(EXTRA_SELECT_COUNT, mMode);
        mMaxCount = intent.getIntExtra(EXTRA_SELECT_COUNT, mMaxCount);
        mShowCamera = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, mShowCamera);
        mResyltList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        if (mResyltList == null) {
            mResyltList = new ArrayList<>();
        }
        //初始化本地图片数据
        initImageList();
        //改变显示
        exchangeViewShow();
    }

    //改变布局显示 需要及时更新
    private void exchangeViewShow() {
        //预览是不是可以点击
        if (mResyltList.size() > 0) {
            //至少选择了一张
            mSelectPreview.setEnabled(true);
            mSelectPreview.setTextColor(Color.RED);
            mSelectPreview.setOnClickListener(this);
        } else {
            //一张都没选
            mSelectPreview.setEnabled(false);
            mSelectPreview.setTextColor(Color.WHITE);
            mSelectPreview.setOnClickListener(null);
        }

        //中间图片的张数
        mSelectNum.setText(mResyltList.size() + "/" + mMaxCount);
    }

    /**
     * 从ContentProvider获取内存卡中的图片
     */
    private void initImageList() {
        //耗时操作
        getLoaderManager().initLoader(LOADER_TYPE, null, mLoaderCallBack);
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallBack = new LoaderManager.LoaderCallbacks<Cursor>() {
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID
        };

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            //查询语句
            CursorLoader cursorLoader = new CursorLoader(SelectImageActivity.this,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[3] + "=? OR "
                            + IMAGE_PROJECTION[3] + "=? ",
                    new String[]{"image/png", "image/jpeg"}, IMAGE_PROJECTION[2] + " DESC");
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            //解析，封装到集合 只保存S挺路径
            //如果有数据变量数据
            if (data != null && data.getCount() > 0) {
                ArrayList<String> images = new ArrayList<>();
                //如果需要显示拍照，就在第一个位置加一个空字符串
                if (mShowCamera) {
                    images.add("");
                }
                //不断循环遍历
                while (data.moveToNext()) {
                    //只保存路径
                    String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                    images.add(path);
                }
                //显示列表数据
                showImageList(images);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    /**
     * 显示图片列表
     *
     * @param images
     */
    private void showImageList(ArrayList<String> images) {
        SelectImageListAdapter listAdapter = new SelectImageListAdapter(this, images, mResyltList, mMaxCount);
        listAdapter.setOnSelectImageListener(this);
        mImageListRv.setLayoutManager(new GridLayoutManager(this, 4));
        mImageListRv.setAdapter(listAdapter);
    }

    @Override
    public void onClick(View v) {
        //图片预览

    }

    @Override
    public void select() {
        exchangeViewShow();
    }

    @OnClick(R.id.select_finish)
    private void sureSelect(View view) {
        //选择好的图片创过去
        Intent intent = new Intent();
        intent.putStringArrayListExtra(EXTRA_RESULT, mResyltList);
        setResult(RESULT_OK, intent);
        //关闭当前页面
        finish();
    }

    //拍照
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //把图片加到集合


        //调用sureSelect()方法

        //通知系统本地有图片改变，下次进来可以找到这个图片
    }
}
