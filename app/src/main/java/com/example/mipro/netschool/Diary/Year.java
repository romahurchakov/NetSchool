package com.example.mipro.netschool.Diary;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.mipro.netschool.Diary.Week.setFirstMillis;

public class Year {

    private final int todayWeek;

    private ArrayList<Week> weeks;

    private static Week getFirstWeek() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 8);
        c.set(Calendar.DAY_OF_MONTH, 1);
        setFirstMillis(c);

        if (Calendar.getInstance().getTimeInMillis() < c.getTimeInMillis())
            c.add(Calendar.YEAR, -1);

        if (c.get(Calendar.DAY_OF_WEEK) == 1)
            c.add(Calendar.DATE, -6);
        else
            c.set(Calendar.DAY_OF_WEEK, 2);

        return new Week(c);
    }

    private static Calendar get31A() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 7);
        c.set(Calendar.DAY_OF_MONTH, 31);

        if (Calendar.getInstance().getTimeInMillis() > c.getTimeInMillis())
            c.add(Calendar.YEAR, 1);

        return c;
    }

    public Year() {
        Week week = getFirstWeek();
        Calendar aug31 = get31A();
        Calendar curDatetime = Calendar.getInstance();
        int curWeek = -1;

        weeks = new ArrayList<Week>();

        do {
            if (week.dayInWeek(curDatetime))
                curWeek = weeks.size();
            weeks.add(week);
            week = week.getNextWeek();
        } while (!week.dayInWeek(aug31));
        weeks.add(week);

        todayWeek = curWeek;
    }

    public ArrayList<Week> getWeeks() {
        return weeks;
    }

    public Week getWeek(int i) {
        return weeks.get(i);
    }

    public int getTodayWeekNum() {
        return todayWeek;
    }

    public int yearSize() {
        return weeks.size();
    }

}
