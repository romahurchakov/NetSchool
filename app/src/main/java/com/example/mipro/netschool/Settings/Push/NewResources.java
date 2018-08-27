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

public class NewResources extends Fragment {
    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_PUSH_NEW_RESOURCES = "push_new_resources";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        getActivity().setTitle("Новые учебные материалы");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_resources_fragment, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayout linearLayout1 = getActivity().findViewById(R.id.res_1);
        LinearLayout linearLayout2 = getActivity().findViewById(R.id.res_2);

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                ImageView imageView = getActivity().findViewById(R.id.push_res_1);
                imageView.setVisibility(View.VISIBLE);
            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                ImageView imageView = getActivity().findViewById(R.id.push_res_2);
                imageView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        clear();
        if (mSettings.contains(APP_PREFERENCES_PUSH_NEW_RESOURCES)) {
            int position = mSettings.getInt(APP_PREFERENCES_PUSH_NEW_RESOURCES, -1);
            if (position != -1) {
                ImageView imageView = getActivity().findViewById(R.id.push_res_1 + position);
                imageView.setVisibility(View.VISIBLE);
            }
        } else{
            ImageView imageView = getActivity().findViewById(R.id.push_res_1);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_PUSH_NEW_RESOURCES, getPosition());
        editor.apply();
    }

    void clear() {
        ImageView imageView1 = getActivity().findViewById(R.id.push_res_1);
        ImageView imageView2 = getActivity().findViewById(R.id.push_res_2);
        imageView1.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
    }

    int getPosition() {
        ImageView imageView1 = getActivity().findViewById(R.id.push_res_1);
        if (imageView1.getVisibility() == View.VISIBLE) {
            return 0;
        } else {
            return 1;
        }
    }
}
