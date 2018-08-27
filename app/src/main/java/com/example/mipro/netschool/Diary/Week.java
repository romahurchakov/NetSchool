package com.example.mipro.netschool.Diary;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.mipro.netschool.MainActivity.SOCKET;

public class Week {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");

    private final Calendar monday;
    private final Calendar sunday;

    public Week(Calendar monday1) {
        monday = copy(monday1);
        setFirstMillis(monday);
        sunday = copy(monday);
        sunday.add(Calendar.DATE, 6);
        setLastMillis(sunday);
    }

    @Override
    public String toString() {
        return sdf.format(monday.getTime()) + " - "
                + sdf.format(sunday.getTime());
    }

    public boolean dayInWeek(Calendar day) {
        return day.getTimeInMillis() >= monday.getTimeInMillis()
                && day.getTimeInMillis() <= sunday.getTimeInMillis();
    }

    public Week getNextWeek() {
        Calendar nextMonday = copy(monday);
        nextMonday.add(Calendar.DATE, 7);
        return new Week(nextMonday);
    }

    public ArrayList<Quest> getQuests() {
        return SOCKET.getTasksAndMarks(monday, 0);
    }

    public Calendar getDay(int i) {
        Calendar day = copy(monday);
        day.add(Calendar.DATE, i % 7);
        return day;
    }

    @NonNull
    public static Calendar copy(Calendar c) {
        Calendar cc = Calendar.getInstance();
        cc.setTimeInMillis(c.getTimeInMillis());
        return cc;
    }

    @NonNull
    public static void setFirstMillis(Calendar c) {
        c.set(Calendar.AM_PM, Calendar.AM);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }

    @NonNull
    public static void setLastMillis(Calendar c) {
        c.set(Calendar.AM_PM, Calendar.PM);
        c.set(Calendar.HOUR, 11);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
    }

}
