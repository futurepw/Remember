package com.bigerdranch.android.test.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/12/3.
 */

public class JsonTodata {
    @SerializedName("http")
    private String name;
    @SerializedName("url")
    private String username;
    @SerializedName("all")
    private String password;

    public JsonTodata() {
    }

    public JsonTodata(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
