package com.example.flink.builder;

import android.os.Message;

/**
 * Message Builder
 */
public class MsgBuilder {
    public static final String ACTION_CLICK = "点击";
    private int what = -1;
    private int arg1 = -1;
    private int arg2 = -1;
    private Object obj = null;
    private int viewId = -1;
    private int actionHashCode = ACTION_CLICK.hashCode();//默认是点击


    private MsgBuilder() {
        //屏蔽
    }

    public static MsgBuilder build() {
        return new MsgBuilder();
    }

    public MsgBuilder setViewId(int viewId) {
        this.viewId = viewId;
        return this;
    }

    public MsgBuilder setActionHashCode(int actionHashCode) {
        this.actionHashCode = actionHashCode;
        return this;
    }

    public MsgBuilder setActionHashCode(String actionStr) {
        this.actionHashCode = actionStr.hashCode();
        return this;
    }

    public MsgBuilder setWhat(int what) {
        this.what = what;
        return this;
    }

    public MsgBuilder setArg1(int arg1) {
        this.arg1 = arg1;
        return this;
    }

    public MsgBuilder setArg2(int arg2) {
        this.arg2 = arg2;
        return this;
    }

    public MsgBuilder setObj(Object obj) {
        this.obj = obj;
        return this;
    }

    public Message makeMsg() {
        Message msg = new Message();

        //如果设置了ViewId，msg的what则为Viewid和对应的事件
        if (viewId != -1) {
            msg.what = viewId + actionHashCode;
        } else {
            msg.what = this.what;
        }
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.obj = obj;
        return msg;
    }

    public int getWhat() {
        return what;
    }

    public int getArg1() {
        return arg1;
    }

    public int getArg2() {
        return arg2;
    }

    public Object getObj() {
        return obj;
    }

}