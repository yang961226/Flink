package com.example.flink.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.flink.R;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarView extends LinearLayout {

    @BindView(R.id.tv_testDate)
    TextView tvTestDate;

    private Date mDate;

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.layout_calendar_view, this);
        //绑定处理
        ButterKnife.bind(this);
    }

}
