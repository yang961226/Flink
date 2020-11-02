package com.example.flink.layout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.adapter.StickyNoteAdapter;
import com.example.flink.item.StickyNoteItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StickyNoteLayout extends LinearLayout {

    @BindView(R.id.lv)
    ListView lv;

    private List<StickyNoteItem> mNoteItemList = new ArrayList<>();
    private StickyNoteAdapter mNoteAdapter;

    public StickyNoteLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        View.inflate(context,R.layout.layout_sticky_note,this);
        //绑定处理
        ButterKnife.bind(this);
        mNoteAdapter=new StickyNoteAdapter(getContext(), mNoteItemList);
        lv.setAdapter(mNoteAdapter);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            StickyNoteItem item=mNoteItemList.get(position);
            item.moveToNextStatus();
            mNoteAdapter.notifyDataSetChanged();
        });
        initTestNote();
    }

    private void initTestNote() {
        for (int i = 1; i < 50; i++) {
            mNoteItemList.add(StickyNoteItem
                    .builder()
                    .setNoteContent("第" + i + "条测试笔记")
                    .build());
        }
    }
}
