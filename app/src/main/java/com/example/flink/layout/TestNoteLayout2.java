package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.R;

public class TestNoteLayout2 extends LinearLayout {


    public TestNoteLayout2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        View.inflate(context, R.layout.layout_testnote2, this);
    }
}