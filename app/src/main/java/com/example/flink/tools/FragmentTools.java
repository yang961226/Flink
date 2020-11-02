package com.example.flink.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.flink.R;
import com.example.flink.common.MyConstants;
import com.example.flink.common.MyException;
import com.example.flink.fragment.FlinkBaseFragment;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class FragmentTools {

    private FragmentTools(){
        //屏蔽
    }

    private static FlinkBaseFragment buildFragment(Class<? extends FlinkBaseFragment>clazz){
        FlinkBaseFragment flinkBaseFragment;
        try{
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            flinkBaseFragment=(FlinkBaseFragment) constructor.newInstance();
        }catch (Exception e){
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR);
        }
        return flinkBaseFragment;
    }

    @SuppressWarnings("unchecked")
    private static FlinkBaseFragment buildFragment(String className){
        Class<? extends FlinkBaseFragment> clazz;
        try{
            clazz= (Class<? extends FlinkBaseFragment>) Class.forName(className);
        }catch (Exception e){
            throw new MyException(MyConstants.CLASS_CONFIG_ERROR);
        }
        return buildFragment(clazz);
    }


}
