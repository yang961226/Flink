package com.example.flink.view;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.mInterface.AfterDateSelectCallBack;
import com.example.flink.mInterface.CalendarSelectEvent;
import com.example.flink.tools.DateUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 显示日历、选择日期的View
 */
public class CalendarSelectView extends LinearLayout implements CalendarSelectEvent {

    @BindView(R.id.btn_changeDate)
    Button btnChangeDate;
    @BindView(R.id.et_test)
    EditText etTest;
    @BindView(R.id.tv_testDate)
    TextView tvTestDate;

    private Date mDate;//目前的日期
    private AfterDateSelectCallBack mAfterDateSelectCallBack;

    public CalendarSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.calendar_select_view, this);
        //绑定处理
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mDate = getDefaultDate();
        //以下代码仅供测试阶段使用
        tvTestDate.setText(DateUtil.format(mDate));
    }

    @OnClick({R.id.btn_changeDate})
    void onViewClick(View view) {
        String dateInput = etTest.getText().toString();
        if (!DateUtil.checkDate(dateInput)) {
            Toast.makeText(getContext(), "日期不合法", Toast.LENGTH_LONG).show();
            return;
        }
        Date date=DateUtil.parse(dateInput);
        selectDateTo(date);
    }


    private Date getDefaultDate() {
        return new Date();
    }

    @Override
    public void selectDateTo(Date date) {
        mDate = date;//更新当前日期
        tvTestDate.setText(DateUtil.format(mDate));
        mAfterDateSelectCallBack.selectTo(date);//调用更新日期后的方法
    }

    @Override
    public void onDayChange(int changeDays) {
        selectDateTo(DateUtil.addDay(mDate, changeDays));
    }

    @Override
    public void onMonthChange(int changeMonths) {
        selectDateTo(DateUtil.addMonth(mDate, changeMonths));
    }

    @Override
    public void setAfterDateSelectCallBack(AfterDateSelectCallBack afterDateSelectCallBack) {
        mAfterDateSelectCallBack=afterDateSelectCallBack;
    }

}
