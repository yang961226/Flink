package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.event.DateChangeEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TestNoteLayout2 extends NoteViewPagerBaseLayout {

    public TestNoteLayout2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDateChange(DateChangeEvent dateChangeEvent) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_testnote2;
    }

    @Override
    public void onClickFunction() {
        Toast.makeText(getContext(), "TestNoteLayout2点击", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClickFunction() {
        Toast.makeText(getContext(), "TestNoteLayout2长按", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewPagerScorll() {

    }
}