package com.example.flink.layout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.adapter.StickyNoteAdapter;
import com.example.flink.item.StickyNoteItem;
import com.example.flink.tools.PopUpWindowHelper;
import com.example.flink.tools.ViewTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class StickyNoteLayout extends NoteViewPagerBaseLayout {

    @BindView(R.id.lv)
    ListView lv;

    private List<StickyNoteItem> mNoteItemList = new ArrayList<>();
    private StickyNoteAdapter mNoteAdapter;

    private ViewGroup calendarSelectLayout;
    private PopUpWindowHelper calenderPopUpHelper;

    private PopUpWindowHelper inputPopUpHelper;
    private PopupInputLayout popupInputLayout;

    private boolean isPopupCalendar=false;//日期选择器是否弹出来

    private InputMethodManager imm;

    public StickyNoteLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_sticky_note;
    }


    @Override
    protected void init(Context context) {
        super.init(context);
        mNoteItemList=new ArrayList<>();
        imm= (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
        mNoteAdapter=new StickyNoteAdapter(getContext(), mNoteItemList);
        lv.setAdapter(mNoteAdapter);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            StickyNoteItem item=mNoteItemList.get(position);
            item.moveToNextStatus();
            mNoteAdapter.notifyDataSetChanged();
        });
        calendarSelectLayout = ViewTools.buildCalendarSelectLayout(getContext());
        calenderPopUpHelper = new PopUpWindowHelper.Builder(getContext())
                .setContentView(calendarSelectLayout)
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.PopupWindowTranslateThemeFromBottom)
                .setTouchable(true)
                .setFocusable(false)
                .setOutsideTouchable(false)
                .setBackgroundDrawable(new ColorDrawable(Color.WHITE))
                .build();

        popupInputLayout=new PopupInputLayout(getContext());
        inputPopUpHelper=new PopUpWindowHelper.Builder(getContext())
                .setContentView(popupInputLayout)
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.PopupWindowTranslateThemeFromBottom)
                .setTouchable(true)
                .setFocusable(true)
                .setOutsideTouchable(false)
                .setBackgroundDrawable(new ColorDrawable(Color.WHITE))
                .build();
        initTestNote();
    }

    private void initTestNote() {
        for (int i = 1; i < 50; i++) {
            mNoteItemList.add(StickyNoteItem
                    .builder()
                    .setNoteContent("第" + i + "条测试笔记")
                    .build());
        }
    }

    private void popupCalendar(){
        if(isPopupCalendar){
            calenderPopUpHelper.dismiss();
        }else{
            calenderPopUpHelper.showPopupWindow(((Activity)getContext()).findViewById(R.id.switchDateLayout), PopUpWindowHelper.LocationType.TOP_TEST);
        }
        isPopupCalendar=!isPopupCalendar;
    }

    @Override
    public void onClickFunction() {
        if(isPopupCalendar){//如果在日历选择window打开的情况下，轻点也是隐藏
            popupCalendar();
        }else{
            inputPopUpHelper.showPopupWindow(((Activity)getContext()).getWindow().getDecorView(), PopUpWindowHelper.LocationType.CENTER);
            popupInputLayout.getFEtNoteContent().requestFocus();
            imm.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onLongClickFunction() {
        popupCalendar();
    }

}
