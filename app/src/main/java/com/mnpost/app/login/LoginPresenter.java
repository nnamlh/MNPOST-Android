package com.mnpost.app.login;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mnpost.app.data.source.remote.APIKeyResponse;
import com.mnpost.app.data.source.remote.UserInfoReponse;
import com.mnpost.app.util.ApiClient;
import com.mnpost.app.util.ApiService;
import com.mnpost.app.util.Const;
import com.mnpost.app.util.PrefUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginPresenter implements LoginContract.Presenter {

    private ApiService apiService;

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @NonNull
    private final LoginContract.View mLogiView;

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    public LoginPresenter(@NonNull LoginContract.View loginView) {

        mLogiView = checkNotNull(loginView, "LoginView cannot be null!");

        mLogiView.setPresenter(this);
    }

    @Override
    public void callLogin(String user, String pass) {

        mLogiView.showLoading(true);

        ConnectableObservable<APIKeyResponse> apiKeyObservable = getAPIKey(user, pass).replay();

        mCompositeDisposable.add(apiKeyObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<APIKeyResponse, ObservableSource<APIKeyResponse>>() {
                    @Override
                    public ObservableSource<APIKeyResponse> apply(APIKeyResponse response) throws Exception {
                        return Observable.just(response);
                    }
                })
                .flatMap(new Function<APIKeyResponse, ObservableSource<UserInfoReponse>>() {
                    @Override
                    public ObservableSource<UserInfoReponse> apply(APIKeyResponse apikey) throws Exception {

                        // save APIkey
                        PrefUtils.storeApiKey(mLogiView.getMContext(),apikey.getAccess_token() );
                        PrefUtils.storeUser(mLogiView.getMContext(), apikey.getUserName());

                        return getUserInfo(apikey.getUserName());
                    }
                })
                .subscribeWith(new DisposableObserver<UserInfoReponse>() {

                    @Override
                    public void onNext(UserInfoReponse response) {

                        if(response.getError() == 1) {
                            Toast.makeText(mLogiView.getMContext(), response.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            // save user info
                            String storeInfo = response.getEmployeeCode() + "|" + response.getFullName()+ "|" + response.getDepartment();
                            PrefUtils.storeData(mLogiView.getMContext(), storeInfo, Const.USER_INFO_KEY);
                            mLogiView.nextTask();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLogiView.showLoading(false);
                        if(e.getClass().equals(HttpException.class)) {

                            HttpException msg = (HttpException)e;
                            if(msg.code() == 400) {
                                Toast.makeText(mLogiView.getMContext(), "Sai tài khoản", Toast.LENGTH_SHORT).show();
                            } else if(msg.code() == 401) {
                                Toast.makeText(mLogiView.getMContext(), "Bạn không có quyền truy cập", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(mLogiView.getMContext(), "Không thể kết nối", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onComplete() {
                        mLogiView.showLoading(false);
                    }
                }));
        apiKeyObservable.connect();

    }

    @Override
    public Observable<APIKeyResponse> getAPIKey(String user, String pass) {

        ApiClient.resetApiClient();

        apiService = ApiClient.getClient(mLogiView.getMContext(), "application/x-www-form-urlencoded").create(ApiService.class);

        return apiService.getAPIKey("password", user, pass).toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UserInfoReponse> getUserInfo(String user) {
        ApiClient.resetApiClient();

        apiService = ApiClient.getClient(mLogiView.getMContext()).create(ApiService.class);

        return apiService.getUserInfo(user).toObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
