package com.example.flink.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.flink.R;

public class BottomBar extends ConstraintLayout {

    private ImageView btnLeft;
    private ImageView btnRight;

    public BottomBar(@NonNull Context context) {
        super(context);
        init();
    }

    public BottomBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setLeftBtnImg(int resId) {
        btnLeft.setImageResource(resId);
    }

    public void setRightBtnImg(int resId) {
        btnRight.setImageResource(resId);
    }

    public void setLeftBtnVisible(boolean isVisible) {
        btnLeft.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void setRightBtnVisible(boolean isVisible) {
        btnRight.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void setLeftBtnOnClickListener(OnClickListener onClickListener) {
        btnLeft.setOnClickListener(onClickListener);
    }

    public void setRightBtnOnClickListener(OnClickListener onClickListener) {
        btnRight.setOnClickListener(onClickListener);
    }

    public void setLeftBtnOnLongClickListener(OnLongClickListener onLongClickListener) {
        btnLeft.setOnLongClickListener(onLongClickListener);
    }

    public void setRightBtnOnLongClickListener(OnLongClickListener onLongClickListener) {
        btnRight.setOnLongClickListener(onLongClickListener);
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.layout_bottom_bar, this);
        btnLeft = view.findViewById(R.id.btn_left);
        btnRight = view.findViewById(R.id.btn_right);
    }
}
