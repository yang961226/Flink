package com.example.flink.mInterface;

import android.view.View;

import com.example.flink.adapter.base.BaseRecyclerViewHolder;

public interface StickyNoteItemClickListener {

    void onItemClickListener(View view, BaseRecyclerViewHolder viewHolder, int position);

    boolean onItemLongClickListener(View view, BaseRecyclerViewHolder viewHolder, int position);

    void onMoreBtnClickListener(View view, BaseRecyclerViewHolder viewHolder, int position);

    boolean onMoreBtnLongClickListener(View view, BaseRecyclerViewHolder viewHolder, int position);


}
