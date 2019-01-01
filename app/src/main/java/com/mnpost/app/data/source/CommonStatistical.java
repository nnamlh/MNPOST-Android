package com.mnpost.app.data.source;

import com.google.gson.annotations.SerializedName;

public class CommonStatistical {

    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private float data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }
}
