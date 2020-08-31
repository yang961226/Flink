package com.example.flink;

/**
 * 日记页面的基类
 */
public abstract class NoteBaseActivity extends FlinkBaseActivity {

    //默认返回
    @Override
    protected int getLayoutId() {
        return R.layout.activity_note;
    }

    @Override
    protected void initView() {
        initTop();
        initCenter();
        initBottom();
    }

    protected abstract void initTop();

    protected abstract void initCenter();

    protected abstract void initBottom();
}
