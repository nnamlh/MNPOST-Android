package com.mnpost.app.deliveryhistory;

import android.content.Context;

import com.mnpost.app.BasePresenter;
import com.mnpost.app.BaseView;
import com.mnpost.app.data.source.remote.MailerDeliveryInfo;

import java.util.List;

public class DeliveryHistoryContract {
    interface View extends BaseView<Presenter> {

        Context getMContext();

        void onBAck();

        void showRefeshWipe(boolean show);

        void refeshData(List<MailerDeliveryInfo> mailerDeliveryInfoList);
    }

    interface Presenter extends BasePresenter {

        void setTimeChoose(String date);

    }
}
