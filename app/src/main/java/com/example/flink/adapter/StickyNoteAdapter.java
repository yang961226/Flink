package com.example.flink.adapter;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.flink.R;
import com.example.flink.adapter.base.BaseRecyclerViewHolder;
import com.example.flink.adapter.base.CommonRecyclerAdapter;
import com.example.flink.item.StickyNoteItem;
import com.example.flink.mInterface.StickyNoteItemClickListener;

import java.util.List;

public class StickyNoteAdapter extends CommonRecyclerAdapter<StickyNoteItem> {

    private StickyNoteItemClickListener stickyNoteItemClickListener;

    public StickyNoteAdapter(Context context, List<StickyNoteItem> itemList) {
        super(context, itemList);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_sticky_note_item;
    }

    public void setStickyNoteItemClickListener(StickyNoteItemClickListener stickyNoteItemClickListener) {
        this.stickyNoteItemClickListener = stickyNoteItemClickListener;
    }

    @Override
    protected void setUi(@NonNull BaseRecyclerViewHolder holder, int position) {
        StickyNoteItem item = itemList.get(position);

        ImageView iv = holder.getView(R.id.iv_status);
        iv.setImageResource(item.getStickyNoteRes(context));

        TextView tvNoteContent = holder.getView(R.id.tv_note_content);
        tvNoteContent.setText(item.getNoteContent());

        LinearLayout llRoot = holder.getView(R.id.ll_root);
        llRoot.setBackgroundColor(item.isSelected() ?
                context.getResources().getColor(R.color.silver, null)
                : context.getResources().getColor(R.color.translate, null));

        LinearLayout llContent = holder.getView(R.id.ll_content);
        llContent.setOnClickListener(v -> {
            if (stickyNoteItemClickListener == null) {
                return;
            }
            stickyNoteItemClickListener.onItemClickListener(v, holder, position);
        });

        llContent.setOnLongClickListener(v -> {
            if (stickyNoteItemClickListener == null) {
                return false;
            }
            return stickyNoteItemClickListener.onItemLongClickListener(v, holder, position);
        });

        ImageButton ibMore = holder.getView(R.id.ib_more);
        ibMore.setOnClickListener(v -> {
            if (stickyNoteItemClickListener == null) {
                return;
            }
            stickyNoteItemClickListener.onMoreBtnClickListener(v, holder, position);
        });
        ibMore.setOnLongClickListener(v -> {
            if (stickyNoteItemClickListener == null) {
                return false;
            }
            return stickyNoteItemClickListener.onMoreBtnLongClickListener(v, holder, position);
        });


    }
}
