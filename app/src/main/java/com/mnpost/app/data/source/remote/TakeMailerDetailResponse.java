package com.mnpost.app.data.source.remote;

import com.google.gson.annotations.SerializedName;
import com.mnpost.app.data.source.ResponseInfo;

import java.util.List;

public class TakeMailerDetailResponse extends ResponseInfo {

    @SerializedName("data")
    private List<TakeMailerDetailInfo> data;

    public List<TakeMailerDetailInfo> getData() {
        return data;
    }

    public void setData(List<TakeMailerDetailInfo> data) {
        this.data = data;
    }
}
