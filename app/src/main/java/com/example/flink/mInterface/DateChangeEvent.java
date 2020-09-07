package com.example.flink.mInterface;

import java.util.Date;

public interface DateChangeEvent {
    void changeTo(Date date);
    void setAfterDateChangeCallBack(AfterDateChangeCallBack afterDateChangeCallBack);
}
