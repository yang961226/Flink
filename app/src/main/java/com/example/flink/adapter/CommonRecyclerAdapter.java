package com.example.flink.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView的万能适配器
 *
 * @param <T>
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    protected Context context;
    protected List<T> itemList;

    public CommonRecyclerAdapter(Context context, List<T> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BaseRecyclerViewHolder
                .createViewHolder(parent.getContext(), parent, getLayoutId());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        setUi(holder, position);
    }

    @Override
    public int getItemCount() {
        return (itemList == null ? 0 : itemList.size());
    }

    /**
     * 获取item的布局id
     *
     * @return item布局id
     */
    protected abstract int getLayoutId();

    protected abstract void setUi(@NonNull BaseRecyclerViewHolder holder, int position);
}
