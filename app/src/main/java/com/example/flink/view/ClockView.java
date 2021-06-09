package com.example.flink.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.event.DateChangeEvent;
import com.example.flink.event.TickEvent;
import com.example.flink.mInterface.Unregister;
import com.example.flink.tools.DateUtil;
import com.example.flink.tools.data.ConfigurationHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.flink.tools.DateUtil.FORMAT_Hms;
import static com.example.flink.tools.DateUtil.FORMAT_hms;
import static com.example.flink.tools.data.ConfigurationHelper.KEY_USE_24_SYS;

public class ClockView extends LinearLayout implements Unregister {

    private static final int[] IAMGE_NUM_SRC_IDS = {
            R.drawable.number_a_0,
            R.drawable.number_a_1,
            R.drawable.number_a_2,
            R.drawable.number_a_3,
            R.drawable.number_a_4,
            R.drawable.number_a_5,
            R.drawable.number_a_6,
            R.drawable.number_a_7,
            R.drawable.number_a_8,
            R.drawable.number_a_9,
    };

    private AdaptationTextView isToday;
    private AdaptationTextView am_or_pm;

    private final List<ImageView> ivNumList = new ArrayList<>();

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.layout_clock, this);
        findViewById(view);
        EventBus.getDefault().register(this);
    }

    private void findViewById(View view) {
        isToday = view.findViewById(R.id.is_today);
        am_or_pm = view.findViewById(R.id.am_or_pm);
        ivNumList.add(view.findViewById(R.id.number_1));
        ivNumList.add(view.findViewById(R.id.number_2));
        ivNumList.add(view.findViewById(R.id.number_3));
        ivNumList.add(view.findViewById(R.id.number_4));
        ivNumList.add(view.findViewById(R.id.number_5));
        ivNumList.add(view.findViewById(R.id.number_6));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent tickEvent) {
        Date nowDate = tickEvent.getDate();
        boolean isAm = DateUtil.isAM(nowDate);
        String dateStr = "";
        if (ConfigurationHelper.getBooleanValue(KEY_USE_24_SYS)) {
            dateStr = DateUtil.format(nowDate, FORMAT_Hms);
            am_or_pm.setVisibility(GONE);
        } else {
            dateStr = DateUtil.format(nowDate, FORMAT_hms);
            am_or_pm.setVisibility(VISIBLE);
            am_or_pm.setText(isAm ? R.string.am_text : R.string.pm_text);
        }
        for (int i = 0; i < dateStr.length(); i++) {
            char num = dateStr.charAt(i);
            ivNumList.get(i).setImageResource(IAMGE_NUM_SRC_IDS[Integer.parseInt(num + "")]);
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDateChange(DateChangeEvent dateChangeEvent) {
        Date date = DateUtil.getNowSelectedDate();
        if (DateUtil.format(date).equals(DateUtil.format(DateUtil.getNowDate())))
            isToday.setVisibility(VISIBLE);
        else isToday.setVisibility(INVISIBLE);
    }

    @Override
    public void unregister() {
        EventBus.getDefault().unregister(this);
    }

}
