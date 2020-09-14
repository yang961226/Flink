package com.example.flink.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.flink.R;
import com.example.flink.adapter.StickyNoteAdapter;
import com.example.flink.item.StickyNoteItem;
import com.example.flink.view.StickyNoteRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class StickyNoteFragment extends FlinkBaseFragment {

    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    private List<StickyNoteItem> mNoteItemList=new ArrayList<>();
    private StickyNoteAdapter mNoteAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_sticky_note;
    }

    @Override
    protected void loadData() {
        StickyNoteRecyclerView recyclerView=buildStickyNoteView();
        initTestNote();
        recyclerView.setAdapter(new StickyNoteAdapter(mNoteItemList));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        llRoot.addView(recyclerView);
    }

    private StickyNoteRecyclerView buildStickyNoteView(){
        StickyNoteRecyclerView stickyNoteRecyclerView=new StickyNoteRecyclerView(getContext(),null);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        stickyNoteRecyclerView.setLayoutParams(layoutParams);
        return stickyNoteRecyclerView;
    }

    @Override
    protected boolean isUseViewPager() {
        return true;
    }

    private void initTestNote(){
        for(int i=1;i<50;i++){
            mNoteItemList.add(StickyNoteItem
                    .builder()
                    .setNoteContent("第"+i+"条测试笔记")
                    .build());
        }
    }
}
