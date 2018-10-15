package com.mnpost.app.login;

import android.app.Activity;
import android.view.View;

import com.mnpost.app.BasePresenter;
import com.mnpost.app.BaseView;
import com.mnpost.app.data.source.remote.APIKeyResponse;
import com.mnpost.app.data.source.remote.UserInfoReponse;

import io.reactivex.Observable;


public interface LoginContract {

    interface View extends BaseView<Presenter> {

        Activity getMContext();

        void loginSubmit();

        void showLoading(boolean flag);

        void nextTask();

    }

    interface Presenter extends BasePresenter {

        void callLogin(String user, String pass);

        Observable<APIKeyResponse> getAPIKey(String user, String pass);

        Observable<UserInfoReponse> getUserInfo(String user);



    }

}
