package com.example.mipro.netschool.Client.Pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class School {

    @SerializedName("schools")
    @Expose
    private List<School_> schools = null;

    public List<School_> getSchools() {
        return schools;
    }

    public void setSchools(List<School_> schools) {
        this.schools = schools;
    }

}