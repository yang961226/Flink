package com.example.flink.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flink.R;
import com.example.flink.item.StickyNoteItem;

import java.util.List;

public class StickyNoteAdapter extends RecyclerView.Adapter<StickyNoteAdapter.ViewHolder>{

    private List<StickyNoteItem> mNoteItemList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivStatus;
        TextView tvNoteContent;

        public ViewHolder(View view){
            super(view);
            ivStatus=view.findViewById(R.id.iv_status);
            tvNoteContent=view.findViewById(R.id.tv_note_content);
        }
    }

    public StickyNoteAdapter(List<StickyNoteItem> noteItemList){
        mNoteItemList =noteItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticky_note,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        StickyNoteItem noteItem= mNoteItemList.get(position);
        holder.ivStatus.setImageResource(noteItem.getmStatus().getScrId());
        holder.tvNoteContent.setText(noteItem.getmNoteContent());
    }

    @Override
    public int getItemCount() {
        return mNoteItemList.size();
    }
}
