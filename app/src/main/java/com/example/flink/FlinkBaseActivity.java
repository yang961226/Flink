package com.example.flink;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flink.Builder.MsgBuilder;
import com.example.flink.item.ActivityControl;
import com.example.flink.tools.HandlerUtils;

import butterknife.ButterKnife;

/**
 * 此项目所有Activity的基类
 */
public abstract class FlinkBaseActivity extends AppCompatActivity implements HandlerUtils.HandlerHolder.FlinkHandleMessageCallBack {
    //获取TAG的activity名称
    protected final String TAG = this.getClass().getSimpleName();
    //是否显示标题栏
    private boolean isShowActionBar = false;
    //是否显示状态栏
    private boolean isShowStatusBar = true;
    //是否允许旋转屏幕
    private boolean isAllowScreenRoate = false;
    //封装Toast对象
    private static Toast toast;
    //上下文
    public Context mContext;
    //消息处理工具
    protected HandlerUtils.HandlerHolder mHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //绑定handler
        mHandler = new HandlerUtils.HandlerHolder(this,this);
        //activity管理
        ActivityControl.getInstance().addActivity(this);
        if (!isShowActionBar && getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (isShowStatusBar) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        //设置布局
        setContentView(getLayoutId());
        //绑定处理
        ButterKnife.bind(this);
        //设置屏幕是否可旋转
        if (isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        //初始化控件
        initView();
        //设置数据
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //activity管理
        ActivityControl.getInstance().removeActivity(this);
    }



    /**
     * 初始化布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 设置数据
     */
    protected void initData(){

    }

    /**
     * 设置是否显示标题栏（子类如要调用，一定要在super.onCreate(savedInstanceState) 方法之前调用此方法）
     *
     * @param showActionBar true or false
     */
    protected void setShowActionBar(boolean showActionBar) {
        isShowActionBar = showActionBar;
    }

    /**
     * 设置是否显示状态栏（子类如要调用，一定要在super.onCreate(savedInstanceState) 方法之前调用此方法）
     *
     * @param showStatusBar true or false
     */
    protected void setShowStatusBar(boolean showStatusBar) {
        isShowStatusBar = showStatusBar;
    }

    /**
     * 是否允许屏幕旋转（子类如要调用，一定要在super.onCreate(savedInstanceState) 方法之前调用此方法）
     *
     * @param allowScreenRoate true or false
     */
    protected void setAllowScreenRoate(boolean allowScreenRoate) {
        isAllowScreenRoate = allowScreenRoate;
    }

    /**
     * 显示提示  toast
     *
     * @param msg 提示信息
     */
    @SuppressLint("ShowToast")
    public void showToast(String msg) {
        try {
            if (null == toast) {
                toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            ActivityControl.getInstance().removeActivity(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }

}
