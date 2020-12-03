package com.example.flink.tools;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

public class HandlerUtils {

    private HandlerUtils() {
        //屏蔽
    }

    private HandlerHolder mHandlerHolder;

    public static class HandlerHolder extends Handler {
        WeakReference<Context> mContext;
        FlinkHandleMessageCallBack mCallback;

        private HandlerHolder() {
            //屏蔽
        }

        public HandlerHolder(Context context, FlinkHandleMessageCallBack callback) {

            mContext = new WeakReference<>(context);
            mCallback = callback;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Context context = mContext.get();
            if (context != null) {
                mCallback.flinkMessageCallBack(msg);
            }
        }

        //接口回调Message
        public interface FlinkHandleMessageCallBack {
            void flinkMessageCallBack(Message msg);
        }


    }


}
