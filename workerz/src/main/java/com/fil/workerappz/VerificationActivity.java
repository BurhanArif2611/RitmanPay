package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.pinedittext.VerificationCodeEditText;
import com.fil.workerappz.pojo.JsonListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.UserListPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HS on 24-Feb-18.
 * FIL AHM
 */

public class VerificationActivity extends ActionBarActivity {

    @BindView(R.id.backImageViewHeader)
    ImageView backImageViewHeader;
    @BindView(R.id.titleTextViewViewHeader)
    TextView titleTextViewViewHeader;
    @BindView(R.id.skipTextViewViewHeader)
    TextView skipTextViewViewHeader;
    @BindView(R.id.exitTextVerification)
    VerificationCodeEditText exitTextVerification;
    @BindView(R.id.submitTextViewVerification)
    TextView submitTextViewVerification;
    @BindView(R.id.verificationCodeTextView)
    TextView verificationCodeTextView;
    @BindView(R.id.mainLinearLayoutVerification)
    LinearLayout mainLinearLayoutVerification;
    @BindView(R.id.textviewVerificationCode)
    TextView textviewVerificationCode;

    private Intent mIntent;
    private SessionManager sessionManager;
    private final ArrayList<JsonListPojo> jsonListPojos = new ArrayList<>();
    private UserListPojo.Data userListPojo;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String verificayionmsg;
    private String nointernetmsg, comeFrom, mobilenumber;
    private final ArrayList<UserListPojo> userListPojos = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.verification);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(VerificationActivity.this);
        userListPojo = sessionManager.userProfileData();


        backImageViewHeader.setVisibility(View.INVISIBLE);
        mIntent = getIntent();
        if (mIntent != null) {
            comeFrom = mIntent.getStringExtra("come_from");
        }
        try {
            SessionManager sessionManager = new SessionManager(VerificationActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {
                verificationCodeTextView.setText(datumLable_languages.getPleaseTypeOTP());
                textviewVerificationCode.setText(datumLable_languages.getVerificationCode());
                submitTextViewVerification.setText(datumLable_languages.getSubmit());
                titleTextViewViewHeader.setText(datumLable_languages.getVerification());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();
            } else {
                titleTextViewViewHeader.setText(getResources().getString(R.string.verification));
                verificationCodeTextView.setText(getResources().getString(R.string.please_type_the_verification_code_sent_to_your_mobile_number));
                textviewVerificationCode.setText(getResources().getString(R.string.verification_code));
                submitTextViewVerification.setText(getResources().getString(R.string.submit));
                nointernetmsg = getResources().getString(R.string.no_internet);
            }
        } catch (
                Exception e)

        {
            e.printStackTrace();
        }
        if (datumLable_languages_msg != null) {
            verificayionmsg = datumLable_languages_msg.getEnterVerificationCode();
        } else {
            verificayionmsg = getResources().getString(R.string.Please_Enter_Verification_Code);
        }
        if (comeFrom.equalsIgnoreCase("")) {
             mobilenumber=userListPojo.getUserMobile();
        } else {
            mobilenumber=comeFrom;
        }
        customTextView(verificationCodeTextView);
    }

    @OnClick({R.id.backImageViewHeader, R.id.submitTextViewVerification, R.id.resendOtp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImageViewHeader:
                finish();
                break;
            case R.id.resendOtp:
                resendOTPJsonCall();
                break;
            case R.id.submitTextViewVerification:
                Constants.hideKeyboard(VerificationActivity.this);
                if (exitTextVerification.getText().toString().length() != 4) {
                    Constants.showMessage(mainLinearLayoutVerification, VerificationActivity.this, verificayionmsg);
                } else {
                    if (IsNetworkConnection.checkNetworkConnection(this)) {
                        verifyVerificationCode();
                    } else {
                        Constants.showMessage(mainLinearLayoutVerification, this, nointernetmsg);
                    }
                }
                break;
        }
    }

//    private void autoCompleteJsonCall() {
//        JSONObject jsonObject = new JSONObject();
//        String userPin = exitTextVerification.getText().toString().trim();
//        try {
//            jsonObject.put("userPin", userPin);
//            jsonObject.put("userMobile", mobilenumber);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String json = "[" + jsonObject + "]";
//        CustomLog.d("System out", "json " + json);
//
//        Constants.showProgress(VerificationActivity.this);
//        Call<List<UserListPojo>> call = RestClient.get().verifyOTPJsonCall(json);
//
//        call.enqueue(new Callback<List<JsonListPojo>>() {
//            @Override
//            public void onResponse(Call<List<JsonListPojo>> call, Response<List<JsonListPojo>> response) {
//                Constants.closeProgress();
//                if (response.body() != null && response.body() instanceof ArrayList) {
//                    jsonListPojos.clear();
//                    jsonListPojos.addAll(response.body());
//                    if (jsonListPojos.get(0).getStatus() == true) {
//                        sessionManager.setVerify(true);
//                        sessionManager.setLogoutVerify(true);
//                        mIntent = new Intent(VerificationActivity.this, UploadYourDocumentActivity.class);
//                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        mIntent.putExtra("come_from", "Registration");
//                        startActivity(mIntent);
//                        finish();
//                    } else {
//                        Constants.showMessage(mainLinearLayoutVerification, VerificationActivity.this, datumLable_languages_msg.getMessage(jsonListPojos.get(0).getInfo().toString()));
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<JsonListPojo>> call, Throwable t) {
//                Constants.closeProgress();
//            }
//        });
//    }


    private void verifyVerificationCode() {
        JSONObject jsonObject = new JSONObject();
        String userPin = exitTextVerification.getText().toString().trim();
        try {
            jsonObject.put("userOTP", userPin);
            jsonObject.put("userMobile", mobilenumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "json " + json);

        Constants.showProgress(VerificationActivity.this);
        Call<List<UserListPojo>> call = RestClient.get().verifyOTPJsonCall(json);

        call.enqueue(new Callback<List<UserListPojo>>() {
            @Override
            public void onResponse(Call<List<UserListPojo>> call, Response<List<UserListPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
//                    jsonListPojos.clear();
//                    jsonListPojos.addAll(response.body());
//                    if (jsonListPojos.get(0).getStatus() == true) {
//                        sessionManager.setVerify(true);
//                        sessionManager.setLogoutVerify(true);
//                        sessionManager.setWalletBalance((float) 0.0);
//                        Constants.showMessage(mainLinearLayoutVerification, VerificationActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                handler.removeCallbacks(this);
//                                finish();
//                                mIntent = new Intent(VerificationActivity.this, HomeActivity.class);
//                                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                mIntent.putExtra("come_from", "Registration");
//                                startActivity(mIntent);
//
//
//                            }
//                        }, 2000);

                    userListPojos.clear();
                    userListPojos.addAll(response.body());
                    if (userListPojos.get(0).getStatus() == true) {
                        SessionManager sessionManager = new SessionManager(VerificationActivity.this);

//                        sessionManager.setuserflagimage(countryFlagImage);

                        sessionManager.updateUserProfile(new Gson().toJson(userListPojos.get(0).getData().get(0)));
                        sessionManager.setLogin(true);
                        sessionManager.setVerify(true);
                        sessionManager.setWalletBalance((float) 0.0);
                        sessionManager.setLogoutVerify(true);
                        Constants.showMessage(mainLinearLayoutVerification, VerificationActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.removeCallbacks(this);
                                finish();

                                mIntent = new Intent(VerificationActivity.this, HomeActivity.class);
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mIntent.putExtra("come_from", "Registration");
                                startActivity(mIntent);


                            }
                        }, 2000);

                    } else {
                        Constants.showMessage(mainLinearLayoutVerification, VerificationActivity.this, datumLable_languages_msg.getMessage(userListPojos.get(0).getInfo().toString()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserListPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

//    private void verifyVerificationPIN() {
//        JSONObject jsonObject = new JSONObject();
//        String userPin = exitTextVerification.getText().toString().trim();
//        try {
//            jsonObject.put("userPin", userPin);
//            jsonObject.put("userMobile", mobilenumber);
//            jsonObject.put("userID", userListPojo.getUserID());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String json = "[" + jsonObject + "]";
//        CustomLog.d("System out", "json " + json);
//
//        Constants.showProgress(VerificationActivity.this);
//        Call<List<JsonListPojo>> call = RestClient.get().verifyOTPJsonCall(json);
//
//        call.enqueue(new Callback<List<JsonListPojo>>() {
//            @Override
//            public void onResponse(Call<List<JsonListPojo>> call, Response<List<JsonListPojo>> response) {
//                Constants.closeProgress();
//                if (response.body() != null && response.body() instanceof ArrayList) {
//                    jsonListPojos.clear();
//                    jsonListPojos.addAll(response.body());
//                    if (jsonListPojos.get(0).getStatus() == true) {
//                        sessionManager.setVerify(true);
//                        sessionManager.setLogoutVerify(true);
//                        mIntent = new Intent(VerificationActivity.this, UploadYourDocumentActivity.class);
//                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        mIntent.putExtra("come_from", "Registration");
//                        startActivity(mIntent);
//                        finish();
//                    } else {
//                        Constants.showMessage(mainLinearLayoutVerification, VerificationActivity.this, datumLable_languages_msg.getMessage(jsonListPojos.get(0).getInfo().toString()));
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<JsonListPojo>> call, Throwable t) {
//                Constants.closeProgress();
//            }
//        });
//    }


    private void customTextView(TextView view) {
        SpannableStringBuilder spanTxt;
        if (datumLable_languages.getPleaseTypeOTP() != null) {
            spanTxt = new SpannableStringBuilder(
                    datumLable_languages.getPleaseTypeOTP() + " ");
        } else {
            spanTxt = new SpannableStringBuilder(
                    getResources().getString(R.string.please_type_the_verification_code_sent_to_your_mobile_number) + " ");
        }
        spanTxt.append(mobilenumber);
        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), spanTxt.length() - mobilenumber.length(), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    private void resendOTPJsonCall() {
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("userMobile", mobilenumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "json " + json);

        Constants.showProgress(VerificationActivity.this);
        Call<List<JsonListPojo>> call = RestClient.get().resendOTPJsonCall(json);

        call.enqueue(new Callback<List<JsonListPojo>>() {
            @Override
            public void onResponse(Call<List<JsonListPojo>> call, Response<List<JsonListPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    jsonListPojos.clear();
                    jsonListPojos.addAll(response.body());
                    if (jsonListPojos.get(0).getStatus() == true) {
                        Constants.showMessage(mainLinearLayoutVerification, VerificationActivity.this, datumLable_languages_msg.getMessage(jsonListPojos.get(0).getInfo().toString()));
                    } else {
                        Constants.showMessage(mainLinearLayoutVerification, VerificationActivity.this, datumLable_languages_msg.getMessage(jsonListPojos.get(0).getInfo().toString()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JsonListPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }
}
