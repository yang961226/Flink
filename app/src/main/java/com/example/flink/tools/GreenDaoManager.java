package com.example.flink.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.flink.greendao.gen.DaoMaster;
import com.example.flink.greendao.gen.DaoSession;


public class GreenDaoManager {

    public static final boolean ENCRYPTED = true;//是否加密

    public static final String DB_NAME = "Xbox.db";

    private static GreenDaoManager mGreenDaoManager;

    private static DaoMaster.DevOpenHelper mDevOpenHelper;
    // 需要升级时用MySQLiteOpenHelper替换DaoMaster.DevOpenHelper，保障更新版本时保存老数据

    private static DaoMaster mDaoMaster;

    private static DaoSession mDaoSession;

    private GreenDaoManager(Context context) {
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        getDaoMaster(context);
        getDaoSession(context);
    }

    public static GreenDaoManager getInstance(Context context) {
        if (null == mGreenDaoManager) {
            synchronized (GreenDaoManager.class) {
                if (null == mGreenDaoManager) {
                    mGreenDaoManager = new GreenDaoManager(context);
                }
            }
        }
        return mGreenDaoManager;
    }

    /**
     * 获取可读数据库 *
     *
     * @param context
     * @return
     */

    public static SQLiteDatabase getReadableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }

        return mDevOpenHelper.getReadableDatabase();

    }

    /**
     * 获取可写数据库 *
     *
     * @param context
     * @return
     */

    public static SQLiteDatabase getWritableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }

        return mDevOpenHelper.getWritableDatabase();

    }

    /**
     * 判断是否存在数据库，如果没有则创建
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (null == mDaoMaster) {
            synchronized (GreenDaoManager.class) {
                if (null == mDaoMaster) {
                    DaoMasterUpgradeOpenHelper helper = new DaoMasterUpgradeOpenHelper(context, DB_NAME, null);
                    mDaoMaster = new DaoMaster(helper.getWritableDatabase());
                }
            }
        }
        return mDaoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (null == mDaoSession) {
            synchronized (GreenDaoManager.class) {
                mDaoSession = getDaoMaster(context).newSession();
            }
        }
        return mDaoSession;
    }
}