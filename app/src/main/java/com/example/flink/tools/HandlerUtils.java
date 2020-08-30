package com.example.flink.tools;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import com.example.flink.Builder.MsgBuilder;
import java.lang.ref.WeakReference;

public class HandlerUtils {

    private HandlerUtils(){
        //屏蔽
    }

    private HandlerHolder mHandlerHolder;

    public static class HandlerHolder extends Handler{
        WeakReference <Context> mContext;
        FlinkHandleMessageCallBack mCallback;

        private HandlerHolder(){
            //屏蔽
        }

        public HandlerHolder(Context context, FlinkHandleMessageCallBack callback){

            mContext = new WeakReference<>(context);
            mCallback=callback;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Context context=mContext.get();
            if(context!=null){
                mCallback.flinkMessageCallBack(msg);
            }
        }

        //接口回调Message
        public interface FlinkHandleMessageCallBack{
            void flinkMessageCallBack(Message msg);
        }

        protected void sendMessage(MsgBuilder msgBuilder){
            if(msgBuilder==null || msgBuilder.getWhat()==-1){
                return;
            }

            Message msg=new Message();
            msg.what=msgBuilder.getWhat();
            if(msgBuilder.getArg1()!=-1){
                msg.arg1=msgBuilder.getArg1();
            }
            if(msgBuilder.getArg2()!=-1){
                msg.arg2=msgBuilder.getArg2();
            }
            if(msgBuilder.getObj()!=null){
                msg.obj=msgBuilder.getObj();
            }
            sendMessage(msg);
        }
    }



}
