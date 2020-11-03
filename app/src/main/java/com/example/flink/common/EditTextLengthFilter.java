package com.example.flink.common;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditTextLengthFilter implements InputFilter {

    int MAX_EN;// 最大英文/数字长度 一个汉字算两个字母
    String regEx = "[\\u4e00-\\u9fa5]"; // unicode编码，判断是否为汉字

    public EditTextLengthFilter(int mAX_EN) {
        super();
        MAX_EN = mAX_EN;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {

        int destCount = dest.toString().length()
                + getChineseCount(dest.toString());
        int sourceCount = source.toString().length()
                + getChineseCount(source.toString());
        if (sourceCount > MAX_EN) {
            return dest;
        } else {
            return source;
        }
    }

    private int getChineseCount(String str) {
        int count = 0;
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count = count + 1;
            }
        }
        return count;
    }

}
