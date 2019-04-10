package com.mnpost.app.data.source.remote;

import com.google.gson.annotations.SerializedName;
import com.mnpost.app.data.source.ResponseInfo;

import java.util.List;

public class ResponseNotices extends ResponseInfo {

    @SerializedName("data")
    private List<NotificationInfo> data;

    public List<NotificationInfo> getData() {
        return data;
    }

    public void setData(List<NotificationInfo> data) {
        this.data = data;
    }
}
