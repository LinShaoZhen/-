package com.example.administrator.essayjoke.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewAnimator;

import com.example.administrator.essayjoke.R;
import com.example.administrator.essayjoke.indicator.ColorTrackTextView;

/**
 * Created by nana on 2018/9/12.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Button leftToRight, rightToLeft;
    private ColorTrackTextView trackTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        leftToRight = view.findViewById(R.id.leftToRight);
        rightToLeft = view.findViewById(R.id.rightToLeft);
        trackTv = view.findViewById(R.id.color_track_tv);
        leftToRight.setOnClickListener(this);
        rightToLeft.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leftToRight:
                trackTv.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                ValueAnimator animator = ObjectAnimator.ofFloat(0, 1);
                animator.setDuration(2000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentProgress = (float) animation.getAnimatedValue();
                        trackTv.setCurrentProgress(currentProgress);
                    }
                });
                animator.start();
                break;
            case R.id.rightToLeft:
                trackTv.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                animator = ObjectAnimator.ofFloat(0, 1);
                animator.setDuration(2000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentProgress = (float) animation.getAnimatedValue();
                        trackTv.setCurrentProgress(currentProgress);

                    }
                });
                animator.start();
                break;
        }

    }
}
