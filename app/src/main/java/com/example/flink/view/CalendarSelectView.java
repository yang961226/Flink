package com.example.flink.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.mInterface.OnDateChangeListener;
import com.example.flink.mInterface.OnDateSelectListener;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 显示日历、选择日期的View
 */
public class CalendarSelectView extends LinearLayout implements OnDateSelectListener {

    @BindView(R.id.btn_changeDate)
    Button btnChangeDate;
    @BindView(R.id.et_test)
    EditText etTest;
    @BindView(R.id.tv_testDate)
    TextView tvTestDate;

    private Date mDate;
    private OnDateChangeListener mOnDateChangeListener;

    public CalendarSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.calendar_select_view, this);
        //绑定处理
        ButterKnife.bind(this);

        mDate=new Date();
    }

    public void setOnDateChangeListener(OnDateChangeListener onDateChangeListener){
        this.mOnDateChangeListener=onDateChangeListener;
    }

    @OnClick({R.id.btn_changeDate})
    void onViewClick(View view){

    }

    @Override
    public boolean onDateChange(Date date) {


        return true;
    }

    @Override
    public boolean onDayChange(int changeDays) {


        return true;
    }

    @Override
    public boolean onMonthChange(int changeMonths) {


        return true;
    }
}
