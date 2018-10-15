package com.mnpost.app.updatedelivery;

import android.content.Context;
import android.graphics.Bitmap;

import com.mnpost.app.BasePresenter;
import com.mnpost.app.BaseView;
import com.mnpost.app.data.source.CommonData;
import com.mnpost.app.data.source.ResponseInfo;
import com.mnpost.app.data.source.remote.ResponseWithListText;
import com.mnpost.app.data.source.remote.UpdateDeliverySend;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class UpdateDeliveryContract  {
    interface View extends BaseView<UpdateDeliveryContract.Presenter> {

        Context getMContext();

        void takePhoto();

        void onBAck();

        void showImage(Bitmap bitmap);

        void updateActivity(UpdateDeliveryActivity activity);

        void updateView();

        void initSpinnerReason(List<CommonData> datas);

        boolean validField();

        void showLoading(boolean flag);

        void finishUpdate();
    }

    interface Presenter extends BasePresenter {

        Observable<ResponseWithListText> uploadImages (RequestBody body);

        Observable<ResponseInfo> updateDelivery(UpdateDeliverySend info);

        void update(RequestBody body, UpdateDeliverySend info);

    }

}
