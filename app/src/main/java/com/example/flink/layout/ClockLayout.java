package com.example.flink.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.flink.R;
import com.example.flink.event.TickEvent;
import com.example.flink.mInterface.Unregister;
import com.example.flink.tools.DateUtil;
import com.example.flink.view.AdaptationTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("NonConstantResourceId")
public class ClockLayout extends LinearLayout implements Unregister {

    private static Bitmap imageNum0;
    private static Bitmap imageNum1;
    private static Bitmap imageNum2;
    private static Bitmap imageNum3;
    private static Bitmap imageNum4;
    private static Bitmap imageNum5;
    private static Bitmap imageNum6;
    private static Bitmap imageNum7;
    private static Bitmap imageNum8;
    private static Bitmap imageNum9;

    private static String number1 = "0";
    private static String number2 = "0";
    private static String number3 = "0";
    private static String number4 = "0";
    private static String number5 = "0";
    private static String number6 = "0";

    @BindView(R.id.is_today)
    AdaptationTextView isToday;
    @BindView(R.id.am_or_pm)
    AdaptationTextView am_or_pm;
    @BindView(R.id.number_1)
    ImageView number1_image;
    @BindView(R.id.number_2)
    ImageView number2_image;
    @BindView(R.id.number_3)
    ImageView number3_image;
    @BindView(R.id.number_4)
    ImageView number4_image;
    @BindView(R.id.number_5)
    ImageView number5_image;
    @BindView(R.id.number_6)
    ImageView number6_image;

    private final Unbinder unbinder;

    public ClockLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.layout_clock, this);
        //绑定处理
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initNumberImage();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent tickEvent) {
        //发生变化时改变数字图片
        char[] numbers = DateUtil.format(tickEvent.getDate(), DateUtil.FORMAT_Hms).toCharArray();
        if (!number1.equals(String.valueOf(numbers[0]))) {
            number1 = String.valueOf(numbers[0]);
            setImage(number1_image, number1);
        }
        if (!number2.equals(String.valueOf(numbers[1]))) {
            number2 = String.valueOf(numbers[1]);
            setImage(number2_image, number2);
        }
        if (!number3.equals(String.valueOf(numbers[2]))) {
            number3 = String.valueOf(numbers[2]);
            setImage(number3_image, number3);
        }
        if (!number4.equals(String.valueOf(numbers[3]))) {
            number4 = String.valueOf(numbers[3]);
            setImage(number4_image, number4);
        }
        if (!number5.equals(String.valueOf(numbers[4]))) {
            number5 = String.valueOf(numbers[4]);
            setImage(number5_image, number5);
        }
        if (!number6.equals(String.valueOf(numbers[5]))) {
            number6 = String.valueOf(numbers[5]);
            setImage(number6_image, number6);
        }
    }

    @Override
    public void unregister() {
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    //初始化数字图片
    private void initNumberImage() {
        imageNum0 = BitmapFactory.decodeResource(getResources(), R.drawable.number_a_0);
        imageNum1 = BitmapFactory.decodeResource(getResources(), R.drawable.number_a_1);
        imageNum2 = BitmapFactory.decodeResource(getResources(), R.drawable.number_a_2);
        imageNum3 = BitmapFactory.decodeResource(getResources(), R.drawable.number_a_3);
        imageNum4 = BitmapFactory.decodeResource(getResources(), R.drawable.number_a_4);
        imageNum5 = BitmapFactory.decodeResource(getResources(), R.drawable.number_a_5);
        imageNum6 = BitmapFactory.decodeResource(getResources(), R.drawable.number_a_6);
        imageNum7 = BitmapFactory.decodeResource(getResources(), R.drawable.number_a_7);
        imageNum8 = BitmapFactory.decodeResource(getResources(), R.drawable.number_a_8);
        imageNum9 = BitmapFactory.decodeResource(getResources(), R.drawable.number_a_9);
    }

    //设置数字图片
    private void setImage(ImageView number_image, String number) {
        switch (number) {
            case "0": number_image.setImageBitmap(imageNum0); break;
            case "1": number_image.setImageBitmap(imageNum1); break;
            case "2": number_image.setImageBitmap(imageNum2); break;
            case "3": number_image.setImageBitmap(imageNum3); break;
            case "4": number_image.setImageBitmap(imageNum4); break;
            case "5": number_image.setImageBitmap(imageNum5); break;
            case "6": number_image.setImageBitmap(imageNum6); break;
            case "7": number_image.setImageBitmap(imageNum7); break;
            case "8": number_image.setImageBitmap(imageNum8); break;
            case "9": number_image.setImageBitmap(imageNum9); break;
            default: break;
        }
    }
}
