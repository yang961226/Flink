package com.example.flink.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.flink.R;
import com.example.flink.fragment.FlinkBaseFragment;

import java.util.List;

public class NotePagerAdapter extends FragmentPagerAdapter {
    List<FlinkBaseFragment> mFragmentList;
    public NotePagerAdapter(FragmentManager fm, List<FlinkBaseFragment>fragmentList){
        super(fm);
        this.mFragmentList=fragmentList;
    }

    @Override
    public int getCount() {
        return mFragmentList==null? 0:mFragmentList.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList==null?new FlinkBaseFragment() {
            @Override
            protected int setContentView() {
                return R.layout.fragment_sticky_note;
            }

            @Override
            protected void lazyLoad() {

            }
        }:mFragmentList.get(position);
    }

}
