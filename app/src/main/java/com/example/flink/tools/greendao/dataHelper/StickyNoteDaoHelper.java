package com.example.flink.tools.greendao.dataHelper;

import com.example.flink.greendao.gen.StickyNoteItemDao;
import com.example.flink.item.StickyNoteItem;
import com.example.flink.tools.DateUtil;
import com.example.flink.tools.greendao.GreenDaoManager;

import java.util.Date;
import java.util.List;

/**
 * 专门访问StickyNote的helper
 */
public class StickyNoteDaoHelper {

    private static volatile StickyNoteDaoHelper singleTon;

    private StickyNoteItemDao stickyNoteItemDao;

    public static StickyNoteDaoHelper getInstance() {
        if (singleTon == null) {
            synchronized (StickyNoteDaoHelper.class) {
                if (singleTon == null) {
                    singleTon = new StickyNoteDaoHelper();
                }
            }
        }
        return singleTon;
    }

    private StickyNoteDaoHelper() {
        stickyNoteItemDao = GreenDaoManager.getDaoSession().getStickyNoteItemDao();
    }

    /**
     * 插入或修改某个笔记
     *
     * @param item 笔记
     */
    public void insertOrReplace(StickyNoteItem item) {
        stickyNoteItemDao.insertOrReplace(item);
    }

    /**
     * 查询某天的所有笔记
     *
     * @param date 要查询的日期
     * @return 某天的所有笔记（按照order排序）
     */
    public List<StickyNoteItem> queryNotesOfOneDay(Date date) {
        Date startDate = DateUtil.clearDateHMS(new Date(date.getTime()));
        Date endDate = DateUtil.clearDateHMS(new Date(date.getTime() + DateUtil.DAY_IN_MILLIS));
        return stickyNoteItemDao.queryBuilder().where(StickyNoteItemDao.Properties.NoteDate.ge(startDate)
                , StickyNoteItemDao.Properties.NoteDate.lt(endDate)).orderAsc(StickyNoteItemDao.Properties.Order).list();
    }

    /**
     * 查询所有的笔记
     *
     * @return 所有的笔记
     */
    public List<StickyNoteItem> loadAllNotes() {
        return stickyNoteItemDao.loadAll();
    }
}
