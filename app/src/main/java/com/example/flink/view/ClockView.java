package com.example.flink.view;

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
import com.example.flink.common.MyConstants;
import com.example.flink.event.DateChangeEvent;
import com.example.flink.event.TickEvent;
import com.example.flink.mInterface.Unregister;
import com.example.flink.tools.DateUtil;
import com.example.flink.tools.data.DataManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

import static com.example.flink.tools.data.DataManager.DataManagerEnum.SP_DATA_MANAGER;

@SuppressLint("NonConstantResourceId")
public class ClockView extends LinearLayout implements Unregister {

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

    private AdaptationTextView isToday;
    private AdaptationTextView am_or_pm;
    private ImageView number1_image;
    private ImageView number2_image;
    private ImageView number3_image;
    private ImageView number4_image;
    private ImageView number5_image;
    private ImageView number6_image;
    private DataManager dataManager;

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.layout_clock, this);
        findViewById(view);
        EventBus.getDefault().register(this);
        dataManager = new DataManager(SP_DATA_MANAGER);
        initNumberImage();
    }

    private void findViewById(View view) {
        isToday = view.findViewById(R.id.is_today);
        am_or_pm = view.findViewById(R.id.am_or_pm);
        number1_image = view.findViewById(R.id.number_1);
        number2_image = view.findViewById(R.id.number_2);
        number3_image = view.findViewById(R.id.number_3);
        number4_image = view.findViewById(R.id.number_4);
        number5_image = view.findViewById(R.id.number_5);
        number6_image = view.findViewById(R.id.number_6);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTick(TickEvent tickEvent) {
        //发生变化时改变数字图片
        String numbers = DateUtil.format(tickEvent.getDate(), DateUtil.FORMAT_Hms);

        if (dataManager.getBoolean(MyConstants.ENABLE_24HOURS_FORMAT)) {//24小时格式
            am_or_pm.setVisibility(INVISIBLE);
        } else {//12小时格式
            am_or_pm.setVisibility(VISIBLE);
            if (!numbers.startsWith("0")) {
                int hour = Integer.parseInt(numbers.substring(0, 2));
                if (hour >= 12) {
                    hour -= 12;
                    numbers = hour + numbers.substring(2, 6);
                    if (hour < 10) numbers = "0" + numbers;
                    am_or_pm.setText("PM");
                } else {
                    am_or_pm.setText("AM");
                }
            }
        }

        if (!number1.equals(numbers.substring(0, 1))) {
            number1 = numbers.substring(0, 1);
            setImage(number1_image, number1);
        }
        if (!number2.equals(numbers.substring(1, 2))) {
            number2 = numbers.substring(1, 2);
            setImage(number2_image, number2);
        }
        if (!number3.equals(numbers.substring(2, 3))) {
            number3 = numbers.substring(2, 3);
            setImage(number3_image, number3);
        }
        if (!number4.equals(numbers.substring(3, 4))) {
            number4 = numbers.substring(3, 4);
            setImage(number4_image, number4);
        }
        if (!number5.equals(numbers.substring(4, 5))) {
            number5 = numbers.substring(4, 5);
            setImage(number5_image, number5);
        }
        if (!number6.equals(numbers.substring(5, 6))) {
            number6 = numbers.substring(5, 6);
            setImage(number6_image, number6);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDateChange(DateChangeEvent dateChangeEvent) {
        Date date = DateUtil.getNowSelectedDate();
        if (DateUtil.format(date).equals(DateUtil.format(new Date())))
            isToday.setVisibility(VISIBLE);
        else isToday.setVisibility(INVISIBLE);
    }

    @Override
    public void unregister() {
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
            case "0":
                number_image.setImageBitmap(imageNum0);
                break;
            case "1":
                number_image.setImageBitmap(imageNum1);
                break;
            case "2":
                number_image.setImageBitmap(imageNum2);
                break;
            case "3":
                number_image.setImageBitmap(imageNum3);
                break;
            case "4":
                number_image.setImageBitmap(imageNum4);
                break;
            case "5":
                number_image.setImageBitmap(imageNum5);
                break;
            case "6":
                number_image.setImageBitmap(imageNum6);
                break;
            case "7":
                number_image.setImageBitmap(imageNum7);
                break;
            case "8":
                number_image.setImageBitmap(imageNum8);
                break;
            case "9":
                number_image.setImageBitmap(imageNum9);
                break;
            default:
                break;
        }
    }
}
