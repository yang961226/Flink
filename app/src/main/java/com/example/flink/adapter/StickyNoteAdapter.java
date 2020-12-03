package com.example.flink.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flink.R;
import com.example.flink.common.ViewHolder;
import com.example.flink.item.StickyNoteItem;

import java.util.List;

public class StickyNoteAdapter extends CommonAdapter<StickyNoteItem> {

    public StickyNoteAdapter(Context context, List<StickyNoteItem> mDatas) {
        super(context, mDatas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        StickyNoteItem item = (StickyNoteItem) getItem(position);

        ImageView iv = holder.getItemViewById(R.id.iv_status);
        iv.setImageResource(item.getStickyNoteRes(context));

        TextView tvNoteContent = holder.getItemViewById(R.id.tv_note_content);
        tvNoteContent.setText(item.getNoteContent());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_sticky_note_item;
    }
}
