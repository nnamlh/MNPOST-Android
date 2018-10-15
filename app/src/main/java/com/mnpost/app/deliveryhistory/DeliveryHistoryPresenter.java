package com.mnpost.app.deliveryhistory;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.mnpost.app.data.UserInfo;
import com.mnpost.app.data.source.remote.GetMailerDeliveryResponse;
import com.mnpost.app.updatedelivery.UpdateDeliveryContract;
import com.mnpost.app.util.ApiClient;
import com.mnpost.app.util.ApiService;
import com.mnpost.app.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DeliveryHistoryPresenter implements DeliveryHistoryContract.Presenter {

    DeliveryHistoryContract.View mDeliveryHistory;

    private ApiService apiService;

    public String timeChoose;

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    public DeliveryHistoryPresenter(DeliveryHistoryContract.View mView) {
        this.mDeliveryHistory = mView;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        timeChoose = sdf.format(new Date());
        ApiClient.resetApiClient();

        mDeliveryHistory.setPresenter(this);


    }

    @Override
    public void subscribe() {


        apiService = ApiClient.getClient(mDeliveryHistory.getMContext()).create(ApiService.class);
        mDeliveryHistory.showRefeshWipe(true);
        String employeeId = UserInfo.getInstance(mDeliveryHistory.getMContext()).getEmployeeID();

        mCompositeDisposable.add( apiService.getMailerDeliveryHistory(employeeId,timeChoose ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<GetMailerDeliveryResponse>() {


            @Override
            public void onSuccess(GetMailerDeliveryResponse response) {

                mDeliveryHistory.showRefeshWipe(false);

                if (response.getError() == 0) {
                    mDeliveryHistory.refeshData(response.getData());
                } else {
                    Toast.makeText(mDeliveryHistory.getMContext(), response.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(Throwable e) {
                Utils.showError(e, mDeliveryHistory.getMContext());
                mDeliveryHistory.showRefeshWipe(false);
            }
        }));
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setTimeChoose(String date) {
        this.timeChoose = date;
    }
}
