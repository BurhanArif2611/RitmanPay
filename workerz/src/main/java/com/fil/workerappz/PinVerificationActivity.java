package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.pojo.BeneficiaryInfoListPojo;
import com.fil.workerappz.pojo.JsonListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.fil.workerappz.utils.SlideHolder;
import com.rengwuxian.materialedittext.MaterialEditText;

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
 * Created by HS on 14-Mar-18.
 * FIL AHM
 */

public class PinVerificationActivity extends ActionBarActivity {

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
    @BindView(R.id.slideHolderPinVerification)
    SlideHolder slideHolderPinVerification;
    @BindView(R.id.mainPinVerificationLinearLayout)
    LinearLayout mainPinVerificationLinearLayout;
    @BindView(R.id.pinEditTextVerifiedPinActivity)
    MaterialEditText pinEditTextVerifiedPinActivity;
    @BindView(R.id.submitTextViewVerifiedPin)
    TextView submitTextViewVerifiedPin;

    private String comeFrom = "";
    private Intent mIntent;
    private BeneficiaryInfoListPojo beneficiaryInfoListPojo;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg=new MessagelistData();
    private String verifiedpinmsg;
    private String nointernetmsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pin_verification);
        ButterKnife.bind(this);


        filterImageViewHeader2.setVisibility(View.INVISIBLE);

        comeFrom = getIntent().getStringExtra("come_from");

        try {
            sessionManager = new SessionManager(PinVerificationActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {

                titleTextViewViewHeader2.setText(datumLable_languages.getVerifiedPIN());
                pinEditTextVerifiedPinActivity.setHint(datumLable_languages.getEnterPin());
                pinEditTextVerifiedPinActivity.setFloatingLabelText(datumLable_languages.getEnterPin());
                submitTextViewVerifiedPin.setText(datumLable_languages.getSubmit());
                nointernetmsg=datumLable_languages.getNoInternetConnectionAvailable();

            } else {

                titleTextViewViewHeader2.setText(getResources().getString(R.string.verified_pin));
                pinEditTextVerifiedPinActivity.setHint(getResources().getString(R.string.enter_pin));
                submitTextViewVerifiedPin.setText(getResources().getString(R.string.submit));
                nointernetmsg=datumLable_languages.getNoInternetConnectionAvailable();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (datumLable_languages_msg != null) {
            verifiedpinmsg=datumLable_languages_msg.getEnter6DigitPIN();

        } else {

            verifiedpinmsg=getResources().getString(R.string.Please_Enter_pin);

        }

        if (comeFrom.equalsIgnoreCase("quick_pay")) {
            titleTextViewViewHeader2.setText(datumLable_languages.getQuickPay());

        } else if (comeFrom.equalsIgnoreCase("wallet_summary")) {
            titleTextViewViewHeader2.setText(datumLable_languages.getWalletSummary());

        } else if (comeFrom.equalsIgnoreCase("history")) {
            titleTextViewViewHeader2.setText(datumLable_languages.getTransactionHistory());

        } else if (comeFrom.equalsIgnoreCase("selectcashnext")) {
            titleTextViewViewHeader2.setText(datumLable_languages.getBeneficiaryInfo());
        }
        else
        {
            titleTextViewViewHeader2.setText(datumLable_languages.getVerifiedPIN());
        }

    }

    private boolean checkValidation() {
        boolean checkFlag = true;
        if (pinEditTextVerifiedPinActivity.getText().toString().trim().length() == 0) {
            Constants.showMessage(mainPinVerificationLinearLayout, PinVerificationActivity.this, verifiedpinmsg);
            checkFlag = false;
        }
        return checkFlag;
    }

    private void verifiedPinJsonCall() {
        Constants.showProgress(PinVerificationActivity.this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userPin", pinEditTextVerifiedPinActivity.getText().toString().trim());
            jsonObject.put("userMobile", getUserData().getUserMobile());
            jsonObject.put("userID", getMyUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "verified json " + json);

        Call<List<JsonListPojo>> call = RestClient.get().verifyPinJsonCall(json);

        call.enqueue(new Callback<List<JsonListPojo>>() {
            @Override
            public void onResponse(Call<List<JsonListPojo>> call, Response<List<JsonListPojo>> response) {
                if (response.body() != null && response.body() instanceof ArrayList) {
                    if (response.body().get(0).getStatus() == true) {
//                        Constants.showMessage(mainPinVerificationLinearLayout, PinVerificationActivity.this, response.body().get(0).getInfo());
                        if (comeFrom.equalsIgnoreCase("quick_pay")) {
                            mIntent = new Intent(PinVerificationActivity.this, QuickPayActivity.class);
                            startActivity(mIntent);
                        } else if (comeFrom.equalsIgnoreCase("wallet_summary")) {
                            mIntent = new Intent(PinVerificationActivity.this, WalletSummaryActivity.class);
                            startActivity(mIntent);
                        } else if (comeFrom.equalsIgnoreCase("history")) {
                            mIntent = new Intent(PinVerificationActivity.this, TransactionHistoryActivity.class);
                            startActivity(mIntent);
                        } else if (comeFrom.equalsIgnoreCase("selectcashnext")) {
                            mIntent = new Intent(PinVerificationActivity.this, BeneficiaryInfoSendActivity.class);
                            mIntent.putExtra("flagimage", getIntent().getExtras().getString("flagimage"));
                            mIntent.putExtra("countryshortcode", getIntent().getExtras().getString("countryshortcode"));
                            beneficiaryInfoListPojo = (BeneficiaryInfoListPojo) getIntent().getSerializableExtra("beneficiary_data");
                            mIntent.putExtra("beneficiary_data", beneficiaryInfoListPojo);
                            startActivity(mIntent);
                        }
                        finish();
                    } else {
                        Constants.showMessage(mainPinVerificationLinearLayout, PinVerificationActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                    }
                    Constants.closeProgress();
                }
            }

            @Override
            public void onFailure(Call<List<JsonListPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    @OnClick({R.id.menuImageViewHeader2, R.id.submitTextViewVerifiedPin, R.id.appImageViewHeader2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
                slideHolderPinVerification.toggle();
                break;
            case R.id.appImageViewHeader2:
                mIntent = new Intent(PinVerificationActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);

                break;
            case R.id.submitTextViewVerifiedPin:
                Constants.hideKeyboard(PinVerificationActivity.this);
                if (checkValidation() == true) {
                    if (IsNetworkConnection.checkNetworkConnection(PinVerificationActivity.this)) {
                        verifiedPinJsonCall();
                    } else {
                        Constants.showMessage(mainPinVerificationLinearLayout, PinVerificationActivity.this, nointernetmsg);
                    }
                }
                break;
        }
    }
}
