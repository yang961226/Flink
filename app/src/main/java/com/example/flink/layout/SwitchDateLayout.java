package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.common.MyConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 底部切换日期的View
 */
public class SwitchDateLayout extends LinearLayout {

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

    private OnSwitchDateListener mOnSwitchDateListener;

    public SwitchDateLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.layout_switch_date_view, this);
        //绑定处理
        ButterKnife.bind(this);
    }

    //设置回调
    public void setmOnSwitchDateListener(OnSwitchDateListener mOnSwitchDateListener) {
        this.mOnSwitchDateListener = mOnSwitchDateListener;
    }

    @OnClick({R.id.btn_lastDay,R.id.btn_lastMonth,R.id.btn_today,R.id.btn_nextDay,R.id.btn_nextMonth})
    public void onViewClick(View view){
        if (mOnSwitchDateListener == null) {
            Toast.makeText(getContext(), MyConstants.UNBIND_LISTENER, Toast.LENGTH_LONG).show();
            return;
        }
        switch (view.getId()) {
            case R.id.btn_lastDay:
                mOnSwitchDateListener.onLastDayBtnClick();
                break;
            case R.id.btn_lastMonth:
                mOnSwitchDateListener.onLastMonthBtnClick();
                break;
            case R.id.btn_today:
                mOnSwitchDateListener.onTodayBtnClick();
                break;
            case R.id.btn_nextDay:
                mOnSwitchDateListener.onNextDayBtnClick();
                break;
            case R.id.btn_nextMonth:
                mOnSwitchDateListener.onNextMonthBtnClick();
                break;
        }
    }

    public interface OnSwitchDateListener {
        void onLastDayBtnClick();

        void onLastMonthBtnClick();

        void onTodayBtnClick();

        void onNextDayBtnClick();

        void onNextMonthBtnClick();
    }
}
