package com.mnpost.app.main.statistical;

import android.support.annotation.NonNull;

import com.mnpost.app.data.UserInfo;
import com.mnpost.app.data.source.remote.StatisticalResponse;
import com.mnpost.app.util.ApiClient;
import com.mnpost.app.util.ApiService;
import com.mnpost.app.util.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

public class StatisticalPresenter implements StatisticalContract.Presenter {

    private ApiService apiService;

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    StatisticalContract.View mView;

    public StatisticalPresenter(StatisticalContract.View view) {

        this.mView =  checkNotNull(view, "LoginView cannot be null!");;

        mView.setPresenter(this);
    }



    @Override
    public void subscribe() {
        String employeeId = UserInfo.getInstance(mView.getMContext()).getEmployeeID();
        ApiClient.resetApiClient();
        apiService = ApiClient.getClient(mView.getMContext()).create(ApiService.class);
        mCompositeDisposable.add(apiService.getReportDelivery(employeeId, mView.getCodeTime())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<StatisticalResponse>() {

                    @Override
                    public void onSuccess(StatisticalResponse statisticalResponse) {
                        if(statisticalResponse.getData() != null) {
                            mView.addPieData(statisticalResponse.getData().getRDelivery());
                            mView.addCoDData(statisticalResponse.getData().getRCOD());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.showError(e, mView.getMContext());
                    }
                }));
    }



    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }
}
