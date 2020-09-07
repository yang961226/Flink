package com.example.flink.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.mInterface.AfterDateChangeCallBack;
import com.example.flink.mInterface.DateChangeEvent;
import com.example.flink.tools.DateUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 显示日历、选择日期的View
 */
public class CalendarSelectView extends LinearLayout implements DateChangeEvent {

    @BindView(R.id.btn_changeDate)
    Button btnChangeDate;
    @BindView(R.id.tv_testDate)
    TextView tvTestDate;

    private Date mDate;//目前的日期
    private AfterDateChangeCallBack mAfterDateChangeCallBack;

    public CalendarSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.calendar_select_view, this);
        //绑定处理
        ButterKnife.bind(this);
        init();
    }

    private void init() {

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
        Date dRandom = new Date(randomTime);
        mAfterDateChangeCallBack.notifyDateHasChange(dRandom);
    }

    @Override
    public void changeTo(Date date) {
        mDate=date;
        tvTestDate.setText(DateUtil.format(mDate));
    }

    @Override
    public void setAfterDateChangeCallBack(AfterDateChangeCallBack afterDateChangeCallBack) {
        mAfterDateChangeCallBack = afterDateChangeCallBack;
    }

}
