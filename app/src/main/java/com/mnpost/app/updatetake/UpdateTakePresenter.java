package com.mnpost.app.updatetake;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.mnpost.app.data.source.ResponseInfo;
import com.mnpost.app.data.source.remote.TakeMailerDetailResponse;
import com.mnpost.app.data.source.remote.UpdateTakeMailerSend;
import com.mnpost.app.util.ApiClient;
import com.mnpost.app.util.ApiService;
import com.mnpost.app.util.Utils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class UpdateTakePresenter implements UpdateTakeContract.Presenter{
    UpdateTakeContract.View mUpdateTakeView;

    private ApiService apiService;

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    public UpdateTakePresenter(UpdateTakeFragment updateTakeFragment) {

        mUpdateTakeView = updateTakeFragment;

        mUpdateTakeView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        ApiClient.resetApiClient();
        apiService = ApiClient.getClient(mUpdateTakeView.getMContext()).create(ApiService.class);

        mCompositeDisposable.add(apiService.getTakeMailerDetails(Utils.TakeMailerInfo.getDocumentID()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<TakeMailerDetailResponse>(){

            @Override
            public void onSuccess(TakeMailerDetailResponse takeMailerDetailResponse) {

                mUpdateTakeView.setRefeshView(false);
                mUpdateTakeView.refeshData(takeMailerDetailResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                Utils.showError(e, mUpdateTakeView.getMContext());
                mUpdateTakeView.setRefeshView(false);
            }
        }));
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void updateTakeMailer(String mailerId, final int position, float weight) {

        mUpdateTakeView.showLoading(true);
        UpdateTakeMailerSend info = new UpdateTakeMailerSend();

        info.setDocumentId(Utils.TakeMailerInfo.getDocumentID());
        info.setWeight(weight);

        info.setMailers(mailerId);

        mCompositeDisposable.add(apiService.updateTakeMailer(info).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<ResponseInfo>(){

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                mUpdateTakeView.showLoading(false);

                if(responseInfo.getError() == 1) {
                    Toast.makeText(mUpdateTakeView.getMContext(), responseInfo.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    mUpdateTakeView.updateTake(position);
                }
            }

            @Override
            public void onError(Throwable e) {
                Utils.showError(e, mUpdateTakeView.getMContext());
                mUpdateTakeView.showLoading(false);
            }
        }));
    }

    @Override
    public void cancelTakeMailer(String mailerId, final int position) {
        mUpdateTakeView.showLoading(true);
        UpdateTakeMailerSend info = new UpdateTakeMailerSend();

        info.setDocumentId(Utils.TakeMailerInfo.getDocumentID());

        info.setWeight(0);
        info.setMailers(mailerId);

        mCompositeDisposable.add(apiService.cancelTakeMailer(info).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<ResponseInfo>(){

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                mUpdateTakeView.showLoading(false);

                if(responseInfo.getError() == 1) {
                    Toast.makeText(mUpdateTakeView.getMContext(), responseInfo.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    mUpdateTakeView.removeTake(position);
                }
            }

            @Override
            public void onError(Throwable e) {
                Utils.showError(e, mUpdateTakeView.getMContext());
                mUpdateTakeView.showLoading(false);
            }
        }));
    }
}
