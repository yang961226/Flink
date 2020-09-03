package com.example.flink.note;

import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.flink.Builder.MsgBuilder;
import com.example.flink.NoteBaseActivity;
import com.example.flink.R;
import com.example.flink.common.MyConstants;
import com.example.flink.mInterface.AfterDateSelectCallBack;
import com.example.flink.mInterface.CalendarSelectEvent;
import com.example.flink.mInterface.ClockEvent;
import com.example.flink.mInterface.DateChangeEvent;
import com.example.flink.tools.DateUtil;
import com.example.flink.tools.ViewBuilder;
import com.example.flink.view.CalendarSelectView;
import com.example.flink.view.SwitchDateView;

import java.util.Date;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

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

    @BindView(R.id.btn_setting)
    ImageView btnSetting;

    @BindView(R.id.btn_calendarSelectPopUp)
    ImageView btn_calendarSelectPopUp;

    private DateChangeEvent mDateChangeEvent;
    private CalendarSelectEvent mCalendarSelectEvent;
    private ClockEvent mClockEvent;

    ViewGroup topLeftLL;
    ViewGroup topRightLL;
    ViewGroup calendarSelectView;

    private boolean isHideCalendarSelectView=true;

    java.util.Timer clockTimer;

    @Override
    protected void initView() {
        super.initView();
        setCallBack();

        //此代码仅测试用，之后会删除
        llCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalendarSelectViewVisibility(true);
            }
        });
    }
    
    @OnClick({R.id.btn_setting,R.id.btn_calendarSelectPopUp})
    @Override
    protected void onBtnClick(View view) {
        switch (view.getId()){
            case R.id.btn_setting:
                // TODO: 2020/9/3 跳转到设置页
                showToast("跳转到设置页面");
                break;
            case R.id.btn_calendarSelectPopUp:
                setCalendarSelectViewVisibility(isHideCalendarSelectView);
                isHideCalendarSelectView=!isHideCalendarSelectView;
                break;
        }
    }

    /**
     * 设置日历页面的可见
     * @param isHide 是否隐藏
     */
    private void setCalendarSelectViewVisibility(boolean isHide){
        if(isHide){
            calendarSelectView.setVisibility(View.GONE);
        }else{
            calendarSelectView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initTop() {
        //初始化左上角
        topLeftLL= ViewBuilder.buildCalendarView(this);
        //如果没有继承这个接口，说明这个View没有按要求去做
        if(!(topLeftLL instanceof DateChangeEvent)){
            showToast(MyConstants.CLASS_CONFIG_ERROR);
            return;
        }
        llTop.addView(topLeftLL);
        mDateChangeEvent = (DateChangeEvent) topLeftLL;

        //初始化右上角
        topRightLL= ViewBuilder.buildClockView(this);
        if(!(topRightLL instanceof ClockEvent)){
            showToast(MyConstants.CLASS_CONFIG_ERROR);
            return;
        }
        llTop.addView(topRightLL);
        mClockEvent=(ClockEvent)topRightLL;
    }

    @Override
    protected void initCenter() {

    }

    @Override
    protected void initBottom() {
        //初始化日历页面
        calendarSelectView= ViewBuilder.buildCalendarSelectView(this);
        setCalendarSelectViewVisibility(true);
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
        clockTimer = new java.util.Timer(true);
        TimerTask clockTask = new TimerTask() {
            public void run() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLOCK_TICK.hashCode()).setObj(DateUtil.getNowDate()).makeMsg());
            }
        };

        clockTimer.schedule(clockTask, 0, 1000);
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
                mCalendarSelectEvent.selectDateTo(DateUtil.getNowDate());
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
        if(msg.what==MyConstants.CLOCK_TICK.hashCode()){
            mClockEvent.tick((Date)msg.obj);
        }
    }

}
