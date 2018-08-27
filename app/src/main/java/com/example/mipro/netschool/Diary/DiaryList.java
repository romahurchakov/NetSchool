package com.example.mipro.netschool.Diary;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mipro.netschool.R;

import java.util.ArrayList;

import static com.example.mipro.netschool.Diary.Diary.YEAR;
import static com.example.mipro.netschool.MainActivity.LOG_TAG;
import static com.example.mipro.netschool.Diary.Quest.COMP_Q;
import static com.example.mipro.netschool.Diary.Quest.NEW_Q;
import static com.example.mipro.netschool.Diary.Quest.PROC_Q;

public class DiaryList extends ListFragment {

    private static final String PAGE_STRING = "number_of_page";

    private DiaryList.DiaryAdapter diaryAdapter;
    private int curWeek;
    ArrayList<Quest> quests;


    static DiaryList newPage(int page) {
        DiaryList diaryList = new DiaryList();
        Bundle arguments = new Bundle();
        arguments.putInt(PAGE_STRING, page);
        diaryList.setArguments(arguments);
        return diaryList;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curWeek = getArguments().getInt(PAGE_STRING);
        quests = YEAR.getWeek(curWeek).getQuests();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "DiaryList::onCreateView");
        View v = inflater.inflate(R.layout.fragment_diary_list, null);

        diaryAdapter = new DiaryList.DiaryAdapter();
        setListAdapter(diaryAdapter);

        return v;
    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, "DiaryList::onResume, week #" + curWeek);
        super.onResume();

        quests = YEAR.getWeek(curWeek).getQuests();
        diaryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Quest quest = quests.get(position);
        quest.addData();

        Intent intent = new Intent(getActivity(), QuestActivity.class);
        intent.putExtra("quest_info", quest);

        startActivity(intent);
    }

    private class DiaryAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return quests.size();
        }

        @Override
        public Object getItem(int i) {
            return quests.get(i);
        }

        @Override
        public long getItemId(int i) {
            return quests.get(i).getId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Log.v(LOG_TAG, "DiaryAdapter::getView, week #" + curWeek + ", lesson #" + i);

            view = getLayoutInflater().inflate(R.layout.list_diary_quest, null);
            LinearLayout ll = view.findViewById(R.id.ll);
            LinearLayout llM = view.findViewById(R.id.llMain);
            LinearLayout llD = view.findViewById(R.id.dayOfWeekDiary);
            TextView tvD = view.findViewById(R.id.dayOfWeekDiaryText);

            View vert = view.findViewById(R.id.vert);
            ImageView imageView = (ImageView)view.findViewById(R.id.typeIm);
            TextView lesson = (TextView)view.findViewById(R.id.lesson);
            TextView description = (TextView)view.findViewById(R.id.description);
            TextView date = (TextView)view.findViewById(R.id.date);
            TextView grade = (TextView)view.findViewById(R.id.grade);

            Quest quest = quests.get(i);

            if (quest.isFirst())
                tvD.setText(quest.getDayOfWeek());
            else
                llM.removeView(llD);

            vert.setBackgroundColor(typeToColor(quest.getStatus(), quest.isInTime()));
            Drawable im = typeToImage(quest.getStatus(), quest.isInTime());

            if (quest.getStatus() == NEW_Q) {
                ((LinearLayout) view.findViewById(R.id.quest_body))
                        .setBackgroundColor(getResources()
                                .getColor(R.color.colorPrimarySuperLight));
            } else {
                ll.removeView(view.findViewById(R.id.quest_new));
            }

            if (im == null) {
                ll.removeView(imageView);
            } else {
                imageView.setImageDrawable(im);
            }

            lesson.setText(quest.getName());
            description.setText(quest.getTitle());
            date.setText(quest.getDateW());
            grade.setText(("" + (quest.getStatus() == COMP_Q ? quest.getWeight() : "")));

            return view;
        }

    }

    private int typeToColor(int type, boolean b) {
        switch (type) {
            case COMP_Q:
                return getResources().getColor(R.color.colorHomeWork);
            case NEW_Q:
                return getResources().getColor(R.color.colorContrWork);
            case PROC_Q:
                if (b)
                    return getResources().getColor(R.color.colorLetters);
                return getResources().getColor(R.color.colorDiaryFail);
        }

        Log.e(LOG_TAG, "DiaryList::typeToColor \"default\"");
        return getResources().getColor(R.color.colorDiaryDate);
    }

    private Drawable typeToImage(int type, boolean b) {
        switch (type) {
            case COMP_Q:
                return getResources().getDrawable(R.drawable.quest_completed);
            case PROC_Q:
                if (!b)
                    return getResources().getDrawable(R.drawable.quest_failed);
        }

        return null;
    }

}

