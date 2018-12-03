package com.fil.workerappz.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.LabelListJsonPojo;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.UserListPojo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by HS on 01-Mar-18.
 * FIL AHM
 */

public class SessionManager {

    private static final String PREF_NAME = "login_session";
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final Context context;
    private final int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();
    }

    public void updateUserProfile(String userDetail) {
        editor.putString("user_detail", userDetail);
        editor.commit();
        CustomLog.d("System out", "userDetail " + userDetail);
        CustomLog.d("System out", "user login session modified!");
    }

    public boolean getRememberMe() {
        return pref.getBoolean("remember_me", false);
    }

    public void setRememberMe(boolean rememberMe) {
        editor.putBoolean("remember_me", rememberMe);
        editor.commit();
    }

    public String getDeviceToken() {
        return pref.getString("device_token", "");
    }

    public String getLoginWith() {
        return pref.getString("login_with", "");
    }

    public String getuserflagimage() {
        return pref.getString("flag_image", "");
    }

    public void setDeviceToken(String deviceToken) {
        editor.putString("device_token", deviceToken);
        editor.commit();
    }
    public String getlanguageselection() {
        return pref.getString("language_selection", "1");
    }

    public void setlanguageselection(String languageId) {
        editor.putString("language_selection", languageId);
        editor.commit();
    }
    public void setLoginWith(String loginwith) {
        editor.putString("login_with", loginwith);
        editor.commit();
    }

    public void setuserflagimage(String flagimage) {
        editor.putString("flag_image", flagimage);
        editor.commit();
    }

    public void setUserCustomerNo(int userCustomerNo) {
        UserListPojo.Data data = userProfileData();
        data.setUserCustomerNo(userCustomerNo);
        updateUserProfile(new Gson().toJson(data));
    }

    public UserListPojo.Data userProfileData() {
        UserListPojo.Data data = new UserListPojo.Data();
        data = new Gson().fromJson(pref.getString("user_detail", ""), new TypeToken<UserListPojo.Data>() {
        }.getType());
        CustomLog.d("System out", "userDetail " + pref.getString("user_detail", ""));
        return data;
    }


    public boolean getLogin() {
        return pref.getBoolean("is_login", false);
    }

    public void setLogin(boolean flag) {
        editor.putBoolean("is_login", flag);
        editor.commit();
    }

    public float getWalletBalance() {
        return pref.getFloat("wallet_balance", 0f);
    }


    public void setWalletBalance(float balance) {
        editor.putFloat("wallet_balance", balance);
        editor.commit();
    }

    public LabelListData getAppLanguageLabel() {
        LabelListData datumLable_languages = new LabelListData();
        Gson gson = new Gson();
        datumLable_languages = (gson.fromJson(pref.getString("app_language", ""), new TypeToken<LabelListData> (){}.getType()));
        return datumLable_languages;
    }
    public MessagelistData getAppLanguageMessage() {
        MessagelistData datumLable_languages_msg = new MessagelistData();
        Gson gson = new Gson();
        datumLable_languages_msg = (gson.fromJson(pref.getString("app_language_msg", ""), new TypeToken<MessagelistData> (){}.getType()));
        return datumLable_languages_msg;
    }

    public void setAppLanguageLabel(LabelListData datumLable_languages) {
        Gson gson = new Gson();
        editor.putString("app_language", gson.toJson(datumLable_languages)).commit();
    }
    public void setAppLanguageMessage(MessagelistData datumLable_languages_msg) {
        Gson gson = new Gson();
        editor.putString("app_language_msg", gson.toJson(datumLable_languages_msg)).commit();
    }
    public boolean getVerify() {
        return pref.getBoolean("is_verify", false);
    }
    public boolean getLogoutVerify() {
        return pref.getBoolean("is_logout_verify", false);
    }

    public void setVerify(boolean flag) {
        editor.putBoolean("is_verify", flag);
        editor.commit();
    }
    public void setLogoutVerify(boolean flag) {
        editor.putBoolean("is_logout_verify", flag);
        editor.commit();
    }


    public void clearUser() {
        editor.clear();
        editor.commit();
    }

    public boolean updateUserData(UserListPojo.Data userData) {
        String data = new Gson().toJson(userData);
        editor.putString("user_detail", data);
        editor.commit();
        return true;
    }


}