package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.R;

public class TestNoteLayout1 extends LinearLayout {


    public TestNoteLayout1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        View.inflate(context, R.layout.layout_testnote1, this);
    }
}
