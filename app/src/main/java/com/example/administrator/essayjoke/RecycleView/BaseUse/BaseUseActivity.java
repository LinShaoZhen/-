package com.example.administrator.essayjoke.RecycleView.BaseUse;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.essayjoke.R;
import com.example.administrator.essayjoke.RecycleView.CommonAdapter.ItemClickListener;
import com.example.administrator.essayjoke.RecycleView.CommonAdapter.ItemLongClickListener;
import com.example.administrator.essayjoke.RecycleView.CommonAdapter.ViewHolder;
import com.example.administrator.essayjoke.RecycleView.wrap.WrapRecyclerAdapter;
import com.example.administrator.essayjoke.RecycleView.wrap.WrapRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by nana on 2018/9/17.
 */

public class BaseUseActivity extends AppCompatActivity {
    private WrapRecyclerView mRecyclerView;
    private List<String> mDatas;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_use);
        initData();
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        //滑动
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //获取响应的方向 包含两个1.拖动dragFlags 2.侧滑删除swipeFlags
                int swipeFlags = ItemTouchHelper.LEFT;
                //拖动暂不处理，默认为0
                int dragFlags=ItemTouchHelper.UP|ItemTouchHelper.DOWN;
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager){
                    dragFlags=ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT|ItemTouchHelper.UP|ItemTouchHelper.DOWN;
                }
                return makeMovementFlags(dragFlags, swipeFlags);
            }
            //在拖动过程中不断回调
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //获取原来位置
                int framPosition= viewHolder.getAdapterPosition();
                //得到目标位置
                int targetPosition=target.getAdapterPosition();
                //需要不断的替换位置，需要替换当前位置
                //只是位置发生了变化
                mAdapter.notifyItemMoved(framPosition,targetPosition);
                //数据也需要更新
                if (framPosition>targetPosition){
                    for (int i=framPosition;i<targetPosition;i++){
                        Collections.swap(mDatas,i,i+1);//改变实际的数据集
                    }
                }else {
                    for (int i=framPosition;i>targetPosition;i--){
                        Collections.swap(mDatas,i,i-1);//改变实际的数据集
                    }
                }
                return false;
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                //状态发生改变 拖动状态  侧滑状态 正常状态
                if (actionState!=ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.GRAY);
                }
            }
            //动画执行完毕
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
                ViewCompat.setTranslationX(viewHolder.itemView,0);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //侧滑删除执行完毕后会回调这个方法，需要移除item然后更新列表
                int currentSwipePosition = viewHolder.getAdapterPosition();
              /*  mDatas.remove(currentSwipePosition);
                mAdapter.notifyDataSetChanged();*/
                mAdapter.notifyItemRemoved(currentSwipePosition);
                mDatas.remove(currentSwipePosition);
            }
        });
        //必须绑定
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


        final View view = LayoutInflater.from(this).inflate(R.layout.layout_header_footer, mRecyclerView, false);
        //   mRecyclerView.addHeaderView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.removeHeaderView(v);
            }
        });

        mAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void setOnClickListener(int position) {
                Toast.makeText(BaseUseActivity.this, mDatas.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        //添加分割线
        mRecyclerView.addItemDecoration(new GridLayoutItemDecoation(BaseUseActivity.this, R.drawable.item_dirver_01));
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                break;
            case R.id.id_action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
        }
        return true;
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i <= 'Z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
