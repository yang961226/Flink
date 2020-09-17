package com.example.flink.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.mInterface.AfterDateChangeCallBack;
import com.example.flink.mInterface.DateChangeEvent;
import com.example.flink.tools.DateUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoxizzCalendarView extends LinearLayout implements DateChangeEvent {

    @BindView(R.id.day_of_week)
    AdaptationTextView dayOfWeek;
    @BindView(R.id.day_month_year)
    AdaptationTextView dayMonthYear;

    private Date mDate;

    public FoxizzCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.foxizz_calendar_view, this);
        //绑定处理
        ButterKnife.bind(this);
    }

    @Override
    public void changeTo(Date date) {
        mDate=date;

        dayOfWeek.setText(DateUtil.getWeekOfDateStr(mDate, DateUtil.WEEK_DAYS_ENGLISH));
        StringBuilder dayMonthYearBuilder = new StringBuilder();
        dayMonthYearBuilder
                .append(DateUtil.getDayOfMonth(mDate)).append(" ")
                .append(DateUtil.getMonthEnglish(mDate)).append(" / ")
                .append(DateUtil.getYear(mDate));
        dayMonthYear.setText(dayMonthYearBuilder);
    }

    @Override
    public void setAfterDateChangeCallBack(AfterDateChangeCallBack afterDateChangeCallBack) {
        //do nothing
    }
}
