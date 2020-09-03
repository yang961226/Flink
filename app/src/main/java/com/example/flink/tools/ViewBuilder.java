package com.example.flink.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.flink.R;
import com.example.flink.common.MyConstants;

import java.lang.reflect.Constructor;

/**
 * 这个类负责生成View
 */
public class ViewBuilder {

    /**
     * 生成日历布局
     * @return 布局
     */
    public static LinearLayout buildCalendarSelectView(Context context) {
        LinearLayout calendarSelectView=new LinearLayout(context,null);
        try{
            Class<?> clazz=Class.forName(Tools.getString(context, R.string.CalendarSelectView));
            Constructor<?> constructor = clazz.getDeclaredConstructor(Context.class, AttributeSet.class);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            calendarSelectView=(LinearLayout)constructor.newInstance(context,null);
            calendarSelectView.setLayoutParams(layoutParams);
        }catch (Exception e){
            Log.e(context.getClass().getName(), MyConstants.CLASS_CONFIG_ERROR);
        }
        return calendarSelectView;
    }

    /**
     * 生成日期布局
     * @return 布局
     */
    public static LinearLayout buildCalendarView(Context context) {
        LinearLayout topLeftView = new LinearLayout(context, null);
        try {
            Class<?> clazz = Class.forName(Tools.getString(context, R.string.CalendarView));
            Constructor<?> constructor = clazz.getDeclaredConstructor(Context.class, AttributeSet.class);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            topLeftView = (LinearLayout) constructor.newInstance(context, null);
            topLeftView.setLayoutParams(layoutParams);
        } catch (Exception e) {
            Log.e(context.getClass().getName(), MyConstants.CLASS_CONFIG_ERROR);
        }
        return topLeftView;
    }

    /**
     * 生成顶部右边的布局
     * @return 布局
     */
    public static  LinearLayout buildClockView(Context context) {
        LinearLayout topRightView = new LinearLayout(context, null);
        try {
            Class<?> clazz = Class.forName(Tools.getString(context, R.string.ClockView));
            Constructor<?> constructor = clazz.getDeclaredConstructor(Context.class, AttributeSet.class);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            topRightView = (LinearLayout) constructor.newInstance(context, null);
            topRightView.setLayoutParams(layoutParams);
        } catch (Exception e) {
            Log.e(context.getClass().getName(), MyConstants.CLASS_CONFIG_ERROR);
        }
        return topRightView;
    }

}
