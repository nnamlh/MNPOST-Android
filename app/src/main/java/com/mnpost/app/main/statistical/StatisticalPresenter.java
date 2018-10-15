package com.mnpost.app.main.statistical;

public class StatisticalPresenter implements StatisticalContract.Presenter {

    StatisticalContract.View mView;

    public StatisticalPresenter(StatisticalContract.View view) {
        this.mView = view;

        mView.setPresenter(this);
    }



    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
