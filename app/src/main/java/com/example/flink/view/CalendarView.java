package com.example.flink.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.mInterface.DateChangeEvent;
import com.example.flink.tools.DateUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarView extends LinearLayout implements DateChangeEvent {

    @BindView(R.id.tv_testDate)
    TextView tvTestDate;

    private Date mDate;

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.calendar_view, this);
        //绑定处理
        ButterKnife.bind(this);
    }

    @Override
    public void changeTo(Date date) {
        mDate=date;
        tvTestDate.setText(DateUtil.format(mDate));
    }
}
