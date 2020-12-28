package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.event.DateChangeEvent;
import com.example.flink.mInterface.Unregister;
import com.example.flink.tools.DateUtil;
import com.haibin.calendarview.CalendarView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 显示日历
 */
public class CalendarSelectLayout extends LinearLayout implements CalendarView.OnCalendarSelectListener, Unregister {

    @BindView(R.id.calendarview)
    CalendarView calendarview;

    private Unbinder unbinder;

    public CalendarSelectLayout(Context context) {
        super(context);
        init(context);
    }

    public CalendarSelectLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.layout_calendar_select, this);
        //绑定处理
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initCalendarView();
    }

    private void initCalendarView() {
        calendarview.setOnCalendarSelectListener(this);
    }

    @Override
    public void onCalendarOutOfRange(com.haibin.calendarview.Calendar calendar) {
        //do nothing
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDateChange(DateChangeEvent dateChangeEvent) {
        //接收到自己发出的事件，忽略
        if (dateChangeEvent.getOriginClassName().equals(getClass().getName())) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateChangeEvent.getDate());
        Log.d("测试", "收到日期切换事件，切换日期" + DateUtil.format(dateChangeEvent.getDate()));
        calendarview.scrollToCalendar(calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH) + 1
                , calendar.get(Calendar.DAY_OF_MONTH), true);
    }

    @Override
    public void onCalendarSelect(com.haibin.calendarview.Calendar calendar, boolean isClick) {
        if (isClick) {
            EventBus.getDefault().post(new DateChangeEvent(
                    getDateByYMD(calendar.getYear(), calendar.getMonth(), calendar.getDay()), getClass().getName()));
            Log.d("测试", "切换日期，点击" + calendar.getYear() + "年" + calendar.getMonth() + "月" + calendar.getDay() + "日");
            DateUtil.saveNowSelectedDate(getContext(), getDateByCalendar(calendar));
        }
    }

    private Date getDateByCalendar(com.haibin.calendarview.Calendar calendar) {
        return getDateByYMD(calendar.getYear(), calendar.getMonth(), calendar.getDay());
    }

    private Date getDateByYMD(int year, int month, int day) {
        return DateUtil.parse(year, month, day);
    }

    @Override
    public void unregister() {
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
