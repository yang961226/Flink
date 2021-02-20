package com.example.flink.event;

/**
 * 日期改变事件已经不再传对应的日期，因为当前选中的日期已经通过DateUtil的getNowSelectedDate()方法获取
 */
public class DateChangeEvent {

    private final String originClassName;//发送这个事件的对象的类名（用于拒收自己发出的事件）

    public DateChangeEvent() {
        originClassName = "";
    }

    public DateChangeEvent(String originClassName) {
        this.originClassName = originClassName;
    }

    public String getOriginClassName() {
        return originClassName;
    }
}
