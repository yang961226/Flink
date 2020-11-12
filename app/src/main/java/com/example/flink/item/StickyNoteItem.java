package com.example.flink.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import com.example.flink.R;
import com.example.flink.tools.DateUtil;


import java.util.Date;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;

/**
 * greenDao类
 */
@Entity
public class StickyNoteItem {

    private int statu;//状态

    private String noteContent;//笔记内容

    private Date noteDate;//笔记日期

    @OrderBy
    private int order;//序号

    @Id(autoincrement = true)
    private Long id;//主键id

    private int parentId;//父亲的主键id
    
    @Generated(hash = 94483912)
    public StickyNoteItem(int statu, String noteContent, Date noteDate, int order,
            Long id, int parentId) {
        this.statu = statu;
        this.noteContent = noteContent;
        this.noteDate = noteDate;
        this.order = order;
        this.id = id;
        this.parentId = parentId;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private int statu;//状态

        private String noteContent;//笔记内容

        private Date noteDate;//笔记日期

        private int order;//序号

        private Long id;//主键id

        private int parentId;//父亲的主键id

        public Builder(){
            statu=0;
            noteContent="";
            noteDate= DateUtil.getNowDate();
            order=1;
            parentId=-1;
        }

        public Builder setStatu(int statu) {
            this.statu = statu;
            return this;
        }

        public Builder setNoteContent(String noteContent) {
            this.noteContent = noteContent;
            return this;
        }

        public Builder setNoteDate(Date noteDate) {
            this.noteDate = noteDate;
            return this;
        }

        public Builder setOrder(int order) {
            this.order = order;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setParentId(int parentId) {
            this.parentId = parentId;
            return this;
        }

        public StickyNoteItem build(){
            StickyNoteItem item=new StickyNoteItem();
            item.setId(id);
            item.setOrder(order);
            item.setParentId(parentId);
            item.setNoteContent(noteContent);
            item.setStatu(statu);
            item.setNoteDate(noteDate);
            return item;
        }
    }

    private StickyNoteItem() {
    }

    //笔记状态枚举
    public enum StickyNoteStatus {
        COMMON(0)
        ,FINISH(1)
        ,HALF(2)
        ,STAR(3)
        ,WAITING(4);

        private final int index;

        StickyNoteStatus(int index){
            this.index = index;
        }

        private int getNextStatuIndex(){
            return (index +1)%values().length;
        }

        public StickyNoteStatus getNextStatus(){
            int index=getNextStatuIndex();
            for(StickyNoteStatus stickyNoteStatus: StickyNoteStatus.values()){
                if(stickyNoteStatus.getIndex()==index){
                    return stickyNoteStatus;
                }
            }
            return COMMON;
        }

        public StickyNoteStatus getStatuByIndex(int index){
            for(StickyNoteStatus status:StickyNoteStatus.values()){
                if(status.index==index){
                    return status;
                }
            }
            return COMMON;
        }

        public int getIndex(){
            return index;
        }
    }

    public void moveToNextStatu(){
        statu=(statu+1)%StickyNoteStatus.values().length;
    }

    public int getStickyNoteRes(Context context){
        TypedArray array=context.getResources().obtainTypedArray(R.array.sticky_note_res);
        int result=array.getResourceId(statu,R.drawable.circle_common2);
        array.recycle();
        return result;
    }

    public int getStatu() {
        return this.statu;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }

    public String getNoteContent() {
        return this.noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public Date getNoteDate() {
        return this.noteDate;
    }

    public void setNoteDate(Date noteDate) {
        this.noteDate = noteDate;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getParentId() {
        return this.parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }


}
