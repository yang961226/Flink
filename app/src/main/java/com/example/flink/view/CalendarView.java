package com.example.flink.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.event.DateChangeEvent;
import com.example.flink.mInterface.Unregister;
import com.example.flink.tools.DateUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarView extends LinearLayout implements Unregister {

    @BindView(R.id.day_of_week)
    AdaptationTextView dayOfWeek;
    @BindView(R.id.day_month_year)
    AdaptationTextView dayMonthYear;

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.layout_foxizz_calendar, this);
        //绑定处理
        ButterKnife.bind(this);

        setDateText(DateUtil.getNowSelectedDate());

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDateChange(DateChangeEvent dateChangeEvent) {
        setDateText(DateUtil.getNowSelectedDate());
    }

    private void setDateText(Date date) {
        dayOfWeek.setText(DateUtil.getWeekOfDateStr(date, DateUtil.WEEK_DAYS_CHI));
        StringBuilder dayMonthYearBuilder = new StringBuilder();
        dayMonthYearBuilder
                .append(DateUtil.getDayOfMonth(date)).append(" ")
                .append(DateUtil.getMonthChi(date)).append(" / ")
                .append(DateUtil.getYear(date));
        dayMonthYear.setText(dayMonthYearBuilder);
    }

    @Override
    public void unregister() {
        EventBus.getDefault().unregister(this);
    }
}
