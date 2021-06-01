package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.flink.R;

/**
 * 通用的对话框布局
 */
public class MessageDialogLayout extends LinearLayout {
    private Context context;
    private View view;
    private TextView tvTitle;
    private TextView tvMsg;

    public MessageDialogLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MessageDialogLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public View findView(int resId) {
        return view.findViewById(resId);
    }

    private void init() {
        view = View.inflate(context, R.layout.layout_common_dialog, this);
        tvTitle = view.findViewById(R.id.tv_title);
        tvMsg = view.findViewById(R.id.tv_msg);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setMessage(String msg) {
        tvMsg.setText(msg);
    }

}
