package com.example.flink.view.dialog;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.flink.R;
import com.example.flink.utils.LogUtil;

public class LongClickDialog extends BaseDialog {
    private Button btEdie;
    private Button btDelete;
    private Button btSubNote;
    private TextView tvTitle;
    private String mTitle;

    public LongClickDialog(Context context) {
        super(context);
    }

    public LongClickDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LongClickDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_long_click_note);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        btEdie = fb(R.id.bt_lc_edit);
        btDelete = fb(R.id.bt_lc_delete);
        btSubNote = fb(R.id.bt_lc_sub);
        tvTitle = fb(R.id.tv_lc_title);
    }

    public void initData() {
        if (!TextUtils.isEmpty(mTitle)) {
            tvTitle.setText(mTitle);
        } else {
            tvTitle.setText(getInvalidTitle());
        }
    }

    private void initEvent() {
        btEdie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditOnClickListener != null) {
                    mEditOnClickListener.onEditClick();
                    dismiss();
                } else {
                    LogUtil.e("没有设置 长按 编辑事件");
                }
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDeleteOnClickListener != null) {
                    mDeleteOnClickListener.onDeleteClick();
                    dismiss();
                } else {
                    LogUtil.e("没有设置 长按 删除事件");
                }
            }
        });

        btSubNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSubNoteOnClickListener != null) {
                    mSubNoteOnClickListener.onSubNoteClick();
                    dismiss();
                } else {
                    LogUtil.e("没有设置 长按 子笔记事件");
                }
            }
        });
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    /*点击事件*/
    private EditOnClickListener mEditOnClickListener;
    private DeleteOnClickListener mDeleteOnClickListener;
    private SubNoteOnClickListener mSubNoteOnClickListener;

    public void setSubNoteOnClickListener(SubNoteOnClickListener subNoteOnClickListener) {
        this.mSubNoteOnClickListener = subNoteOnClickListener;
    }

    public void setEditOnClickListener(EditOnClickListener editOnClickListener) {
        this.mEditOnClickListener = editOnClickListener;
    }

    public void setDeleteOnClickListener(DeleteOnClickListener deleteOnClickListener) {
        this.mDeleteOnClickListener = deleteOnClickListener;
    }

    public interface EditOnClickListener {
        void onEditClick();
    }

    public interface DeleteOnClickListener {
        void onDeleteClick();
    }

    public interface SubNoteOnClickListener {
        void onSubNoteClick();
    }
}
