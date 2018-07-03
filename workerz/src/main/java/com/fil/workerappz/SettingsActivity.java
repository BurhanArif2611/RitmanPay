package com.fil.workerappz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.pojo.JsonListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.UserListPojo;
import com.fil.workerappz.retrofit.RestApi;
import com.fil.workerappz.utils.ApiClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HS on 09-Mar-18.
 * FIL AHM
 */

public class SettingsActivity extends ActionBarActivity {


    @BindView(R.id.menuImageViewHeader2)
    ImageView menuImageViewHeader2;
    @BindView(R.id.appImageViewHeader2)
    ImageView appImageViewHeader2;
    @BindView(R.id.titleTextViewViewHeader2)
    TextView titleTextViewViewHeader2;
    @BindView(R.id.appLeftImageViewHeader2)
    ImageView appLeftImageViewHeader2;
    @BindView(R.id.skipTextViewViewHeader2)
    TextView skipTextViewViewHeader2;
    @BindView(R.id.filterImageViewHeader2)
    ImageView filterImageViewHeader2;
    @BindView(R.id.pushSwitch)
    SwitchCompat pushSwitch;
    @BindView(R.id.pushLinearLayoutMoreActivity)
    LinearLayout pushLinearLayoutMoreActivity;
    @BindView(R.id.smsSwitch)
    SwitchCompat smsSwitch;
    @BindView(R.id.smsLinearLayoutMoreActivity)
    LinearLayout smsLinearLayoutMoreActivity;
    @BindView(R.id.emailSwitch)
    SwitchCompat emailSwitch;
    @BindView(R.id.emailLinearLayoutMoreActivity)
    LinearLayout emailLinearLayoutMoreActivity;
    @BindView(R.id.mainSettingsLinearLayout)
    LinearLayout mainSettingsLinearLayout;
    @BindView(R.id.textviewpushnotification)
    TextView textviewpushnotification;
    @BindView(R.id.textviewsmsnotification)
    TextView textviewsmsnotification;
    @BindView(R.id.textviewemailnotification)
    TextView textviewemailnotification;
    private UserListPojo.Data userListPojo;
    private String pushNotification = "";
    private String smsNotification = "";
    private String emailNotification = "";
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String nointernetmsg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings);
        ButterKnife.bind(this);


        filterImageViewHeader2.setVisibility(View.INVISIBLE);
        menuImageViewHeader2.setImageResource(R.drawable.back_btn);

        userListPojo = getUserData();
        try {
            sessionManager = new SessionManager(SettingsActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {


                titleTextViewViewHeader2.setText(datumLable_languages.getSettings());
                textviewpushnotification.setText(datumLable_languages.getPushNotifications());
                textviewsmsnotification.setText(datumLable_languages.getSMSNotifications());
                textviewemailnotification.setText(datumLable_languages.getEmailNotifications());
                nointernetmsg=datumLable_languages.getNoInternetConnectionAvailable();

            } else {
                titleTextViewViewHeader2.setText(getResources().getString(R.string.settings));
                textviewpushnotification.setText(getResources().getString(R.string.push_notifications));
                textviewsmsnotification.setText(getResources().getString(R.string.sms_notifications));
                textviewemailnotification.setText(getResources().getString(R.string.email_notifications));
                nointernetmsg=getResources().getString(R.string.no_internet);



            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        smsNotification = userListPojo.getUserSMSNotification();
        pushNotification = userListPojo.getUserPushNotification();
        emailNotification = userListPojo.getUserEmailNotification();

        if (smsNotification.equalsIgnoreCase("Yes")) {
            smsSwitch.setChecked(true);
        } else {
            smsSwitch.setChecked(false);
        }

        if (pushNotification.equalsIgnoreCase("Yes")) {
            pushSwitch.setChecked(true);
        } else {
            pushSwitch.setChecked(false);
        }

        if (emailNotification.equalsIgnoreCase("Yes")) {
            emailSwitch.setChecked(true);
        } else {
            emailSwitch.setChecked(false);
        }

        emailSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    emailNotification = "Yes";
                } else {
                    emailNotification = "No";
                }
                checkInternetAndJsonCall();
            }
        });

        smsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    smsNotification = "Yes";
                } else {
                    smsNotification = "No";
                }
                checkInternetAndJsonCall();
            }
        });

        pushSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    pushNotification = "Yes";
                } else {
                    pushNotification = "No";
                }
                checkInternetAndJsonCall();
            }
        });
    }

    private void checkInternetAndJsonCall() {
        if (IsNetworkConnection.checkNetworkConnection(SettingsActivity.this)) {
            saveSettingsJsonCall();
        } else {
            Constants.showMessage(mainSettingsLinearLayout, this,nointernetmsg);
        }
    }

    @OnClick(R.id.menuImageViewHeader2)
    public void onViewClicked() {
        finish();
    }

    private void saveSettingsJsonCall() {
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", getUserData().getUserID());
            jsonObject.put("userEmailNotification", emailNotification);
            jsonObject.put("userSMSNotification", smsNotification);
            jsonObject.put("userPushNotification", pushNotification);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.i("System out", "json " + json);

        Call<List<JsonListPojo>> call = apiService.updateUserSettingsJsonCall(json);
        Constants.showProgress(SettingsActivity.this);

        call.enqueue(new Callback<List<JsonListPojo>>() {
            @Override
            public void onResponse(Call<List<JsonListPojo>> call, Response<List<JsonListPojo>> response) {
                CustomLog.d("System out", "response code " + response.body());
                if (response.body() != null) {
                    if (response.body().get(0).getStatus() == true) {
                        userListPojo.setUserPushNotification(pushNotification);
                        userListPojo.setUserEmailNotification(emailNotification);
                        userListPojo.setUserSMSNotification(smsNotification);
                        updateUser(userListPojo);
                        Constants.showMessage(mainSettingsLinearLayout, SettingsActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                    } else {
                        CustomLog.d("System out", "response false " + response.body().get(0).getInfo());
                    }
                } else {

                }
                Constants.closeProgress();
            }

            @Override
            public void onFailure(Call<List<JsonListPojo>> call, Throwable t) {
                // Log error here since request failed
                CustomLog.e("System out", t.getMessage());
                Constants.closeProgress();
            }
        });
    }
}