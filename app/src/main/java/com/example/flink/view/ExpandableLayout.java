package com.example.flink.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * 可折叠Layout
 */
public class ExpandableLayout extends LinearLayout {

    private boolean isExpand = false;//是否展开
    private boolean isPlayingAnim = false;//是否正在播放动画
    private int height = 0;
    private long duration = 500;//动画持续时间
    private Interpolator interpolator;

    private Animation animationDown;
    private Animation animationUp;

    private boolean isFirstPalyAnim = true;//是否是第一次启动动画

    private OnExpandingListener onExpandingListener;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        if (isFirstPalyAnim) {
            this.setVisibility(GONE);
        }
    }

    public ExpandableLayout(Context context) {
        super(context);
    }

    public ExpandableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        interpolator = new AccelerateDecelerateInterpolator();//默认是加速减速插值器
    }

    public void setOnExpandingListener(OnExpandingListener onExpandingListener) {
        this.onExpandingListener = onExpandingListener;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void expand() {
        if (animationUp == null || animationDown == null) {
            initAnimation();
        }

        if (isPlayingAnim) {
            return;
        }
        if (!isExpand) {
            startAnimation(animationDown);
            isExpand = true;
        } else {
            isExpand = false;
            startAnimation(animationUp);
        }
    }

    private void initAnimation() {
        if (animationDown == null) {
            animationDown = new ExpandAnim(this, height, true);
            animationDown.setInterpolator(interpolator);
            animationDown.setDuration(duration);
            animationDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    isPlayingAnim = true;
                    if (isFirstPalyAnim) {
                        isFirstPalyAnim = false;
                        ExpandableLayout.this.setVisibility(VISIBLE);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isPlayingAnim = false;

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        if (animationUp == null) {
            animationUp = new ExpandAnim(this, height, false);
            animationUp.setInterpolator(interpolator);
            animationUp.setDuration(duration);
            animationUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    isPlayingAnim = true;
                    if (isFirstPalyAnim) {
                        isFirstPalyAnim = false;
                        ExpandableLayout.this.setVisibility(VISIBLE);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isPlayingAnim = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    public interface OnExpandingListener {

        //获取展开的百分比
        void onExpandingInterpolatedTime(float interpolatedTime, boolean isDown);

        //获取展开的距离（无论是向上展开还是向下展开都是从0到展开值）
        void onExpandingRange(int expandRange, boolean isDown);

        void onExpandingEnd(int expandRange, boolean isDown);

    }


    private class ExpandAnim extends Animation {

        private int expandRange;//展开的范围

        private View targetView;

        private boolean expandToDown;//是否向下展开

        public ExpandAnim(View targetView, int expandRange, boolean isdown) {
            this.targetView = targetView;
            this.expandRange = expandRange;
            this.expandToDown = isdown;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newHeight;
            if (expandToDown) {
                if (onExpandingListener != null) {
                    onExpandingListener.onExpandingRange((int) (expandRange * interpolatedTime), true);
                    onExpandingListener.onExpandingInterpolatedTime(interpolatedTime, true);
                    if (interpolatedTime == 1.0) {
                        onExpandingListener.onExpandingEnd(expandRange, true);
                    }
                }
                newHeight = (int) (expandRange * interpolatedTime);
            } else {
                if (onExpandingListener != null) {
                    onExpandingListener.onExpandingRange((int) (expandRange * interpolatedTime), false);
                    onExpandingListener.onExpandingInterpolatedTime(interpolatedTime, false);
                    if (interpolatedTime == 1.0) {
                        onExpandingListener.onExpandingEnd(expandRange, false);
                    }
                }
                newHeight = (int) (expandRange * (1 - interpolatedTime));
            }

            targetView.getLayoutParams().height = newHeight;
            targetView.requestLayout();
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }


}