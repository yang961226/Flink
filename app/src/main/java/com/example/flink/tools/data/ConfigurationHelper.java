package com.example.flink.tools.data;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.flink.tools.data.DataManager.DataManagerEnum.RAM_DATA_MANAGER;
import static com.example.flink.tools.data.DataManager.DataManagerEnum.SP_DATA_MANAGER;

/**
 * 配置存储器，保存数据的时候SP和内存都保存一份数据，每次APP启动的时候，从SP里面取数据复制到内存中，因为访问的时候
 * 是访问内存那部分的数据，如果频繁访问SP性能会很差
 * <p>
 * 注：所有配置都以字符串的形式存储，布尔值为true和false（或则空值）
 */
public class ConfigurationHelper {

    public static final String KEY_12_24_SYS = "12/24小时制";
    public static final String KEY_TACTILE_FEEDBACK = "触觉反馈";

    private static DataManager spDataManager = new DataManager(SP_DATA_MANAGER);
    private static DataManager ramDataManager = new DataManager(RAM_DATA_MANAGER);

    private ConfigurationHelper() {
    }

    //所有配置Key都在这里实现
    private static List<String> getKeyList() {
        List<String> keyList = new ArrayList<>();
        keyList.add(KEY_12_24_SYS);
        keyList.add(KEY_TACTILE_FEEDBACK);
        return keyList;
    }

    //将SP里面存储的配置复制一份到RAM里面去
    public static void initRamConfig() {
        for (String key : getKeyList()) {
            ramDataManager.saveString(key, spDataManager.getString(key));
        }
    }

    public static String getStringValue(String key) {
        return ramDataManager.getString(key);
    }

    public static boolean getBooleanValue(String key) {
        String result = getStringValue(key);
        return !TextUtils.isEmpty(result) && !result.equals("false");
    }

    public static void setStringValue(String key, String value) {
        spDataManager.saveString(key, value);
        ramDataManager.saveString(key, value);
    }

    public static void setBooleanValue(String key, boolean value) {
        setStringValue(key, value ? "true" : "false");
    }


}
