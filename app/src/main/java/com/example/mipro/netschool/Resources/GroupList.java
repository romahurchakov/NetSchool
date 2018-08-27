package com.example.mipro.netschool.Resources;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mipro.netschool.Diary.DiaryList;
import com.example.mipro.netschool.Diary.Quest;
import com.example.mipro.netschool.Diary.QuestActivity;
import com.example.mipro.netschool.R;
import com.example.mipro.netschool.Timetable;

import java.util.ArrayList;

import static com.example.mipro.netschool.Diary.Quest.COMP_Q;
import static com.example.mipro.netschool.Diary.Quest.NEW_Q;
import static com.example.mipro.netschool.MainActivity.LOG_TAG;

public class GroupList extends ListFragment {

    ArrayList<Group> groupList;
    GroupList.GroupAdapter groupAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Учебные материалы");
        View view = inflater.inflate(R.layout.fragment_diary_list, null);
        getData();
        groupAdapter = new GroupList.GroupAdapter(getContext(), groupList);
        setListAdapter(groupAdapter);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Fragment fragment = new SubGroupsList();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Group", groupList.get(position));
        fragment.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flMAIN, fragment);
        ft.addToBackStack("stack");
        ft.commit();
    }


    void getData() {
        groupList = new ArrayList<Group>();

        //Тут обращаемся к серверу и получаем данные о группах, но я это имитриую(как твоя мамаша оргазм)
        ArrayList<File> files = new ArrayList<File>();
        ArrayList<Group.SubGroups> subGroups = new ArrayList<Group.SubGroups>();

        // если файл isFirst, то добавляем специальный файл с название "special"
        files.add(new File("sdas1", "asda1", R.drawable.ic_account_circle_black_24dp, true, null));
        files.add(new File("sdas2", "asda2", R.drawable.ic_account_circle_black_24dp, false, null));
        files.add(new File("sdas3", "asda3", R.drawable.ic_account_circle_black_24dp, false, null));
        files.add(new File("sdas4", "asda4", R.drawable.ic_account_circle_black_24dp, false, null));

        subGroups.add(new Group.SubGroups("fffff1", files));
        subGroups.add(new Group.SubGroups("fffff2", files));
        subGroups.add(new Group.SubGroups("fffff3", files));

        groupList.add(new Group("kek1", false, files, null));
        groupList.add(new Group("kek2", false, files, null));
        groupList.add(new Group("kek3", true, null, subGroups));
        groupList.add(new Group("kek4", false, files, null));

    }

    private class GroupAdapter extends ArrayAdapter<Group> {
        ArrayList<Group> groups;
        Context context;

        public GroupAdapter(Context context, ArrayList<Group> groups) {
            super(context, R.layout.section, groups);
            this.groups = groups;
            this.context = context;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.section, null);
            Group group = groups.get(i);

            TextView title = (TextView) view.findViewById(R.id.title_of_section);
            TextView description = (TextView) view.findViewById(R.id.description_of_section);

            title.setText(group.getTitle());
            description.setText("тут может быть описание, или я его удалю");

            return view;
        }

    }
}


