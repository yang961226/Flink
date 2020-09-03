package com.example.flink.mInterface;

import java.util.Date;

/**
 * 日期模块监听器
 */
public interface CalendarSelectEvent {
    void selectDateTo(Date date);

    void onDayChange(int changeDays);

    void onMonthChange(int changeMonths);

    void setAfterDateSelectCallBack(AfterDateSelectCallBack afterDateSelectCallBack);
}
