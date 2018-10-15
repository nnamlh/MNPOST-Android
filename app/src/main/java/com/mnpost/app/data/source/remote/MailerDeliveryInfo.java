package com.mnpost.app.data.source.remote;

import com.google.gson.annotations.SerializedName;

public class MailerDeliveryInfo {

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
}