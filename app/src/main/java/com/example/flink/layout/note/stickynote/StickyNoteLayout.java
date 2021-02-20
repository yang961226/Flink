package com.example.flink.layout.note.stickynote;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flink.R;
import com.example.flink.adapter.StickyNoteAdapter;
import com.example.flink.adapter.base.BaseRecyclerViewHolder;
import com.example.flink.common.StickyNoteItemDrag;
import com.example.flink.event.DateChangeEvent;
import com.example.flink.event.SchemeChangeEvent;
import com.example.flink.item.StickyNoteItem;
import com.example.flink.layout.CalendarSelectLayout;
import com.example.flink.layout.PopupInputLayout;
import com.example.flink.layout.note.NoteViewPagerBaseLayout;
import com.example.flink.mInterface.StickyNoteItemClickListener;
import com.example.flink.tools.DateUtil;
import com.example.flink.tools.PopUpWindowHelper;
import com.example.flink.tools.greendao.dataHelper.StickyNoteDaoHelper;
import com.example.flink.tools.notify.LogUtil;
import com.example.flink.tools.notify.ToastUtil;
import com.haibin.calendarview.Calendar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class StickyNoteLayout extends NoteViewPagerBaseLayout {

    @BindView(R.id.rv)
    RecyclerView stickyNoteRecyclerView;
    private List<StickyNoteItem> mNoteItemList;

    //日历选择器
    private PopUpWindowHelper calenderPopUpHelper;
    private CalendarSelectLayout calendarSelectLayout;

    //编辑条弹窗
    private PopUpWindowHelper editPopUpHelper;
    private EditStickyNoteLayout editStickyNoteLayout;

    //新建弹窗
    private PopUpWindowHelper popupInputHelper;
    private PopupInputLayout popupInputLayout;

    private StickyNoteAdapter stickyNoteAdapter;

    private InputMethodManager imm;

    private int currentSelectItemIndex = -1;//当前选择的item的index，切换日期会自动设置为-1

    private StickyNoteDaoHelper stickyNoteDaoHelper;

    public StickyNoteLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_sticky_note;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDateChange(DateChangeEvent dateChangeEvent) {
        refreshData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSchemeChangeEvent(SchemeChangeEvent schemeChangeEvent) {
        if (schemeChangeEvent.getDate() == null) {
            return;
        }
        if (schemeChangeEvent.isAdd()) {
            calendarSelectLayout.getCalendarview()
                    .addSchemeDate(DateUtil.calendarTrans(schemeChangeEvent.getDate()));
        } else {
            calendarSelectLayout.getCalendarview()
                    .removeSchemeDate(DateUtil.calendarTrans(schemeChangeEvent.getDate()));
        }
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        stickyNoteDaoHelper = StickyNoteDaoHelper.getInstance();
        initStickyNoteRv();

        //初始化3个弹窗
        initCalenderSelect(context);
        initPopupInput(context);
        initEdit(context);

        refreshData();
        refreshScheme();
    }

    private void initCalenderSelect(Context context) {
        calendarSelectLayout = new CalendarSelectLayout(context);
        calenderPopUpHelper = new PopUpWindowHelper
                .Builder(context)
                .setContentView(calendarSelectLayout)
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.PopupWindowTranslateThemeFromBottom)
                .setTouchable(true)
                .setFocusable(false)
                .setOutsideTouchable(false)
                .setBackgroundDrawable(new ColorDrawable(Color.WHITE))
                .build();
    }

    private void initPopupInput(Context context) {
        popupInputLayout = new PopupInputLayout(context);
        popupInputLayout.setConfirmBtnClickListener(() -> {
            if (popupInputLayout.isInputContentEmpty()) {
                Toast.makeText(context, "输入内容为空", Toast.LENGTH_LONG).show();
                return;
            }
            String inputContent = popupInputLayout.getInputContent();
            StickyNoteItem item = StickyNoteItem.builder()
                    .setNoteDate(DateUtil.getNowSelectedDate())
                    .setNoteContent(inputContent)
                    .build();
            //当天的笔记是不是空的，需要插入新的标记
            boolean needAddScheme = insertDatehasNoNote();

            stickyNoteDaoHelper.insertOrReplace(item);
            popupInputHelper.dismiss();
            popupInputLayout.clearInputContent();
            Toast.makeText(context, "新建笔记成功", Toast.LENGTH_SHORT).show();

            if (needAddScheme) {
                EventBus.getDefault().post(new SchemeChangeEvent(DateUtil.getNowSelectedDate(), true));
            }
            refreshData();
        });
        popupInputHelper = new PopUpWindowHelper.Builder(context)
                .setContentView(popupInputLayout)
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.PopupWindowTranslateThemeFromBottom)
                .setTouchable(true)
                .setFocusable(true)
                .setOutsideTouchable(false)
                .setBackgroundDrawable(new ColorDrawable(Color.WHITE))
                .build();
    }

    private void initEdit(Context context) {
        editStickyNoteLayout = new EditStickyNoteLayout(context);
        editPopUpHelper = new PopUpWindowHelper.Builder(context)
                .setContentView(editStickyNoteLayout)
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.PopupWindowTranslateThemeFromBottom)
                .setTouchable(true)
                .setFocusable(false)
                .setOutsideTouchable(true)
                .setBackgroundDrawable(new ColorDrawable(Color.WHITE))
                .setOnDismissListener(() -> {
                    if (currentSelectItemIndex != -1 && !mNoteItemList.isEmpty()) {
                        mNoteItemList.get(currentSelectItemIndex).setSelected(false);
                        stickyNoteAdapter.notifyItemChanged(currentSelectItemIndex);
                    }
                })
                .build();
    }


    /**
     * 插入笔记的那一天不存在笔记
     *
     * @return true:不存在笔记 false:存在笔记
     */
    private boolean insertDatehasNoNote() {
        return stickyNoteDaoHelper.queryNotesOfOneDay(DateUtil.getNowSelectedDate()).isEmpty();
    }

    private void initStickyNoteRv() {
        mNoteItemList = new ArrayList<>();
        stickyNoteAdapter = new StickyNoteAdapter(getContext(), mNoteItemList);
        stickyNoteRecyclerView.setAdapter(stickyNoteAdapter);
        stickyNoteRecyclerView.addItemDecoration(new DividerItemDecoration(getContext()
                , DividerItemDecoration.VERTICAL));
        stickyNoteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stickyNoteAdapter.setStickyNoteItemClickListener(new StickyNoteItemClickListener() {
            @Override
            public void onItemClickListener(View view, BaseRecyclerViewHolder viewHolder, int position) {
                StickyNoteItem item = mNoteItemList.get(position);
                item.moveToNextStatu();
                stickyNoteDaoHelper.insertOrReplace(item);
                stickyNoteAdapter.notifyItemChanged(position);
            }

            @Override
            public boolean onItemLongClickListener(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return true;
            }

            @Override
            public void onMoreBtnClickListener(View view, BaseRecyclerViewHolder viewHolder, int position) {
                if (calenderPopUpHelper.isShowing()) {
                    calenderPopUpHelper.dismiss();
                }
                if (currentSelectItemIndex == position) {//选中的是已经选中的，则反选
                    mNoteItemList.get(position).setSelected(false);
                    stickyNoteAdapter.notifyItemChanged(currentSelectItemIndex);
                    currentSelectItemIndex = -1;
                    editPopUpHelper.dismiss();
                } else {
                    mNoteItemList.get(position).setSelected(true);
                    stickyNoteAdapter.notifyItemChanged(position);
                    currentSelectItemIndex = position;
                    editStickyNoteItem(position);
                }

            }

            @Override
            public boolean onMoreBtnLongClickListener(View view, BaseRecyclerViewHolder viewHolder, int position) {
                ToastUtil.show(getContext(), "长按设置按钮：" + position);
                return true;
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new StickyNoteItemDrag(mNoteItemList, stickyNoteAdapter));
        itemTouchHelper.attachToRecyclerView(stickyNoteRecyclerView);
    }

    private void editStickyNoteItem(int i) {

        editPopUpHelper.showPopupWindow(((Activity) getContext()).findViewById(R.id.switchDateLayout), PopUpWindowHelper.LocationType.TOP_TEST);
        editStickyNoteLayout.setCurPosition(i);
        editStickyNoteLayout.setStickyNoteItemList(mNoteItemList);
        editStickyNoteLayout.setOnCompleteListener((changePosition, isDelete) -> {
            stickyNoteAdapter.notifyItemRemoved(changePosition);
            if (isDelete) {
                currentSelectItemIndex = -1;//原来选中的item已经被删除了
            }
        });
    }

    /**
     * 查询数据库，刷新笔记页面
     */
    private void refreshData() {
        if (mNoteItemList == null || stickyNoteAdapter == null) {
            return;
        }
        currentSelectItemIndex = -1;

        mNoteItemList.clear();
        mNoteItemList.addAll(stickyNoteDaoHelper.queryNotesOfOneDay(DateUtil.getNowSelectedDate()));
        for (int i = 0; i < mNoteItemList.size(); i++) {
            LogUtil.d("测试", mNoteItemList.get(i).toString() + "\n");
        }
        stickyNoteAdapter.notifyDataSetChanged();
    }

    /**
     * 查询数据库，查出所有笔记，并生成日历标记
     */
    private void refreshScheme() {
        List<StickyNoteItem> allNoteList = new ArrayList<>(stickyNoteDaoHelper.loadAllNotes());
        Map<String, Calendar> map = new HashMap<>();
        for (StickyNoteItem noteItem : allNoteList) {
            Calendar calendar = DateUtil.calendarTrans(DateUtil.getCalendarByDate(noteItem.getNoteDate()));
            map.put(calendar.toString(), calendar);
        }
        calendarSelectLayout.getCalendarview().setSchemeDate(map);
    }


    private void popupCalendar() {
        calenderPopUpHelper.showPopupWindow(((Activity) getContext()).findViewById(R.id.switchDateLayout)
                , PopUpWindowHelper.LocationType.TOP_TEST);
    }

    @Override
    public void onClickFunction() {
        if (calenderPopUpHelper.isShowing()) {
            calenderPopUpHelper.dismiss();
        }
        popupInputHelper.showPopupWindow(((Activity) getContext()).getWindow().getDecorView(), PopUpWindowHelper.LocationType.CENTER);
        popupInputLayout.getFEtNoteContent().requestFocus();
        imm.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onLongClickFunction() {
        if (calenderPopUpHelper.isShowing()) {
            calenderPopUpHelper.dismiss();
            return;
        }
        popupCalendar();
    }

    @Override
    public void onViewPagerScorll() {
//        if (isPopupCalendar) {
//            popupCalendar();
//        }
    }

}
