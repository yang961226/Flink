package com.example.flink.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.flink.R;
import com.example.flink.common.ViewHolder;
import com.example.flink.item.StickyNoteItem;

import java.util.List;

public class StickyNoteAdapter extends CommenAdapter<StickyNoteItem> {

    public StickyNoteAdapter(Context context, List<StickyNoteItem> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        StickyNoteItem item= (StickyNoteItem) getItem(position);

        TextView tvNoteContet=holder.getItemViewById(R.id.tv_note_content);
        tvNoteContet.setText(item.getmNoteContent());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_sticky_note_item;
    }
}
