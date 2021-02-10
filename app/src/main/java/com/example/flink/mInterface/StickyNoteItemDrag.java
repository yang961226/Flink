package com.example.flink.mInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flink.FlinkApplication;
import com.example.flink.R;
import com.example.flink.item.StickyNoteItem;
import com.example.flink.tools.greendao.dataHelper.StickyNoteDaoHelper;
import com.example.flink.tools.notify.LogUtil;

import java.util.Collections;
import java.util.List;

/**
 * 便签笔记item拖拽callback
 */
public class StickyNoteItemDrag extends ItemTouchHelper.Callback {

    private List<StickyNoteItem> itemList;

    private StickyNoteDaoHelper stickyNoteDaoHelper;

    public StickyNoteItemDrag(final List<StickyNoteItem> itemList) {
        this.itemList = itemList;
        stickyNoteDaoHelper = StickyNoteDaoHelper.getInstance();
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
        int fromPosition = viewHolder.getAbsoluteAdapterPosition();
        //得到当前拖拽到的viewHolder的Position
        int toPosition = target.getAbsoluteAdapterPosition();

        StickyNoteItem fromItem = itemList.get(fromPosition);
        StickyNoteItem toItem = itemList.get(toPosition);

        Collections.swap(itemList, fromPosition, toPosition);
        recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
        LogUtil.d("拖拽日志测试", String.format("%s    %s", fromPosition, toPosition));

        //交换order
        long tmpOrder = fromItem.getOrder();
        fromItem.setOrder(toItem.getOrder());
        toItem.setOrder(tmpOrder);
        stickyNoteDaoHelper.insertOrReplace(fromItem);
        stickyNoteDaoHelper.insertOrReplace(toItem);
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
        viewHolder.itemView.setBackgroundColor(
                FlinkApplication.getContext().getResources().getColor(R.color.translate, null));
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
}
