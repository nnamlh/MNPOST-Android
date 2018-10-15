package com.mnpost.app.data.source.remote;

import com.google.gson.annotations.SerializedName;
import com.mnpost.app.data.source.ResponseInfo;

import java.util.List;

public class ResponseWithListText extends ResponseInfo {

    @SerializedName("data")
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
