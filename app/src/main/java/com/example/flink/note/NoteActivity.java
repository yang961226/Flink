package com.example.flink.note;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.flink.Builder.MsgBuilder;
import com.example.flink.NoteBaseActivity;
import com.example.flink.R;
import com.example.flink.common.MyConstants;
import com.example.flink.common.MyException;
import com.example.flink.mInterface.AfterDateChangeCallBack;
import com.example.flink.mInterface.ClockEvent;
import com.example.flink.mInterface.DateChangeEvent;
import com.example.flink.tools.DateUtil;
import com.example.flink.tools.PopUpWindowHelper;
import com.example.flink.tools.ViewTools;
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

    private DateChangeEvent mCalendarDateChangeEvent;
    private DateChangeEvent mTopLeftLLDateChangeEvent;
    private ClockEvent mTopRightLLDateChangeEvent;

    ViewGroup topLeftLL;
    ViewGroup topRightLL;

    private Date mDate;

    private java.util.Timer clockTimer;

    private PopUpWindowHelper popUpWindowHelper;

    private boolean isPopupCalendar;

    @Override
    protected void initData() {
        mDate=DateUtil.getNowDate();
        isPopupCalendar=false;
    }

    @Override
    protected void initView() {
        super.initView();
        setCallBack();

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
                popupCalendar(isPopupCalendar);
                isPopupCalendar=!isPopupCalendar;
                break;
        }
    }

    /**
     * 开启时钟
     */
    private void startToTick(){
        //每秒更新一次时钟View
        clockTimer = new java.util.Timer(true);
        TimerTask clockTask = new TimerTask() {
            public void run() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLOCK_TICK.hashCode()).setObj(DateUtil.getNowDate()).makeMsg());
            }
        };
        clockTimer.schedule(clockTask, 0, 1000);
    }

    @Override
    protected void initTop() {
        //初始化左上角
        topLeftLL= ViewTools.buildCalendarView(this);
        //如果没有继承这个接口，说明这个View没有按要求去做
        if(!(topLeftLL instanceof DateChangeEvent)){
            showToast(MyConstants.CLASS_CONFIG_ERROR);
            return;
        }
        llTop.addView(topLeftLL);
        mTopLeftLLDateChangeEvent = (DateChangeEvent) topLeftLL;
        mTopLeftLLDateChangeEvent.changeTo(mDate);//初始化日期

        //初始化右上角
        topRightLL= ViewTools.buildClockView(this);
        if(!(topRightLL instanceof ClockEvent)){
            showToast(MyConstants.CLASS_CONFIG_ERROR);
            return;
        }
        llTop.addView(topRightLL);
        mTopRightLLDateChangeEvent =(ClockEvent)topRightLL;
        startToTick();
    }

    @Override
    protected void initCenter() {

    }

    @Override
    protected void initBottom() {

    }

    @Override
    protected void initOther() {
        ViewGroup calendarView=ViewTools.buildCalendarSelectView(this);
        if(!(calendarView instanceof DateChangeEvent) ){
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR);
        }
        mCalendarDateChangeEvent=(DateChangeEvent)calendarView;
        mCalendarDateChangeEvent.changeTo(mDate);//初始化日期
        popUpWindowHelper=new PopUpWindowHelper
                .Builder(this)
                .setContentView(calendarView)
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.PopupWindowTranslateThemeFromBottom)
                .setTouchable(true)
                .setFocusable(false)
                .setOutsideTouchable(false)
                .setBackgroundDrawable(new ColorDrawable(Color.WHITE))
                .build();
        mCalendarDateChangeEvent.setAfterDateChangeCallBack(date -> {
            mTopLeftLLDateChangeEvent.changeTo(date);
            mCalendarDateChangeEvent.changeTo(date);
            mDate=date;
        });
    }

    private void popupCalendar(boolean isPopupCalendar){
        if(isPopupCalendar){
            popUpWindowHelper.dismiss();
        }else{
            popUpWindowHelper.showPopupWindow(switchDateView, PopUpWindowHelper.LocationType.TOP_CENTER);
        }
    }

    private void setCallBack() {
        //当最底部切换日期的按钮被点击的时候，通知View去更新自己的内容
        switchDateView.setmOnSwitchDateListener(new SwitchDateView.OnSwitchDateListener() {
            @Override
            public void onLastDayBtnClick() {
                Date tmpDate=DateUtil.addDay(mDate,-1);
                mTopLeftLLDateChangeEvent.changeTo(tmpDate);
                mCalendarDateChangeEvent.changeTo(tmpDate);
                mDate=tmpDate;
            }

            @Override
            public void onLastMonthBtnClick() {
                Date tmpDate=DateUtil.addMonth(mDate,-1);
                mTopLeftLLDateChangeEvent.changeTo(tmpDate);
                mCalendarDateChangeEvent.changeTo(tmpDate);
                mDate=tmpDate;
            }

            @Override
            public void onTodayBtnClick() {
                Date tmpDate=DateUtil.getNowDate();
                mTopLeftLLDateChangeEvent.changeTo(tmpDate);
                mCalendarDateChangeEvent.changeTo(tmpDate);
                mDate=tmpDate;
            }

            @Override
            public void onNextDayBtnClick() {
                Date tmpDate=DateUtil.addDay(mDate,1);
                mTopLeftLLDateChangeEvent.changeTo(tmpDate);
                mCalendarDateChangeEvent.changeTo(tmpDate);
                mDate=tmpDate;
            }

            @Override
            public void onNextMonthBtnClick() {
                Date tmpDate=DateUtil.addMonth(mDate,1);
                mTopLeftLLDateChangeEvent.changeTo(tmpDate);
                mCalendarDateChangeEvent.changeTo(tmpDate);
                mDate=tmpDate;
            }
        });

    }

    @Override
    public void flinkMessageCallBack(Message msg) {
        if(msg.what==MyConstants.CLOCK_TICK.hashCode()){
            mTopRightLLDateChangeEvent.tick((Date)msg.obj);
        }
    }

}
