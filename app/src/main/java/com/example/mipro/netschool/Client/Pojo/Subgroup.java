package com.example.mipro.netschool.Client.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subgroup {

    @SerializedName("subgroupTitle")
    @Expose
    private String subgroupTitle;
    @SerializedName("files")
    @Expose
    private List<File_> files = null;

    public String getSubgroupTitle() {
        return subgroupTitle;
    }

    public void setSubgroupTitle(String subgroupTitle) {
        this.subgroupTitle = subgroupTitle;
    }

    public List<File_> getFiles() {
        return files;
    }

    public void setFiles(List<File_> files) {
        this.files = files;
    }

}