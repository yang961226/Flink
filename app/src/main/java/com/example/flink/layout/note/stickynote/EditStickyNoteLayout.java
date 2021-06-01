package com.example.flink.layout.note.stickynote;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.flink.R;
import com.example.flink.adapter.StickyNoteAdapter;
import com.example.flink.item.StickyNoteItem;
import com.example.flink.tools.PopUpWindowHelper;
import com.example.flink.tools.greendao.dataHelper.StickyNoteDaoHelper;
import com.example.flink.tools.notify.MessageDialogHelper;

import java.util.List;
/***
 * Created by : 村长只会去森林打野
 * Created on : <com.example.flink.layout>
 * Created on : 2020 12 16:31
 *
 * My motto   : 我在皮皮虾凑数的日子
 */
public class EditStickyNoteLayout extends LinearLayout {

    private final StickyNoteDaoHelper stickyNoteDaoHelper;
    private List<StickyNoteItem> stickyNoteItemList;
    private int curPosition;
    private MessageDialogHelper messageDialogHelper;//删除提示

    public EditStickyNoteLayout(Context context) {
        super(context);
        View view = View.inflate(context, R.layout.layout_edit_note, this);
        findViewById(view);
        stickyNoteDaoHelper = StickyNoteDaoHelper.getInstance();
    }

    private void findViewById(View view) {
//        ImageView ivArrowDown = view.findViewById(R.id.iv_arrow_down);
//        ImageView ivArrowUp = view.findViewById(R.id.iv_arrow_up);
//        RelativeLayout rlSub = view.findViewById(R.id.rl_sub);
//        RelativeLayout rlEdit = view.findViewById(R.id.rl_edit);
        RelativeLayout rlDelete = view.findViewById(R.id.rl_delete);
        rlDelete.setOnClickListener(view1 -> deleteItem());
    }

    public void setStickyNoteItemList(List<StickyNoteItem> stickyNoteItemList) {
        this.stickyNoteItemList = stickyNoteItemList;
    }

    public void setCurPosition(int curPosition) {
        this.curPosition = curPosition;
    }

    public void setStickyNoteAdapter(StickyNoteAdapter stickyNoteAdapter) {
    }

//    private void moveDown() {
//        tmpPosition++;
//        if (tmpPosition < stickyNoteItemList.size() && stickyNoteItemList != null && curPosition < stickyNoteItemList.size()) {
//            Collections.swap(stickyNoteItemList, curPosition, tmpPosition);
//            curPosition++;
//            tmpPosition = curPosition;
//        } else {
//            tmpPosition = curPosition;
//        }
//
//        LogUtil.d("moveDown: curPosition = " + curPosition + "  tmpPosition = " + tmpPosition);
//    }
//
//    private void moveUp() {
//        tmpPosition--;
//        if (tmpPosition >= 0 && stickyNoteItemList != null && curPosition >= 0) {
//            Collections.swap(stickyNoteItemList, curPosition, tmpPosition);
//            curPosition--;
//            tmpPosition = curPosition;
//        } else {
//            tmpPosition = curPosition;
//        }
//
//        LogUtil.d("moveUp: curPosition = " + curPosition + "  tmpPosition = " + tmpPosition);
//    }

    private void deleteItem() {
        if (messageDialogHelper == null) {
            messageDialogHelper = new MessageDialogHelper(getContext());
        }
        messageDialogHelper.showMsgDialog(
                "通知",
                "是否删除该笔记：" + stickyNoteItemList.get(curPosition).getNoteContent()
                , new MessageDialogHelper.OnDialogButtonClickListener() {
                    @Override
                    public void onOkClick(PopUpWindowHelper popUpWindowHelper, View v) {
                        stickyNoteDaoHelper.delete(stickyNoteItemList.get(curPosition));
                        stickyNoteItemList.remove(curPosition);
                        onCompleteListener.onComplete(curPosition, true);
                        messageDialogHelper.dismissDialog();
                    }

                    @Override
                    public void onCancleClick(PopUpWindowHelper popUpWindowHelper, View v) {
                        messageDialogHelper.dismissDialog();
                    }
                });

    }

    /*内部回调*/
    private OnCompleteListener onCompleteListener;

    public interface OnCompleteListener {
        void onComplete(int changePosition, boolean isDelete);
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }
}
