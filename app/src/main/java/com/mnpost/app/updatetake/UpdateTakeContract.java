package com.mnpost.app.updatetake;

import android.content.Context;

import com.mnpost.app.BasePresenter;
import com.mnpost.app.BaseView;
import com.mnpost.app.data.source.remote.TakeMailerDetailInfo;

import java.util.List;


public class UpdateTakeContract {

    interface View extends BaseView<Presenter> {

        Context getMContext();

        void onBAck();

        void refeshData(List<TakeMailerDetailInfo> datas);

        void setRefeshView(boolean isView);

        void searchMailer(String code);

        void showLoading(boolean flag);

        void updateTake(int position);
    }

    interface Presenter extends BasePresenter {

        void updateTakeMailer(String mailerId, int position);

    }
}
