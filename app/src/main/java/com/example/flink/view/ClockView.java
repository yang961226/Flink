package com.example.flink.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.mInterface.ClockEvent;
import com.example.flink.tools.DateUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockView extends LinearLayout implements ClockEvent {

    @BindView(R.id.testNowTime)
    TextView tvNowTime;

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.clock_view, this);
        //绑定处理
        ButterKnife.bind(this);
    }

    @Override
    public void tick(Date date) {
        tvNowTime.setText(DateUtil.format(date,DateUtil.FORMAT_LONG));
    }
}
