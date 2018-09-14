package com.example.mipro.netschool.Client.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("groupTitle")
    @Expose
    private String groupTitle;
    @SerializedName("files")
    @Expose
    private List<File> files = null;
    @SerializedName("subgroups")
    @Expose
    private List<Subgroup> subgroups = null;

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<Subgroup> getSubgroups() {
        return subgroups;
    }

    public void setSubgroups(List<Subgroup> subgroups) {
        this.subgroups = subgroups;
    }

}