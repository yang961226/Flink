package com.example.flink.tools;

import android.app.Service;
import android.os.Vibrator;

import com.example.flink.FlinkApplication;

public class VibratorUtil {

    private static Vibrator vib;
    public static final long SHORT_VIBRATE_TIME = 70L;
    public static final long LONG_VIBRATE_TIME = 150L;

    public static void vibrateShortTime() {
        if (vib == null) {
            vib = (Vibrator) FlinkApplication.getContext().getSystemService(Service.VIBRATOR_SERVICE);
        }
        vib.vibrate(SHORT_VIBRATE_TIME);
    }

    public static void vibrateLongTime() {
        if (vib == null) {
            vib = (Vibrator) FlinkApplication.getContext().getSystemService(Service.VIBRATOR_SERVICE);
        }
        vib.vibrate(LONG_VIBRATE_TIME);
    }

}
