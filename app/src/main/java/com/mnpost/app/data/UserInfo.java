package com.mnpost.app.data;

import android.content.Context;
import android.text.TextUtils;

import com.mnpost.app.util.Const;
import com.mnpost.app.util.PrefUtils;

public class UserInfo {

    private String FullName;
    private String PostOfficeID;
    private String EmployeeID;
    private String UserName;

    private static UserInfo instance;

    protected UserInfo() {

    }

    public static UserInfo getInstance(Context context) {
        if (instance == null) {

            instance = new UserInfo();

            String userName = PrefUtils.getUser(context);

            instance.setUserName(userName);

            String info = PrefUtils.getStoreData(context, Const.USER_INFO_KEY);
            if (!TextUtils.isEmpty(info)) {

                String[] strSplit = info.split("\\|");

                if (strSplit.length == 3) {
                    instance.setEmployeeID(strSplit[0]);
                    instance.setFullName(strSplit[1]);
                    instance.setPostOfficeID(strSplit[2]);
                }

            }
        }

        return instance;
    }

    public static void reset() {
        instance = null;
    }


    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPostOfficeID() {
        return PostOfficeID;
    }

    public void setPostOfficeID(String postOfficeID) {
        PostOfficeID = postOfficeID;
    }
}
