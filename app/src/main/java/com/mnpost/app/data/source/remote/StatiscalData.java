package com.mnpost.app.data.source.remote;

import com.google.gson.annotations.SerializedName;
import com.mnpost.app.data.source.CommonStatistical;

import java.util.List;

public class StatiscalData {

    @SerializedName("RDelivery")
    private List<CommonStatistical> RDelivery;


    @SerializedName("RCOD")
    private List<CommonStatistical> RCOD;

    public List<CommonStatistical> getRDelivery() {
        return RDelivery;
    }

    public void setRDelivery(List<CommonStatistical> RDelivery) {
        this.RDelivery = RDelivery;
    }

    public List<CommonStatistical> getRCOD() {
        return RCOD;
    }

    public void setRCOD(List<CommonStatistical> RCOD) {
        this.RCOD = RCOD;
    }
}
