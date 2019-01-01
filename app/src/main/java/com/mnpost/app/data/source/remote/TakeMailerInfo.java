package com.mnpost.app.data.source.remote;

import com.google.gson.annotations.SerializedName;

public class TakeMailerInfo {

    @SerializedName("DocumentID")
    private String DocumentID;
    @SerializedName("CustomerAddress")
    private String CustomerAddress;
    @SerializedName("CreateTime")
    private String CreateTime;
    @SerializedName("CustomerID")
    private String CustomerID;
    @SerializedName("CustomerPhone")
    private String CustomerPhone;
    @SerializedName("Content")
    private String Content;
    @SerializedName("CustomerName")
    private String CustomerName;

    @SerializedName("AllMailer")
    private String AllMailer;

    public String getDocumentID() {
        return DocumentID;
    }

    public void setDocumentID(String documentID) {
        DocumentID = documentID;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getCustomerPhone() {
        return CustomerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        CustomerPhone = customerPhone;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getAllMailer() {
        return AllMailer;
    }

    public void setAllMailer(String allMailer) {
        AllMailer = allMailer;
    }
}
