package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.event.DateChangeEvent;
import com.example.flink.tools.DateUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 显示日历
 */
public class CalendarSelectLayout extends LinearLayout {

    @BindView(R.id.btn_changeDate)
    Button btnChangeDate;
    @BindView(R.id.tv_testDate)
    TextView tvTestDate;

    private Date mDate;//目前的日期

    public CalendarSelectLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.layout_calendar_select, this);
        //绑定处理
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_changeDate})
    void onViewClick(View view) {
        //随机日期
        long second = 1000;
        long minute = 60*second;
        long hour = minute * 60;
        long day = hour*24;
        long year = day * 365;
        long year2020Start  = (2020-1970) * year;
        long randomTime = (long) (Math.random()*(year-1) + year2020Start);
        mDate = new Date(randomTime);
        tvTestDate.setText(DateUtil.format(mDate));
        EventBus.getDefault().post(new DateChangeEvent(mDate));
    }

}
