package com.example.flink.view.dialog;


import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BaseDialog extends Dialog {

    private final String mInvalidTitle = "Invalid heading";

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public String getInvalidTitle() {
        return mInvalidTitle;
    }

    public <T> T fb(int id) {
        return (T) findViewById(id);
    }

}
