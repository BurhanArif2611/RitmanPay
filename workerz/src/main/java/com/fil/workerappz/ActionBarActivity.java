package com.fil.workerappz;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.autofill.AutofillManager;

import com.fil.workerappz.pojo.LabelListJsonPojo;
import com.fil.workerappz.pojo.UserListPojo;
import com.fil.workerappz.utils.SessionManager;

/**
 * Created by HS on 23-Feb-18.
 * FIL AHM
 */

public class ActionBarActivity extends AppCompatActivity {

    private View mDecorView;
    private SessionManager sessionManager;
    private UserListPojo.Data userListPojo = new UserListPojo.Data();


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


        if(Build.VERSION.SDK_INT >= 26) {
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

}