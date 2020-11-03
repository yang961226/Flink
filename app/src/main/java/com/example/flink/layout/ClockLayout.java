package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.event.TickEvent;
import com.example.flink.mInterface.UnregisterEventBus;
import com.example.flink.tools.DateUtil;
import com.example.flink.view.AdaptationTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockLayout extends LinearLayout implements UnregisterEventBus {

    @BindView(R.id.testNowTime)
    AdaptationTextView tvNowTime;

    public ClockLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.layout_clock, this);
        //绑定处理
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent tickEvent){
        tvNowTime.setText(DateUtil.format(tickEvent.getDate(),DateUtil.FORMAT_LONG));
    }

    @Override
    public void unregister() {
        EventBus.getDefault().unregister(this);
    }
}
