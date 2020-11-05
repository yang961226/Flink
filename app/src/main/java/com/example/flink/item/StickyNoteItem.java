package com.example.flink.item;

import com.example.flink.R;
import com.example.flink.tools.DateUtil;

import java.util.Date;
import java.util.UUID;

public class StickyNoteItem {

    private StickyNoteStatus mStatus;//状态

    private String mNoteContent;//笔记内容

    private Date mNoteDate;//笔记日期

    private int order;//序号

    private int id;//主键id

    private int parentId;//父亲的主键id

    private StickyNoteItem(){
        //屏蔽
    }

    public static Builder builder(){
        Builder builder=new Builder();
        builder.setNoteDate(DateUtil.getNowDate());
        builder.setStatus(StickyNoteStatus.COMMON);
        return builder;
    }

    public static class Builder{

        private StickyNoteStatus status;
        private String noteContent;
        private Date noteDate;
        private int order;
        private int id;
        private int parentId;

        private Builder(){

        }


        public void setStatus(StickyNoteStatus status){
            this.status=status;
        }

        public Builder setNoteContent(String noteContent){
            this.noteContent=noteContent;
            return this;
        }

        public Builder setNoteDate(Date noteDate){
            this.noteContent=noteContent;
            return this;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public StickyNoteItem build(){
            StickyNoteItem item=new StickyNoteItem();
            item.setNoteContent(noteContent);
            item.setStatus(status);
            item.setNoteDate(noteDate);
            item.setId(id);
            item.setParentId(parentId);
            item.setOrder(order);
            return item;
        }

    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public StickyNoteStatus getStatus() {
        return mStatus;
    }

    public void setStatus(StickyNoteStatus mStatus) {
        this.mStatus = mStatus;
    }

    public void moveToNextStatus(){
        this.mStatus=this.mStatus.getNextStatus();
    }

    public String getNoteContent() {
        return mNoteContent;
    }

    public void setNoteContent(String mNoteContent) {
        this.mNoteContent = mNoteContent;
    }

    public Date getNoteDate() {
        return mNoteDate;
    }

    public void setNoteDate(Date mNoteDate) {
        this.mNoteDate = mNoteDate;
    }

    //笔记状态枚举
    public enum StickyNoteStatus {
        COMMON(0, R.drawable.circle_common2)
        ,FINISH(1,R.drawable.circle_finish)
        ,HALF(2,R.drawable.circle_half)
        ,STAR(3,R.drawable.circle_star)
        ,WAITING(4,R.drawable.delay);

        private final int index;
        private final int srcId;

        StickyNoteStatus(int index, int srcId){
            this.index = index;
            this.srcId=srcId;
        }

        private int getNextStatuIndex(){
            return (index +1)%values().length;
        }

        public StickyNoteStatus getNextStatus(){
            int index=getNextStatuIndex();
            for(StickyNoteStatus stickyNoteStatus:StickyNoteStatus.values()){
                if(stickyNoteStatus.getIndex()==index){
                    return stickyNoteStatus;
                }
            }
            return COMMON;
        }

        public int getIndex(){
            return index;
        }

        public int getScrId(){
            return srcId;
        }
    }
}
