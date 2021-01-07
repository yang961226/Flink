package com.example.flink;

import android.content.res.TypedArray;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.flink.common.MyConstants;
import com.example.flink.mInterface.DataInterface;
import com.example.flink.tools.CommonTools;
import com.example.flink.tools.data.DataManager;
import com.example.flink.tools.notify.ToastUtil;

public class SettingsActivity extends FlinkBaseActivity implements View.OnClickListener {

    private LinearLayout iconsLayout;
    private LinearLayout iconsIntroduction;
    private LinearLayout hoursTypeLayout;
    private CheckBox hoursTypeCheck;
    private LinearLayout tactileLayout;
    private LinearLayout aboutLayout;

    private DataInterface dataInterface;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public void flinkMessageCallBack(Message msg) {

    }

    @Override
    protected void initData() {
        super.initData();
        dataInterface = DataManager.getDataManager(DataManager.DataManagerEnum.SP_DATA_MANAGER);
    }

    @Override
    protected void initView() {
        iconsLayout = findViewById(R.id.icons_layout);
        iconsIntroduction = findViewById(R.id.icons_introduction);
        hoursTypeLayout = findViewById(R.id.hours_type_layout);
        hoursTypeCheck = findViewById(R.id.hours_type_check);
        tactileLayout = findViewById(R.id.tactile_layout);
        aboutLayout = findViewById(R.id.about_layout);

        iconsLayout.setOnClickListener(this);
        hoursTypeLayout.setOnClickListener(this);
        tactileLayout.setOnClickListener(this);
        aboutLayout.setOnClickListener(this);

        hoursTypeCheck.setChecked(dataInterface.getBoolean(MyConstants.ENABLE_24HOURS_FORMAT));

        TypedArray array = this.getResources().obtainTypedArray(R.array.sticky_note_res);
        for (int i = 0; i < array.length(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(array.getResourceId(i, R.drawable.circle_common2));
            int finalI = i;
            imageView.setOnClickListener(v -> ToastUtil.showWhenDebug(this, "click " + finalI + " icon"));
            iconsIntroduction.addView(imageView);

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            lp.setMargins(CommonTools.dip2px(this, 5)
                    , CommonTools.dip2px(this, 5)
                    , CommonTools.dip2px(this, 5)
                    , CommonTools.dip2px(this, 5));
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.icons_layout) {
            ToastUtil.show(this, "click iconsLayout");
            //TODO
        } else if (viewId == R.id.hours_type_layout) {
            ToastUtil.show(this, "click hoursTypeLayout");
            hoursTypeCheck.setChecked(!hoursTypeCheck.isChecked());
            dataInterface.saveBoolean(MyConstants.ENABLE_24HOURS_FORMAT, hoursTypeCheck.isChecked());
        } else if (viewId == R.id.tactile_layout) {
            ToastUtil.show(this, "click tactileLayout");
            //TODO
        } else if (viewId == R.id.about_layout) {
            ToastUtil.show(this, "click aboutLayout");
            //TODO
        }
    }

}
