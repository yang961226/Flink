package com.example.flink.item;

import com.example.flink.R;
import com.example.flink.tools.DateUtil;

import java.util.Date;

public class StickyNoteItem {

    private StickyNoteStatus mStatus;//状态

    private String mNoteContent;//笔记内容

    private Date mNoteDate;//笔记日期

    private StickyNoteItem(){
        //屏蔽
    }

    public static Builder builder(){
        Builder builder=new Builder();
        builder.setNoteContent("");
        builder.setNoteDate(DateUtil.getNowDate());
        builder.setStatus(StickyNoteStatus.COMMON);
        return builder;
    }

    public static class Builder{

        private StickyNoteStatus status;
        private String noteContent;
        private Date noteDate;

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

        public StickyNoteItem build(){
            StickyNoteItem item=new StickyNoteItem();
            item.setmNoteContent(noteContent);
            item.setmStatus(status);
            item.setmNoteDate(noteDate);
            return item;
        }

    }

    public StickyNoteStatus getmStatus() {
        return mStatus;
    }

    public void setmStatus(StickyNoteStatus mStatus) {
        this.mStatus = mStatus;
    }

    public String getmNoteContent() {
        return mNoteContent;
    }

    public void setmNoteContent(String mNoteContent) {
        this.mNoteContent = mNoteContent;
    }

    public Date getmNoteDate() {
        return mNoteDate;
    }

    public void setmNoteDate(Date mNoteDate) {
        this.mNoteDate = mNoteDate;
    }

    //笔记状态枚举
    public static enum StickyNoteStatus {
        COMMON(0, R.drawable.circle_common)
        ,FINISH(1,R.drawable.circle_finish)
        ,WARNING(2,R.drawable.circle_warning)
        ,WAITING(3,R.drawable.circle_waiting);

        private final int statu;
        private final int srcId;

        StickyNoteStatus(int statu, int srcId){
            this.statu=statu;
            this.srcId=srcId;
        }

        public int getNextStatu(){
            return (statu+1)%values().length;
        }

        public int getStatu(){
            return statu;
        }

        public int getScrId(){
            return srcId;
        }
    }
}
