package com.example.mipro.netschool.Resources;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class File implements Parcelable {
    private String resources;
    private String link;
    private int image;
    private String subGroupTitle;
    private boolean isFirst;
    private int size;

    File(String resources, String link, int image, boolean isFirst, String subGroupTitle) {
        this.resources = resources;
        this.link = link;
        this.image = image;
        this.isFirst = isFirst;
        this.subGroupTitle = subGroupTitle;
        this.size = -1;
    }

    protected File(Parcel in) {
        resources = in.readString();
        link = in.readString();
        image = in.readInt();
        subGroupTitle = in.readString();
        isFirst = in.readByte() != 0;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static final Creator<File> CREATOR = new Creator<File>() {
        @Override
        public File createFromParcel(Parcel in) {
            return new File(in);
        }

        @Override
        public File[] newArray(int size) {
            return new File[size];
        }
    };

    String getResources(){
        return this.resources;
    }

    Uri getLink(){
        return Uri.parse(this.link);
    }

    int getImage() {
        return this.image;
    }

    boolean getIsFirst(){
        return this.isFirst;
    }

    String getSubGroupTitle() { return this.subGroupTitle; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(resources);
        parcel.writeString(link);
        parcel.writeInt(image);
        parcel.writeString(subGroupTitle);
        parcel.writeByte((byte) (isFirst ? 1 : 0));
    }
}
