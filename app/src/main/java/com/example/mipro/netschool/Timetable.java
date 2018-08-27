package com.example.mipro.netschool;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Timetable extends Fragment {
    String[] lessons = {
            "Английский Язык", "каб. английского языка 1",
            "Английский Язык", "каб. английского языка 1",
            "Музыка", "каб. музыки",
            "Геометрия", "каб. химии" };
    String[] time = {"8:30", "9:15", "9:30", "10:15", "10:30", "11:15", "11:25", "12:10"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_timetable, null);
        ListView listView = (ListView) view.findViewById(R.id.menuMain);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        return view;
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return lessons.length/2;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout, null);
            ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
            TextView textView_name = (TextView)view.findViewById(R.id.resources);
            TextView textView_class = (TextView)view.findViewById(R.id.classRoom);
            TextView textView_time1 = (TextView)view.findViewById(R.id.timeOne);
            TextView textView_time2 = (TextView)view.findViewById(R.id.timeTwo);
            TextView textView_checker = (TextView)view.findViewById(R.id.checker);
            textView_name.setText(lessons[2*i]);
            textView_class.setText(lessons[2*i+1]);
            textView_time1.setText(time[2*i]);
            textView_time2.setText(time[2*i+1]);
            textView_checker.setText("ququ");
            return view;
        }
    }
}
