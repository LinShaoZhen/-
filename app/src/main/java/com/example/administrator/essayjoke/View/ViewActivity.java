package com.example.administrator.essayjoke.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.example.administrator.essayjoke.Adapter.ViewAdapter;
import com.example.administrator.essayjoke.R;
import com.example.administrator.essayjoke.RecycleView.CommonAdapter.ItemClickListener;
import com.example.administrator.essayjoke.RecycleView.wrap.WrapRecyclerView;
import com.example.administrator.essayjoke.View.LetterSideBar.LetterSideBarActivity;
import com.example.administrator.essayjoke.View.QQStepView.QQStepView;
import com.example.administrator.essayjoke.View.QQStepView.QQStepViewActivity;
import com.example.administrator.essayjoke.View.TextView.TextViewActivity;
import com.example.baselibrary.ioc.ViewById;
import com.example.framelibrary.BaseSkinActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends BaseSkinActivity {
    @ViewById(R.id.view_rv)
    private WrapRecyclerView viewRv;

    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_view);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        list.add("自定义TextView");
        list.add("仿QQ计步器效果");
        list.add("字母索引");

        ViewAdapter adapter = new ViewAdapter(ViewActivity.this, list);
        adapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void setOnClickListener(int position) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(ViewActivity.this, TextViewActivity.class);

                        break;
                    case 1:
                        intent = new Intent(ViewActivity.this, QQStepViewActivity.class);
                        break;
                    case 2:
                        intent = new Intent(ViewActivity.this, LetterSideBarActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
        viewRv.setLayoutManager(new LinearLayoutManager(ViewActivity.this));
        viewRv.setAdapter(adapter);
    }
}
