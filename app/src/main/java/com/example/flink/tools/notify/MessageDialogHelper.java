package com.example.flink.tools.notify;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.flink.R;
import com.example.flink.layout.MessageDialogLayout;
import com.example.flink.tools.PopUpWindowHelper;

/**
 * 通用消息通知对话框
 */
public class MessageDialogHelper {

    private final Context context;
    private PopUpWindowHelper popUpWindowHelper;
    private MessageDialogLayout messageDialogLayout;
    private OnDialogButtonClickListener onDialogButtonClickListener;

    public MessageDialogHelper(Context context) {
        this.context = context;
    }

    public void dismissDialog() {
        popUpWindowHelper.dismiss();
    }

    public void showMsgDialog(String title
            , String msg
            , OnDialogButtonClickListener onDialogButtonClickListener) {
        showMsgDialog(title, -1, msg, onDialogButtonClickListener);
    }

    public void showMsgDialog(String title, int titleIconSrcId, String msg, OnDialogButtonClickListener onDialogButtonClickListener) {
        if (popUpWindowHelper == null) {
            messageDialogLayout = new MessageDialogLayout(context);
            TextView tvCancle = (TextView) messageDialogLayout.findView(R.id.tv_cancle);
            popUpWindowHelper = new PopUpWindowHelper
                    .Builder(context)
                    .setContentView(messageDialogLayout)
                    .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                    .setAnimationStyle(R.style.PopupWindowScaleTheme)
                    .setTouchable(true)
                    .setFocusable(false)
                    .setOutsideTouchable(true)
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT))
                    .build();
            TextView tvOk = (TextView) messageDialogLayout.findView(R.id.tv_ok);
            tvOk.setOnClickListener(v -> {
                if (onDialogButtonClickListener == null) {
                    return;
                }
                onDialogButtonClickListener.onOkClick(popUpWindowHelper, v);
            });
            tvCancle.setOnClickListener(v -> {
                if (onDialogButtonClickListener == null) {
                    return;
                }
                onDialogButtonClickListener.onCancleClick(popUpWindowHelper, v);
            });
        }
        if (titleIconSrcId != -1) {
            messageDialogLayout.setTitleIcon(titleIconSrcId);
        }
        messageDialogLayout.setTitle(title);
        messageDialogLayout.setMessage(msg);
        popUpWindowHelper.showPopupWindow(((Activity) context).getWindow().getDecorView(), PopUpWindowHelper.LocationType.CENTER);
    }

    public interface OnDialogButtonClickListener {

        void onOkClick(PopUpWindowHelper popUpWindowHelper, View v);

        void onCancleClick(PopUpWindowHelper popUpWindowHelper, View v);

    }

}
