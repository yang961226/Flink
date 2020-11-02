package com.example.flink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.flink.R;
import com.example.flink.common.ViewHolder;

import java.util.List;

/**
 * 万用适配器（GridView,ListView等，暂不适配RecyclerView）
 */
public abstract class  CommenAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;

    public CommenAdapter(Context context,List<T> mDatas){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder
                .getInstance(convertView, parent.getContext(), getLayoutId());

        setUI(holder,position,parent.getContext());

        return holder.getConvertView();
    }

    protected abstract void setUI(ViewHolder holder, int position, Context context);
    protected abstract int getLayoutId();
}
