package com.example.flink.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.common.MyException;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 底部导航条
 */
public class NavigationBarView extends LinearLayout {

    public static final int MAX_ITEM_NUM=10;//本View支持的最大的导航页数
    private int itemNum;//设置的导航页数
    private int selectIndex;//当前选择的导航页页数索引
    private List<ImageView>ivNavItems;

    public NavigationBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.navigation_bar_view, this);
        //绑定处理
        ButterKnife.bind(this);
        init();
    }

    /**
     * 获取导航图标被选中的图片资源id
     * @return 导航图标被选中的图片资源id
     */
    private int getSelectedNavItemResId(){
        return 0;
    }

    private int getUnSelectedNavItemResId(){
        return 0;
    }

    public int getSelectIndex(){
        return selectIndex;
    }

    public void setItemNum(int itemNum){
        if(itemNum<0||itemNum>MAX_ITEM_NUM){
            throw new MyException("导航条的个数不可以负数或者大于"+MAX_ITEM_NUM);
        }
        this.itemNum=itemNum;
    }

    public int getItemNum(){
        return itemNum;
    }

    private void init() {
        selectIndex=0;
    }
}
