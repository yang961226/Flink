package com.example.flink.tools;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * 通用PopupWindow工具,https://github.com/jzmanu/MPopupWindow
 */
public class PopUpWindowHelper {

    private Context mContext;
    private PopupWindow mPopupWindow;
    private Drawable mBackgroundDrawable;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private boolean mOutsideTouchable;
    private int mAnimationStyle;
    private int mWidth;
    private View mView;
    private int mLayoutId;
    private int mHeight;
    private int mOffsetX;
    private int mOffsetY;
    private int mGravity;
    private boolean mFocusable;
    private boolean mTouchable;

    private PopUpWindowHelper() {
        //屏蔽
    }

    public void showPopupWindow(View v, LocationType type) {
        if (mView != null) {
            mPopupWindow.setContentView(mView);
        } else if (mLayoutId != -1) {
            View contentView = LayoutInflater.from(mContext).inflate(mLayoutId, null);
            mPopupWindow.setContentView(contentView);
        }
        mPopupWindow.setWidth(mWidth);
        mPopupWindow.setHeight(mHeight);
        mPopupWindow.setBackgroundDrawable(mBackgroundDrawable);
        mPopupWindow.setOutsideTouchable(mOutsideTouchable);
        mPopupWindow.setOnDismissListener(mOnDismissListener);
        mPopupWindow.setAnimationStyle(mAnimationStyle);
        mPopupWindow.setTouchable(mTouchable);
        mPopupWindow.setFocusable(mFocusable);

        int[] locations = new int[2];
        v.getLocationOnScreen(locations);
        int left = locations[0];
        int top = locations[1];

        mPopupWindow.getContentView()
                .measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = mPopupWindow.getContentView().getMeasuredWidth();
        int popupHeight = mPopupWindow.getContentView().getMeasuredHeight();

        switch (type) {
            case TOP_TEST:
                mPopupWindow.showAsDropDown(v, mOffsetX, -(v.getHeight() + popupHeight) + mOffsetY, Gravity.NO_GRAVITY);
                break;
            case CENTER:
                mPopupWindow.showAtLocation(v, Gravity.CENTER, mOffsetX, mOffsetY);
                break;
            //下面的均未测试过，如有问题请和作者联系
            case TOP_LEFT:
                mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, left - popupWidth + mOffsetX, top - popupHeight + mOffsetY);
                break;
            case TOP_CENTER:
                int offsetX = (v.getWidth() - popupWidth) / 2;
                mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, left + offsetX + mOffsetX, top - popupHeight + mOffsetY);
                break;
            case TOP_RIGHT:
                mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, left + v.getWidth() + mOffsetX, top - popupHeight + mOffsetY);
                break;
            case BOTTOM_LEFT:
                mPopupWindow.showAsDropDown(v, -popupWidth + mOffsetX, mOffsetY);
                break;
            case BOTTOM_CENTER:
                int offsetX1 = (v.getWidth() - popupWidth) / 2;
                mPopupWindow.showAsDropDown(v, offsetX1 + mOffsetX, mOffsetY);
                break;
            case BOTTOM_RIGHT:
                mPopupWindow.showAsDropDown(v, v.getWidth() + mOffsetX, mOffsetY);
                break;
            case LEFT_TOP:
                mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, left - popupWidth + mOffsetX, top - popupHeight + mOffsetY);
                break;
            case LEFT_BOTTOM:
                mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, left - popupWidth + mOffsetX, top + v.getHeight() + mOffsetY);
                break;
            case LEFT_CENTER:
                int offsetY = (v.getHeight() - popupHeight) / 2;
                mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, left - popupWidth + mOffsetX, top + offsetY + mOffsetY);
                break;
            case RIGHT_TOP:
                mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, left + v.getWidth() + mOffsetX, top - popupHeight + mOffsetY);
                break;
            case RIGHT_BOTTOM:
                mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, left + v.getWidth() + mOffsetX, top + v.getHeight() + mOffsetY);
                break;
            case RIGHT_CENTER:
                int offsetY1 = (v.getHeight() - popupHeight) / 2;
                mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, left + v.getWidth() + mOffsetX, top + offsetY1 + mOffsetY);
                break;
            case FROM_BOTTOM:
                mPopupWindow.showAtLocation(v, mGravity, mOffsetX, mOffsetY);
                break;
        }
    }

    public static class Builder {
        private Context context;
        private View contentView;
        private int layoutId;
        private PopupWindow popupWindow;
        private boolean outsideTouchable;
        private Drawable backgroundDrawable;
        private PopupWindow.OnDismissListener onDismissListener;
        private int animationStyle;
        private int width;
        private int height;
        private int offsetX;
        private int offsetY;
        private int gravity;
        private boolean touchable;
        private boolean focusable;

        public Builder(Context context) {
            this.context = context;
            this.popupWindow = new PopupWindow(context);
            this.outsideTouchable = true;
            this.touchable = true;
            this.backgroundDrawable = new ColorDrawable(Color.TRANSPARENT);
            this.width = WindowManager.LayoutParams.WRAP_CONTENT;
            this.height = WindowManager.LayoutParams.WRAP_CONTENT;
            this.gravity = Gravity.CENTER;
            this.layoutId = -1;
            this.offsetX = 0;
            this.offsetY = 0;
        }

        public Builder setFocusable(boolean focusable) {
            this.focusable = focusable;
            return this;
        }

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder setLayoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        public Builder setBackgroundDrawable(Drawable backgroundDrawable) {
            this.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setOutsideTouchable(boolean outsideTouchable) {
            this.outsideTouchable = outsideTouchable;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setOffsetX(int offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        public Builder setOffsetY(int offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle) {
            this.animationStyle = animationStyle;
            return this;
        }

        public Builder setTouchable(boolean touchable) {
            this.touchable = touchable;
            return this;
        }

        public PopUpWindowHelper build() {
            PopUpWindowHelper popUpWindowHelper = new PopUpWindowHelper();
            setPopupWindowConfig(popUpWindowHelper);
            return popUpWindowHelper;
        }

        private void setPopupWindowConfig(PopUpWindowHelper popUpWindowHelper) {
            if (contentView != null && layoutId != -1) {
                throw new RuntimeException("setContentView and setLayoutId can't be used together.");
            } else if (contentView == null && layoutId == -1) {
                throw new RuntimeException("contentView or layoutId can't be null.");
            }

            if (context == null) {
                throw new RuntimeException("context can't be null.");
            } else {
                popUpWindowHelper.mContext = this.context;
            }

            popUpWindowHelper.mWidth = this.width;
            popUpWindowHelper.mHeight = this.height;
            popUpWindowHelper.mView = this.contentView;
            popUpWindowHelper.mLayoutId = layoutId;
            popUpWindowHelper.mPopupWindow = this.popupWindow;
            popUpWindowHelper.mOutsideTouchable = this.outsideTouchable;
            popUpWindowHelper.mBackgroundDrawable = this.backgroundDrawable;
            popUpWindowHelper.mOnDismissListener = this.onDismissListener;
            popUpWindowHelper.mAnimationStyle = this.animationStyle;
            popUpWindowHelper.mTouchable = this.touchable;
            popUpWindowHelper.mFocusable = this.focusable;
            popUpWindowHelper.mOffsetX = this.offsetX;
            popUpWindowHelper.mOffsetY = this.offsetY;
            popUpWindowHelper.mGravity = this.gravity;
        }
    }

    public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    public enum LocationType {
        TOP_LEFT,
        TOP_RIGHT,
        TOP_CENTER,

        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        BOTTOM_CENTER,

        RIGHT_TOP,
        RIGHT_BOTTOM,
        RIGHT_CENTER,

        LEFT_TOP,
        LEFT_BOTTOM,
        LEFT_CENTER,

        FROM_BOTTOM,

        CENTER,//已测试

        TOP_TEST//已测试
    }
}
