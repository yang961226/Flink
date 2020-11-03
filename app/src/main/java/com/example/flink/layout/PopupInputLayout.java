package com.example.flink.layout;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flink.R;
import com.example.flink.common.EditTextLengthFilter;
import com.example.flink.mInterface.OnWordNumChangeListener;
import com.example.flink.view.FliterEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupInputLayout extends LinearLayout {

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.fet_note_content)
    FliterEditText fetNoteContent;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    private int maxWordNum=15;//默认值

    public PopupInputLayout(Context context) {
        super(context);
        View.inflate(context, R.layout.layout_popup_input, this);
        //绑定处理
        ButterKnife.bind(this);
        maxWordNum=getResources().getInteger(R.integer.sticky_note_popup_input_max_length);
        tvProgress.setText(0+"/"+maxWordNum);
        fetNoteContent.init(maxWordNum);
        fetNoteContent.setOnWordNumChangeListener(wordNumAfterChange -> tvProgress.setText(wordNumAfterChange+"/"+maxWordNum));

    }



    private void onConfirm(){
        Toast.makeText(getContext(),"确定",Toast.LENGTH_LONG).show();
    }

    public FliterEditText getFEtNoteContent(){
        return fetNoteContent;
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        onConfirm();
    }
}
