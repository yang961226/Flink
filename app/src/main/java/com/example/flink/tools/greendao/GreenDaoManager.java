package com.example.flink.tools.greendao;

import com.example.flink.FlinkApplication;
import com.example.flink.greendao.gen.DaoMaster;
import com.example.flink.greendao.gen.DaoSession;



public class GreenDaoManager {

    public static final boolean ENCRYPTED = true;//是否加密

    public static final String DB_NAME = "Xbox.db";

    private static GreenDaoManager mGreenDaoManager;

//    private DaoMaster.DevOpenHelper mDevOpenHelper;
    // 需要升级时用MySQLiteOpenHelper替换DaoMaster.DevOpenHelper，保障更新版本时保存老数据

    private static DaoMaster mDaoMaster;

    private static DaoSession mDaoSession;

    private GreenDaoManager() {
//        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        getDaoMaster();
        getDaoSession();
    }

    public static GreenDaoManager getInstance() {
        if (null == mGreenDaoManager) {
            synchronized (GreenDaoManager.class) {
                if (null == mGreenDaoManager) {
                    mGreenDaoManager = new GreenDaoManager();
                }
            }
        }
        return mGreenDaoManager;
    }

    /**
     * 获取可读数据库 *          注：mDevOpenHelper有对context进行直接引用导致内存泄漏问题，目前已经注释，待后面解决
     *
     * @param context
     * @return
     */

//    public static SQLiteDatabase getReadableDatabase(Context context) {
//        if (null == mDevOpenHelper) {
//            getInstance(context);
//        }
//
//        return mDevOpenHelper.getReadableDatabase();
//
//    }

    /**
     * 获取可写数据库 *            注：mDevOpenHelper有对context进行直接引用导致内存泄漏问题，目前已经注释，待后面解决
     *
     * @param context
     * @return
     */

//    public static SQLiteDatabase getWritableDatabase(Context context) {
//        if (null == mDevOpenHelper) {
//            getInstance(context);
//        }
//
//        return mDevOpenHelper.getWritableDatabase();
//
//    }

    /**
     * 判断是否存在数据库，如果没有则创建
     *
     * @return
     */
    public static DaoMaster getDaoMaster() {
        if (null == mDaoMaster) {
            synchronized (GreenDaoManager.class) {
                if (null == mDaoMaster) {
                    DaoMasterUpgradeOpenHelper helper = new DaoMasterUpgradeOpenHelper(FlinkApplication.getContext(), DB_NAME, null);
                    mDaoMaster = new DaoMaster(helper.getWritableDatabase());
                }
            }
        }
        return mDaoMaster;
    }

    public static DaoSession getDaoSession() {
        if (null == mDaoSession) {
            synchronized (GreenDaoManager.class) {
                mDaoSession = getDaoMaster().newSession();
            }
        }
        return mDaoSession;
    }
}