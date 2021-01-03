package com.example.flink.layout;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flink.R;
import com.example.flink.view.ExpandableLayout;
import com.example.flink.view.FilterEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupInputLayout extends LinearLayout {

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.fet_note_content)
    FilterEditText fetNoteContent;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.iv_expand)
    ImageView ivExpand;
    @BindView(R.id.el)
    ExpandableLayout el;

    private int maxWordNum = 15;//默认值

    private ConfirmBtnClickListener confirmBtnClickListener;

    private static final int SRC_PLUS_ID = R.drawable.ic_plus_circle_gray;
    private static final int SRC_REDUCE_ID = R.drawable.ic_reduce_circle_gray;

    public PopupInputLayout(Context context) {
        super(context);
        View.inflate(context, R.layout.layout_popup_input, this);
        //绑定处理
        ButterKnife.bind(this);
        maxWordNum = getResources().getInteger(R.integer.sticky_note_popup_input_max_length);
        tvProgress.setText(0 + "/" + maxWordNum);
        fetNoteContent.init(maxWordNum);
        fetNoteContent.setOnWordNumChangeListener(wordNumAfterChange -> tvProgress.setText(wordNumAfterChange + "/" + maxWordNum));

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

    public FilterEditText getFEtNoteContent() {
        return fetNoteContent;
    }

    @OnClick(R.id.btn_confirm)
    public void onConfirmClicked() {
        if (confirmBtnClickListener == null) {
            Toast.makeText(getContext(), "监听器未配置", Toast.LENGTH_LONG).show();
        } else {
            confirmBtnClickListener.onConfirmBtnClick();
        }
    }

    @OnClick(R.id.iv_expand)
    public void onExpandClicked() {
//        if(el.isPlayingAnim()){
//            return;
//        }
//        el.expand();
//        ivExpand.setImageResource(el.isExpand()?SRC_REDUCE_ID:SRC_PLUS_ID);
    }

    public interface ConfirmBtnClickListener {
        void onConfirmBtnClick();
    }
}
