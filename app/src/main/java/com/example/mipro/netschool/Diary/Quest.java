package com.example.mipro.netschool.Diary;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.mipro.netschool.MainActivity.SOCKET;

public class Quest implements Parcelable {

    public static final int NEW_Q = 0;
    public static final int COMP_Q = 1;
    public static final int PROC_Q = 2;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private static final String[] wordsD = { "января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря" };
    private static final String[] wordsW = { "ВОСКРЕСЕНЬЕ", "ПОНЕДЕЛЬНИК", "ВТОРНИК", "СРЕДА", "ЧЕТВЕРГ", "ПЯТНИЦА", "СУББОТА" };

    private int id;
    private int status;
    private boolean inTime;
    private String date;
    private String name;
    private String author;
    private String title;
    private String type;
    private String mark;
    private String weight;
    private boolean isFirst;
    private String description;
    private String file;

    public Quest(int id, int status, boolean inTime, String date,
          String name, String author, String title, String type,
          String mark, String weight, boolean isFirst) {
        this.id = id;
        this.status = status;
        this.inTime = inTime;
        this.date = date;
        this.name = name;
        this.author = author;
        this.title = title;
        this.type = type;
        this.mark = mark;
        this.weight = weight;
        this.isFirst = isFirst;
        this.description = "Empty description";
        this.file = "";
    }

    private Quest(Parcel parcel) {
        this.id = parcel.readInt();
        this.status = parcel.readInt();
        this.inTime = parcel.readInt() == 1;
        this.date = parcel.readString();
        this.name = parcel.readString();
        this.author = parcel.readString();
        this.title = parcel.readString();
        this.type = parcel.readString();
        this.mark = parcel.readString();
        this.weight = parcel.readString();
        this.isFirst = parcel.readInt() == 1;
        this.description = parcel.readString();
        this.file = parcel.readString();
    }

    public static final Parcelable.Creator<Quest> CREATOR = new Parcelable.Creator<Quest>() {
        public Quest createFromParcel(Parcel in) {
            return new Quest(in);
        }

        public Quest[] newArray(int size) {
            return new Quest[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(status);
        parcel.writeInt(inTime ? 1 : 0);
        parcel.writeString(date);
        parcel.writeString(name);
        parcel.writeString(author);
        parcel.writeString(title);
        parcel.writeString(type);
        parcel.writeString(mark);
        parcel.writeString(weight);
        parcel.writeInt(isFirst ? 1 : 0);
        parcel.writeString(description);
        parcel.writeString(file);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void addData(String description, String file) {
        this.description = description;
        this.file = file;
    }

    public void addData() {
        SOCKET.getLessonDescription(this);
    }


    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public boolean isInTime() {
        return inTime;
    }

    public String getDate() {
        return date;
    }

    public String getDateW() {
        Calendar c = getCalendar();
        return "" + c.get(Calendar.DAY_OF_MONTH)
                + ' ' + wordsD[c.get(Calendar.MONTH)]
                + ' ' + c.get(Calendar.YEAR);
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getMark() {
        return mark;
    }

    public String getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }

    public String getFile() {
        return file;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public String getDayOfWeek() {
        Calendar c = getCalendar();
        return wordsW[c.get(Calendar.DAY_OF_WEEK) - 1];
    }

    private Calendar getCalendar() {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return c;
    }

}
