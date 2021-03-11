package com.example.flink;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.layout.note.SettingLine;
import com.example.flink.tools.data.ConfigurationHelper;
import com.example.flink.tools.notify.ToastUtil;
import com.example.flink.view.BottomBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.example.flink.tools.data.ConfigurationHelper.KEY_12_24_SYS;
import static com.example.flink.tools.data.ConfigurationHelper.KEY_TACTILE_FEEDBACK;

public class SettingsActivity extends FlinkBaseActivity {

    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;

    private Map<String, SettingLine> keyValueLineMap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetSettingState();
    }

    /**
     * 恢复设置的状态：从配置信息中读取，然后恢复各个开关的默认状态
     */
    private void resetSettingState() {
        if (ConfigurationHelper.getBooleanValue(KEY_12_24_SYS)) {
            keyValueLineMap.get("12/24小时制").refreshSwitchState(true);
        }
        if (ConfigurationHelper.getBooleanValue(KEY_TACTILE_FEEDBACK)) {
            keyValueLineMap.get("触觉反馈").refreshSwitchState(true);
        }
    }

    @Override
    protected void initView() {
        keyValueLineMap = new HashMap<>();
        addSwitchSettingLine("12/24小时制", switchOpen -> {
            ToastUtil.show(SettingsActivity.this, switchOpen ? "24小时" : "12小时");
            ConfigurationHelper.setBooleanValue(KEY_12_24_SYS, switchOpen);
        });
        addSwitchSettingLine("触觉反馈", switchOpen -> {
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

    /**
     * 构建一个切换的设置行
     *
     * @param title 标题
     */
    private void addSwitchSettingLine(String title, SettingLine.OnSwitchListener onSwitchListener) {
        SettingLine settingLine = new SettingLine(this);
        settingLine.setTitle(title);
        settingLine.setOnSwitchListener(onSwitchListener);
        llContent.addView(settingLine);
        keyValueLineMap.put(title, settingLine);
    }

    private void addNormalSettingLine(String title, View.OnClickListener onClickListener) {
        SettingLine settingLine = new SettingLine(this);
        settingLine.setTitle(title);
        settingLine.makeSwitchToButton(onClickListener);
        llContent.addView(settingLine);
    }

    @Override
    public void flinkMessageCallBack(Message msg) {

    }

}
