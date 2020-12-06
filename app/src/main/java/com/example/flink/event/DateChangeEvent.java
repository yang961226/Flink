package com.example.flink.event;

import java.util.Date;

public class DateChangeEvent {

    private final Date mDate;

    private final String originClassName;//发送这个事件的对象的类名（用于拒收自己发出的事件）

    public DateChangeEvent(Date date) {
        mDate = date;
        originClassName = "";
    }

    public DateChangeEvent(Date date, String originClassName) {
        mDate = date;
        this.originClassName = originClassName;
    }

    public Date getDate() {
        return mDate;
    }

    public String getOriginClassName() {
        return originClassName;
    }
}
