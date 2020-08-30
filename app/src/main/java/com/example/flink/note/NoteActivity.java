package com.example.flink.note;

import android.os.Message;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.flink.NoteBaseActivity;
import com.example.flink.R;

import butterknife.BindView;

public class NoteActivity extends NoteBaseActivity {
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.ll_center)
    LinearLayout llCenter;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        return false;
    }
}
