package com.mnpost.app.data.source.remote;

import android.content.pm.ResolveInfo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TakeMailerResponse extends ResolveInfo {


    @SerializedName("data")
    private List<TakeMailerInfo> data;


    public List<TakeMailerInfo> getData() {
        return data;
    }

    public void setData(List<TakeMailerInfo> data) {
        this.data = data;
    }
}
