package com.example.flink.layout.note;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.flink.R;

/**
 * 设置页中的一行，右边默认是点击按钮，也可以自己设置自定义布局
 */
public class SettingLine extends ConstraintLayout {

    private LinearLayout llClickableContent;
    private TextView tvTitle;
    private ImageButton ibtnSwitch;

    private boolean switchToButton;//开关是否退化成单纯的按钮
    private OnClickListener onClickListener;//切换成按钮之后用到的回调

    private boolean switchState;//开关状态，如果是自定义布局和纯按钮的状态下，这个属性没用

    private OnSwitchListener onSwitchListener;

    public SettingLine(@NonNull Context context) {
        super(context);
        init();
    }

    public SettingLine(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SettingLine(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_setting_line, this);
        findViewById(view);
    }

    private void findViewById(View view) {
        llClickableContent = view.findViewById(R.id.ll_clickable_content);
        tvTitle = view.findViewById(R.id.tv_title);
        ibtnSwitch = view.findViewById(R.id.ibtn_switch);
        ibtnSwitch.setOnClickListener(view1 -> {
            //如果已经是按钮状态的话，就不响应swtich事件了，而是响应点击事件
            if (switchToButton) {
                clickButton();
                return;
            }
            clickSwitch();
        });
    }

    /**
     * 将开关退化成单纯的按钮
     */
    public void makeSwitchToButton(OnClickListener onClickListener) {
        switchToButton = true;
        this.onClickListener = onClickListener;
        ibtnSwitch.setImageResource(R.drawable.ic_button_hand);
        ibtnSwitch.setBackgroundResource(R.drawable.button_selector);
    }

    /**
     * 添加自定义布局
     *
     * @param view 自定义布局
     */
    public void addViewInContent(View view) {
        ibtnSwitch.setVisibility(GONE);
        llClickableContent.setVisibility(VISIBLE);
        llClickableContent.addView(view);
    }

    /**
     * 如果点击的是单纯的按钮
     */
    protected void clickButton() {
        onClickListener.onClick(ibtnSwitch);
    }

    /**
     * 如果点击的是开关
     */
    protected void clickSwitch() {
        refreshSwitchState(!switchState);
        if (onSwitchListener != null) {
            onSwitchListener.onSwitch(switchState);
        }
    }

    public void refreshSwitchState(boolean currSwitchState) {
        ibtnSwitch.setBackgroundResource(switchState ? R.drawable.ic_switch_close : R.drawable.ic_switch_open);
        switchState = currSwitchState;
    }

    public void setOnSwitchListener(OnSwitchListener onSwitchListener) {
        this.onSwitchListener = onSwitchListener;
    }

    public interface OnSwitchListener {
        void onSwitch(boolean switchOpen);
    }

    public String getTitle() {
        return tvTitle.getText().toString();
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }
}
