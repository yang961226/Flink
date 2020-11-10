package com.example.flink.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.flink.greendao.gen.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * 自定义openHelper类,继承DaoMaster.DevOpenHelper，重写onUpgrade(Database db, int oldVersion, int newVersion)方法
 * ，在该方法中使用MigrationHelper进行数据库升级以及数据迁移
 */
public class DaoMasterUpgradeOpenHelper extends DaoMaster.OpenHelper {

    public DaoMasterUpgradeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //把需要管理的数据库表DAO作为最后一个参数传入到方法中（只传更改过的表，新增的不用）
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }

        });
    }
}
