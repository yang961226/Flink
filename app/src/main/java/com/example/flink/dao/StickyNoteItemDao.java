package com.example.flink.dao;

import org.greenrobot.greendao.annotation.Entity;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * greenDao类
 */
@Entity
public class StickyNoteItemDao {

    private int statu;//状态

    private String mNoteContent;//笔记内容

    private Date mNoteDate;//笔记日期

    private int order;//序号

    private int id;//主键id

    private int parentId;//父亲的主键id

    @Generated(hash = 2109005870)
    public StickyNoteItemDao(int statu, String mNoteContent, Date mNoteDate,
            int order, int id, int parentId) {
        this.statu = statu;
        this.mNoteContent = mNoteContent;
        this.mNoteDate = mNoteDate;
        this.order = order;
        this.id = id;
        this.parentId = parentId;
    }

    @Generated(hash = 787537621)
    public StickyNoteItemDao() {
    }

    public int getStatu() {
        return this.statu;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }

    public String getMNoteContent() {
        return this.mNoteContent;
    }

    public void setMNoteContent(String mNoteContent) {
        this.mNoteContent = mNoteContent;
    }

    public Date getMNoteDate() {
        return this.mNoteDate;
    }

    public void setMNoteDate(Date mNoteDate) {
        this.mNoteDate = mNoteDate;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return this.parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

}
