package com.example.flink.view;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;

public class WidthAdaptationTextView extends androidx.appcompat.widget.AppCompatTextView {
    private Paint mTextPaint;
    private float mMaxTextSize;
    private float mMinTextSize;

    public WidthAdaptationTextView(Context context) {
        this(context, null);
    }

    public WidthAdaptationTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    //默认设置
    private void initialise() {
        setGravity(getGravity() | Gravity.CENTER_VERTICAL);//水平居中
        setLines(1);//一行

        //最大字体大小为默认大小，最小为8
        mMaxTextSize = this.getTextSize();
        mMinTextSize = 8;

        mTextPaint = new TextPaint();
        mTextPaint.set(this.getPaint());
    }

    //内容改变时
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        refitText(text.toString(), this.getWidth());
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    //宽度改变时
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw) {
            refitText(this.getText().toString(), w);
        }
    }

    /**
     * 调整字体大小，使指定的文本适合于文本框
     * 假设文本框是指定的宽度
     */
    private void refitText(String text, int textWidth) {
        if (textWidth > 0) {
            int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
            float trySize = mMaxTextSize;

            mTextPaint.setTextSize(trySize);
            while (mTextPaint.measureText(text) > availableWidth) {
                trySize -= 1;
                if (trySize <= mMinTextSize) {
                    trySize = mMinTextSize;
                    break;
                }
                mTextPaint.setTextSize(trySize);
            }

            //setTextSize参数值为sp值
            setTextSize(px2sp(getContext(), trySize));
        }
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static float px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (pxValue / fontScale);
    }

}