package com.example.flink.layout.note;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.mInterface.NoteFunctionClickListener;
import com.example.flink.mInterface.Unregister;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class NoteViewPagerBaseLayout extends LinearLayout implements Unregister, NoteFunctionClickListener {

    private Unbinder unbinder;

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
        View.inflate(context, getLayoutResId(), this);
        //绑定处理
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    protected abstract int getLayoutResId();

    @Override
    public void unregister() {
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
