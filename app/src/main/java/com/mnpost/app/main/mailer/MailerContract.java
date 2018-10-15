package com.mnpost.app.main.mailer;

import android.content.Context;

import com.mnpost.app.BasePresenter;
import com.mnpost.app.BaseView;
import com.mnpost.app.data.source.remote.MailerDeliveryInfo;

import java.util.List;

public class MailerContract {

    interface View extends BaseView<Presenter> {

        Context getMContext();

        void openScanBarcode();

        void refeshData(List<MailerDeliveryInfo> mailerDeliveryInfoList);
        void stopRefeshWipe();

    }

    interface Presenter extends BasePresenter {


    }

}
