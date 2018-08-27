package com.example.mipro.netschool.Resources;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Group implements Parcelable {

    private String title;
    private boolean haveSub;
    private ArrayList<File> files;
    private ArrayList<SubGroups> subGroups;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        if (subGroups != null) {
            parcel.writeArray(subGroups.toArray());
            parcel.writeByte((byte)1);
        }  else {
            parcel.writeArray(files.toArray());
            parcel.writeByte((byte)0);
        }
    }

    public static final Parcelable.Creator<Group> CREATOR = new Parcelable.Creator<Group>() {
        // распаковываем объект из Parcel
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    // конструктор, считывающий данные из Parcel
    private Group(Parcel parcel) {
        title = parcel.readString();
        Object[] kek = parcel.readArray(ClassLoader.getSystemClassLoader());
        byte flag = parcel.readByte();
        if (flag == 1) {
            haveSub = true;
            for (int i = 0; i < kek.length; i++) {
                subGroups.add((SubGroups)kek[i]);
            }
            files = null;
        } else {
            haveSub = false;
            for (int i = 0; i < kek.length; i++) {
                files.add((File)kek[i]);
            }
            subGroups = null;
        }
    }


    public static class SubGroups implements Parcelable {
        private String title;
        private ArrayList<File> files;
        SubGroups(String title, ArrayList<File> files) {
            this.title = title;
            this.files = files;
        }

        protected SubGroups(Parcel in) {
            title = in.readString();
            files = in.createTypedArrayList(File.CREATOR);
        }

        public static final Creator<SubGroups> CREATOR = new Creator<SubGroups>() {
            @Override
            public SubGroups createFromParcel(Parcel in) {
                return new SubGroups(in);
            }

            @Override
            public SubGroups[] newArray(int size) {
                return new SubGroups[size];
            }
        };

        String getTitle() {
            return this.title;
        }

        ArrayList<File> getFiles() {
            return this.files;
        }

        File getFileOfIndex(int index) {
            return this.files.get(index);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(title);
            parcel.writeTypedList(files);
        }
    }

    Group(String title, boolean haveSub, ArrayList<File> files, ArrayList<SubGroups> subGroups) {
        this.haveSub = haveSub;
        this.title = title;
        if (haveSub) {
            this.files = null;
            this.subGroups = subGroups;
        } else {
            this.files = files;
            this.subGroups = null;
        }
    }

    String getTitle() {
        return this.title;
    }

    boolean isHaveSub(){
        return this.haveSub;
    }

    ArrayList<File> getFiles(){
        return this.files;
    }

    ArrayList<SubGroups> getSubGroups() {
        return this.subGroups;
    }
}
