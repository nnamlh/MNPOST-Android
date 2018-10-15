package com.mnpost.app.Splash;

import android.content.Context;

import com.mnpost.app.BasePresenter;
import com.mnpost.app.BaseView;
import com.mnpost.app.data.source.remote.UserInfoReponse;
import com.mnpost.app.login.LoginContract;

import io.reactivex.Observable;

public interface SplashContract {

    interface View extends BaseView<Presenter> {
        void showSettingsDialog();
        void openSettings();
        void checkPermission();
        void showNextTask();
        void showLoginForm();

        Context getMContext();

    }

    interface Presenter extends BasePresenter {

        boolean checkLogin();
        void submitCheck();



    }

}
