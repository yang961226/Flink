package com.example.flink.event;

import java.util.Date;

/**
 * 本事件控制日历框架的标记
 */
public class SchemeChangeEvent {

    private final Date mDate;
    private final boolean isAdd;

    /**
     * @param date  标记发生变化的某天
     * @param isAdd true:增加标记 false:删除标记
     */
    public SchemeChangeEvent(Date date, boolean isAdd) {
        mDate = date;
        this.isAdd = isAdd;
    }

    public Date getDate() {
        return mDate;
    }

    public boolean isAdd() {
        return isAdd;
    }
}
