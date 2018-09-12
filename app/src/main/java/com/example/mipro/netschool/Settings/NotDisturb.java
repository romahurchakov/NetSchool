package com.example.mipro.netschool.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mipro.netschool.R;

public class NotDisturb extends Fragment {

    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_NOT_DISTURB = "not_disturb";
    public static final String APP_PREFERENCES_TIMEPICKER_M = "timepicker_m";
    public static final String APP_PREFERENCES_TIMEPICKER_H = "timepicker_h";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        getActivity().setTitle("Не беспокоить");
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_NOT_DISTURB, getDisturb());
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        removeIcon();
        if (mSettings.contains(APP_PREFERENCES_NOT_DISTURB)) {
            int position = mSettings.getInt(APP_PREFERENCES_NOT_DISTURB, -1);

            if (position != -1) {
                final LinearLayout linearLayout = getView().findViewById(R.id.dn_1 + position);
                ImageView imageView = getActivity().findViewById(R.id.set_nd_2 + position);
                imageView.setVisibility(View.VISIBLE);
            }
        }
        TextView textView = getView().findViewById(R.id.dn_current_time);
        int minutes = 0;
        int hour = 0;
        if (mSettings.contains("timepicker_m") && mSettings.contains("timepicker_h")) {
            minutes = mSettings.getInt("timepicker_m", -1);
            hour = mSettings.getInt("timepicker_h", -1);
            String cur_hour = hour <10? "0"+hour: ""+hour;
            String cur_min = minutes <10? "0"+minutes: ""+minutes;
            textView.setText("" + cur_hour + ":" + cur_min);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.not_disturb, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final LinearLayout linearLayout = getView().findViewById(R.id.dn_1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeIcon();
                ImageView imageView = getActivity().findViewById(R.id.set_nd_2);
                imageView.setVisibility(View.VISIBLE);
            }
        });

        final LinearLayout linearLayout1 = getView().findViewById(R.id.dn_2);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeIcon();
                ImageView imageView = getActivity().findViewById(R.id.set_nd_3);
                imageView.setVisibility(View.VISIBLE);
            }
        });

        final LinearLayout linearLayout2 = getView().findViewById(R.id.dn_3);
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeIcon();
                ImageView imageView = getActivity().findViewById(R.id.set_nd_4);
                imageView.setVisibility(View.VISIBLE);
            }
        });

        final LinearLayout linearLayout3 = getView().findViewById(R.id.dn_4);
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                MyTimePickerFragment myTimePickerFragment = new MyTimePickerFragment();
                Bundle bundleTime = new Bundle();
                myTimePickerFragment.setArguments(bundleTime);
                myTimePickerFragment.setTargetFragment(NotDisturb.this, 1);
                myTimePickerFragment.show(getFragmentManager(), "timer");
            }
        });

        final LinearLayout linearLayout4 = getView().findViewById(R.id.dn_clear);
        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeIcon();
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putInt(APP_PREFERENCES_TIMEPICKER_H, 0);
                editor.putInt(APP_PREFERENCES_TIMEPICKER_M, 0);
                TextView textView = getActivity().findViewById(R.id.dn_current_time);
                textView.setText("" + "00" + ":" + "00");
                editor.apply();
            }
        });
    }

    private void removeIcon() {
        for (int i = 0 ; i < 4; i++) {
            ImageView imageView = getActivity().findViewById(R.id.set_nd_2 + i);
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    int getDisturb() {
        for (int i = 0; i < 4; i++) {
            ImageView imageView = getActivity().findViewById(R.id.set_nd_2 + i);
            if (imageView.getVisibility() == View.VISIBLE) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            removeIcon();
            int cur_h = data.getIntExtra("cur_h", -1);
            int cur_m = data.getIntExtra("cur_m", -1);
            String cur_hour = cur_h <10? "0"+cur_h: ""+cur_h;
            String cur_min = cur_m <10? "0"+cur_m: ""+cur_m;
            ImageView imageView = getActivity().findViewById(R.id.set_nd_5);
            imageView.setVisibility(View.VISIBLE);
            TextView textView = getActivity().findViewById(R.id.dn_current_time);
            textView.setText("" + cur_hour + ":" + cur_min);
        }
    }
}
