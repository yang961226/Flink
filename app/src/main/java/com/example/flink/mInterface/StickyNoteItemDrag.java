package com.example.flink.mInterface;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flink.greendao.gen.DaoSession;
import com.example.flink.greendao.gen.StickyNoteItemDao;
import com.example.flink.item.StickyNoteItem;
import com.example.flink.tools.greendao.GreenDaoManager;
import com.example.flink.tools.notify.LogUtil;

import java.util.Collections;
import java.util.List;

/**
 * 便签笔记item拖拽callback
 */
public class StickyNoteItemDrag extends ItemTouchHelper.Callback {

    private DaoSession daoSession;
    private StickyNoteItemDao stickyNoteItemDao;
    private List<StickyNoteItem> itemList;

    public StickyNoteItemDrag(Context context, final List<StickyNoteItem> itemList) {
        daoSession = GreenDaoManager.getDaoSession(context);
        stickyNoteItemDao = daoSession.getStickyNoteItemDao();
        this.itemList = itemList;
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
        //拿到当前拖拽到的item的viewHolder
        int toPosition = target.getAbsoluteAdapterPosition();

        StickyNoteItem fromItem = itemList.get(fromPosition);
        StickyNoteItem toItem = itemList.get(toPosition);

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(itemList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(itemList, i, i - 1);
            }
        }

        recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

        //交换order
        long tmpOrder = fromItem.getOrder();
        fromItem.setOrder(toItem.getOrder());
        toItem.setOrder(tmpOrder);
        stickyNoteItemDao.insertOrReplace(fromItem);
        stickyNoteItemDao.insertOrReplace(toItem);
        LogUtil.d("拖拽交换测试：", String.format("内容：%s，order：%s  交换  内容：%s，order：%s", fromItem.getNoteContent()
                , fromItem.getOrder(), toItem.getNoteContent(), toItem.getOrder()));
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
