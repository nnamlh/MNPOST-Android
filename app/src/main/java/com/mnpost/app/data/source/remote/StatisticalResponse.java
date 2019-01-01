package com.mnpost.app.data.source.remote;

import com.google.gson.annotations.SerializedName;
import com.mnpost.app.data.source.ResponseInfo;

public class StatisticalResponse extends ResponseInfo {

    @SerializedName("data")
    private StatiscalData data;


    public StatiscalData getData() {
        return data;
    }

    public void setData(StatiscalData data) {
        this.data = data;
    }
}
