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


/**
 * 底部切换日期的View
 */
public class SwitchDateLayout extends LinearLayout {

    private OnSwitchDateListener mOnSwitchDateListener;

    public SwitchDateLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.layout_switch_date, this);
        findViewById(view);
    }

    private void findViewById(View view) {
        ImageView btnLastDay = view.findViewById(R.id.btn_lastDay);
        ImageView btnLastMonth = view.findViewById(R.id.btn_lastMonth);
        ImageView btnToday = view.findViewById(R.id.btn_today);
        ImageView btnNextDay = view.findViewById(R.id.btn_nextDay);
        ImageView btnNextMonth = view.findViewById(R.id.btn_nextMonth);

        OnClickListener onClickListener = view1 -> {
            if (mOnSwitchDateListener == null) {
                Toast.makeText(getContext(), MyConstants.UNBIND_LISTENER, Toast.LENGTH_LONG).show();
                return;
            }
            int id = view1.getId();
            if (id == R.id.btn_lastDay) {
                mOnSwitchDateListener.onLastDayBtnClick();
            } else if (id == R.id.btn_lastMonth) {
                mOnSwitchDateListener.onLastMonthBtnClick();
            } else if (id == R.id.btn_today) {
                mOnSwitchDateListener.onTodayBtnClick();
            } else if (id == R.id.btn_nextDay) {
                mOnSwitchDateListener.onNextDayBtnClick();
            } else if (id == R.id.btn_nextMonth) {
                mOnSwitchDateListener.onNextMonthBtnClick();
            }
        };

        btnLastDay.setOnClickListener(onClickListener);
        btnLastMonth.setOnClickListener(onClickListener);
        btnToday.setOnClickListener(onClickListener);
        btnNextDay.setOnClickListener(onClickListener);
        btnNextMonth.setOnClickListener(onClickListener);
    }

    //设置回调
    public void setmOnSwitchDateListener(OnSwitchDateListener mOnSwitchDateListener) {
        this.mOnSwitchDateListener = mOnSwitchDateListener;
    }

    public interface OnSwitchDateListener {
        void onLastDayBtnClick();

        void onLastMonthBtnClick();

        void onTodayBtnClick();

        void onNextDayBtnClick();

        void onNextMonthBtnClick();
    }
}
