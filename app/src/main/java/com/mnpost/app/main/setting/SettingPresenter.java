package com.mnpost.app.main.setting;

public class SettingPresenter implements SettingContract.Presenter {

    SettingContract.View mView;

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }


    public SettingPresenter(SettingContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

}
