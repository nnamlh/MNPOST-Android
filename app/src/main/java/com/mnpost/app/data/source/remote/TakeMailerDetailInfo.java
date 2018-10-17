package com.mnpost.app.data.source.remote;

import com.google.gson.annotations.SerializedName;

public class TakeMailerDetailInfo {

    @SerializedName("RecieverAddress")
    private String RecieverAddress;
    @SerializedName("RecieverWardID")
    private String RecieverWardID;
    @SerializedName("MailerDescription")
    private String MailerDescription;
    @SerializedName("PaymentMethodID")
    private String PaymentMethodID;
    @SerializedName("RecieverDistrictID")
    private String RecieverDistrictID;
    @SerializedName("Weight")
    private String Weight;
    @SerializedName("COD")
    private String COD;
    @SerializedName("Amount")
    private String Amount;
    @SerializedName("RecieverProvinceID")
    private String RecieverProvinceID;
    @SerializedName("Quantity")
    private String Quantity;

    @SerializedName("RecieverPhone")
    private String RecieverPhone;

    @SerializedName("Notes")
    private String Notes;

    @SerializedName("TimeTake")
    private String TimeTake;
    @SerializedName("MailerID")
    private String MailerID;
    @SerializedName("MailerTypeID")
    private String MailerTypeID;
    @SerializedName("RecieverName")
    private String RecieverName;

    @SerializedName("CurrentStatusID")
    private int CurrentStatusID;
    public String getRecieverAddress() {
        return RecieverAddress;
    }

    public void setRecieverAddress(String recieverAddress) {
        RecieverAddress = recieverAddress;
    }

    public String getRecieverWardID() {
        return RecieverWardID;
    }

    public void setRecieverWardID(String recieverWardID) {
        RecieverWardID = recieverWardID;
    }

    public String getMailerDescription() {
        return MailerDescription;
    }

    public void setMailerDescription(String mailerDescription) {
        MailerDescription = mailerDescription;
    }

    public String getPaymentMethodID() {
        return PaymentMethodID;
    }

    public void setPaymentMethodID(String paymentMethodID) {
        PaymentMethodID = paymentMethodID;
    }

    public String getRecieverDistrictID() {
        return RecieverDistrictID;
    }

    public void setRecieverDistrictID(String recieverDistrictID) {
        RecieverDistrictID = recieverDistrictID;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getCOD() {
        return COD;
    }

    public void setCOD(String COD) {
        this.COD = COD;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getRecieverProvinceID() {
        return RecieverProvinceID;
    }

    public void setRecieverProvinceID(String recieverProvinceID) {
        RecieverProvinceID = recieverProvinceID;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getRecieverPhone() {
        return RecieverPhone;
    }

    public void setRecieverPhone(String recieverPhone) {
        RecieverPhone = recieverPhone;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getTimeTake() {
        return TimeTake;
    }

    public void setTimeTake(String timeTake) {
        TimeTake = timeTake;
    }

    public String getMailerID() {
        return MailerID;
    }

    public void setMailerID(String mailerID) {
        MailerID = mailerID;
    }

    public String getMailerTypeID() {
        return MailerTypeID;
    }

    public void setMailerTypeID(String mailerTypeID) {
        MailerTypeID = mailerTypeID;
    }

    public String getRecieverName() {
        return RecieverName;
    }

    public void setRecieverName(String recieverName) {
        RecieverName = recieverName;
    }

    public int getCurrentStatusID() {
        return CurrentStatusID;
    }

    public void setCurrentStatusID(int currentStatusID) {
        CurrentStatusID = currentStatusID;
    }
}
