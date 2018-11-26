package com.fil.workerappz;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fil.workerappz.pojo.JsonListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.UserListPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HS on 23-Feb-18.
 * FIL AHM
 */

public class ActionBarActivity extends AppCompatActivity {

    private View mDecorView;
    private SessionManager sessionManager;
    private UserListPojo.Data userListPojo = new UserListPojo.Data();

    public static final long DISCONNECT_TIMEOUT = 300000;

//    public static final long DISCONNECT_TIMEOUT = 60000;


    //    300000
    private Intent mIntent;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDecorView = getWindow().getDecorView();
//        hideSystemUI();

        sessionManager = new SessionManager(this);
//        if (sessionManager.getVerify() == true) {
        if (sessionManager.userProfileData() != null) {
            userListPojo = sessionManager.userProfileData();
        }


        if (Build.VERSION.SDK_INT >= 26) {
            //only api 21 above
            disableAutoFill();
        }
//


//        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void disableAutoFill() {
        getWindow().getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
    }


    public String getUserFirstName() {
        return userListPojo.getUserFirstName();
    }

    public String getUserLastName() {
        return userListPojo.getUserLastName();
    }

    UserListPojo.Data getUserData() {
        return userListPojo;
    }

    float getWalletBalance() {
        return sessionManager.getWalletBalance();
    }

    public void setWalletBalance(float balance) {
        sessionManager.setWalletBalance(balance);
    }

    int getMyUserId() {
        return userListPojo.getUserID();
    }

    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        mDecorView.setSystemUiVisibility(uiOptions);
    }

    boolean updateUser(UserListPojo.Data userListPojo) {
        return sessionManager.updateUserData(userListPojo);
    }

    //code for detact user inactive]
    private static Handler disconnectHandler = new Handler();



    private Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {
            // Perform any required operation on disconnect
            if (sessionManager.getLogin() && sessionManager.getVerify()) {
                Log.d("inactive user log1", "Hi!You were logged out of the App since you were missing in action. You can continue accessing the app by logging in when needed");
                updateDeviceTokenJsonCall();


            }
        }
    };

    public void resetDisconnectTimer() {
        Log.d("inactive user resumed", "Hi!You were logged out of the App since you were missing in action. You can continue accessing the app by logging in when needed");
        disconnectHandler.removeCallbacksAndMessages(null);
        disconnectHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager.getLogin() && sessionManager.getVerify()) {
                    Log.d("inactive user log1", "Hi!You were logged out of the App since you were missing in action. You can continue accessing the app by logging in when needed");
                    updateDeviceTokenJsonCall();
                }
            }
        }, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
    }

    @Override
    public void onUserInteraction() {
//        if (sessionManager.getLogin() && sessionManager.getVerify()) {
//            resetDisconnectTimer();
//        }
    }

    /*@Override
    public void onResume() {
        super.onResume();
        if (sessionManager.getLogin() && sessionManager.getVerify()) {
            resetDisconnectTimer();
        }
    }*/

    /*@Override
    public void onStop() {
        super.onStop();
        if (sessionManager.getLogin() && sessionManager.getVerify()) {
            stopDisconnectTimer();
            Log.d("inactive user log1 stop", "Hi!You were logged out of the App since you were missing in action. You can continue accessing the app by logging in when needed");
        }
    }*/

    private void updateDeviceTokenJsonCall() {
//        Constants.showProgress((Activity) mContext);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userDeviceType", Constants.device_type);
            jsonObject.put("userDeviceID", "");
            jsonObject.put("userID", "" + userListPojo.getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "device token json " + json);
        Call<List<JsonListPojo>> call = RestClient.get().updateDeviceTokenJsonCall(json);

        call.enqueue(new Callback<List<JsonListPojo>>() {
            @Override
            public void onResponse(Call<List<JsonListPojo>> call, Response<List<JsonListPojo>> response) {
                if (response.body() != null && response.body() instanceof ArrayList) {
                    CustomLog.d("System out", "" + response.body().get(0).getInfo());


                    datumLable_languages_msg = sessionManager.getAppLanguageMessage();
                    datumLable_languages = sessionManager.getAppLanguageLabel();

                    if (response.body().get(0).getStatus() == true) {
                        Constants.closeProgress();
                        if (sessionManager.getRememberMe() == true) {
                            sessionManager.setLogoutVerify(false);
                        } else {
                            sessionManager.clearUser();
                        }

//                        sessionManager.clearUser();
                        sessionManager.setLogin(false);
                        sessionManager.setVerify(false);
                        sessionManager.setAppLanguageLabel(datumLable_languages);
                        sessionManager.setAppLanguageMessage(datumLable_languages_msg);
                        Constants.cashBenificaryCount = 0;
                        Constants.bankNextBenificaryCount = 0;
                        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.cancelAll();
                        mIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                        mIntent.putExtra("auto_logout", "log out");
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mIntent);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JsonListPojo>> call, Throwable t) {

            }
        });
    }

    private void logoutUser() {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(ActionBarActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(ActionBarActivity.this);
        }

        builder.setTitle(datumLable_languages.getWorkerAppz())
                .setMessage("Hi!You were logged out of the App since you were missing in action. You can continue accessing the app by logging in when needed")
                .setCancelable(false)
                .setIcon(R.drawable.app_icon);
//                .show();
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                    updateDeviceTokenJsonCall();
                }
            }
        }, 2500); //change 5000 with a specific time you want
    }
}