package com.mnpost.app.data.source.remote;

import com.google.gson.annotations.SerializedName;
import com.mnpost.app.data.source.ResponseInfo;

import java.util.List;

public class GetMailerDeliveryResponse extends ResponseInfo {

    @SerializedName("data")
    private List<MailerDeliveryInfo> data;

    public List<MailerDeliveryInfo> getData() {
        return data;
    }

    public void setData(List<MailerDeliveryInfo> data) {
        this.data = data;
    }
}
