package com.mnpost.app.updatedelivery;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.mnpost.app.data.source.ResponseInfo;
import com.mnpost.app.data.source.remote.ReponseListCommonInfo;
import com.mnpost.app.data.source.remote.ResponseWithListText;
import com.mnpost.app.data.source.remote.UpdateDeliverySend;
import com.mnpost.app.util.ApiClient;
import com.mnpost.app.util.ApiService;
import com.mnpost.app.util.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

import static com.google.common.base.Preconditions.checkNotNull;

public class UpdateDeliveryPresenter implements UpdateDeliveryContract.Presenter {

    UpdateDeliveryContract.View updateView;

    private ApiService apiService;

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void subscribe() {
        ApiClient.resetApiClient();
        apiService = ApiClient.getClient(updateView.getMContext()).create(ApiService.class);

        mCompositeDisposable.add(apiService.getReturnReasons().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<ReponseListCommonInfo>() {

            @Override
            public void onSuccess(ReponseListCommonInfo reponseListCommonInfo) {
                updateView.initSpinnerReason(reponseListCommonInfo.getData());
            }

            @Override
            public void onError(Throwable e) {
                Utils.showError(e, updateView.getMContext());
            }
        }));
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    public UpdateDeliveryPresenter(@NonNull UpdateDeliveryContract.View updateView) {

        this.updateView = checkNotNull(updateView, "updateView cannot be null!");

        this.updateView.setPresenter(this);
    }


    @Override
    public Observable<ResponseWithListText> uploadImages(RequestBody body) {

        ApiClient.resetApiClient();

        apiService = ApiClient.getUploadClient(updateView.getMContext()).create(ApiService.class);

        return apiService.uploadDeliveryImages(body).toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ResponseInfo> updateDelivery(UpdateDeliverySend info) {

        ApiClient.resetApiClient();

        apiService = ApiClient.getClient(updateView.getMContext()).create(ApiService.class);

        return apiService.updateDeliveryInfo(info).toObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void update(RequestBody body, final UpdateDeliverySend info) {

        updateView.showLoading(true);

        if (body == null) {
            info.setImages(new ArrayList<String>());

            ApiClient.resetApiClient();

            apiService = ApiClient.getClient(updateView.getMContext()).create(ApiService.class);


            mCompositeDisposable.add(apiService.updateDeliveryInfo(info).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<ResponseInfo>() {


                        @Override
                        public void onSuccess(ResponseInfo response) {
                            updateView.showLoading(false);
                            if (response.getError() == 1) {
                                Toast.makeText(updateView.getMContext(), response.getMsg(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(updateView.getMContext(), "Đã cập nhật", Toast.LENGTH_SHORT).show();
                                updateView.finishUpdate();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            updateView.showLoading(false);
                        }
                    }));
        } else {

            mCompositeDisposable.add(uploadImages(body).flatMap(new Function<ResponseWithListText, ObservableSource<ResponseInfo>>() {
                @Override
                public ObservableSource<ResponseInfo> apply(ResponseWithListText responseWithListText) {

                    info.setImages(responseWithListText.getData());

                    return updateDelivery(info);
                }
            }).subscribeWith(new DisposableObserver<ResponseInfo>() {
                @Override
                public void onNext(ResponseInfo response) {
                    updateView.showLoading(false);
                    if (response.getError() == 1) {
                        Toast.makeText(updateView.getMContext(), response.getMsg(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(updateView.getMContext(), "Đã cập nhật", Toast.LENGTH_SHORT).show();
                        updateView.finishUpdate();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Utils.showError(e, updateView.getMContext());
                    updateView.showLoading(false);
                }

                @Override
                public void onComplete() {
                    updateView.showLoading(false);
                }
            }));
        }
    }
}
