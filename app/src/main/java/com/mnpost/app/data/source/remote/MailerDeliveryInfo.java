package com.mnpost.app.data.source.remote;

import com.google.gson.annotations.SerializedName;

public class MailerDeliveryInfo {

    @SerializedName("DetailId")
    private String DetailId;

    @SerializedName("DocumentID")
    private String DocumentID;
    @SerializedName("DocumentDate")
    private String DocumentDate;
    @SerializedName("DocumentTime")
    private String DocumentTime;
    @SerializedName("MailerID")
    private String MailerID;
    @SerializedName("COD")
    private float COD;

    @SerializedName("Amount")
    private float Amount;

    @SerializedName("PaymentMethodID")
    private String PaymentMethodID;

    @SerializedName("RecieverAddress")
    private String RecieverAddress;
    @SerializedName("RecieverProvinceID")
    private String RecieverProvinceID;
    @SerializedName("RecieverPhone")
    private String RecieverPhone;



    @SerializedName("RecieverName")

    private String RecieverName;
    @SerializedName("RecieverDistrictID")
    private String RecieverDistrictID;

    @SerializedName("ReceiDistrictName")
    private String ReceiDistrictName;

    @SerializedName("RecieProvinceName")
    private String RecieProvinceName;
    @SerializedName("ReceiWardName")
    private String ReceiWardName;

    @SerializedName("DeliveryNotes")
    private String DeliveryNotes ;
    @SerializedName("DeliveryTo")
    private String DeliveryTo ;
    @SerializedName("DeliveryDate")
    private String DeliveryDate;
    @SerializedName("DeliveryTime")
    private String DeliveryTime ;

    @SerializedName("CurrentStatusID")
    private int CurrentStatusID;

    @SerializedName("MailerTypeID")
    private String MailerTypeID;

    public String getDeliveryNotes() {
        return DeliveryNotes;
    }

    public void setDeliveryNotes(String deliveryNotes) {
        DeliveryNotes = deliveryNotes;
    }

    public String getDeliveryTo() {
        return DeliveryTo;
    }

    public void setDeliveryTo(String deliveryTo) {
        DeliveryTo = deliveryTo;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public String getDocumentID() {
        return DocumentID;
    }

    public void setDocumentID(String documentID) {
        DocumentID = documentID;
    }

    public String getDocumentDate() {
        return DocumentDate;
    }

    public void setDocumentDate(String documentDate) {
        DocumentDate = documentDate;
    }

    public String getDocumentTime() {
        return DocumentTime;
    }

    public void setDocumentTime(String documentTime) {
        DocumentTime = documentTime;
    }

    public String getMailerID() {
        return MailerID;
    }

    public void setMailerID(String mailerID) {
        MailerID = mailerID;
    }

    public float getCOD() {
        return COD;
    }

    public void setCOD(float COD) {
        this.COD = COD;
    }

    public String getRecieverAddress() {
        return RecieverAddress;
    }

    public void setRecieverAddress(String recieverAddress) {
        RecieverAddress = recieverAddress;
    }

    public String getRecieverProvinceID() {
        return RecieverProvinceID;
    }

    public void setRecieverProvinceID(String recieverProvinceID) {
        RecieverProvinceID = recieverProvinceID;
    }

    public String getRecieverPhone() {
        return RecieverPhone;
    }

    public void setRecieverPhone(String recieverPhone) {
        RecieverPhone = recieverPhone;
    }

    public String getRecieverName() {
        return RecieverName;
    }

    public void setRecieverName(String recieverName) {
        RecieverName = recieverName;
    }

    public String getRecieverDistrictID() {
        return RecieverDistrictID;
    }

    public void setRecieverDistrictID(String recieverDistrictID) {
        RecieverDistrictID = recieverDistrictID;
    }

    public int getCurrentStatusID() {
        return CurrentStatusID;
    }

    public void setCurrentStatusID(int currentStatusID) {
        CurrentStatusID = currentStatusID;
    }

    public String getReceiDistrictName() {
        return ReceiDistrictName;
    }

    public void setReceiDistrictName(String receiDistrictName) {
        ReceiDistrictName = receiDistrictName;
    }

    public String getRecieProvinceName() {
        return RecieProvinceName;
    }

    public void setRecieProvinceName(String recieProvinceName) {
        RecieProvinceName = recieProvinceName;
    }

    public String getReceiWardName() {
        return ReceiWardName;
    }

    public void setReceiWardName(String receiWardName) {
        ReceiWardName = receiWardName;
    }

    public String getMailerTypeID() {
        return MailerTypeID;
    }

    public void setMailerTypeID(String mailerTypeID) {
        MailerTypeID = mailerTypeID;
    }

    public String getDetailId() {
        return DetailId;
    }

    public void setDetailId(String detailId) {
        DetailId = detailId;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public String getPaymentMethodID() {
        return PaymentMethodID;
    }

    public void setPaymentMethodID(String paymentMethodID) {
        PaymentMethodID = paymentMethodID;
    }
}
