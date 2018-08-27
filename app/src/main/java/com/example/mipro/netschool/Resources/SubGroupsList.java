package com.example.mipro.netschool.Resources;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mipro.netschool.R;
import com.example.mipro.netschool.Timetable;

import java.util.ArrayList;

import static com.example.mipro.netschool.MainActivity.LOG_TAG;

public class SubGroupsList extends ListFragment {
    Group group;
    ArrayList<File> files;
    SubGroupAdapter subGroupAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary_list, null);
        getData();
        getActivity().setTitle("Учебные материалы");
        generationData();
        subGroupAdapter = new SubGroupAdapter(getContext(), files);
        setListAdapter(subGroupAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://qaru.site/questions/694700/android-passing-objects-between-fragments"));
        getActivity().startActivity(intent);
    }

    void getData() {
        Bundle bundle = getArguments();
        group = (Group) bundle.getParcelable("Group");

    }

    void generationData() {
        //Проходимся по подгруппам и формируем массив файлов, в котром первый файл в разделе
        // имеет поле isFirst == true. У остальных isFirst == false

        //доделать тут
        files = new ArrayList<File>();
        if (group.isHaveSub()) {
            for (int i = 0; i < group.getSubGroups().size(); i++) {
                files.add(new File("special", group.getTitle(), R.drawable.ic_account_circle_black_24dp, false,  null));
                for (int j = 0; j < group.getSubGroups().get(i).getFiles().size(); j++) {
                    files.add(group.getSubGroups().get(i).getFiles().get(j));
                }
            }
        } else {
            files.add(new File("special", group.getTitle(), R.drawable.ic_account_circle_black_24dp, false,  null));
            for (int i = 0; i < group.getFiles().size(); i++) {
                files.add(group.getFiles().get(i));
            }
        }
    }



    private class SubGroupAdapter extends ArrayAdapter<File> {
        Context context;
        ArrayList<File> files;

        public SubGroupAdapter(Context context, ArrayList<File> files) {
            super(context, R.layout.section, files);
            this.files = files;
            this.context = context;
        }

        /*@Override
        public boolean isEnabled(int position) {
            if (files.get(position).getIsFirst()) {
                return false;
            } else {
                return true;
            }
        }*/

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            File file = files.get(i);

            if (file.getResources().equals("special")) {
                view = getLayoutInflater().inflate(R.layout.group_name, null);
                TextView textView = view.findViewById(R.id.group_name);
                textView.setText(file.getLink().toString());
                return view;
            } else {
                view = getLayoutInflater().inflate(R.layout.res_list_elem, null);
                TextView resources = view.findViewById(R.id.resources);
                TextView link = view.findViewById(R.id.link);
                ImageView imageView = view.findViewById(R.id.badgeOfFile);
                resources.setText(file.getResources());
                link.setText(file.getLink().toString());
                imageView.setImageResource(file.getImage());
                return view;
            }
/*

            if (file.getIsFirst()) {
                section.setText("kek");
            } else {
                delete.removeAllViews();
            }
*/


        }

    }
}
