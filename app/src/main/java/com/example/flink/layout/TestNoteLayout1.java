package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.event.DateChangeEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TestNoteLayout1 extends NoteViewPagerBaseLayout {


    public TestNoteLayout1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDateChange(DateChangeEvent dateChangeEvent) {

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
}
