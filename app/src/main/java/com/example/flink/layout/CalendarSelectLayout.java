package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
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

    private Date mDate;//目前的日期
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

        mDate = DateUtil.getNowDate();
        initCalendarView();
    }

    private void initCalendarView() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendarview.setOnCalendarSelectListener(this);
        calendarview.scrollToCalendar(calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH));

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
        calendarview.scrollToCalendar(calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)
                , isNeedSommthScroll(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)));
    }

    /**
     * 判断接收到时间变更时间后，是否需要日历滚动到指定日期，如果只是天数发生变化，则不用
     *
     * @param toYear  目的年份
     * @param toMonth 目的月
     * @return true：需要滚动
     */
    private boolean isNeedSommthScroll(int toYear, int toMonth) {
        return toYear == calendarview.getCurYear() && toMonth == calendarview.getCurMonth();
    }

    @Override
    public void onCalendarSelect(com.haibin.calendarview.Calendar calendar, boolean isClick) {
        if (isClick) {
            EventBus.getDefault().post(new DateChangeEvent(
                    getDateByYMD(calendar.getYear(), calendar.getMonth(), calendar.getDay()), getClass().getName()));
        }
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
