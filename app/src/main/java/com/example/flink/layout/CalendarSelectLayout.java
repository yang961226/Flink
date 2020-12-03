package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.R;

import java.util.Date;

import butterknife.ButterKnife;

/**
 * 显示日历
 */
public class CalendarSelectLayout extends LinearLayout {


    private Date mDate;//目前的日期

    public CalendarSelectLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.layout_calendar_select, this);
        //绑定处理
        ButterKnife.bind(this);
    }

}
