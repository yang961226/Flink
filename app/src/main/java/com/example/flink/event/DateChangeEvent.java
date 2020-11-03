package com.example.flink.event;

import java.util.Date;

public class DateChangeEvent {

    private final Date mDate;

    public DateChangeEvent(Date date){
        mDate=date;
    }

    public Date getDate(){
        return mDate;
    }

}
