package com.example.administrator.essayjoke.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.essayjoke.R;
import com.example.administrator.essayjoke.RecycleView.BaseUse.BaseUseActivity;

/**
 * Created by nana on 2018/9/12.
 */

public class MessageFragment extends Fragment implements View.OnClickListener {
    private TextView mBaseUseTv;
    private TextView mListTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        mBaseUseTv = view.findViewById(R.id.baseuse_tv);
        mBaseUseTv.setOnClickListener(this);
        mListTv = view.findViewById(R.id.list_tv);
        mListTv.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.baseuse_tv:
                Intent intent = new Intent(getActivity(), BaseUseActivity.class);
                startActivity(intent);
                break;
            case R.id.list_tv:
                break;
        }


    }
}
