package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.common.MyException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 底部导航条
 */
public class NavigationBarLayout extends LinearLayout {

    public static final int MAX_ITEM_NUM=10;//本View支持的最大的导航页数
    private int itemNum;//设置的导航页数
    private int selectIndex;//当前选择的导航页页数索引
    private boolean isAlreadyInitItem =false;//是否已经初始化过导航小球了
    private List<ImageView>ivNavItems;

    private static final String HAS_INIT="控件已经初始化，禁止再次初始化";
    private static final String OVER_NUM="页数不可以超过"+MAX_ITEM_NUM;

    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    public NavigationBarLayout(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        View.inflate(context, R.layout.layout_navigation_bar_view, this);
        //绑定处理
        ButterKnife.bind(this);
    }

    public void selectTo(int selectIndex){
        ivNavItems.get(this.selectIndex).setImageResource(getUnSelectedNavItemResId());
        ivNavItems.get(selectIndex).setImageResource(getSelectedNavItemResId());
        this.selectIndex=selectIndex;
    }

    /**
     * 获取导航图标被选中的图片资源id
     * @return 导航图标被选中的图片资源id
     */
    private int getSelectedNavItemResId(){
        return R.drawable.circle_select;
    }

    private int getUnSelectedNavItemResId(){
        return R.drawable.circle_unselect;
    }

    public int getSelectIndex(){
        return selectIndex;
    }

    public int getItemNum(){
        return itemNum;
    }


    public static class NavigationBarViewConfig{

        private int itemNum=0;
        private int defaultSelectIndex=0;

        private  NavigationBarViewConfig(){

        }

        public static NavigationBarViewConfig create(){
            return new NavigationBarViewConfig();
        }

        public NavigationBarViewConfig setItemNum(int itemNum){
            this.itemNum=itemNum;
            return this;
        }

        public NavigationBarViewConfig setDefaultSelectIndex(int defaultSelectIndex){
            this.defaultSelectIndex=defaultSelectIndex;
            return this;
        }

        public int getItemNum(){
            return itemNum;
        }

        public int getDefaultSelectIndex(){return defaultSelectIndex;}
    }

    public void init(NavigationBarViewConfig config){
        //已经初始化过了，请不要再初始化，停止你的愚蠢行为
        if(isAlreadyInitItem){
            throw new MyException(HAS_INIT);
        }
        if(config.getItemNum()>MAX_ITEM_NUM){
            throw new MyException(OVER_NUM);
        }
        ivNavItems=new ArrayList<>();
        isAlreadyInitItem=true;
        this.itemNum=config.getItemNum();
        this.selectIndex=config.getDefaultSelectIndex();
        for(int i=0;i<itemNum;i++){
            ImageView imageView = new ImageView(getContext());
            LayoutParams layoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10,0,10,0);
            imageView.setLayoutParams(layoutParams);  //设置图片宽高

            if(i==selectIndex){
                imageView.setImageResource(getSelectedNavItemResId());
            }else{
                imageView.setImageResource(getUnSelectedNavItemResId());
            }
            ivNavItems.add(imageView);//添加进去以便后续管理
            llRoot.addView(imageView);
        }

    }

}
