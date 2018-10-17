package com.mnpost.app.main.mailer;

import android.content.Context;

import com.mnpost.app.BasePresenter;
import com.mnpost.app.BaseView;
import com.mnpost.app.data.source.remote.MailerDeliveryInfo;
import com.mnpost.app.data.source.remote.TakeMailerInfo;

import java.util.List;

public class MailerContract {

    interface View extends BaseView<Presenter> {

        Context getMContext();

        void openScanBarcode();

        void refeshDelivery(List<MailerDeliveryInfo> mailerDeliveryInfoList);

        void refeshTake(List<TakeMailerInfo> takeMailerInfos);
        void stopRefeshWipe();

        void searchMailer(String code);

    }

    interface Presenter extends BasePresenter {

        void getDelivery();

        void getTake();

    }

}
