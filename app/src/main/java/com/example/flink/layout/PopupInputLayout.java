package com.example.flink.layout;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flink.R;
import com.example.flink.view.FilterEditTextView;


public class PopupInputLayout extends LinearLayout {

    private TextView tvProgress;
    private FilterEditTextView fetNoteContent;
    private TextView tvTitle;

    private int maxWordNum = getResources().getInteger(R.integer.sticky_note_popup_input_max_length);//默认值

    private ConfirmBtnClickListener confirmBtnClickListener;

//    private static final int SRC_PLUS_ID = R.drawable.ic_plus_circle_gray;
//    private static final int SRC_REDUCE_ID = R.drawable.ic_reduce_circle_gray;

    private boolean isEditMode;//是否是编辑模式

    public PopupInputLayout(Context context) {
        super(context);
        View view = View.inflate(context, R.layout.layout_popup_input, this);
        findViewById(view);
        tvProgress.setText(0 + "/" + maxWordNum);
        fetNoteContent.init(maxWordNum);
        fetNoteContent.setOnWordNumChangeListener(wordNumAfterChange -> tvProgress.setText(wordNumAfterChange + "/" + maxWordNum));

    }


    private void findViewById(View rootView) {
        fetNoteContent = rootView.findViewById(R.id.fet_note_content);
        tvProgress = rootView.findViewById(R.id.tv_progress);
        tvTitle = rootView.findViewById(R.id.tv_title);
        TextView tvConfirm = rootView.findViewById(R.id.tv_confirm);
        tvConfirm.setOnClickListener(view -> {
            if (confirmBtnClickListener == null) {
                Toast.makeText(getContext(), "监听器未配置", Toast.LENGTH_LONG).show();
            } else {
                confirmBtnClickListener.onConfirmBtnClick();
            }
        });
    }

    /**
     * 切换为编辑模式
     */
    public void changeToEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
        tvTitle.setText(isEditMode ? getResources().getString(R.string.edit_sticky_note) : getResources().getString(R.string.add_sticky_note));
    }

    public boolean isInputContentEmpty() {
        if (fetNoteContent == null || fetNoteContent.getText() == null) {
            return true;
        }
        return TextUtils.isEmpty(fetNoteContent.getText().toString());
    }

    public void clearInputContent() {
        if (fetNoteContent == null) {
            return;
        }
        fetNoteContent.setText("");
    }

    public String getInputContent() {
        if (fetNoteContent == null || fetNoteContent.getText() == null) {
            return "";
        }
        return fetNoteContent.getText().toString();
    }

    public void setConfirmBtnClickListener(ConfirmBtnClickListener confirmBtnClickListener) {
        this.confirmBtnClickListener = confirmBtnClickListener;
    }

    public FilterEditTextView getFEtNoteContent() {
        return fetNoteContent;
    }

    public interface ConfirmBtnClickListener {
        void onConfirmBtnClick();
    }
}
