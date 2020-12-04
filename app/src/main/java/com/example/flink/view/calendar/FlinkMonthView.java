package com.example.flink.view.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.flink.R;
import com.example.flink.tools.CommonTools;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

public class FlinkMonthView extends MonthView {

    private Paint selectedTextPaint;
    private Paint selectedBackgroundPaint;

    private Paint defaultBackgroundPaint;
    private Paint defaultTextPaint;

    private Paint otherMonthBackgroundPaint;
    private Paint otherMonthTextPaint;

    private static final int CALENDAR_ITEM_PADDING = 10;//日历item的padding
    private static final int DEFAULT_BG_PAINT_STROKE_WIDTH_DP = 2;//单位：DP


    public FlinkMonthView(Context context) {
        super(context);

        init();
    }

    private void init() {
        initDefaultPaint();
        initSelectedPaint();
        initOtherMonthPaint();
    }

    /**
     * 没有用源码替工的paint
     */
    private void initSelectedPaint() {
        selectedBackgroundPaint = new Paint();
        selectedBackgroundPaint.setStyle(Paint.Style.FILL);
        selectedBackgroundPaint.setAntiAlias(true);
        selectedBackgroundPaint.setColor(getResources().getColor(R.color.gray, null));

        selectedTextPaint = new Paint();
        selectedTextPaint.setColor(getResources().getColor(R.color.white, null));
        selectedTextPaint.setTextSize(CommonTools.dip2px(getContext(), 15));
    }

    private void initDefaultPaint() {
        defaultBackgroundPaint = new Paint();
        defaultBackgroundPaint.setColor(getResources().getColor(R.color.gray, null));
        defaultBackgroundPaint.setStyle(Paint.Style.STROKE);
        defaultBackgroundPaint.setStrokeWidth(CommonTools.dip2px(getContext(), DEFAULT_BG_PAINT_STROKE_WIDTH_DP));
        defaultBackgroundPaint.setAntiAlias(true);

        defaultTextPaint = new Paint();
        defaultTextPaint.setColor(getResources().getColor(R.color.gray, null));
        defaultTextPaint.setTextSize(CommonTools.dip2px(getContext(), 15));
    }

    private void initOtherMonthPaint() {
        otherMonthBackgroundPaint = new Paint();
        otherMonthBackgroundPaint.setColor(getResources().getColor(R.color.silver, null));
        otherMonthBackgroundPaint.setStyle(Paint.Style.STROKE);
        otherMonthBackgroundPaint.setStrokeWidth(CommonTools.dip2px(getContext(), DEFAULT_BG_PAINT_STROKE_WIDTH_DP));
        otherMonthBackgroundPaint.setAntiAlias(true);

        otherMonthTextPaint = new Paint();
        otherMonthTextPaint.setColor(getResources().getColor(R.color.silver, null));
        otherMonthTextPaint.setTextSize(CommonTools.dip2px(getContext(), 15));
    }

    /**
     * 如果需要点击Scheme没有效果，则return true
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return false 则不绘制onDrawScheme，因为这里背景色是互斥的
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        canvas.drawRect(x + CALENDAR_ITEM_PADDING
                , y + CALENDAR_ITEM_PADDING
                , x + mItemWidth - CALENDAR_ITEM_PADDING
                , y + mItemHeight - CALENDAR_ITEM_PADDING
                , selectedBackgroundPaint);
        return true;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {

        if (isSelected) {
            onDrawSelectedText(canvas, calendar, x, y);
        } else if (hasScheme) {
            // TODO: 2020/12/4 预留
        } else {
            if (calendar.isCurrentMonth()) {
                onDrawDefaultBackground(canvas, calendar, x, y);
                onDrawDefaultText(canvas, calendar, x, y);
            } else {
                onDrawOtherBackground(canvas, calendar, x, y);
                onDrawOhterMonthText(canvas, calendar, x, y);
            }

        }
    }

    protected void onDrawSelectedText(Canvas canvas, Calendar calendar, int x, int y) {
        float baselineY = mTextBaseLine + y;
        int textLength = (int) defaultTextPaint.measureText(calendar.getDay() + "");
        int cx = x + mItemWidth - CALENDAR_ITEM_PADDING * 2 - textLength;
        canvas.drawText(calendar.getDay() + "",
                cx,
                baselineY,
                selectedTextPaint);
    }

    protected void onDrawDefaultText(Canvas canvas, Calendar calendar, int x, int y) {
        float baselineY = mTextBaseLine + y;
        int textLength = (int) defaultTextPaint.measureText(calendar.getDay() + "");
        int cx = x + mItemWidth - CALENDAR_ITEM_PADDING * 2 - textLength;
        canvas.drawText(calendar.getDay() + "",
                cx,
                baselineY,
                defaultTextPaint);
    }

    protected void onDrawOhterMonthText(Canvas canvas, Calendar calendar, int x, int y) {
        float baselineY = mTextBaseLine + y;
        int textLength = (int) defaultTextPaint.measureText(calendar.getDay() + "");
        int cx = x + mItemWidth - CALENDAR_ITEM_PADDING * 2 - textLength;
        canvas.drawText(calendar.getDay() + "",
                cx,
                baselineY,
                otherMonthTextPaint);
    }

    /*
     * 绘制默认状态下的日历背景，因为官方源码不提供这个，所以在onDrawText中插入实现
     */
    protected void onDrawDefaultBackground(Canvas canvas, Calendar calendar, int x, int y) {
        canvas.drawRect(x + CALENDAR_ITEM_PADDING
                , y + CALENDAR_ITEM_PADDING
                , x + mItemWidth - CALENDAR_ITEM_PADDING
                , y + mItemHeight - CALENDAR_ITEM_PADDING
                , defaultBackgroundPaint);
    }

    protected void onDrawOtherBackground(Canvas canvas, Calendar calendar, int x, int y) {
        canvas.drawRect(x + CALENDAR_ITEM_PADDING
                , y + CALENDAR_ITEM_PADDING
                , x + mItemWidth - CALENDAR_ITEM_PADDING
                , y + mItemHeight - CALENDAR_ITEM_PADDING
                , otherMonthBackgroundPaint);
    }
}
