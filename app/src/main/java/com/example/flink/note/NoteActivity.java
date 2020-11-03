package com.example.flink.note;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.example.flink.layout.NavigationBarLayout;
import com.example.flink.layout.PopupInputLayout;
import com.example.flink.layout.SwitchDateLayout;
import com.example.flink.mInterface.UnregisterEventBus;
import com.example.flink.tools.DateUtil;
import com.example.flink.tools.PopUpWindowHelper;
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

    private static final int VIEW_PAGER_ID=5242;

    private ViewGroup topLeftViewGroup;//左上角布局
    private ViewGroup topRightViewGroup;//右上角布局
    private ViewGroup calendarSelectLayout;//点击右下角按钮弹出的日历选择器
    private NavigationBarLayout navBarLayout;//笔记导航条

    private PopupInputLayout popupInputLayout;

    private Date mDate;//当前日期

    private PopUpWindowHelper calenderPopUpHelper;
    private PopUpWindowHelper inputPopUpHelper;

    private boolean isPopupCalendar=false;//日期选择器是否弹出来

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private List<View> noteViewList;

    private InputMethodManager imm;

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

    @OnClick({R.id.btn_setting,R.id.btn_function})
    @Override
    protected void onBtnClick(View view) {
        switch (view.getId()){
            case R.id.btn_setting:
                // TODO: 2020/9/3 跳转到设置页
                showToast("跳转到设置页面");
                break;
            case R.id.btn_function:
                onFunctionClick(false);
                break;
        }
    }

    @OnLongClick({R.id.btn_function})
    protected void onBtnLongClick(View view){
        switch (view.getId()){
            case R.id.btn_function:
                onFunctionClick(true);
                break;
        }
    }

    private void onFunctionClick(boolean isLongClick){
        if(isLongClick){
            popupCalendar();
        }else{
            if(isPopupCalendar){//如果在日历选择window打开的情况下，轻点也是隐藏
                calenderPopUpHelper.dismiss();
                isPopupCalendar=!isPopupCalendar;
            }else{
                inputPopUpHelper.showPopupWindow(this.getWindow().getDecorView(), PopUpWindowHelper.LocationType.CENTER);
                popupInputLayout.getFEtNoteContent().requestFocus();
                imm.toggleSoftInput(1000,InputMethodManager.HIDE_NOT_ALWAYS);
            }
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
        topLeftViewGroup = ViewTools.buildCalendarLayout(this);
        //如果没有继承这个接口，说明这个View没有按要求去做
        if(topLeftViewGroup ==null){
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR);
        }
        llTop.addView(topLeftViewGroup);

        //初始化右上角
        topRightViewGroup = ViewTools.buildClockLayout(this);
        if(topRightViewGroup == null){
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR);
        }
        llTop.addView(topRightViewGroup);
        startToTick();
    }

    @Override
    protected void initCenter() {
        initViewPager();
        navBarLayout =ViewTools.buildNavBarView(this);
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
               navBarLayout.selectTo(position);
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
        viewPager.setOffscreenPageLimit(NavigationBarLayout.MAX_ITEM_NUM);//设置回收缓存，不要让ViewPager回收里面的碎片
        return viewPager;
    }

    @Override
    protected void initOther() {
        calendarSelectLayout =ViewTools.buildCalendarSelectLayout(this);
        calenderPopUpHelper = new PopUpWindowHelper.Builder(this)
                .setContentView(calendarSelectLayout)
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.PopupWindowTranslateThemeFromBottom)
                .setTouchable(true)
                .setFocusable(false)
                .setOutsideTouchable(false)
                .setBackgroundDrawable(new ColorDrawable(Color.WHITE))
                .build();

        popupInputLayout=new PopupInputLayout(this);
        inputPopUpHelper=new PopUpWindowHelper.Builder(this)
                .setContentView(popupInputLayout)
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.PopupWindowTranslateThemeFromBottom)
                .setTouchable(true)
                .setFocusable(true)
                .setOutsideTouchable(false)
                .setBackgroundDrawable(new ColorDrawable(Color.WHITE))
                .build();
        imm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    private void popupCalendar(){
        if(isPopupCalendar){
            calenderPopUpHelper.dismiss();
        }else{
            calenderPopUpHelper.showPopupWindow(switchDateLayout, PopUpWindowHelper.LocationType.TOP_TEST);
        }
        isPopupCalendar=!isPopupCalendar;
    }

    private void setCallBack() {
        //当最底部切换日期的按钮被点击的时候，通知View去更新自己的内容
        switchDateLayout.setmOnSwitchDateListener(new SwitchDateLayout.OnSwitchDateListener() {
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
