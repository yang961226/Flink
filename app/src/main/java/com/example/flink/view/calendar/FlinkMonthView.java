package com.example.flink.view.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;

import com.example.flink.R;
import com.example.flink.tools.CommonTools;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

import java.util.List;

public class FlinkMonthView extends MonthView {

    private Paint selectedTextPaint;
    private Paint selectedBackgroundPaint;
    private Paint selectedHasNoteSchemePaint;

    private Paint defaultBackgroundPaint;
    private Paint defaultTextPaint;
    private Paint defaultTodayBackgroundPaint;
    private Paint defaultTodayTextPaint;
    private Paint defaultHasNoteSchemePaint;

    private Paint otherMonthBackgroundPaint;
    private Paint otherMonthTextPaint;


    private static final int CALENDAR_ITEM_PADDING_DP = 10;//日历item的padding
    private static final int DEFAULT_BG_PAINT_STROKE_WIDTH_DP = 2;//单位：DP
    private static final int DEFAULT_TEXT_SIZE_DP = 13;//单位：DP


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
     * 没有用源码的画笔
     */
    private void initSelectedPaint() {
        selectedBackgroundPaint = new Paint();
        selectedBackgroundPaint.setStyle(Paint.Style.FILL);
        selectedBackgroundPaint.setAntiAlias(true);
        selectedBackgroundPaint.setColor(getResources().getColor(R.color.gray, null));

        selectedTextPaint = new Paint();
        selectedTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        selectedTextPaint.setColor(getResources().getColor(R.color.white, null));
        selectedTextPaint.setTextSize(CommonTools.dip2px(getContext(), DEFAULT_TEXT_SIZE_DP));

        selectedHasNoteSchemePaint = new Paint();
        selectedHasNoteSchemePaint.setColor(getResources().getColor(R.color.white, null));
        selectedHasNoteSchemePaint.setStyle(Paint.Style.FILL);
        selectedHasNoteSchemePaint.setAntiAlias(true);
    }

    private void initDefaultPaint() {
        defaultBackgroundPaint = new Paint();
        defaultBackgroundPaint.setColor(getResources().getColor(R.color.gray, null));
        defaultBackgroundPaint.setStyle(Paint.Style.STROKE);
        defaultBackgroundPaint.setStrokeWidth(CommonTools.dip2px(getContext(), DEFAULT_BG_PAINT_STROKE_WIDTH_DP));
        defaultBackgroundPaint.setAntiAlias(true);

        defaultTodayBackgroundPaint = new Paint();
        defaultTodayBackgroundPaint.setColor(getResources().getColor(R.color.silver, null));
        defaultTodayBackgroundPaint.setStyle(Paint.Style.FILL);
        defaultTodayBackgroundPaint.setAntiAlias(true);

        defaultTextPaint = new Paint();
        defaultTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        defaultTextPaint.setColor(getResources().getColor(R.color.gray, null));
        defaultTextPaint.setTextSize(CommonTools.dip2px(getContext(), DEFAULT_TEXT_SIZE_DP));

        defaultTodayTextPaint = new Paint();
        defaultTodayTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        defaultTodayTextPaint.setColor(getResources().getColor(R.color.gray, null));
        defaultTodayTextPaint.setTextSize(CommonTools.dip2px(getContext(), DEFAULT_TEXT_SIZE_DP));

        defaultHasNoteSchemePaint = new Paint();
        defaultHasNoteSchemePaint.setColor(getResources().getColor(R.color.gray, null));
        defaultHasNoteSchemePaint.setStyle(Paint.Style.FILL);
        defaultHasNoteSchemePaint.setAntiAlias(true);
    }

    private void initOtherMonthPaint() {
        otherMonthBackgroundPaint = new Paint();
        otherMonthBackgroundPaint.setColor(getResources().getColor(R.color.silver, null));
        otherMonthBackgroundPaint.setStyle(Paint.Style.STROKE);
        otherMonthBackgroundPaint.setStrokeWidth(CommonTools.dip2px(getContext(), DEFAULT_BG_PAINT_STROKE_WIDTH_DP));
        otherMonthBackgroundPaint.setAntiAlias(true);

        otherMonthTextPaint = new Paint();
        otherMonthTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        otherMonthTextPaint.setColor(getResources().getColor(R.color.silver, null));
        otherMonthTextPaint.setTextSize(CommonTools.dip2px(getContext(), DEFAULT_TEXT_SIZE_DP));
    }

    /**
     * 如果需要点击Scheme没有效果，则return true
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return false 则不绘制onDrawScheme
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        canvas.drawRect(x + CALENDAR_ITEM_PADDING_DP
                , y + CALENDAR_ITEM_PADDING_DP
                , x + mItemWidth - CALENDAR_ITEM_PADDING_DP
                , y + mItemHeight - CALENDAR_ITEM_PADDING_DP
                , selectedBackgroundPaint);
        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        if (isSelected) {
            onDrawSelectedText(canvas, calendar, x, y);
            if (hasScheme) {
                onDrawScheme(canvas, x, y, true);
            }
        } else {
            if (calendar.isCurrentMonth()) {
                onDrawDefaultBackground(canvas, calendar, x, y);
                onDrawDefaultText(canvas, calendar, x, y);
            } else {
                onDrawOtherBackground(canvas, calendar, x, y);
                onDrawOhterMonthText(canvas, calendar, x, y);
            }
            if (hasScheme) {
                onDrawScheme(canvas, x, y, false);
            }
        }
    }

    protected void onDrawScheme(Canvas canvas, int x, int y, boolean isSelected) {
        Path path = new Path();
        path.moveTo(x + CALENDAR_ITEM_PADDING_DP + DEFAULT_BG_PAINT_STROKE_WIDTH_DP, y + CALENDAR_ITEM_PADDING_DP + DEFAULT_BG_PAINT_STROKE_WIDTH_DP);
        path.lineTo(x + CALENDAR_ITEM_PADDING_DP + DEFAULT_BG_PAINT_STROKE_WIDTH_DP, y + mItemHeight / 2 + CALENDAR_ITEM_PADDING_DP + DEFAULT_BG_PAINT_STROKE_WIDTH_DP);
        path.lineTo(x + CALENDAR_ITEM_PADDING_DP + DEFAULT_BG_PAINT_STROKE_WIDTH_DP + mItemHeight / 2, y + CALENDAR_ITEM_PADDING_DP + DEFAULT_BG_PAINT_STROKE_WIDTH_DP);
        path.close();
        if (isSelected) {
            canvas.drawPath(path, selectedHasNoteSchemePaint);
        } else {
            canvas.drawPath(path, defaultHasNoteSchemePaint);
        }
    }

    protected void onDrawSelectedText(Canvas canvas, Calendar calendar, int x, int y) {
        float baselineY = mTextBaseLine + y;
        int textLength = (int) defaultTextPaint.measureText(calendar.getDay() + "");
        int cx = x + mItemWidth - CALENDAR_ITEM_PADDING_DP * 2 - textLength;
        canvas.drawText(calendar.getDay() + "",
                cx,
                baselineY,
                selectedTextPaint);
    }

    protected void onDrawDefaultText(Canvas canvas, Calendar calendar, int x, int y) {
        float baselineY = mTextBaseLine + y;
        int textLength = (int) defaultTextPaint.measureText(calendar.getDay() + "");
        int cx = x + mItemWidth - CALENDAR_ITEM_PADDING_DP * 2 - textLength;
        canvas.drawText(calendar.getDay() + "",
                cx,
                baselineY,
                defaultTextPaint);
    }

    protected void onDrawOhterMonthText(Canvas canvas, Calendar calendar, int x, int y) {
        float baselineY = mTextBaseLine + y;
        int textLength = (int) defaultTextPaint.measureText(calendar.getDay() + "");
        int cx = x + mItemWidth - CALENDAR_ITEM_PADDING_DP * 2 - textLength;
        canvas.drawText(calendar.getDay() + "",
                cx,
                baselineY,
                otherMonthTextPaint);
    }

    /*
     * 绘制默认状态下的日历背景，因为官方源码不提供这个，所以在onDrawText中插入实现
     */
    protected void onDrawDefaultBackground(Canvas canvas, Calendar calendar, int x, int y) {
        if (calendar.isCurrentDay()) {
            canvas.drawRect(x + CALENDAR_ITEM_PADDING_DP
                    , y + CALENDAR_ITEM_PADDING_DP
                    , x + mItemWidth - CALENDAR_ITEM_PADDING_DP
                    , y + mItemHeight - CALENDAR_ITEM_PADDING_DP
                    , defaultTodayBackgroundPaint);
        } else {
            canvas.drawRect(x + CALENDAR_ITEM_PADDING_DP
                    , y + CALENDAR_ITEM_PADDING_DP
                    , x + mItemWidth - CALENDAR_ITEM_PADDING_DP
                    , y + mItemHeight - CALENDAR_ITEM_PADDING_DP
                    , defaultBackgroundPaint);
        }

//        if (!(calendar.getScheme() == null)) {
//
//            List<Calendar.Scheme> schemes = calendar.getSchemes();
//            if (checkHasTheScheme(schemes, SchemeEnum.HAS_NOTE)) {//今天包含日记
//                Path path = new Path();
//                path.moveTo(x, y);
//                path.lineTo(x, y + mItemHeight / 2);
//                path.lineTo(x + mItemHeight / 2, y);
//                path.close();
//                canvas.drawPath(path, defaultHasNoteSchemePaint);
//            }
//        }
    }

    /**
     * 校验schemes中是否包含某个scheme
     *
     * @param schemes 全部schemes
     * @param type    scheme对应的值
     * @return true:包含
     */
    private boolean checkHasTheScheme(List<Calendar.Scheme> schemes, int type) {
        for (int i = 0; i < schemes.size(); i++) {
            if (schemes.get(i).getType() == type) {
                return true;
            }
        }
        return false;
    }

    protected void onDrawOtherBackground(Canvas canvas, Calendar calendar, int x, int y) {
        canvas.drawRect(x + CALENDAR_ITEM_PADDING_DP
                , y + CALENDAR_ITEM_PADDING_DP
                , x + mItemWidth - CALENDAR_ITEM_PADDING_DP
                , y + mItemHeight - CALENDAR_ITEM_PADDING_DP
                , otherMonthBackgroundPaint);
    }
}
