package com.example.flink.tools.data;

import android.content.Context;

import com.example.flink.mInterface.DataInterface;

import java.lang.ref.WeakReference;

/**
 * 数据管理器
 */
public class DataManager {

    private WeakReference<Context> reference;

    public DataManager(Context context) {
        reference = new WeakReference<>(context);
    }

    public DataInterface getDataManager(DataManagerEnum dataManagerEnum) {
        switch (dataManagerEnum) {
            case SP_DATA_MANAGER:
                return SpDataManager.getInstance(reference.get());
            case RAM_DATA_MANAGER:
                return RamDataManager.getInstance(reference.get());
            default:
                return SpDataManager.getInstance(reference.get());
        }
    }

    public enum DataManagerEnum {

        /**
         * 内存数据管理器，数据存储在内存中，APP关闭后失去数据
         */
        RAM_DATA_MANAGER,

        /**
         * 文件数据管理器，数据存储在SP中，APP关闭后依然保存数据
         */
        SP_DATA_MANAGER,

//        NET_DATA_MANAGER（未来实现）

    }

}
