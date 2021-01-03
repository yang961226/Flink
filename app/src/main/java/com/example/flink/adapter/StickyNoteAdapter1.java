package com.example.flink.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.flink.R;
import com.example.flink.common.ViewHolder;
import com.example.flink.item.StickyNoteItem;

import java.util.List;

/**
 * 注：已弃用
 */
@Deprecated
public class StickyNoteAdapter1 extends CommonAdapter<StickyNoteItem> {

    public StickyNoteAdapter1(Context context, List<StickyNoteItem> mDatas) {
        super(context, mDatas);
    }

    @Override
    public void updateData(List<StickyNoteItem> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    protected void setUI(ViewHolder holder, int position, Context context) {
        StickyNoteItem item = (StickyNoteItem) getItem(position);

        ImageView iv = holder.getItemViewById(R.id.iv_status);
        iv.setImageResource(item.getStickyNoteRes(context));

        TextView tvNoteContent = holder.getItemViewById(R.id.tv_note_content);
        tvNoteContent.setText(item.getNoteContent());

        TextView tvSelect = holder.getItemViewById(R.id.tv_select);
        tvSelect.setVisibility(item.isSelected() ? View.VISIBLE : View.GONE);

        LinearLayout llRoot = holder.getItemViewById(R.id.ll_root);
        llRoot.setBackgroundColor(item.isSelected() ?
                context.getResources().getColor(R.color.silver, null)
                : context.getResources().getColor(R.color.translate, null));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_sticky_note_item;
    }
}
