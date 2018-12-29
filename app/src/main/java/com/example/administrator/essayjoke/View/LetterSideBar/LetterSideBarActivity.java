package com.example.administrator.essayjoke.View.LetterSideBar;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.essayjoke.R;

public class LetterSideBarActivity extends AppCompatActivity implements LetterSideBar.LetterTouchListener {
    private TextView mLetterTv;
    private LetterSideBar mLetterSideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_side_bar);
        mLetterTv=findViewById(R.id.letter_tv);
        mLetterSideBar = findViewById(R.id.letter_side_bar);

        mLetterSideBar.setOnLetterTouchListener(this);
    }

    @Override
    public void touch(CharSequence letter,boolean isTouch) {
        if (isTouch) {
            mLetterTv.setVisibility(View.VISIBLE);
            mLetterTv.setText(letter);
        }else {
            mLetterTv.setVisibility(View.GONE);
        }
    }
}
