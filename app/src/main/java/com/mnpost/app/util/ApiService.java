package com.mnpost.app.util;

import com.mnpost.app.data.source.CommonData;
import com.mnpost.app.data.source.ResponseInfo;
import com.mnpost.app.data.source.remote.APIKeyResponse;
import com.mnpost.app.data.source.remote.GetMailerDeliveryResponse;
import com.mnpost.app.data.source.remote.ReponseListCommonInfo;
import com.mnpost.app.data.source.remote.ResponseWithListText;
import com.mnpost.app.data.source.remote.TakeMailerDetailResponse;
import com.mnpost.app.data.source.remote.TakeMailerResponse;
import com.mnpost.app.data.source.remote.UpdateDeliverySend;
import com.mnpost.app.data.source.remote.UpdateTakeMailerSend;
import com.mnpost.app.data.source.remote.UserInfoReponse;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // get API Key
    @FormUrlEncoded
    @POST("mntoken")
    Single<APIKeyResponse> getAPIKey(@Field("grant_type") String grant_type, @Field("username") String username, @Field("password") String password);


    // user info
    @GET("api/userapi/getinfo")
    Single<UserInfoReponse> getUserInfo(@Query("firebaseId") String firebaseId);

    // mailer delivery
    @GET("api/mailerapi/GetDeliveryByEmployee")
    Single<GetMailerDeliveryResponse> getMailerDelivery(@Query("employeeId") String employeeId);

    @GET("api/mailerapi/GetReturnReasons")
    Single<ReponseListCommonInfo> getReturnReasons();

    @GET("api/mailerapi/GetDeliveryEmployeeHistory")
    Single<GetMailerDeliveryResponse> getMailerDeliveryHistory(@Query("employeeId") String employeeId, @Query("date") String date);


    // update delivery mailer
    // --- update image
    // upload file
    @POST("upload/MailerImages")
    Single<ResponseWithListText> uploadDeliveryImages(@Body RequestBody info);

    //-- update mailer
    @POST("api/mailerapi/UpdateDelivery")
    Single<ResponseInfo> updateDeliveryInfo(@Body UpdateDeliverySend info);


    // ;ay hang
    @GET("api/MailerAPI/GetTakeMailerInDay")
    Single<TakeMailerResponse> getTakeMailers();

    @GET("api/MailerAPI/GetDetails")
    Single<TakeMailerDetailResponse> getTakeMailerDetails(@Query("documentID") String documentID);


    @POST("api/MailerAPI/UpdateTakeMailer")
    Single<ResponseInfo> updateTakeMailer(@Body UpdateTakeMailerSend info);



}
