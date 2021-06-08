package com.example.flink.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 当item为空的时候会自动消失的RecyclerView
 */
public class RecyclerViewWithEmptyView extends RecyclerView {

    private View emptyView;//当recyclerView为空的时候，需要显示的View

    public RecyclerViewWithEmptyView(@NonNull Context context) {
        super(context);
    }

    public RecyclerViewWithEmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty();
    }

    @Override
    public void setAdapter(@Nullable @org.jetbrains.annotations.Nullable RecyclerView.Adapter adapter) {
        Adapter<?> oldAdapter = getAdapter();
        //解绑旧适配器的监听
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(adapterDataObserver);
        }
        super.setAdapter(adapter);
        //新适配器监听
        if (adapter != null) {
            adapter.registerAdapterDataObserver(adapterDataObserver);
        }
        checkIfEmpty();
    }

    final private AdapterDataObserver adapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            checkIfEmpty();
        }
    };

    /**
     * 校验是否item为空
     */
    private void checkIfEmpty() {
        if (getAdapter() == null) {
            return;
        }
        boolean isEmpty = getAdapter().getItemCount() == 0;
        if (emptyView != null) {
            emptyView.setVisibility(isEmpty ? VISIBLE : GONE);
        }
        setVisibility(isEmpty ? GONE : VISIBLE);
    }
}
