package com.example.administrator.essayjoke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.example.administrator.essayjoke.Fragment.FandFragment;
import com.example.administrator.essayjoke.Fragment.HomeFragment;
import com.example.administrator.essayjoke.Fragment.MessageFragment;
import com.example.administrator.essayjoke.Fragment.NewFragment;
import com.example.baselibrary.ioc.OnClick;
import com.example.baselibrary.ioc.ViewById;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private HomeFragment mHomeFragment;
    private FandFragment mFandFragment;
    private NewFragment mNewFragment;
    private MessageFragment mMessageFragment;
    private FragmentManagerHelper mFramentHelper;
    private RadioButton homeRb;
    private RadioButton fandRb;
    private RadioButton messageRb;
    private RadioButton newRb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initData();

    }

    private void initView() {
        homeRb=findViewById(R.id.fragment_home_rb);
        fandRb=findViewById(R.id.fragment_fand_rb);
        messageRb=findViewById(R.id.fragment_message_rb);
        newRb=findViewById(R.id.fragment_new_rb);
    }

    private void initData() {
        homeRb.setOnClickListener(this);
        fandRb.setOnClickListener(this);
        messageRb.setOnClickListener(this);
        newRb.setOnClickListener(this);

        mFramentHelper = new FragmentManagerHelper(getSupportFragmentManager(), R.id.main_tab_fl);
        mHomeFragment = new HomeFragment();
        mFramentHelper.add(mHomeFragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_home_rb:
                Log.e("lsz", "hoemOnClick");
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                mFramentHelper.switchFragment(mHomeFragment);
                break;
            case R.id.fragment_fand_rb:
                if (mFandFragment == null) {
                    mFandFragment = new FandFragment();
                }
                mFramentHelper.switchFragment(mFandFragment);
                break;
            case R.id.fragment_message_rb:
                if (mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                }
                mFramentHelper.switchFragment(mMessageFragment);
                break;
            case R.id.fragment_new_rb:
                if (mNewFragment == null) {
                    mNewFragment = new NewFragment();
                }
                mFramentHelper.switchFragment(mNewFragment);
                break;
        }

    }
}
