package com.example.mipro.netschool.Settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.mipro.netschool.R;

import java.util.ArrayList;

public class PushElement extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary_list, null);
        return view;
    }

    private class PushAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> arrayList;

        public PushAdapter(Context context, ArrayList<String> arrayList) {
            super(context, R.layout.section, arrayList);
            this.context = context;
            this.arrayList = arrayList;
        }
    }
}
