package com.example.mipro.netschool.Settings.Push;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mipro.netschool.R;

public class Exercises extends Fragment {

    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_EXERCISES_1 = "exercises_1";
    public static final String APP_PREFERENCES_EXERCISES_2 = "exercises_2";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        getActivity().setTitle("Задания и оценки");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_exercises_fragment, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayout linearLayout_oc_1 = getActivity().findViewById(R.id.oc_l_1);
        LinearLayout linearLayout_oc_2 = getActivity().findViewById(R.id.oc_l_2);
        LinearLayout linearLayout_oc_3 = getActivity().findViewById(R.id.oc_l_3);

        linearLayout_oc_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear1();
                ImageView imageView = getActivity().findViewById(R.id.oc_1);
                imageView.setVisibility(View.VISIBLE);
            }
        });

        linearLayout_oc_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear1();
                ImageView imageView = getActivity().findViewById(R.id.oc_2);
                imageView.setVisibility(View.VISIBLE);
            }
        });

        linearLayout_oc_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear1();
                ImageView imageView = getActivity().findViewById(R.id.oc_3);
                imageView.setVisibility(View.VISIBLE);
            }
        });

        LinearLayout linearLayout_ex_1 = getActivity().findViewById(R.id.ex_l_1);
        LinearLayout linearLayout_ex_2 = getActivity().findViewById(R.id.ex_l_2);
        LinearLayout linearLayout_ex_3 = getActivity().findViewById(R.id.ex_l_3);

        linearLayout_ex_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear2();
                ImageView imageView = getActivity().findViewById(R.id.ex_1);
                imageView.setVisibility(View.VISIBLE);
            }
        });

        linearLayout_ex_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear2();
                ImageView imageView = getActivity().findViewById(R.id.ex_2);
                imageView.setVisibility(View.VISIBLE);
            }
        });

        linearLayout_ex_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear2();
                ImageView imageView = getActivity().findViewById(R.id.ex_3);
                imageView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        clear1();
        if (mSettings.contains(APP_PREFERENCES_EXERCISES_1)) {
            int position = mSettings.getInt(APP_PREFERENCES_EXERCISES_1, -1);
            if (position != -1) {
                ImageView imageView = getActivity().findViewById(R.id.oc_1 + position);
                imageView.setVisibility(View.VISIBLE);
            }
        } else{
            ImageView imageView = getActivity().findViewById(R.id.oc_1);
            imageView.setVisibility(View.VISIBLE);
        }

        clear2();
        if (mSettings.contains(APP_PREFERENCES_EXERCISES_2)) {
            int position = mSettings.getInt(APP_PREFERENCES_EXERCISES_2, -1);
            if (position != -1) {
                ImageView imageView = getActivity().findViewById(R.id.ex_1 + position);
                imageView.setVisibility(View.VISIBLE);
            }
        } else{
            ImageView imageView = getActivity().findViewById(R.id.ex_1);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_EXERCISES_1, getPosition1());
        editor.putInt(APP_PREFERENCES_EXERCISES_2, getPosition2());
        editor.apply();
    }

    void clear1() {
        ImageView imageView_oc_1 = getActivity().findViewById(R.id.oc_1);
        ImageView imageView_oc_2 = getActivity().findViewById(R.id.oc_2);
        ImageView imageView_oc_3 = getActivity().findViewById(R.id.oc_3);

        imageView_oc_1.setVisibility(View.INVISIBLE);
        imageView_oc_2.setVisibility(View.INVISIBLE);
        imageView_oc_3.setVisibility(View.INVISIBLE);
    }

    int getPosition1() {
        ImageView imageView_oc_1 = getActivity().findViewById(R.id.oc_1);
        ImageView imageView_oc_2 = getActivity().findViewById(R.id.oc_2);
        if (imageView_oc_1.getVisibility() == View.VISIBLE) {
            return 0;
        } else if (imageView_oc_2.getVisibility() == View.VISIBLE) {
            return 1;
        } else {
            return 2;
        }
    }

    void clear2() {
        ImageView imageView_oc_1 = getActivity().findViewById(R.id.ex_1);
        ImageView imageView_oc_2 = getActivity().findViewById(R.id.ex_2);
        ImageView imageView_oc_3 = getActivity().findViewById(R.id.ex_3);

        imageView_oc_1.setVisibility(View.INVISIBLE);
        imageView_oc_2.setVisibility(View.INVISIBLE);
        imageView_oc_3.setVisibility(View.INVISIBLE);
    }

    int getPosition2() {
        ImageView imageView_oc_1 = getActivity().findViewById(R.id.ex_1);
        ImageView imageView_oc_2 = getActivity().findViewById(R.id.ex_2);
        if (imageView_oc_1.getVisibility() == View.VISIBLE) {
            return 0;
        } else if (imageView_oc_2.getVisibility() == View.VISIBLE) {
            return 1;
        } else {
            return 2;
        }
    }
    /*private int getValue() {

    }*/
}
