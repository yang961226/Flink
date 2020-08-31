package com.example.flink.note;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.flink.Builder.MsgBuilder;
import com.example.flink.NoteBaseActivity;
import com.example.flink.R;
import com.example.flink.common.MyConstants;
import com.example.flink.mInterface.OnDateChangeListener;
import com.example.flink.mInterface.OnDateSelectListener;
import com.example.flink.tools.Tools;
import com.example.flink.view.CalendarSelectView;
import com.example.flink.view.CalendarView;
import com.example.flink.view.ClockView;
import com.example.flink.view.SwitchDateView;

import java.lang.reflect.Constructor;

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

    @BindView(R.id.testTextView)
    TextView testTextView;

    private OnDateChangeListener mOnDateChangeListener;
    private OnDateSelectListener mOnDateSelectListener;

    @Override
    public void flinkMessageCallBack(Message msg) {
        testTextView.setText((String)msg.obj);
    }

    @Override
    protected void initView() {
        super.initView();
        setOnSwitchDateBtnClickListener();
    }

    @Override
    protected void initTop() {
        LinearLayout topLeftLL= getTopLeftView();
        llTop.addView(topLeftLL);
        mOnDateChangeListener=(OnDateChangeListener)topLeftLL;

        LinearLayout topRightLL=getTopRightView();
        llTop.addView(topRightLL);
    }

    @Override
    protected void initCenter() {

    }

    @Override
    protected void initBottom() {
        LinearLayout bottomLL=getDateSelectView();
        llBottom.addView(bottomLL,0);
        mOnDateSelectListener =(OnDateSelectListener)bottomLL;
    }

    @Override
    protected void initData() {

    }

    private void setOnSwitchDateBtnClickListener(){
        switchDateView.setmOnSwitchDateBtnClickListener(new SwitchDateView.OnSwitchDateBtnClickListener() {
            @Override
            public void onLastDayBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_LAST_DAY.hashCode()).setObj(MyConstants.CLICK_BTN_LAST_DAY).makeMsg());
            }

            @Override
            public void onLastMonthBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_LAST_MONTH.hashCode()).setObj(MyConstants.CLICK_BTN_LAST_MONTH).makeMsg());
            }

            @Override
            public void onTodayBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_TODAY.hashCode()).setObj(MyConstants.CLICK_BTN_TODAY).makeMsg());
            }

            @Override
            public void onNextDayBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_NEXT_DAY.hashCode()).setObj(MyConstants.CLICK_BTN_NEXT_DAY).makeMsg());
            }

            @Override
            public void onNextMonthBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_NEXT_MONTH.hashCode()).setObj(MyConstants.CLICK_BTN_NEXT_MONTH).makeMsg());
            }
        });
    }

    private LinearLayout getDateSelectView(){
        CalendarSelectView calendarSelectView=new CalendarSelectView(this,null);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        calendarSelectView.setLayoutParams(layoutParams);
        return calendarSelectView;
    }

    private LinearLayout getTopLeftView(){
        LinearLayout topLeftView=new LinearLayout(this,null);
        try{
            Class<?> clazz = Class.forName(Tools.getString(this,R.string.CalendarView));
            Constructor<?> constructor=clazz.getDeclaredConstructor(Context.class, AttributeSet.class);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1);
            topLeftView=(LinearLayout) constructor.newInstance(this,null);
            topLeftView.setLayoutParams(layoutParams);
        }catch (Exception e){
            Log.e(this.getClass().getName(),MyConstants.CLASS_CONFIG_ERROR);
        }
        return topLeftView;
    }

    private LinearLayout getTopRightView(){
        LinearLayout topRightView=new LinearLayout(this,null);
        try{
            Class<?> clazz = Class.forName(Tools.getString(this,R.string.ClockView));
            Constructor<?> constructor=clazz.getDeclaredConstructor(Context.class, AttributeSet.class);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1);
            topRightView=(LinearLayout) constructor.newInstance(this,null);
            topRightView.setLayoutParams(layoutParams);
        }catch (Exception e){
            Log.e(this.getClass().getName(),MyConstants.CLASS_CONFIG_ERROR);
        }
        return topRightView;
//        ClockView clockView=new ClockView(this,null);
//        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        clockView.setLayoutParams(layoutParams);
//        return clockView;
    }
}
