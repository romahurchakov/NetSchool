package com.example.mipro.netschool.Settings;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;


public class MyTimePickerFragment extends DialogFragment {

    private int minutes = 0;
    private int hour = 0;
    private SharedPreferences mSettings;
    TimePickerDialog timePickerDialog;

    static private final int defaultValue = 0;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_TIMEPICKER_M = "timepicker_m";
    public static final String APP_PREFERENCES_TIMEPICKER_H = "timepicker_h";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        timePickerDialog = new TimePickerDialog(getActivity(), timeSetListener, hour, minutes, true);
        return timePickerDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }

    @Override
    public void onResume() {
        super.onResume();
        hour = mSettings.getInt(APP_PREFERENCES_TIMEPICKER_H, 0);
        minutes = mSettings.getInt(APP_PREFERENCES_TIMEPICKER_M, 0);
        timePickerDialog.updateTime(hour, minutes);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_TIMEPICKER_H, hour);
        editor.putInt(APP_PREFERENCES_TIMEPICKER_M, minutes);
        editor.apply();
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    hour = view.getHour();
                    minutes = view.getMinute();
                    Intent intent = new Intent();
                    intent.putExtra("cur_h" ,hour);
                    intent.putExtra("cur_m" ,minute);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                }
            };

    public int getMinutes() {
        return minutes;
    }

    public int getHour() {
        return hour;
    }

}