package com.example.mipro.netschool.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mipro.netschool.R;

import static com.example.mipro.netschool.MainActivity.LOG_TAG;

public class TimeTable extends Fragment {
    private SharedPreferences mSettings;

    private int position = -1;

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_TIMETABLE = "timetable";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        getActivity().setTitle("Расписание");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timetable_fragment, null);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSettings.contains(APP_PREFERENCES_TIMETABLE)) {
            position = mSettings.getInt(APP_PREFERENCES_TIMETABLE, -1);
            final ImageView test = new ImageView(getContext());
            test.setImageResource(R.drawable.ic_done_black_24dp);
            test.setId(R.id.iconView);

            if (position != -1) {
                final LinearLayout linearLayout = getView().findViewById(R.id.ll + position+1);
                linearLayout.addView(test);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_TIMETABLE, getTimetable());
        editor.apply();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ImageView test = new ImageView(getContext());
        test.setImageResource(R.drawable.ic_done_black_24dp);
        test.setId(R.id.iconView);

        final LinearLayout linearLayout = getView().findViewById(R.id.ll1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeIcon();
                linearLayout.addView(test);
            }
        });

        final LinearLayout linearLayout1 = getView().findViewById(R.id.ll2);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeIcon();
                linearLayout1.addView(test);
            }
        });

        final LinearLayout linearLayout2 = getView().findViewById(R.id.ll3);
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeIcon();
                linearLayout2.addView(test);
            }
        });

        final LinearLayout linearLayout3 = getView().findViewById(R.id.ll4);
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeIcon();
                linearLayout3.addView(test);
            }
        });
    }

    void removeIcon() {
        final LinearLayout linearLayout = getView().findViewById(R.id.ll1);
        final LinearLayout linearLayout1 = getView().findViewById(R.id.ll2);
        final LinearLayout linearLayout2 = getView().findViewById(R.id.ll3);
        final LinearLayout linearLayout3 = getView().findViewById(R.id.ll4);

        if (linearLayout.findViewById(R.id.iconView) != null) {
            linearLayout.removeView(linearLayout.findViewById(R.id.iconView));
        }
        if (linearLayout1.findViewById(R.id.iconView) != null) {
            linearLayout1.removeView(linearLayout1.findViewById(R.id.iconView));
        }
        if (linearLayout2.findViewById(R.id.iconView) != null) {
            linearLayout2.removeView(linearLayout2.findViewById(R.id.iconView));
        }
        if (linearLayout3.findViewById(R.id.iconView) != null) {
            linearLayout3.removeView(linearLayout3.findViewById(R.id.iconView));
        }
    }

    int getTimetable() {
        final LinearLayout linearLayout = getView().findViewById(R.id.ll1);
        final LinearLayout linearLayout1 = getView().findViewById(R.id.ll2);
        final LinearLayout linearLayout2 = getView().findViewById(R.id.ll3);
        final LinearLayout linearLayout3 = getView().findViewById(R.id.ll4);

        if (linearLayout.findViewById(R.id.iconView) != null) {
            return 0;
        }
        if (linearLayout1.findViewById(R.id.iconView) != null) {
            return 1;
        }
        if (linearLayout2.findViewById(R.id.iconView) != null) {
            return 2;
        }
        if (linearLayout3.findViewById(R.id.iconView) != null) {
           return 3;
        }
        return -1;
    }
}
