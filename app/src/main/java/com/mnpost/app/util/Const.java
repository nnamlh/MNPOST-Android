package com.mnpost.app.util;

public class Const {
    public static final String BASE_URL = "http://noiboapi.miennampost.vn/";
    public static final String BASE_URL_UPLOAD = "http://media.miennampost.vn/";

    // Pref key
    public static final String API_KEY = "API_KEY";
    public static final String USER_KEY = "USER_KEY";
    public static final String USER_INFO_KEY = "USER_INFO_KEY";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";
    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final String SHARED_PREF = "ah_firebase";
}
