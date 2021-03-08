package com.example.flink;

import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.example.flink.layout.note.SettingLine;
import com.example.flink.tools.notify.ToastUtil;

import butterknife.BindView;

public class SettingsActivity extends FlinkBaseActivity {

    @BindView(R.id.ll_content)
    LinearLayout llContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        addSwitchSettingLine("12/24小时制");
        addSwitchSettingLine("触觉反馈");
        addNormalSettingLine("评价这款APP", v -> {
            ToastUtil.show(this, "点击了评价");
        });
        addNormalSettingLine("联系开发者", v -> {
            ToastUtil.show(this, "点击了联系开发者");
        });
    }

    /**
     * 构建一个切换的设置行
     *
     * @param title 标题
     */
    private void addSwitchSettingLine(String title) {
        SettingLine settingLine = new SettingLine(this);
        settingLine.setTitle(title);
        llContent.addView(settingLine);
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
