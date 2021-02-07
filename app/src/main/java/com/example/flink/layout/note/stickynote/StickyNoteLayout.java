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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flink.R;
import com.example.flink.adapter.StickyNoteAdapter;
import com.example.flink.adapter.base.BaseRecyclerViewHolder;
import com.example.flink.event.DateChangeEvent;
import com.example.flink.event.SchemeChangeEvent;
import com.example.flink.greendao.gen.StickyNoteItemDao;
import com.example.flink.item.StickyNoteItem;
import com.example.flink.layout.CalendarSelectLayout;
import com.example.flink.layout.PopupInputLayout;
import com.example.flink.layout.note.NoteViewPagerBaseLayout;
import com.example.flink.mInterface.StickyNoteItemClickListener;
import com.example.flink.mInterface.StickyNoteItemDrag;
import com.example.flink.tools.DateUtil;
import com.example.flink.tools.PopUpWindowHelper;
import com.example.flink.tools.greendao.GreenDaoManager;
import com.example.flink.tools.notify.ToastUtil;
import com.haibin.calendarview.Calendar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class StickyNoteLayout extends NoteViewPagerBaseLayout {

    @BindView(R.id.rv)
    RecyclerView stickyNoteRecyclerView;
    private List<StickyNoteItem> mNoteItemList;

    private CalendarSelectLayout calendarSelectLayout;
    private PopUpWindowHelper calenderPopUpHelper;

    private PopUpWindowHelper editPopUpHelper;
    private EditStickyNoteLayout editStickyNoteLayout;

    private PopUpWindowHelper popupInputHelper;
    private PopupInputLayout popupInputLayout;

    private StickyNoteAdapter stickyNoteAdapter;

    private boolean isPopupCalendar = false;//日期选择器是否弹出来

    private InputMethodManager imm;

    private Date mDate;

    private StickyNoteItemDao stickyNoteItemDao;

    private int currentSelectItemIndex = -1;//当前选择的item的index，切换日期会自动设置为-1

    public StickyNoteLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_sticky_note;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDateChange(DateChangeEvent dateChangeEvent) {
        mDate = dateChangeEvent.getDate();
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
        mDate = DateUtil.getNowSelectedDate();
        stickyNoteItemDao = GreenDaoManager.getDaoSession(getContext()).getStickyNoteItemDao();
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
                    .setNoteContent(inputContent)
                    .build();
            //当天的笔记是不是空的，需要插入新的标记
            boolean needAddScheme = insertDatehasNoNote();

            stickyNoteItemDao.insertOrReplace(item);
            popupInputHelper.dismiss();
            popupInputLayout.clearInputContent();
            Toast.makeText(context, "新建笔记成功", Toast.LENGTH_SHORT).show();

            if (needAddScheme) {
                EventBus.getDefault().post(new SchemeChangeEvent(mDate, true));
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
        return getSelectedDayStickyNoteList().isEmpty();
    }

    private List<StickyNoteItem> getSelectedDayStickyNoteList() {
        Date startDate = DateUtil.clearDateHMS(new Date(mDate.getTime()));
        Date endDate = DateUtil.clearDateHMS(new Date(mDate.getTime() + DateUtil.DAY_IN_MILLIS));
        return stickyNoteItemDao.queryBuilder().where(StickyNoteItemDao.Properties.NoteDate.ge(startDate)
                , StickyNoteItemDao.Properties.NoteDate.lt(endDate)).list();
    }

    private void initStickyNoteRv() {
        mNoteItemList = new ArrayList<>();
        stickyNoteAdapter = new StickyNoteAdapter(getContext(), mNoteItemList);
        stickyNoteRecyclerView.setAdapter(stickyNoteAdapter);
        stickyNoteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new StickyNoteItemDrag(getContext(), mNoteItemList));
        itemTouchHelper.attachToRecyclerView(stickyNoteRecyclerView);
        stickyNoteAdapter.setStickyNoteItemClickListener(new StickyNoteItemClickListener() {
            @Override
            public void onItemClickListener(View view, BaseRecyclerViewHolder viewHolder, int position) {
                StickyNoteItem item = mNoteItemList.get(position);
                item.moveToNextStatu();
                stickyNoteItemDao.insertOrReplace(item);
                stickyNoteAdapter.notifyItemChanged(position);
            }

            @Override
            public boolean onItemLongClickListener(View view, BaseRecyclerViewHolder viewHolder, int position) {
                itemTouchHelper.startDrag(viewHolder);
                return true;
            }

            @Override
            public void onMoreBtnClickListener(View view, BaseRecyclerViewHolder viewHolder, int position) {
                if (currentSelectItemIndex >= 0 && currentSelectItemIndex < stickyNoteAdapter.getItemCount()) {
                    mNoteItemList.get(currentSelectItemIndex).setSelected(false);
                }
                if (currentSelectItemIndex == position) {
                    mNoteItemList.get(position).setSelected(false);
                    stickyNoteAdapter.notifyItemChanged(currentSelectItemIndex);
                    currentSelectItemIndex = -1;
                    editPopUpHelper.dismiss();
                } else {
                    mNoteItemList.get(position).setSelected(true);
                    stickyNoteAdapter.notifyItemChanged(currentSelectItemIndex);
                    currentSelectItemIndex = position;
                    stickyNoteAdapter.notifyItemChanged(position);
                    editStickyNoteItem(stickyNoteItemDao, position);
                }

            }

            @Override
            public boolean onMoreBtnLongClickListener(View view, BaseRecyclerViewHolder viewHolder, int position) {
                ToastUtil.show(getContext(), "长按设置按钮：" + position);
                return true;
            }
        });
    }


    private void editStickyNoteItem(StickyNoteItemDao dao, int i) {
        if (isPopupCalendar) {
            popupCalendar();
        }
        editPopUpHelper.showPopupWindow(((Activity) getContext()).findViewById(R.id.switchDateLayout), PopUpWindowHelper.LocationType.TOP_TEST);
        editStickyNoteLayout.setStickyNoteItemDao(dao);
        editStickyNoteLayout.setCurPosition(i);
        editStickyNoteLayout.setStickyNoteItemList(mNoteItemList);
        editStickyNoteLayout.setOnCompleteListener(list -> {
            mNoteItemList = list;
            stickyNoteAdapter.notifyDataSetChanged();
        });
    }

    /**
     * 查询数据库，刷新笔记页面
     */
    private void refreshData() {
        if (mNoteItemList == null || stickyNoteAdapter == null || stickyNoteItemDao == null) {
            return;
        }
        currentSelectItemIndex = -1;
        Date startDate = DateUtil.clearDateHMS(new Date(mDate.getTime()));
        Date endDate = DateUtil.clearDateHMS(new Date(mDate.getTime() + DateUtil.DAY_IN_MILLIS));
        mNoteItemList.clear();
        mNoteItemList.addAll(stickyNoteItemDao.queryBuilder().where(StickyNoteItemDao.Properties.NoteDate.ge(startDate)
                , StickyNoteItemDao.Properties.NoteDate.lt(endDate)).list());
        stickyNoteAdapter.notifyDataSetChanged();
    }

    /**
     * 查询数据库，查出所有笔记，并生成日历标记
     */
    private void refreshScheme() {
        List<StickyNoteItem> allNoteList = new ArrayList<>(stickyNoteItemDao.loadAll());
        Map<String, Calendar> map = new HashMap<>();
        for (StickyNoteItem noteItem : allNoteList) {
            Calendar calendar = DateUtil.calendarTrans(DateUtil.getCalendarByDate(noteItem.getNoteDate()));
            map.put(calendar.toString(), calendar);
        }
        calendarSelectLayout.getCalendarview().setSchemeDate(map);
    }


    private void popupCalendar() {
        if (isPopupCalendar) {
            calenderPopUpHelper.dismiss();
        } else {
            calenderPopUpHelper.showPopupWindow(((Activity) getContext()).findViewById(R.id.switchDateLayout)
                    , PopUpWindowHelper.LocationType.TOP_TEST);
        }
        isPopupCalendar = !isPopupCalendar;
    }

    @Override
    public void onClickFunction() {
        if (isPopupCalendar) {//如果在日历选择window打开的情况下，轻点也是隐藏
            popupCalendar();
        } else {
            popupInputHelper.showPopupWindow(((Activity) getContext()).getWindow().getDecorView(), PopUpWindowHelper.LocationType.CENTER);
            popupInputLayout.getFEtNoteContent().requestFocus();
            imm.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onLongClickFunction() {
        popupCalendar();
    }

    @Override
    public void onViewPagerScorll() {
        if (isPopupCalendar) {
            popupCalendar();
        }
    }

}
