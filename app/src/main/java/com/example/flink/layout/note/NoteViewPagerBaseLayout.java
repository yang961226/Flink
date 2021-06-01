package com.example.flink.layout.note;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.mInterface.NoteFunctionClickListener;
import com.example.flink.mInterface.Unregister;

import org.greenrobot.eventbus.EventBus;


public abstract class NoteViewPagerBaseLayout extends LinearLayout implements Unregister, NoteFunctionClickListener {

    protected View rootView;

    public NoteViewPagerBaseLayout(Context context) {
        super(context);
        init(context);
    }

    public NoteViewPagerBaseLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NoteViewPagerBaseLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        rootView = LayoutInflater.from(context).inflate(getLayoutResId(), this);
        EventBus.getDefault().register(this);
    }

    protected abstract int getLayoutResId();

    @Override
    public void unregister() {
        EventBus.getDefault().unregister(this);
    }
}
