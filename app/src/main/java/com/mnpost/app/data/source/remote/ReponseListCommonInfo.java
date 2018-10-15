package com.mnpost.app.data.source.remote;

import com.google.gson.annotations.SerializedName;
import com.mnpost.app.data.source.CommonData;
import com.mnpost.app.data.source.ResponseInfo;

import java.util.List;

public class ReponseListCommonInfo extends ResponseInfo {

    @SerializedName("data")
    private List<CommonData> data;

    public List<CommonData> getData() {
        return data;
    }

    public void setData(List<CommonData> data) {
        this.data = data;
    }
}
