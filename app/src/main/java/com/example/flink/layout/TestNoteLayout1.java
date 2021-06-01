package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.event.DateChangeEvent;
import com.example.flink.layout.note.NoteViewPagerBaseLayout;
import com.example.flink.tools.notify.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class TestNoteLayout1 extends NoteViewPagerBaseLayout {
    private ExpandableLayout el;
    private TextView tvExpandPercent;
    private TextView tvExpandRange;

    public TestNoteLayout1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDateChange(DateChangeEvent dateChangeEvent) {
        //占位
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        initViewById();
    }

    private void initViewById() {
        el = rootView.findViewById(R.id.el);
        tvExpandPercent = rootView.findViewById(R.id.tv_expand_percent);
        tvExpandRange = rootView.findViewById(R.id.tv_expand_range);
        el.setDuration(1500);
        el.setInterpolator(new AccelerateDecelerateInterpolator());
        el.setChangeAplhaWhileExpanding(true);
        el.setOnExpandingListener(new ExpandableLayout.OnExpandingListener() {
            @Override
            public void onExpandingInterpolatedTime(float interpolatedTime, boolean isDown) {
                tvExpandPercent.setText(interpolatedTime + "/1.0");
            }

            @Override
            public void onExpandingRange(int expandRange, boolean isDown) {
                tvExpandRange.setText(expandRange + "");
            }

            @Override
            public void onExpandingEnd(int expandRange, boolean isDown) {
                if (isDown) {
                    ToastUtil.show(getContext(), "向下扩展" + expandRange, true);
                } else {
                    ToastUtil.show(getContext(), "向上收缩" + expandRange, true);
                }

            }
        });
        rootView.findViewById(R.id.btn_expand).setOnClickListener(view -> el.expand());
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.layout_testnote1;
    }

    @Override
    public void onClickFunction() {
        Toast.makeText(getContext(), "TestNoteLayout1点击", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClickFunction() {
        Toast.makeText(getContext(), "TestNoteLayout1长按", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewPagerScorll() {

    }
}
