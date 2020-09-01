package com.example.flink.mInterface;

import java.util.Date;

/**
 * 日期模块监听器
 */
public interface OnCalendarSelectListener {
    boolean onDateChange(Date date);

    boolean onDayChange(int changeDays);

    boolean onMonthChange(int changeMonths);

    void setAfterDateChangeCallBack(AfterDateSelectCallBack afterDateSelectCallBack);
}
