package com.example.flink.common;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

/**
 * 万用适配器对应的Adapter
 */
public class ViewHolder {

    /**
     * 保存所有itemview的集合
     */
    private final SparseArray<View> mViews;

    private final View mConvertView;

    private ViewHolder(Context context,int layoutId){
        mConvertView=View.inflate(context,layoutId,null);
        mConvertView.setTag(this);

        mViews=new SparseArray<>();
    }

    public static ViewHolder getInstance(View convertView,Context context,int layoutId){
        if(convertView==null){
            return new ViewHolder(context,layoutId);
        }else{
            return (ViewHolder)convertView.getTag();
        }
    }

    public View getConvertView(){
        return mConvertView;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getItemViewById(int id){
        View view=mViews.get(id);
        if(view==null){
            view=mConvertView.findViewById(id);
            mViews.append(id,view);
        }

        return (T) view;
    }
}
