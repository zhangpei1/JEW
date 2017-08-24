package com.new_jew.toolkit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by chao on 2015/8/19.
 */
public class UserUtil {
    public static final String STATE_LOGIN = "state_login";
    public static final String USER_TOKEN = "user_token";

    public static boolean getLoginState(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.getBoolean(STATE_LOGIN, false);
    }
    public static void setLoginState(Context context, boolean isLogin) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(STATE_LOGIN, isLogin);
        editor.commit();
    }

    public static String getUserToken(Context context,String key) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.getString(key, "-1");
    }

    public static void setUserToken(Context context, String userToken,String key) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, userToken);
        editor.commit();
    }
}
