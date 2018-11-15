package com.mnpost.app.Splash;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mnpost.app.data.UserInfo;
import com.mnpost.app.data.source.remote.UserInfoReponse;
import com.mnpost.app.util.ApiClient;
import com.mnpost.app.util.ApiService;
import com.mnpost.app.util.Const;
import com.mnpost.app.util.PrefUtils;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

public class SplashPresenter implements SplashContract.Presenter {

    @NonNull
    SplashContract.View mSplashView;

    ApiService apiService;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    public SplashPresenter(@NonNull SplashContract.View splashView) {
        mSplashView = checkNotNull(splashView, "SplashView can be null !!!");

        mSplashView.setPresenter(this);



    }

    @Override
    public boolean checkLogin() {
        String user = PrefUtils.getUser(mSplashView.getMContext());

        if (TextUtils.isEmpty(user)) {
            return false;
        }

        return true;
    }
    protected String getFirebaseReg() {
        SharedPreferences pref = mSplashView.getMContext().getSharedPreferences(Const.SHARED_PREF, 0);
        String token = pref.getString("regId", "");

        return  token;
    }

    @Override
    public void submitCheck() {
        ApiClient.resetApiClient();
        apiService = ApiClient.getClient(mSplashView.getMContext()).create(ApiService.class);

        String user = PrefUtils.getUser(mSplashView.getMContext());
        final String firebaseID = getFirebaseReg();

        apiService.getUserInfo(firebaseID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new SingleObserver<UserInfoReponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(UserInfoReponse response) {
                if(response.getError() == 1) {
                    Toast.makeText(mSplashView.getMContext(), response.getMsg(), Toast.LENGTH_SHORT).show();
                    mSplashView.showLoginForm();
                } else {



                    if (!TextUtils.isEmpty(firebaseID)) {
                        String departmentID = UserInfo.getInstance(mSplashView.getMContext()).getPostOfficeID();
                        if(!TextUtils.isEmpty(departmentID)) {
                            FirebaseMessaging.getInstance().unsubscribeFromTopic(departmentID);
                        }

                        FirebaseMessaging.getInstance().subscribeToTopic(response.getPostOfficeID());
                    }

                    // save user info
                    String storeInfo = response.getEmployeeCode() + "|" + response.getFullName()+ "|" + response.getPostOfficeID();
                    PrefUtils.storeData(mSplashView.getMContext(), storeInfo, Const.USER_INFO_KEY);

                    mSplashView.showNextTask();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("SplashScreen", "onError: " + e.getMessage());
                mSplashView.showLoginForm();
            }
        });

    }

}