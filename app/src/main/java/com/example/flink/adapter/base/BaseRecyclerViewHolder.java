package com.example.flink.adapter.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * recyclerView通用ViewHolder
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private View convertView;

    public BaseRecyclerViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        views = new SparseArray<>();
        convertView = itemView;
    }

    public static BaseRecyclerViewHolder createViewHolder(Context context, View itemView) {
        return new BaseRecyclerViewHolder(context, itemView);
    }

    public static BaseRecyclerViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        return new BaseRecyclerViewHolder(context, itemView);
    }

    /**
     * 通过viewId获取控件
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

}
