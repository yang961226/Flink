package com.example.flink.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.flink.layout.NoteViewPagerBaseLayout;

import java.util.List;

public class FlinkPagerAdapter extends PagerAdapter {

    private final List<NoteViewPagerBaseLayout> mList;

    public FlinkPagerAdapter(List<NoteViewPagerBaseLayout> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.isEmpty() ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    //下面的两个方法要自己写，当ViewPager+Fragment的时候，适配器需要继承FragmentPagerAdapter，并且不写这两个方法

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mList.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }
}
