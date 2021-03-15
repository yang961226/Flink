package com.example.flink;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.flink.common.MyConstants;
import com.example.flink.layout.note.NoteActivity;
import com.example.flink.tools.CommonTools;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class WelcomeActivity extends FlinkBaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.ll_icons)
    LinearLayout llIcons;

    private static final int REQUEST_CODE = 5242;
    private static final String PERMISSIONS_TIPS = "正在请求的是必要权限，请授予，否则无法使用APP";
    private static final long DELAY_TIME = 1500L;//欢迎页停顿时间

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        initIconImageView();
    }

    @Override
    protected void initData() {

    }

    private void initIconImageView() {
        TypedArray array = this.getResources().obtainTypedArray(R.array.sticky_note_res);
        for (int i = 0; i < array.length(); i++) {
            ImageView imageView = CommonTools.buildIconImageView(this, array.getResourceId(i, R.drawable.circle_common2));
            llIcons.addView(imageView);
        }
        array.recycle();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 将申请权限的结果传给EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_CODE)
    private void requestPermissions() {
        if (EasyPermissions.hasPermissions(this, MyConstants.NEED_PERMISSIONS)) {
            CommonTools.redirectDelay(this, NoteActivity.class, DELAY_TIME, true);
        } else {
            // 没有获得全部权限，申请权限
            EasyPermissions.requestPermissions(this, PERMISSIONS_TIPS, REQUEST_CODE, MyConstants.NEED_PERMISSIONS);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //do nothing
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //用户点击了禁止并不再提醒的按钮之后会走到这里，会有一个弹窗点击之后会进入APP的设置界面
            new AppSettingsDialog
                    .Builder(this)
                    .setRationale("请在设置中授予权限，授予后重新进入可正常使用APP。")
                    .setTitle("提示")
                    .build()
                    .show();
        } else if (!perms.isEmpty()) {
            //在用户点击了禁止某个权限之后会走到这里
            EasyPermissions.requestPermissions(this, PERMISSIONS_TIPS, REQUEST_CODE, perms.toArray(new String[0]));
        }
    }


    @Override
    public void flinkMessageCallBack(Message msg) {
        //to nothing
    }
}