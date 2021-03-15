package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.mInterface.Unregister;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 通用的对话框布局
 */
public class MessageDialogLayout extends LinearLayout implements Unregister {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    private Context context;
    private Unbinder unbinder;
    private View view;

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
        //绑定处理
        unbinder = ButterKnife.bind(this);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setMessage(String msg) {
        tvMsg.setText(msg);
    }

    @Override
    public void unregister() {
        unbinder.unbind();
    }

}
