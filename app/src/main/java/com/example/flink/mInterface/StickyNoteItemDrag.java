package com.example.flink.mInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 便签笔记item拖拽callback
 */
public class StickyNoteItemDrag extends ItemTouchHelper.Callback {

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFrlg = 0;
        dragFrlg = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFrlg, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        //起点
        int fromPosition = viewHolder.getBindingAdapterPosition();
        //终点
        int toPosition = target.getBindingAdapterPosition();

        recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        // TODO: 2021/1/16
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        // TODO: 2021/1/16
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }
}
