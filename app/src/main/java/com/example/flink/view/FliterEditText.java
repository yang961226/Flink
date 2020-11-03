package com.example.flink.view;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.flink.common.EditTextLengthFilter;
import com.example.flink.mInterface.OnWordNumChangeListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 计算字符的方式为中文2个字符，其余1个字符
 */
public class FliterEditText extends androidx.appcompat.widget.AppCompatEditText {

    private int maxWordNum=20;
    private OnWordNumChangeListener onWordNumChangeListener;

    public FliterEditText(Context context) {
        super(context);
    }

    public FliterEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FliterEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(int maxWordNum){
        this.maxWordNum=maxWordNum;
        addTextChangedListener(getTextWatcher());
        setFilters(getInputFilters());
    }

    public void setOnWordNumChangeListener(OnWordNumChangeListener onWordNumChangeListener){
        this.onWordNumChangeListener=onWordNumChangeListener;
    }

    protected InputFilter[] getInputFilters(){
        InputFilter[] filters={new EditTextLengthFilter(maxWordNum)};
        return filters;
    }

    protected TextWatcher getTextWatcher(){
        return new TextWatcher() {

            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //获取光标开始的位置
                selectionStart = FliterEditText.this.getSelectionStart();
                //获取光标结束的位置
                selectionEnd = FliterEditText.this.getSelectionEnd();
                if(getInputLength()>maxWordNum){
                    s.delete(selectionStart - 1, selectionEnd);
                }
                if(onWordNumChangeListener!=null){
                    onWordNumChangeListener.onWordNumChange(getInputLength());
                }
            }

            private int getInputLength(){//汉字算2个字符，其他算1个
                return temp.length()
                        +getChineseCount(temp.toString());
            }

            private int getChineseCount(String str) {
                String regEx = "[\\u4e00-\\u9fa5]"; // unicode编码，判断是否为汉字
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
        };
    }


}
