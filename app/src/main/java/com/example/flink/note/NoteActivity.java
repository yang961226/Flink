package com.example.flink.note;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.flink.FlickPagerAdapter;
import com.example.flink.NoteBaseActivity;
import com.example.flink.R;
import com.example.flink.common.MyConstants;
import com.example.flink.common.MyException;
import com.example.flink.event.DateChangeEvent;
import com.example.flink.event.TickEvent;
import com.example.flink.mInterface.UnregisterEventBus;
import com.example.flink.tools.DateUtil;
import com.example.flink.tools.PopUpWindowHelper;
import com.example.flink.tools.ViewTools;
import com.example.flink.view.NavigationBarView;
import com.example.flink.view.SwitchDateView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class NoteActivity extends NoteBaseActivity {

    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    @BindView(R.id.ll_top)
    LinearLayout llTop;

    @BindView(R.id.ll_center)
    LinearLayout llCenter;

    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    @BindView(R.id.switchDateView)
    SwitchDateView switchDateView;

    @BindView(R.id.btn_setting)
    ImageView btnSetting;

    @BindView(R.id.btn_calendarSelectPopUp)
    ImageView btn_calendarSelectPopUp;

    private static final int VIEW_PAGER_ID=5242;

    private ViewGroup topLeftViewGroup;//左上角布局
    private ViewGroup topRightViewGroup;//右上角布局
    private ViewGroup calendarSelectView;//点击右下角按钮弹出的日历选择器
    private NavigationBarView navBarView;//笔记导航条

    private Date mDate;//当前日期

    private PopUpWindowHelper popUpWindowHelper;

    private boolean isPopupCalendar=false;//日期选择器是否弹出来

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private List<View> noteViewList;

    @Override
    protected void initData() {
        mDate=DateUtil.getNowDate();
        EventBus.getDefault().post(new DateChangeEvent(mDate));
        noteViewList=new ArrayList<>();
    }

    @Override
    protected void initView() {
        super.initView();
        setCallBack();
        EventBus.getDefault().post(new DateChangeEvent(DateUtil.getNowDate()));
    }

    @OnClick({R.id.btn_setting,R.id.btn_calendarSelectPopUp})
    @Override
    protected void onBtnClick(View view) {
        switch (view.getId()){
            case R.id.btn_setting:
                // TODO: 2020/9/3 跳转到设置页
                showToast("跳转到设置页面");
                break;
            case R.id.btn_calendarSelectPopUp:
                popupCalendar(isPopupCalendar);
                isPopupCalendar=!isPopupCalendar;
                break;
        }
    }

    /**
     * 开启时钟
     */
    private void startToTick(){
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
        topLeftViewGroup = ViewTools.buildCalendarView(this);
        //如果没有继承这个接口，说明这个View没有按要求去做
        if(topLeftViewGroup ==null){
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR);
        }
        llTop.addView(topLeftViewGroup);

        //初始化右上角
        topRightViewGroup = ViewTools.buildClockView(this);
        if(topRightViewGroup == null){
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR);
        }
        llTop.addView(topRightViewGroup);
        startToTick();
    }

    @Override
    protected void initCenter() {
        initViewPager();
        navBarView=ViewTools.buildNavBarView(this);
        navBarView.init(NavigationBarView.NavigationBarViewConfig
                .create()
                .setItemNum(3)// TODO: 2020/9/10 现在是写死3个，后面这个值要改成根据实际页数来填写 
                .setDefaultSelectIndex(0));
        llCenter.addView(navBarView);
        llCenter.addView(mViewPager);

    }

    @Override
    protected void initBottom() {

    }

    private void initViewPager(){
        mViewPager=buildViewPager();
        mViewPager.setId(VIEW_PAGER_ID);

        noteViewList=ViewTools.buildNoteViewFunctions(this);
        mPagerAdapter=new FlickPagerAdapter(noteViewList);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               navBarView.selectTo(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPagerAdapter.notifyDataSetChanged();
    }

    private ViewPager buildViewPager(){
        ViewPager viewPager=new ViewPager(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,1);
        viewPager.setLayoutParams(layoutParams);
        viewPager.setOffscreenPageLimit(NavigationBarView.MAX_ITEM_NUM);//设置回收缓存，不要让ViewPager回收里面的碎片
        return viewPager;
    }

    @Override
    protected void initOther() {
        calendarSelectView =ViewTools.buildCalendarSelectView(this);
        if(calendarSelectView ==null ){
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR);
        }

        popUpWindowHelper=new PopUpWindowHelper
                .Builder(this)
                .setContentView(calendarSelectView)
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.PopupWindowTranslateThemeFromBottom)
                .setTouchable(true)
                .setFocusable(false)
                .setOutsideTouchable(false)
                .setBackgroundDrawable(new ColorDrawable(Color.WHITE))
                .build();
    }

    private void popupCalendar(boolean isPopupCalendar){
        if(isPopupCalendar){
            popUpWindowHelper.dismiss();
        }else{
            popUpWindowHelper.showPopupWindow(switchDateView, PopUpWindowHelper.LocationType.TOP_TEST);
        }
    }

    private void setCallBack() {
        //当最底部切换日期的按钮被点击的时候，通知View去更新自己的内容
        switchDateView.setmOnSwitchDateListener(new SwitchDateView.OnSwitchDateListener() {
            @Override
            public void onLastDayBtnClick() {
                Date tmpDate=DateUtil.addDay(mDate,-1);
                EventBus.getDefault().post(new DateChangeEvent(tmpDate));
                mDate=tmpDate;
            }

            @Override
            public void onLastMonthBtnClick() {
                Date tmpDate=DateUtil.addMonth(mDate,-1);
                EventBus.getDefault().post(new DateChangeEvent(tmpDate));
                mDate=tmpDate;
            }

            @Override
            public void onTodayBtnClick() {
                Date tmpDate=DateUtil.getNowDate();
                EventBus.getDefault().post(new DateChangeEvent(tmpDate));
                mDate=tmpDate;
            }

            @Override
            public void onNextDayBtnClick() {
                Date tmpDate=DateUtil.addDay(mDate,1);
                EventBus.getDefault().post(new DateChangeEvent(tmpDate));
                mDate=tmpDate;
            }

            @Override
            public void onNextMonthBtnClick() {
                Date tmpDate=DateUtil.addMonth(mDate,1);
                EventBus.getDefault().post(new DateChangeEvent(tmpDate));
                mDate=tmpDate;
            }
        });
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
    private void unregisterEventBus(){
        if(topLeftViewGroup instanceof UnregisterEventBus){
            ((UnregisterEventBus) topLeftViewGroup).unregister();
        }
        if(topRightViewGroup instanceof UnregisterEventBus){
            ((UnregisterEventBus) topRightViewGroup).unregister();
        }
    }
}
