package com.example.flink.note;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.flink.Builder.MsgBuilder;
import com.example.flink.NoteBaseActivity;
import com.example.flink.R;
import com.example.flink.common.MyConstants;
import com.example.flink.mInterface.OnCalendarSelectListener;
import com.example.flink.mInterface.OnDateChangeListener;
import com.example.flink.tools.Tools;
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

    private OnDateChangeListener mOnDateChangeListener;
    private OnCalendarSelectListener mOnCalendarSelectListener;

    @Override
    protected void initView() {
        super.initView();
        setCallBack();
    }

    @Override
    protected void initTop() {
        //初始化左上角
        LinearLayout topLeftLL = buildTopLeftView();
        llTop.addView(topLeftLL);
        mOnDateChangeListener = (OnDateChangeListener) topLeftLL;

        //初始化右上角
        LinearLayout topRightLL = buildTopRightView();
        llTop.addView(topRightLL);
    }

    @Override
    protected void initCenter() {

    }

    @Override
    protected void initBottom() {
        LinearLayout calendarSelectView = buildDateSelectView();
        llBottom.addView(calendarSelectView, 0);
        mOnCalendarSelectListener = (OnCalendarSelectListener) calendarSelectView;
    }

    @Override
    protected void initData() {

    }

    private void setCallBack() {
        //当最底部切换日期的View按钮被点击的时候，会触发日历对应的事件，改变日历的时间
        switchDateView.setmOnSwitchDateListener(new SwitchDateView.OnSwitchDateListener() {
            @Override
            public void onLastDayBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_LAST_DAY.hashCode()).makeMsg());
            }

            @Override
            public void onLastMonthBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_LAST_MONTH.hashCode()).makeMsg());
            }

            @Override
            public void onTodayBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_TODAY.hashCode()).makeMsg());
            }

            @Override
            public void onNextDayBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_NEXT_DAY.hashCode()).makeMsg());
            }

            @Override
            public void onNextMonthBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_NEXT_MONTH.hashCode()).makeMsg());
            }
        });

        //当日历切换日期的时候，会触发当前日期View对应的事件，改变当前日期
        mOnCalendarSelectListener.setAfterDateChangeCallBack(date -> mOnDateChangeListener.changeTo(date));

    }

    @Override
    public void flinkMessageCallBack(Message msg) {
        if (msg.what == MyConstants.CLICK_BTN_LAST_DAY.hashCode()) {
            mOnCalendarSelectListener.onDayChange(-1);
        } else if (msg.what == MyConstants.CLICK_BTN_LAST_MONTH.hashCode()) {
            mOnCalendarSelectListener.onMonthChange(-1);
        } else if (msg.what == MyConstants.CLICK_BTN_TODAY.hashCode()) {
            mOnCalendarSelectListener.onDateChange(new Date());// TODO: 2020/9/1 以后要修改成不是new的版本，不然每次都要New一个Date类，很浪费内存
        } else if (msg.what == MyConstants.CLICK_BTN_NEXT_DAY.hashCode()) {
            mOnCalendarSelectListener.onDayChange(1);
        } else if (msg.what == MyConstants.CLICK_BTN_NEXT_MONTH.hashCode()) {
            mOnCalendarSelectListener.onMonthChange(1);
        }
    }

    // TODO: 2020/9/1 以后改成反射
    private LinearLayout buildDateSelectView() {
        CalendarSelectView calendarSelectView = new CalendarSelectView(this, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        calendarSelectView.setLayoutParams(layoutParams);
        return calendarSelectView;
    }

    private LinearLayout buildTopLeftView() {
        LinearLayout topLeftView = new LinearLayout(this, null);
        try {
            Class<?> clazz = Class.forName(Tools.getString(this, R.string.CalendarView));
            Constructor<?> constructor = clazz.getDeclaredConstructor(Context.class, AttributeSet.class);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            topLeftView = (LinearLayout) constructor.newInstance(this, null);
            topLeftView.setLayoutParams(layoutParams);
        } catch (Exception e) {
            Log.e(this.getClass().getName(), MyConstants.CLASS_CONFIG_ERROR);
        }
        return topLeftView;
    }

    private LinearLayout buildTopRightView() {
        LinearLayout topRightView = new LinearLayout(this, null);
        try {
            Class<?> clazz = Class.forName(Tools.getString(this, R.string.ClockView));
            Constructor<?> constructor = clazz.getDeclaredConstructor(Context.class, AttributeSet.class);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            topRightView = (LinearLayout) constructor.newInstance(this, null);
            topRightView.setLayoutParams(layoutParams);
        } catch (Exception e) {
            Log.e(this.getClass().getName(), MyConstants.CLASS_CONFIG_ERROR);
        }
        return topRightView;
    }
}
