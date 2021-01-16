package com.example.flink.note;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.flink.NoteBaseActivity;
import com.example.flink.R;
import com.example.flink.SettingsActivity;
import com.example.flink.adapter.FlinkPagerAdapter;
import com.example.flink.common.MyConstants;
import com.example.flink.event.DateChangeEvent;
import com.example.flink.event.TickEvent;
import com.example.flink.layout.NavigationBarLayout;
import com.example.flink.layout.NoteViewPagerBaseLayout;
import com.example.flink.layout.SwitchDateLayout;
import com.example.flink.mInterface.NoteFunctionClickListener;
import com.example.flink.mInterface.Unregister;
import com.example.flink.tools.DateUtil;
import com.example.flink.tools.ViewTools;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class NoteActivity extends NoteBaseActivity {

    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    @BindView(R.id.ll_top)
    LinearLayout llTop;

    @BindView(R.id.ll_center)
    LinearLayout llCenter;

    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    @BindView(R.id.switchDateLayout)
    SwitchDateLayout switchDateLayout;

    @BindView(R.id.btn_setting)
    ImageView btnSetting;

    @BindView(R.id.btn_function)
    ImageView btn_function;

    private static final int VIEW_PAGER_ID = 5242;

    private ViewGroup topLeftViewGroup;//左上角布局
    private ViewGroup topRightViewGroup;//右上角布局

    private NavigationBarLayout navBarLayout;//笔记导航条


    private ViewPager mViewPager;
    private List<NoteViewPagerBaseLayout> noteViewList;

    private int vpIndex = 0;//当前ViewPager页的索引

    @Override
    protected void initData() {
        noteViewList = new ArrayList<>();
    }

    @OnClick({R.id.btn_setting, R.id.btn_function})
    @Override
    protected void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_setting:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.btn_function:
                onFunctionClick(false);
                break;
        }
    }

    @OnLongClick({R.id.btn_function})
    protected void onBtnLongClick(View view) {
        if (view.getId() == R.id.btn_function) {
            onFunctionClick(true);
        }
    }

    private void onFunctionClick(boolean isLongClick) {
        View view = noteViewList.get(vpIndex);
        NoteFunctionClickListener noteFunctionClickListener;
        if (view == null) {
            showToast("当前页面配置错误，请联系作者");
            return;
        } else {
            noteFunctionClickListener = (NoteFunctionClickListener) view;
        }
        if (isLongClick) {
            noteFunctionClickListener.onLongClickFunction();
        } else {
            noteFunctionClickListener.onClickFunction();
        }
    }

    /**
     * 开启时钟
     */
    private void startToTick() {
        //每秒更新一次时钟View
        java.util.Timer clockTimer = new java.util.Timer(true);
        TimerTask clockTask = new TimerTask() {
            public void run() {
                EventBus.getDefault().post(new TickEvent(DateUtil.getNowDate()));
            }
        };
        clockTimer.schedule(clockTask, 0, 1000);
    }

    @Override
    protected void initTop() {
        //初始化左上角
        topLeftViewGroup = ViewTools.buildCalendarLayout(this);
        //如果没有继承这个接口，说明这个View没有按要求去做
        if (topLeftViewGroup == null) {
            throw new RuntimeException(MyConstants.CLASS_CONFIG_ERROR + ":笔记页左上角布局初始化失败");
        }
        llTop.addView(topLeftViewGroup);

        //初始化右上角
        topRightViewGroup = ViewTools.buildClockLayout(this);
        if (topRightViewGroup == null) {
            throw new RuntimeException(MyConstants.CLASS_CONFIG_ERROR + "笔记页右上角布局初始化失败");
        }
        llTop.addView(topRightViewGroup);
        startToTick();
    }

    @Override
    protected void initCenter() {
        initViewPager();
        navBarLayout = ViewTools.buildNavBarView(this);
        navBarLayout.init(NavigationBarLayout.NavigationBarViewConfig
                .create()
                .setItemNum(3)// TODO: 2020/9/10 现在是写死3个，后面这个值要改成根据实际页数来填写 
                .setDefaultSelectIndex(0));
        llCenter.addView(navBarLayout);
        llCenter.addView(mViewPager);

    }

    @Override
    protected void initBottom() {

    }

    private void initViewPager() {
        mViewPager = buildViewPager();
        mViewPager.setId(VIEW_PAGER_ID);

        noteViewList = ViewTools.buildNoteViewFunctions(this);
        PagerAdapter mPagerAdapter = new FlinkPagerAdapter(noteViewList);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navBarLayout.selectTo(position);
                vpIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPagerAdapter.notifyDataSetChanged();
    }

    private ViewPager buildViewPager() {
        ViewPager viewPager = new ViewPager(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        viewPager.setLayoutParams(layoutParams);
        viewPager.setOffscreenPageLimit(NavigationBarLayout.MAX_ITEM_NUM);//设置回收缓存，不要让ViewPager回收里面的碎片
        return viewPager;
    }

    @Override
    protected void initOther() {
        //当最底部切换日期的按钮被点击的时候，通知View去更新自己的内容
        switchDateLayout.setmOnSwitchDateListener(new SwitchDateLayout.OnSwitchDateListener() {
            @Override
            public void onLastDayBtnClick() {
                Date tmpDate = DateUtil.addDay(getNowDateFromDataManager(), -1);
                DateUtil.saveDateAsSelectedDate(tmpDate);
                EventBus.getDefault().post(new DateChangeEvent(tmpDate));
            }

            @Override
            public void onLastMonthBtnClick() {
                Date tmpDate = DateUtil.addMonth(getNowDateFromDataManager(), -1);
                DateUtil.saveDateAsSelectedDate(tmpDate);
                EventBus.getDefault().post(new DateChangeEvent(tmpDate));
            }

            @Override
            public void onTodayBtnClick() {
                Date tmpDate = DateUtil.getNowDate();
                DateUtil.saveDateAsSelectedDate(tmpDate);
                EventBus.getDefault().post(new DateChangeEvent(tmpDate));
            }

            @Override
            public void onNextDayBtnClick() {
                Date tmpDate = DateUtil.addDay(getNowDateFromDataManager(), 1);
                DateUtil.saveDateAsSelectedDate(tmpDate);
                EventBus.getDefault().post(new DateChangeEvent(tmpDate));
            }

            @Override
            public void onNextMonthBtnClick() {
                Date tmpDate = DateUtil.addMonth(getNowDateFromDataManager(), 1);
                DateUtil.saveDateAsSelectedDate(tmpDate);
                EventBus.getDefault().post(new DateChangeEvent(tmpDate));
            }
        });
    }

    private Date getNowDateFromDataManager() {
        return DateUtil.getNowSelectedDate();
    }

    @Override
    public void flinkMessageCallBack(Message msg) {

    }

    @Override
    protected void onDestroy() {
        unregisterEventBus();
        super.onDestroy();
    }

    /**
     * 将所有注册了eventBus的View(非activity)反注册
     */
    private void unregisterEventBus() {
        if (topLeftViewGroup instanceof Unregister) {
            ((Unregister) topLeftViewGroup).unregister();
        }
        if (topRightViewGroup instanceof Unregister) {
            ((Unregister) topRightViewGroup).unregister();
        }
        for (View view : noteViewList) {
            if (view instanceof Unregister) {
                ((Unregister) view).unregister();
            }
        }
    }
}
