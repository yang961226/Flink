package com.example.flink.layout;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.flink.R;
import com.example.flink.greendao.gen.StickyNoteItemDao;
import com.example.flink.item.StickyNoteItem;
import com.example.flink.utils.LogUtil;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * Created by : 村长只会去森林打野
 * Created on : <com.example.flink.layout>
 * Created on : 2020 12 16:31
 *
 * My motto   : 我在皮皮虾凑数的日子
 */
public class EditStickyNoteLayout extends LinearLayout {

    private List<StickyNoteItem> stickyNoteItemList;
    private StickyNoteItemDao stickyNoteItemDao;
    private int curPosition;
    private int tmpPosition;

    public EditStickyNoteLayout(Context context) {
        super(context);
        View.inflate(context, R.layout.layout_edit_note, this);
        //绑定处理
        ButterKnife.bind(this);
    }

    public void setStickyNoteItemList(List<StickyNoteItem> stickyNoteItemList) {
        this.stickyNoteItemList = stickyNoteItemList;
    }

    public void setCurPosition(int curPosition) {
        tmpPosition = curPosition;
        this.curPosition = curPosition;
        LogUtil.e("setCurPosition = " + curPosition);
    }

    public void setStickyNoteItemDao(StickyNoteItemDao stickyNoteItemDao) {
        this.stickyNoteItemDao = stickyNoteItemDao;
    }

    @OnClick({
            R.id.iv_arrow_down, R.id.iv_arrow_up,
            R.id.rl_delete, R.id.rl_sub, R.id.rl_edit,
    })
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_arrow_down:
                moveDown();
                onCompleteListener.onComplete(stickyNoteItemList);
                break;
            case R.id.iv_arrow_up:
                moveUp();
                onCompleteListener.onComplete(stickyNoteItemList);
                break;
            case R.id.rl_delete:
                deleteItem();
                onCompleteListener.onComplete(stickyNoteItemList);
                break;
            case R.id.rl_sub:
                break;
            case R.id.rl_edit:
                break;
            default:
                break;
        }
    }

    private void moveDown() {
        tmpPosition++;
        if (tmpPosition < stickyNoteItemList.size() && stickyNoteItemList != null && curPosition < stickyNoteItemList.size()) {
            Collections.swap(stickyNoteItemList, curPosition, tmpPosition);
            curPosition++;
            tmpPosition = curPosition;
        } else {
            tmpPosition = curPosition;
        }

        LogUtil.d("moveDown: curPosition = " + curPosition + "  tmpPosition = " + tmpPosition);
    }

    private void moveUp() {
        tmpPosition--;
        if (tmpPosition >= 0 && stickyNoteItemList != null && curPosition >= 0) {
            Collections.swap(stickyNoteItemList, curPosition, tmpPosition);
            curPosition--;
            tmpPosition = curPosition;
        } else {
            tmpPosition = curPosition;
        }

        LogUtil.d("moveUp: curPosition = " + curPosition + "  tmpPosition = " + tmpPosition);
    }

    private void deleteItem() {
        if (stickyNoteItemDao != null || stickyNoteItemList != null) {
            stickyNoteItemDao.delete(stickyNoteItemList.get(curPosition));
            stickyNoteItemList.remove(curPosition);
        }
    }

    /*内部回调*/
    private OnCompleteListener onCompleteListener;

    public interface OnCompleteListener {
        void onComplete(List<StickyNoteItem> list);
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }
}
