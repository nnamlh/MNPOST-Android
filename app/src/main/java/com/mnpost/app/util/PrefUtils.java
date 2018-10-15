package com.mnpost.app.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {
    /**
     * Storing API Key in shared preferences to
     * add it in header part of every retrofit request
     */
    public PrefUtils() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
    }

    public static void storeApiKey(Context context, String apiKey) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(Const.API_KEY, apiKey);
        editor.commit();
    }

    public static String getApiKey(Context context) {
        return getSharedPreferences(context).getString(Const.API_KEY, "");
    }

    //
    public static void storeUser(Context context, String user) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(Const.USER_KEY, user);
        editor.commit();
    }

    public static String getUser(Context context) {
        return getSharedPreferences(context).getString(Const.USER_KEY, "");
    }


    // store any data
    public  static  void storeData(Context context, String data, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, data);
        editor.commit();
    }
    public static String getStoreData(Context context, String key) {
        return getSharedPreferences(context).getString(key, "");
    }

}
