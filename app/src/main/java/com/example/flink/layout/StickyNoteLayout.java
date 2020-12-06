package com.example.flink.layout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.adapter.StickyNoteAdapter;
import com.example.flink.event.DateChangeEvent;
import com.example.flink.greendao.gen.DaoSession;
import com.example.flink.greendao.gen.StickyNoteItemDao;
import com.example.flink.item.StickyNoteItem;
import com.example.flink.tools.DateUtil;
import com.example.flink.tools.PopUpWindowHelper;
import com.example.flink.tools.greendao.GreenDaoManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class StickyNoteLayout extends NoteViewPagerBaseLayout {

    @BindView(R.id.lv)
    ListView lv;

    private List<StickyNoteItem> mNoteItemList;
    private StickyNoteAdapter mNoteAdapter;

    private CalendarSelectLayout calendarSelectLayout;
    private PopUpWindowHelper calenderPopUpHelper;

    private PopUpWindowHelper popupInputHelper;
    private PopupInputLayout popupInputLayout;

    private boolean isPopupCalendar = false;//日期选择器是否弹出来

    private InputMethodManager imm;

    private Date mDate;

    private Context context;

    private DaoSession daoSession;

    public StickyNoteLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
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

    @Override
    protected void init(Context context) {
        super.init(context);
        imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);

        mDate = DateUtil.getNowSelectedDate(context);

        daoSession = GreenDaoManager.getDaoSession(context);

        mNoteItemList = new ArrayList<>();
        mNoteAdapter = new StickyNoteAdapter(context, mNoteItemList);
        lv.setAdapter(mNoteAdapter);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            StickyNoteItem item = mNoteItemList.get(position);
            item.moveToNextStatu();
            daoSession.getStickyNoteItemDao().insertOrReplace(item);
            mNoteAdapter.notifyDataSetChanged();
        });

        calendarSelectLayout = new CalendarSelectLayout(getContext());
        calenderPopUpHelper = new PopUpWindowHelper.Builder(context)
                .setContentView(calendarSelectLayout)
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.PopupWindowTranslateThemeFromBottom)
                .setTouchable(true)
                .setFocusable(false)
                .setOutsideTouchable(false)
                .setBackgroundDrawable(new ColorDrawable(Color.WHITE))
                .build();

        popupInputLayout = new PopupInputLayout(context);
        popupInputLayout.setConfirmBtnClickListener(() -> {
            if (popupInputLayout.isInputContentEmpty()) {
                Toast.makeText(context, "输入内容为空", Toast.LENGTH_LONG).show();
                return;
            }
            String inputContent = popupInputLayout.getInputContent();
            StickyNoteItem item = StickyNoteItem.builder()
                    .setNoteContent(inputContent)
                    .setNoteDate(mDate)
                    .setOrder(1)
                    .build();
            GreenDaoManager.getDaoSession(context).getStickyNoteItemDao().insert(item);
            popupInputHelper.dismiss();
            popupInputLayout.clearInputContent();
            Toast.makeText(context, "创建笔记", Toast.LENGTH_SHORT).show();
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

    /**
     * 查询数据库，刷新笔记页面
     */
    private void refreshData() {
        if (mNoteItemList == null || mNoteAdapter == null) {
            return;
        }
        Date startDate = DateUtil.clearDateHMS(new Date(mDate.getTime()));
        Date endDate = DateUtil.clearDateHMS(new Date(mDate.getTime() + DateUtil.DAY_IN_MILLIS));
        mNoteItemList.clear();
        mNoteItemList.addAll(daoSession
                .getStickyNoteItemDao().queryBuilder()
                .where(StickyNoteItemDao.Properties.NoteDate.ge(startDate)
                        , StickyNoteItemDao.Properties.NoteDate.lt(endDate))
                .list());
        mNoteAdapter.notifyDataSetChanged();
    }

    private void popupCalendar() {
        if (isPopupCalendar) {
            calenderPopUpHelper.dismiss();
        } else {
            calenderPopUpHelper.showPopupWindow(((Activity) context).findViewById(R.id.switchDateLayout), PopUpWindowHelper.LocationType.TOP_TEST);
        }
        isPopupCalendar = !isPopupCalendar;
    }

    @Override
    public void onClickFunction() {
        if (isPopupCalendar) {//如果在日历选择window打开的情况下，轻点也是隐藏
            popupCalendar();
        } else {
            popupInputHelper.showPopupWindow(((Activity) context).getWindow().getDecorView(), PopUpWindowHelper.LocationType.CENTER);
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
            calenderPopUpHelper.dismiss();
        } else {
            popupInputHelper.dismiss();
        }
    }

}
