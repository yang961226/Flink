package com.example.flink.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.example.flink.adapter.StickyNoteAdapter;
import com.example.flink.item.StickyNoteItem;

import java.util.List;

import butterknife.ButterKnife;

public class StickyNoteRecyclerView extends RecyclerView {

    public StickyNoteRecyclerView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        //绑定处理
        ButterKnife.bind(this);


    }



}
