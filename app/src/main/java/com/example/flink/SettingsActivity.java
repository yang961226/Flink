package com.example.flink;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.layout.note.SettingLine;
import com.example.flink.tools.CommonTools;
import com.example.flink.tools.PopUpWindowHelper;
import com.example.flink.tools.data.ConfigurationHelper;
import com.example.flink.tools.notify.MessageDialogHelper;
import com.example.flink.tools.notify.ToastUtil;
import com.example.flink.view.BottomBar;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.flink.tools.data.ConfigurationHelper.KEY_TACTILE_FEEDBACK;
import static com.example.flink.tools.data.ConfigurationHelper.KEY_USE_24_SYS;

public class SettingsActivity extends FlinkBaseActivity {

    private LinearLayout llContent;
    private BottomBar bottomBar;

    private static final Map<String, SettingLine> KEY_VALUE_LINE_MAP = new HashMap<>();
    private MessageDialogHelper messageDialogHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initData() {
        initViewById();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetSettingState();
    }

    private void initViewById() {
        llContent = findViewById(R.id.ll_content);
        bottomBar = findViewById(R.id.bottom_bar);
    }

    /**
     * 恢复设置的状态：从配置信息中读取，然后恢复各个开关的默认状态
     */
    private void resetSettingState() {
        if (ConfigurationHelper.getBooleanValue(KEY_USE_24_SYS)) {
            Objects.requireNonNull(KEY_VALUE_LINE_MAP.get(KEY_USE_24_SYS)).refreshSwitchState(true);
        }
        if (ConfigurationHelper.getBooleanValue(KEY_TACTILE_FEEDBACK)) {
            Objects.requireNonNull(KEY_VALUE_LINE_MAP.get(KEY_TACTILE_FEEDBACK)).refreshSwitchState(true);
        }
    }

    @Override
    protected void initView() {
        addCustomizeSettingLine("笔记标志含义", getIconsLinearlayout());
        addSwitchSettingLine(KEY_USE_24_SYS, switchOpen -> {
            ConfigurationHelper.setBooleanValue(KEY_USE_24_SYS, switchOpen);
        });
        addSwitchSettingLine(KEY_TACTILE_FEEDBACK, switchOpen -> {
            ToastUtil.show(SettingsActivity.this, switchOpen ? "触觉反馈开启" : "触觉反馈关闭");
            ConfigurationHelper.setBooleanValue(KEY_TACTILE_FEEDBACK, switchOpen);
        });
        addNormalSettingLine("评价这款APP", v -> {
            ToastUtil.show(this, "点击了评价");
        });
        addNormalSettingLine("联系开发者", v -> {
            ToastUtil.show(this, "点击了联系开发者");
        });

        bottomBar.setLeftBtnOnClickListener(v -> finish());
        bottomBar.setRightBtnVisible(false);
        bottomBar.setLeftBtnImg(R.drawable.ic_back);
    }

    private View getIconsLinearlayout() {
        LinearLayout ll = new LinearLayout(this);
        LinearLayout.LayoutParams llLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        ll.setLayoutParams(llLp);
        TypedArray array = this.getResources().obtainTypedArray(R.array.sticky_note_res);
        int[] srcId = new int[array.length()];
        for (int i = 0; i < array.length(); i++) {
            srcId[i] = array.getResourceId(i, R.drawable.circle_common2);
        }
        array.recycle();
        String[] iconName = getResources().getStringArray(R.array.sticky_note_icon_name);
        String[] iconDesc = getResources().getStringArray(R.array.sticky_note_icon_desc);
        for (int i = 0; i < iconName.length; i++) {
            ImageView imageView = CommonTools.buildIconImageView(this, srcId[i]);
            ll.addView(imageView);
            int finalI = i;
            imageView.setOnClickListener(v -> {
                if (messageDialogHelper == null) {
                    messageDialogHelper = new MessageDialogHelper(SettingsActivity.this);
                }
                messageDialogHelper.showMsgDialog(
                        iconName[finalI]
                        , srcId[finalI]
                        , iconDesc[finalI]
                        , new MessageDialogHelper.OnDialogButtonClickListener() {
                            @Override
                            public void onOkClick(PopUpWindowHelper popUpWindowHelper, View v) {
                                popUpWindowHelper.dismiss();
                            }

                            @Override
                            public void onCancleClick(PopUpWindowHelper popUpWindowHelper, View v) {
                                popUpWindowHelper.dismiss();
                            }
                        });
            });
        }

        return ll;
    }

    //添加一个自定义的设置行
    private void addCustomizeSettingLine(String title, View view) {
        SettingLine settingLine = new SettingLine(this);
        settingLine.setTitle(title);
        settingLine.addViewInContent(view);
        llContent.addView(settingLine);
    }

    //添加一个切换功能的设置行
    private void addSwitchSettingLine(String title, SettingLine.OnSwitchListener onSwitchListener) {
        SettingLine settingLine = new SettingLine(this);
        settingLine.setTitle(title);
        settingLine.setOnSwitchListener(onSwitchListener);
        llContent.addView(settingLine);
        KEY_VALUE_LINE_MAP.put(title, settingLine);
    }

    //添加一个纯点击功能的设置行
    private void addNormalSettingLine(String title, View.OnClickListener onClickListener) {
        SettingLine settingLine = new SettingLine(this);
        settingLine.setTitle(title);
        settingLine.makeSwitchToButton(onClickListener);
        llContent.addView(settingLine);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (messageDialogHelper != null) {
            messageDialogHelper.dismissDialog();
        }
    }

    @Override
    public void flinkMessageCallBack(Message msg) {

    }

}
