package com.mnpost.app.main.mailer;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mnpost.app.data.UserInfo;
import com.mnpost.app.data.source.remote.GetMailerDeliveryResponse;
import com.mnpost.app.data.source.remote.TakeMailerResponse;
import com.mnpost.app.data.source.remote.UserInfoReponse;
import com.mnpost.app.updatedelivery.UpdateDeliveryContract;
import com.mnpost.app.updatedelivery.UpdateDeliveryFragment;
import com.mnpost.app.util.ApiClient;
import com.mnpost.app.util.ApiService;
import com.mnpost.app.util.Const;
import com.mnpost.app.util.PrefUtils;
import com.mnpost.app.util.Utils;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

public class MailerPresenter implements MailerContract.Presenter {

    private ApiService apiService;

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @NonNull
    private MailerContract.View mMailerView;

    public MailerPresenter(@NonNull MailerContract.View nailerView) {

        mMailerView = checkNotNull(nailerView, "LoginView cannot be null!");

        mMailerView.setPresenter(this);
    }


    @Override
    public void subscribe() {

        ApiClient.resetApiClient();
        apiService = ApiClient.getClient(mMailerView.getMContext()).create(ApiService.class);
        getDelivery();
        getTake();



    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void getDelivery() {
        String employeeId = UserInfo.getInstance(mMailerView.getMContext()).getEmployeeID();

        mCompositeDisposable.add(apiService.getMailerDelivery(employeeId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<GetMailerDeliveryResponse>() {
            @Override
            public void onSuccess(GetMailerDeliveryResponse getMailerDeliveryResponse) {
                if(getMailerDeliveryResponse.getData() != null)
                    mMailerView.refeshDelivery(getMailerDeliveryResponse.getData());

                mMailerView.stopRefeshWipe();
            }

            @Override
            public void onError(Throwable e) {
                Utils.showError(e, mMailerView.getMContext());
                mMailerView.stopRefeshWipe();
            }
        }));
    }

    @Override
    public void getTake() {
        mCompositeDisposable.add(apiService.getTakeMailers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<TakeMailerResponse>(){

            @Override
            public void onSuccess(TakeMailerResponse takeMailerResponse) {

                mMailerView.refeshTake(takeMailerResponse.getData());

                mMailerView.stopRefeshWipe();
            }

            @Override
            public void onError(Throwable e) {
                Utils.showError(e, mMailerView.getMContext());
                mMailerView.stopRefeshWipe();
            }
        }));
    }
}
