package com.example.flink.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.R;

import butterknife.ButterKnife;

public class ClockView extends LinearLayout {
    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.clock_view, this);
        //绑定处理
        ButterKnife.bind(this);
    }
}
