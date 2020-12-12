package com.example.flink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.flink.common.ViewHolder;

import java.util.List;

/**
 * 万用适配器（GridView,ListView等，暂不适配RecyclerView）
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDataList;

    public CommonAdapter(Context context, List<T> mDataList) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.isEmpty() ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder
                .getInstance(convertView, parent.getContext(), getLayoutId());

        setUI(holder, position, parent.getContext());

        return holder.getConvertView();
    }

    public abstract void updateData(List<T> mDataList);

    protected abstract void setUI(ViewHolder holder, int position, Context context);

    protected abstract int getLayoutId();
}
