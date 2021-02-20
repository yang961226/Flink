package com.example.flink.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flink.FlinkApplication;
import com.example.flink.R;
import com.example.flink.adapter.StickyNoteAdapter;
import com.example.flink.item.StickyNoteItem;
import com.example.flink.tools.greendao.dataHelper.StickyNoteDaoHelper;

import java.util.Collections;
import java.util.List;

/**
 * 便签笔记item拖拽callback
 */
public class StickyNoteItemDrag extends ItemTouchHelper.Callback {

    private List<StickyNoteItem> itemList;

    private StickyNoteDaoHelper stickyNoteDaoHelper;

    private StickyNoteAdapter adapter;

    public StickyNoteItemDrag(final List<StickyNoteItem> itemList, StickyNoteAdapter adapter) {
        this.itemList = itemList;
        stickyNoteDaoHelper = StickyNoteDaoHelper.getInstance();
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFrlg;
        dragFrlg = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFrlg, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        //得到当前拖拽的viewHolder的Position
        int fromPosition = viewHolder.getAdapterPosition();
        //得到当前拖拽到的viewHolder的Position
        int toPosition = target.getAdapterPosition();
        //交换order
        StickyNoteItem fromItem = itemList.get(fromPosition);
        StickyNoteItem toItem = itemList.get(toPosition);
        long tmpOrder = fromItem.getOrder();
        fromItem.setOrder(toItem.getOrder());
        toItem.setOrder(tmpOrder);
        stickyNoteDaoHelper.insertOrReplace(fromItem);
        stickyNoteDaoHelper.insertOrReplace(toItem);

        if (fromPosition < toPosition) {
            //从上往下拖动，每滑动一个item，都将list中的item向下交换，向上滑同理。
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(itemList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(itemList, i, i - 1);
            }
        }
        adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(
                    FlinkApplication.getContext().getResources().getColor(R.color.silver, null));
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
}
