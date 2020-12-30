package com.example.flink.tools.data;

import com.example.flink.mInterface.DataInterface;

/**
 * 数据管理器
 */
public class DataManager {

    private DataManager() {

    }


    public static DataInterface getDataManager(DataManagerEnum dataManagerEnum) {
        switch (dataManagerEnum) {
            case SP_DATA_MANAGER:
                return SpDataManager.getInstance();
            case RAM_DATA_MANAGER:
                return RamDataManager.getInstance();
            default:
                return SpDataManager.getInstance();
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
