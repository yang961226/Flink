package com.example.flink.view;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;

public class HeightAdaptationTextView extends androidx.appcompat.widget.AppCompatTextView {
    private Paint mTextPaint;
    private float mMaxTextSize;
    private float mMinTextSize;

    public HeightAdaptationTextView(Context context) {
        super(context);
    }

    public HeightAdaptationTextView(Context context, AttributeSet attrs) {
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
        refitText(this.getHeight());//textView视图的高度
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    //高度改变时
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (h != oldh) {
            refitText(h);
        }
    }

    /**
     * 调整字体大小，使指定的文本适合于文本框
     * 假设文本框是指定的高度
     */
    private void refitText(int height) {
        if (height > 0) {
            //减去边距为字体的实际高度
            int availableHeight = height - this.getPaddingTop() - this.getPaddingBottom();
            float trySize = mMaxTextSize;
            mTextPaint.setTextSize(trySize);
            //测量的字体高度过大，不断地缩放
            while (mTextPaint.descent()-mTextPaint.ascent() > availableHeight) {
                trySize -= 1;//字体不断地减小来适应
                if (trySize <= mMinTextSize) {
                    trySize = mMinTextSize;//最小为这个
                    break;
                }
                mTextPaint.setTextSize(trySize);
            }
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