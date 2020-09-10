package com.example.flink.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.flink.R;
import com.example.flink.common.MyConstants;
import com.example.flink.common.MyException;
import com.example.flink.view.NavigationBarView;

import java.lang.reflect.Constructor;

/**
 * 这个类负责生成View
 */
public class ViewTools {

    /**
     * 生成日历布局
     * @return 布局
     */
    public static LinearLayout buildCalendarSelectView(Context context) {
        LinearLayout calendarSelectView;
        try{
            Class<?> clazz=Class.forName(CommonTools.getString(context, R.string.CalendarSelectView));
            Constructor<?> constructor = clazz.getDeclaredConstructor(Context.class, AttributeSet.class);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            calendarSelectView=(LinearLayout)constructor.newInstance(context,null);
            calendarSelectView.setLayoutParams(layoutParams);
        }catch (Exception e){
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR);
        }
        return calendarSelectView;
    }

    /**
     * 生成日期布局
     * @return 布局
     */
    public static LinearLayout buildCalendarView(Context context) {
        LinearLayout topLeftView;
        try {
            Class<?> clazz = Class.forName(CommonTools.getString(context, R.string.CalendarView));
            Constructor<?> constructor = clazz.getDeclaredConstructor(Context.class, AttributeSet.class);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            topLeftView = (LinearLayout) constructor.newInstance(context, null);
            topLeftView.setLayoutParams(layoutParams);

        } catch (Exception e) {
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR);
        }
        return topLeftView;
    }

    /**
     * 生成时钟的布局
     * @return 布局
     */
    public static  LinearLayout buildClockView(Context context) {
        LinearLayout topRightView;
        try {
            Class<?> clazz = Class.forName(CommonTools.getString(context, R.string.ClockView));
            Constructor<?> constructor = clazz.getDeclaredConstructor(Context.class, AttributeSet.class);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            topRightView = (LinearLayout) constructor.newInstance(context, null);
            topRightView.setLayoutParams(layoutParams);
        } catch (Exception e) {
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR);
        }
        return topRightView;
    }

    /**
     * 通用的ViewGroup生成器
     * @param context 上下文
     * @param clazz 类文件
     * @param layoutParams 参数
     * @return 生成的ViewGroup
     */
    public static ViewGroup buildViewGroup(Context context,Class<? extends ViewGroup>clazz,ViewGroup.LayoutParams layoutParams){
        ViewGroup viewGroup;
        try{
            Constructor<?> constructor = clazz.getDeclaredConstructor(Context.class, AttributeSet.class);
            viewGroup=(ViewGroup)constructor.newInstance(context, null);
            viewGroup.setLayoutParams(layoutParams);
        }catch (Exception e){
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR);
        }
        return viewGroup;
    }

    public static ViewGroup buildViewGroup(Context context,Class<? extends ViewGroup>clazz){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        return buildViewGroup(context,clazz,layoutParams);
    }

    public static NavigationBarView buildNavBarView(Context context){
        NavigationBarView navBarView=new NavigationBarView(context,null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        navBarView.setLayoutParams(layoutParams);
        return navBarView;
    }
}
