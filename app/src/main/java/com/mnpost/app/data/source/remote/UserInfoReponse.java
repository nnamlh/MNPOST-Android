package com.mnpost.app.data.source.remote;

import com.google.gson.annotations.SerializedName;
import com.mnpost.app.data.source.ResponseInfo;

public class UserInfoReponse extends ResponseInfo {

    @SerializedName("FullName")
    private String FullName;
    @SerializedName("UserName")
    private String UserName;
    @SerializedName("Function")
    private String Function;
    @SerializedName("EmployeeCode")
    private String EmployeeCode;

    @SerializedName("PostOfficeID")
    private String PostOfficeID;
    
    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFunction() {
        return Function;
    }

    public void setFunction(String function) {
        Function = function;
    }

    public String getEmployeeCode() {
        return EmployeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        EmployeeCode = employeeCode;
    }


    public String getPostOfficeID() {
        return PostOfficeID;
    }

    public void setPostOfficeID(String postOfficeID) {
        PostOfficeID = postOfficeID;
    }
}
