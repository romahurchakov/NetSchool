package com.example.mipro.netschool.Diary;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mipro.netschool.R;

import java.util.Calendar;

import static com.example.mipro.netschool.Diary.Diary.YEAR;
import static com.example.mipro.netschool.MainActivity.LOG_TAG;

public class CalendarFragment extends DialogFragment {

    public static final String SELECTED_WEEK = "selected_week";
    private static final String[] MONTHS = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };

    private int curWeek;
    private TextView title;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "CalendarFragment::onCreateView");
        View v = inflater.inflate(R.layout.fragment_calendar, null);

        curWeek = getArguments().getInt(SELECTED_WEEK);
        title = (TextView) v.findViewById(R.id.calendar_title);
        ListView listView = (ListView) v.findViewById(R.id.list_calendar_weeks);

        CalendarAdapter calendarAdapter = new CalendarAdapter();
        listView.setAdapter(calendarAdapter);

        listView.setSelection(curWeek - 1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(LOG_TAG, "CalendarFragment::onItemClick, new week #" + i);

                Intent intent = new Intent();
                intent.putExtra(SELECTED_WEEK, i);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

                dismiss();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                Log.d(LOG_TAG, "Scroll state: " + scrollState);
            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                Calendar sunday = YEAR.getWeek(firstVisibleItem + 1).getDay(6);
                String month = MONTHS[sunday.get(Calendar.MONTH)];
                String year = "" + sunday.get(Calendar.YEAR);
                title.setText(month + ", " + year);
            }
        });

        return v;
    }

    public void onClick(View v) {
        Log.d(LOG_TAG, "CalendarFragment::onClick");
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        Log.d(LOG_TAG, "CalendarFragment::onDismiss");
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        Log.d(LOG_TAG, "CalendarFragment::onCancel");
        super.onCancel(dialog);
    }

    private class CalendarAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            Log.v(LOG_TAG, "CalendarAdapter::getCount");
            return YEAR.yearSize();
        }

        @Override
        public Object getItem(int i) {
            Log.v(LOG_TAG, "CalendarAdapter::getItem");
            return YEAR.getWeeks().get(i);
        }

        @Override
        public long getItemId(int i) {
            Log.v(LOG_TAG, "CalendarAdapter::getItemId");
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Log.v(LOG_TAG, "CalendarAdapter::getView, week #" + i);
            view = getLayoutInflater().inflate(R.layout.list_calendar_week, null);

            int bckgrnd;
            if (i == YEAR.getTodayWeekNum()) {
                bckgrnd = getResources().getColor(R.color.calendarToday);
            } else if (i == curWeek) {
                bckgrnd = getResources().getColor(R.color.calendarCurrent);
            } else {
                if (i % 2 == 1) {
                    bckgrnd = getResources().getColor(R.color.calendarLight);
                } else {
                    bckgrnd = getResources().getColor(R.color.calendarDark);
                }
            }
            (view.findViewById(R.id.week_days)).setBackgroundColor(bckgrnd);

            ((TextView)view.findViewById(R.id.week_number)).setText("" + (i + 1));
            ((TextView)view.findViewById(R.id.day1)).setText("" + YEAR.getWeek(i).getDay(0).get(Calendar.DAY_OF_MONTH));
            ((TextView)view.findViewById(R.id.day2)).setText("" + YEAR.getWeek(i).getDay(1).get(Calendar.DAY_OF_MONTH));
            ((TextView)view.findViewById(R.id.day3)).setText("" + YEAR.getWeek(i).getDay(2).get(Calendar.DAY_OF_MONTH));
            ((TextView)view.findViewById(R.id.day4)).setText("" + YEAR.getWeek(i).getDay(3).get(Calendar.DAY_OF_MONTH));
            ((TextView)view.findViewById(R.id.day5)).setText("" + YEAR.getWeek(i).getDay(4).get(Calendar.DAY_OF_MONTH));
            ((TextView)view.findViewById(R.id.day6)).setText("" + YEAR.getWeek(i).getDay(5).get(Calendar.DAY_OF_MONTH));
            ((TextView)view.findViewById(R.id.day7)).setText("" + YEAR.getWeek(i).getDay(6).get(Calendar.DAY_OF_MONTH));
            
            return view;
        }
    }

}
