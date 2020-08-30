package com.example.flink.note;

import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.flink.Builder.MsgBuilder;
import com.example.flink.NoteBaseActivity;
import com.example.flink.R;
import com.example.flink.common.MyConstants;
import com.example.flink.view.SwitchDateView;

import butterknife.BindView;

public class NoteActivity extends NoteBaseActivity {

    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.ll_center)
    LinearLayout llCenter;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.switchDateView)
    SwitchDateView switchDateView;
    @BindView(R.id.testTextView)
    TextView testTextView;
    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {


        switchDateView.setmOnSwitchDateBtnClickListener(new SwitchDateView.OnSwitchDateBtnClickListener() {
            @Override
            public void onLastDayBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_LAST_DAY.hashCode()).setObj(MyConstants.CLICK_BTN_LAST_DAY).makeMsg());
            }

            @Override
            public void onLastMonthBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_LAST_MONTH.hashCode()).setObj(MyConstants.CLICK_BTN_LAST_MONTH).makeMsg());
            }

            @Override
            public void onTodayBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_TODAY.hashCode()).setObj(MyConstants.CLICK_BTN_TODAY).makeMsg());
            }

            @Override
            public void onNextDayBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_NEXT_DAY.hashCode()).setObj(MyConstants.CLICK_BTN_NEXT_DAY).makeMsg());
            }

            @Override
            public void onNextMonthBtnClick() {
                mHandler.sendMessage(MsgBuilder.build().setWhat(MyConstants.CLICK_BTN_NEXT_MONTH.hashCode()).setObj(MyConstants.CLICK_BTN_NEXT_MONTH).makeMsg());
            }
        });
    }


    @Override
    public void flinkMessageCallBack(Message msg) {
        testTextView.setText((String)msg.obj);
    }
}
