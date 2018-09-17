package com.example.mipro.netschool.Client.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Notification {

    @SerializedName("posts")
    @Expose
    private List<Notification_> posts = null;

    public List<Notification_> getNotifications() {
        return posts;
    }

    public void setNotifications(List<Notification_> posts) {
        this.posts = posts;
    }
}
