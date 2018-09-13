package com.example.mipro.netschool.Client.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("passkey")
    @Expose
    private String passkey;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("systemType")
    @Expose
    private Integer systemType;

    public LoginRequest(String login, String passkey, Integer id, String token, Integer systemType) {
        this.login = login;
        this.passkey = passkey;
        this.id = id;
        this.token = token;
        this.systemType = systemType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasskey() {
        return passkey;
    }

    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getSystemType() {
        return systemType;
    }

    public void setSystemType(Integer systemType) {
        this.systemType = systemType;
    }

}