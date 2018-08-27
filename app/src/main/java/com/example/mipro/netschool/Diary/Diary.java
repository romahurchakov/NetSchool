package com.example.mipro.netschool.Diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mipro.netschool.R;

import java.util.Calendar;

import static com.example.mipro.netschool.MainActivity.LOG_TAG;

public class Diary extends Fragment {

    public static Year YEAR;
    private final static int REQUEST_CALENDAR = 232323;

    private int curWeek;
    private ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Diary::onCreate");
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        YEAR = new Year();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Diary::onCreateView");
        View v = inflater.inflate(R.layout.fragment_diary, null);

        Log.d(LOG_TAG, "1");
        //viewPager = new ViewPager(getContext());

        viewPager = (ViewPager) v.findViewById(R.id.pager_diary);

        PagerAdapter pagerAdapter = new DiaryPagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabs = (TabLayout) v.findViewById(R.id.pager_tap_strip_diary);

        tabs.setupWithViewPager(viewPager);

        curWeek = YEAR.getTodayWeekNum();
        Log.d(LOG_TAG, "2");
        viewPager.setCurrentItem(curWeek);

        Log.d(LOG_TAG, "3");

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d(LOG_TAG, "Diary::onPageSelected, week #" + position);
                curWeek = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });

        return v;
    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, "DiaryList::onResume");
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.diary, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_calendar: {
                showCalendar();
                break;
            }
            default: {
                Log.d(LOG_TAG, "Diary::onOptionsItemSelected, default");
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCalendar() {
        Bundle arguments = new Bundle();
        arguments.putInt(CalendarFragment.SELECTED_WEEK, curWeek);
        CalendarFragment calendar = new CalendarFragment();
        calendar.setArguments(arguments);
        calendar.setTargetFragment(this, REQUEST_CALENDAR);
        calendar.show(getFragmentManager(), calendar.getClass().getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "Diary::onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CALENDAR: {
                    int sel_week = data.getIntExtra(CalendarFragment.SELECTED_WEEK, -1);
                    curWeek = sel_week;
                    viewPager.setCurrentItem(curWeek);
                    break;
                }
                default: {
                    Log.d(LOG_TAG, "Diary::onActivityResult, default");
                    break;
                }
            }
        }
    }

    private class DiaryPagerAdapter extends FragmentStatePagerAdapter {

        public DiaryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DiaryList.newPage(position);
        }

        @Override
        public int getCount() {
            return YEAR.yearSize();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return YEAR.getWeek(position).toString();
        }

    }

}
