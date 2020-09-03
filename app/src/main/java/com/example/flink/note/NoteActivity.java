package com.example.flink.note;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.flink.NoteBaseActivity;
import com.example.flink.R;
import com.example.flink.common.MyConstants;
import com.example.flink.mInterface.AfterDateSelectCallBack;
import com.example.flink.mInterface.CalendarSelectEvent;
import com.example.flink.mInterface.DateChangeEvent;
import com.example.flink.tools.Tools;
import com.example.flink.tools.ViewBuilder;
import com.example.flink.view.CalendarSelectView;
import com.example.flink.view.SwitchDateView;

import java.lang.reflect.Constructor;
import java.util.Date;

import butterknife.BindView;

public class NoteActivity extends NoteBaseActivity {

    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    @BindView(R.id.ll_top)
    LinearLayout llTop;

    @BindView(R.id.ll_center)
    LinearLayout llCenter;

    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    @BindView(R.id.switchDateView)
    SwitchDateView switchDateView;

    private DateChangeEvent mDateChangeEvent;
    private CalendarSelectEvent mCalendarSelectEvent;

    @Override
    protected void initView() {
        super.initView();
        setCallBack();
    }

    @Override
    protected void initTop() {
        //初始化左上角
        LinearLayout topLeftLL = ViewBuilder.buildCalendarView(this);
        llTop.addView(topLeftLL);
        //如果没有继承这个接口，说明这个View没有按要求去做
        if(!(topLeftLL instanceof DateChangeEvent)){
            showToast(MyConstants.CLASS_CONFIG_ERROR);
            return;
        }
        mDateChangeEvent = (DateChangeEvent) topLeftLL;

        //初始化右上角
        LinearLayout topRightLL = ViewBuilder.buildClockView(this);
        llTop.addView(topRightLL);
    }

    @Override
    protected void initCenter() {

    }

    @Override
    protected void initBottom() {
        //初始化日历页面
        LinearLayout calendarSelectView = ViewBuilder.buildCalendarSelectView(this);
        llBottom.addView(calendarSelectView, 0);
        //如果没有继承这个接口，说明这个View没有按要求去做
        if(!(calendarSelectView instanceof CalendarSelectView)){
            showToast(MyConstants.CLASS_CONFIG_ERROR);
            return;
        }
        mCalendarSelectEvent = (CalendarSelectEvent) calendarSelectView;
    }

    @Override
    protected void initData() {

    }

    private void setCallBack() {
        //当最底部切换日期的按钮被点击的时候，会触发日历View对应的事件，改变日历的时间
        switchDateView.setmOnSwitchDateListener(new SwitchDateView.OnSwitchDateListener() {
            @Override
            public void onLastDayBtnClick() {
                mCalendarSelectEvent.onDayChange(-1);
            }

            @Override
            public void onLastMonthBtnClick() {
                mCalendarSelectEvent.onMonthChange(-1);
            }

            @Override
            public void onTodayBtnClick() {
                mCalendarSelectEvent.selectDateTo(new Date());
            }

            @Override
            public void onNextDayBtnClick() {
                mCalendarSelectEvent.onDayChange(1);
            }

            @Override
            public void onNextMonthBtnClick() {
                mCalendarSelectEvent.onMonthChange(1);
            }
        });

        //当日历切换日期的时候，会触发当前日期View对应的事件，改变当前日期
        mCalendarSelectEvent.setAfterDateSelectCallBack(new AfterDateSelectCallBack() {
            @Override
            public void selectTo(Date date) {
                mDateChangeEvent.changeTo(date);
            }
        });

    }

    @Override
    public void flinkMessageCallBack(Message msg) {

    }

}
