package com.example.mipro.netschool.Settings.Push;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mipro.netschool.R;

import static com.example.mipro.netschool.Settings.NotDisturb.APP_PREFERENCES_NOT_DISTURB;

public class Push extends Fragment {

    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_PUSH_NOTIFICATION = "push_notification";
    public static final String APP_PREFERENCES_PUSH_CHANGE = "push_change";
    public static final String APP_PREFERENCES_PUSH_FORUM = "push_forum";
    public static final String APP_PREFERENCES_PUSH_MAIL = "push_mail";
    public static final String APP_PREFERENCES_PUSH_NEW_RESOURCES = "push_new_resources";
    public static final String APP_PREFERENCES_EXERCISES_1 = "exercises_1";
    public static final String APP_PREFERENCES_EXERCISES_2 = "exercises_2";

    @Override
    public void onResume() {
        super.onResume();
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);


        LinearLayout linearLayout1 = getActivity().findViewById(R.id.push_exercises);
        LinearLayout linearLayout2 = getActivity().findViewById(R.id.push_notification);
        LinearLayout linearLayout3 = getActivity().findViewById(R.id.push_change);
        LinearLayout linearLayout4 = getActivity().findViewById(R.id.push_mail);
        LinearLayout linearLayout5 = getActivity().findViewById(R.id.push_message);
        LinearLayout linearLayout6 = getActivity().findViewById(R.id.push_new_resource);

        getDescription();

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMAIN, new Exercises());
                ft.addToBackStack("stack");
                ft.commit();
            }
        });

        linearLayout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMAIN, new NewResources());
                ft.addToBackStack("stack");
                ft.commit();
            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMAIN, new Notification());
                ft.addToBackStack("stack");
                ft.commit();
            }
        });

        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMAIN, new Change());
                ft.addToBackStack("stack");
                ft.commit();
            }
        });

        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMAIN, new Mail());
                ft.addToBackStack("stack");
                ft.commit();
            }
        });

        linearLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMAIN, new Forum());
                ft.addToBackStack("stack");
                ft.commit();
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Уведомления");
        View view = inflater.inflate(R.layout.push_fragment, null);
        return view;
    }

    void getDescription() {
        String exercises1 = "Все";
        String exercises2 = "Все";
        int position = -1;
        TextView textView = getActivity().findViewById(R.id.exercises_description);
        if (mSettings.contains(APP_PREFERENCES_EXERCISES_1)) {
            position = mSettings.getInt(APP_PREFERENCES_EXERCISES_1, -1);
            if (position == 1) {
                exercises1 = "Только важные";
            } else if (position == 2) {
                exercises1 = "Никакие";
            }
        }
        if (mSettings.contains(APP_PREFERENCES_EXERCISES_2)) {
            position = mSettings.getInt(APP_PREFERENCES_EXERCISES_2, -1);
            if (position == 1) {
                exercises2 = "Только домашние задания";
            } else if (position == 2) {
                exercises2 = "Никакие";
            }
        }
        textView.setText(exercises1+"/"+exercises2);

        String notification = "Все";
        position = -1;
        textView = getActivity().findViewById(R.id.notification_description);

        if (mSettings.contains(APP_PREFERENCES_PUSH_NOTIFICATION)) {
            position = mSettings.getInt(APP_PREFERENCES_PUSH_NOTIFICATION, -1);
            if (position == 1) {
                notification = "Выключено";
            }
        }

        textView.setText(notification);

        String change = "Предупреждать об изменениях";
        position = -1;
        textView = getActivity().findViewById(R.id.change_description);

        if (mSettings.contains(APP_PREFERENCES_PUSH_CHANGE)) {
            position = mSettings.getInt(APP_PREFERENCES_PUSH_CHANGE, -1);
            if (position == 1) {
                change = "Выключено";
            }
        }
        textView.setText(change);

        String mail = "Входящие";
        position = -1;
        textView = getActivity().findViewById(R.id.mail_description);

        if (mSettings.contains(APP_PREFERENCES_PUSH_MAIL)) {
            position = mSettings.getInt(APP_PREFERENCES_PUSH_MAIL, -1);
            if (position == 1) {
                mail = "Выключено";
            }
        }
        textView.setText(mail);

        String forum = "Все";
        position = -1;
        textView = getActivity().findViewById(R.id.forum_description);

        if (mSettings.contains(APP_PREFERENCES_PUSH_FORUM)) {
            position = mSettings.getInt(APP_PREFERENCES_PUSH_FORUM, -1);
            if (position == 1) {
                forum = "Выключено";
            }
        }
        textView.setText(forum);

        String new_resources = "Появление новый файлов";
        position = -1;
        textView = getActivity().findViewById(R.id.new_resources_description);

        if (mSettings.contains(APP_PREFERENCES_PUSH_NEW_RESOURCES)) {
            position = mSettings.getInt(APP_PREFERENCES_PUSH_NEW_RESOURCES, -1);
            if (position == 1) {
                new_resources = "Выключено";
            }
        }
        textView.setText(new_resources);
    }


}
