package com.mnpost.app.data.source.remote;

import com.google.gson.annotations.SerializedName;
import com.mnpost.app.data.source.CommonData;
import com.mnpost.app.data.source.ResponseInfo;

public class ResponseCommonInfo extends ResponseInfo {

    @SerializedName("data")
    private CommonData data;

    public CommonData getData() {
        return data;
    }

    public void setData(CommonData data) {
        this.data = data;
    }
}
