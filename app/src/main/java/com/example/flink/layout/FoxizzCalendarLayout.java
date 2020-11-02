package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.event.DateChangeEvent;
import com.example.flink.mInterface.UnregisterEventBus;
import com.example.flink.tools.DateUtil;
import com.example.flink.view.AdaptationTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoxizzCalendarLayout extends LinearLayout implements UnregisterEventBus {

    @BindView(R.id.day_of_week)
    AdaptationTextView dayOfWeek;
    @BindView(R.id.day_month_year)
    AdaptationTextView dayMonthYear;

    private Date mDate;

    public FoxizzCalendarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.layout_foxizz_calendar_view, this);
        //绑定处理
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDateChange(DateChangeEvent dateChangeEvent){
        mDate=dateChangeEvent.getDate();
        dayOfWeek.setText(DateUtil.getWeekOfDateStr(mDate, DateUtil.WEEK_DAYS_ENGLISH));
        StringBuilder dayMonthYearBuilder = new StringBuilder();
        dayMonthYearBuilder
                .append(DateUtil.getDayOfMonth(mDate)).append(" ")
                .append(DateUtil.getMonthEnglish(mDate)).append(" / ")
                .append(DateUtil.getYear(mDate));
        dayMonthYear.setText(dayMonthYearBuilder);
    }

    @Override
    public void unregister() {
        EventBus.getDefault().unregister(this);
    }
}
