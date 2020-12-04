package com.example.flink.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.flink.R;
import com.example.flink.common.MyConstants;
import com.example.flink.common.MyException;
import com.example.flink.layout.NavigationBarLayout;
import com.example.flink.layout.NoteViewPagerBaseLayout;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类负责生成View
 */
public class ViewTools {

    /**
     * 生成日期布局
     *
     * @return 布局
     */
    public static ViewGroup buildCalendarLayout(Context context) {
        ViewGroup topLeftView;
        try {
            Class<?> clazz = Class.forName(CommonTools.getString(context, R.string.CalendarLayout));
            Constructor<?> constructor = clazz.getDeclaredConstructor(Context.class, AttributeSet.class);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            topLeftView = (ViewGroup) constructor.newInstance(context, null);
            topLeftView.setLayoutParams(layoutParams);

        } catch (Exception e) {
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR + "错误描述：" + e.toString());
        }
        return topLeftView;
    }

    /**
     * 生成时钟的布局
     *
     * @return 布局
     */
    public static ViewGroup buildClockLayout(Context context) {
        ViewGroup topRightView;
        try {
            Class<?> clazz = Class.forName(CommonTools.getString(context, R.string.ClockLayout));
            Constructor<?> constructor = clazz.getDeclaredConstructor(Context.class, AttributeSet.class);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            topRightView = (ViewGroup) constructor.newInstance(context, null);
            topRightView.setLayoutParams(layoutParams);
        } catch (Exception e) {
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR + "错误描述：" + e.toString());
        }
        return topRightView;
    }

    /**
     * 通用的ViewGroup生成器
     *
     * @param context      上下文
     * @param clazz        类文件
     * @param layoutParams 参数
     * @return 生成的ViewGroup
     */
    public static ViewGroup buildViewGroup(Context context, Class<? extends ViewGroup> clazz, ViewGroup.LayoutParams layoutParams) {
        ViewGroup viewGroup;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(Context.class, AttributeSet.class);
            viewGroup = (ViewGroup) constructor.newInstance(context, null);
            viewGroup.setLayoutParams(layoutParams);
        } catch (Exception e) {
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR);
        }
        return viewGroup;
    }

    public static ViewGroup buildViewGroup(Context context, Class<? extends ViewGroup> clazz) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        return buildViewGroup(context, clazz, layoutParams);
    }

    public static NavigationBarLayout buildNavBarView(Context context) {
        NavigationBarLayout navBarView = new NavigationBarLayout(context, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        navBarView.setLayoutParams(layoutParams);
        return navBarView;
    }

    /**
     * @param context
     * @return
     */
    public static List<NoteViewPagerBaseLayout> buildNoteViewFunctions(Context context) {
        List<NoteViewPagerBaseLayout> arrayList = new ArrayList<>();
        String[] functions = context.getResources().getStringArray(R.array.note_function);
        try {
            for (String function : functions) {
                arrayList.add((NoteViewPagerBaseLayout) buildViewGroup(context, (Class<? extends ViewGroup>) Class.forName(function)));
            }
        } catch (Exception e) {
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR);
        }

        return arrayList;
    }
}
