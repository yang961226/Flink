package com.example.flink.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.flink.R;
import com.example.flink.item.StickyNoteItem;
import com.example.flink.mInterface.MyOnItemClickListener;

import java.util.List;

public class StickyNoteAdapter extends CommonRecyclerAdapter<StickyNoteItem> {

    private MyOnItemClickListener mOnItemClickListener;

    public StickyNoteAdapter(Context context, List<StickyNoteItem> itemList) {
        super(context, itemList);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_sticky_note_item;
    }

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        mOnItemClickListener = myOnItemClickListener;
    }

    @Override
    protected void setUi(@NonNull BaseRecyclerViewHolder holder, int position) {
        StickyNoteItem item = itemList.get(position);

        ImageView iv = holder.getView(R.id.iv_status);
        iv.setImageResource(item.getStickyNoteRes(context));

        TextView tvNoteContent = holder.getView(R.id.tv_note_content);
        tvNoteContent.setText(item.getNoteContent());

        TextView tvSelect = holder.getView(R.id.tv_select);
        tvSelect.setVisibility(item.isSelected() ? View.VISIBLE : View.GONE);

        LinearLayout llRoot = holder.getView(R.id.ll_root);
        llRoot.setBackgroundColor(item.isSelected() ?
                context.getResources().getColor(R.color.silver, null)
                : context.getResources().getColor(R.color.translate, null));
        if (mOnItemClickListener != null) {
            llRoot.setOnClickListener(v -> {
                mOnItemClickListener.onItemClickListener(v, position);
            });

            llRoot.setOnLongClickListener(v -> mOnItemClickListener.onItemLongClickListener(v, position));
        }

    }
}
