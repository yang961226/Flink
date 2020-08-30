package com.example.flink.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.tools.HandlerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 底部切换日期的View
 */
public class SwitchDateView extends LinearLayout {

    @BindView(R.id.btn_lastDay)
    ImageView btn_lastDay;
    @BindView(R.id.btn_lastMonth)
    ImageView btn_lastMonth;
    @BindView(R.id.btn_today)
    ImageView btn_today;
    @BindView(R.id.btn_nextDay)
    ImageView btn_nextDay;
    @BindView(R.id.btn_nextMonth)
    ImageView btn_nextMonth;

    private OnSwitchDateBtnClickListener mOnSwitchDateBtnClickListener;

    //消息处理工具
    private HandlerUtils.HandlerHolder myHandler;

    public SwitchDateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.switchdate_view, this);
        //绑定处理
        ButterKnife.bind(this);
    }

    //设置回调
    public void setmOnSwitchDateBtnClickListener(OnSwitchDateBtnClickListener mOnSwitchDateBtnClickListener){
        this.mOnSwitchDateBtnClickListener=mOnSwitchDateBtnClickListener;
    }

    @OnClick({R.id.btn_lastDay,R.id.btn_lastMonth,R.id.btn_today,R.id.btn_nextDay,R.id.btn_nextMonth})
    public void onViewClick(View view){
        if(mOnSwitchDateBtnClickListener==null){
            return;
        }
        switch (view.getId()) {
            case R.id.btn_lastDay:
                mOnSwitchDateBtnClickListener.onLastDayBtnClick();
                break;
            case R.id.btn_lastMonth:
                mOnSwitchDateBtnClickListener.onLastMonthBtnClick();
                break;
            case R.id.btn_today:
                mOnSwitchDateBtnClickListener.onTodayBtnClick();
                break;
            case R.id.btn_nextDay:
                mOnSwitchDateBtnClickListener.onNextDayBtnClick();
                break;
            case R.id.btn_nextMonth:
                mOnSwitchDateBtnClickListener.onNextMonthBtnClick();
                break;
        }
    }

    public interface OnSwitchDateBtnClickListener {
        void onLastDayBtnClick();
        void onLastMonthBtnClick();
        void onTodayBtnClick();
        void onNextDayBtnClick();
        void onNextMonthBtnClick();
    }
}
