package com.example.flink.event;

import com.example.flink.tools.DateUtil;

import java.util.Date;

public class TickEvent {

    private Date mDate;

    public TickEvent(Date date){
        mDate=date;
    }

    public TickEvent(){
        mDate= DateUtil.getNowDate();
    }

    public Date getDate(){
        return mDate;
    }

}
