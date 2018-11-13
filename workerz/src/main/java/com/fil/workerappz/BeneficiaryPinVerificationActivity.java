package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.pojo.BeneficiaryInfoListPojo;
import com.fil.workerappz.pojo.BeneficiaryListPojo;
import com.fil.workerappz.pojo.CountryData;
import com.fil.workerappz.pojo.JsonListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.QuickPayDataPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.fil.workerappz.utils.SlideHolder;
import com.orm.SugarRecord;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

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

public class BeneficiaryPinVerificationActivity extends ActionBarActivity {

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


    private Intent mIntent;
    private BeneficiaryInfoListPojo beneficiaryInfoListPojo;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String verifiedpinmsg;
    private String nointernetmsg;
    private String activitytype;
    private String searchType;
    private BeneficiaryListPojo.Data bankbenefiardata;
    private QuickPayDataPojo quickPayData = new QuickPayDataPojo();
    private BeneficiaryInfoListPojo beneficiaryinfoPojo;
    private final ArrayList<CountryData> countryListPojos = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pin_verification);
        ButterKnife.bind(this);


        filterImageViewHeader2.setVisibility(View.INVISIBLE);

        activitytype = getIntent().getExtras().getString("come_from");
        searchType = getIntent().getExtras().getString("search");

        if (activitytype.equalsIgnoreCase("bank")) {
            bankbenefiardata = (BeneficiaryListPojo.Data) getIntent().getSerializableExtra("beneficiary_object");
        } else if (activitytype.equalsIgnoreCase("bankquickpay")) {
            quickPayData = (QuickPayDataPojo) getIntent().getSerializableExtra("beneficiary_object");
        } else if (activitytype.equalsIgnoreCase("cash")) {
            bankbenefiardata = (BeneficiaryListPojo.Data) getIntent().getSerializableExtra("beneficiary_object");
        } else if (activitytype.equalsIgnoreCase("cashquickpay")) {
            quickPayData = (QuickPayDataPojo) getIntent().getSerializableExtra("beneficiary_object");
        }

        try {
            sessionManager = new SessionManager(BeneficiaryPinVerificationActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {

//                titleTextViewViewHeader2.setText(datumLable_languages.getVerifiedPIN());
                titleTextViewViewHeader2.setText("Verify Pin");
                pinEditTextVerifiedPinActivity.setHint(datumLable_languages.getEnterPin());
                pinEditTextVerifiedPinActivity.setFloatingLabelText(datumLable_languages.getEnterPin());
                submitTextViewVerifiedPin.setText(datumLable_languages.getSubmit());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();

            } else {

//                titleTextViewViewHeader2.setText(getResources().getString(R.string.verified_pin));
                titleTextViewViewHeader2.setText("Verify Pin");
                pinEditTextVerifiedPinActivity.setHint(getResources().getString(R.string.enter_pin));
                submitTextViewVerifiedPin.setText(getResources().getString(R.string.submit));
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (datumLable_languages_msg != null) {
            verifiedpinmsg = datumLable_languages_msg.getEnter6DigitPIN();

        } else {

            verifiedpinmsg = getResources().getString(R.string.Please_Enter_pin);

        }


//        titleTextViewViewHeader2.setText(datumLable_languages.getVerifiedPIN());

    }


    private boolean checkValidation() {
        boolean checkFlag = true;
        if (pinEditTextVerifiedPinActivity.getText().toString().trim().length() == 0) {
            Constants.showMessage(mainPinVerificationLinearLayout, BeneficiaryPinVerificationActivity.this, verifiedpinmsg);
            checkFlag = false;
        }
        return checkFlag;
    }

    private void verifiedPinJsonCall() {
        Constants.showProgress(BeneficiaryPinVerificationActivity.this);

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
                    Constants.closeProgress();
                    if (response.body().get(0).getStatus() == true) {
//                        Constants.showMessage(mainPinVerificationLinearLayout, BeneficiaryPinVerificationActivity.this, response.body().get(0).getInfo());
                        if (searchType.equalsIgnoreCase("")) {
                            if (activitytype.equalsIgnoreCase("cash")) {
                                mIntent = new Intent(BeneficiaryPinVerificationActivity.this, SelectBeneficiaryViewActivity.class);
                                mIntent.putExtra("come_from", activitytype);
                                mIntent.putExtra("beneficiary_object", bankbenefiardata);
                                startActivity(mIntent);
                                finish();
                            } else if (activitytype.equalsIgnoreCase("bank")) {
                                mIntent = new Intent(BeneficiaryPinVerificationActivity.this, SelectBeneficiaryViewActivity.class);
                                mIntent.putExtra("come_from", activitytype);
                                mIntent.putExtra("beneficiary_object", bankbenefiardata);
                                startActivity(mIntent);
                                finish();
                            }
                        } else {

                            String flagimage = "";
                            if (SugarRecord.count(CountryData.class) > 0) {
                                countryListPojos.addAll(SugarRecord.listAll(CountryData.class));
                                for (int i = 0; i < countryListPojos.size(); i++) {
                                    if (bankbenefiardata.getBenificaryNationality().equalsIgnoreCase(countryListPojos.get(i).getCountryShortCode())) {
                                        flagimage=countryListPojos.get(i).getCountryFlagImage();
                                        break;
                                    }

                                }
                            }
                            BeneficiaryInfoListPojo beneficiaryInfoListPojo = new BeneficiaryInfoListPojo();
                            beneficiaryInfoListPojo.setFirstName(bankbenefiardata.getBenificaryFirstName());
                            beneficiaryInfoListPojo.setMiddleName(bankbenefiardata.getBenificaryMiddleName());
                            beneficiaryInfoListPojo.setLastName(bankbenefiardata.getBenificaryLastName());
                            beneficiaryInfoListPojo.setNickName(bankbenefiardata.getBenificaryNickName());
                            beneficiaryInfoListPojo.setAddress(bankbenefiardata.getBenificaryAddress());
                            beneficiaryInfoListPojo.setLandMark(bankbenefiardata.getBenificaryLandMark());
                            beneficiaryInfoListPojo.setZipCode(bankbenefiardata.getBenificaryZipCode());
                            beneficiaryInfoListPojo.setEmailID(bankbenefiardata.getBenificaryEmailID());
                            beneficiaryInfoListPojo.setDateOfBirth(bankbenefiardata.getBenificaryDateOfBirth());
                            beneficiaryInfoListPojo.setCountryFlagImage(flagimage);
                            beneficiaryInfoListPojo.setTelephone(bankbenefiardata.getBenificaryTelephone());
                            beneficiaryInfoListPojo.setIDType(bankbenefiardata.getBenificaryIDType());
                            beneficiaryInfoListPojo.setIDNumber(bankbenefiardata.getBenificaryIDNumber());
                            beneficiaryInfoListPojo.setIDtype_Description(bankbenefiardata.getBenificaryIDtype_Description());
//                    beneficiaryInfoListPojo.setNationality(nationalitySpinnerAddBeneficiary.getSelectedItem().toString());
                            beneficiaryInfoListPojo.setNationality(bankbenefiardata.getBenificaryNationality());
                            beneficiaryInfoListPojo.setPayoutcurrency(bankbenefiardata.getBenificaryPayOutCurrency());
                            beneficiaryInfoListPojo.setPayoutcountry(bankbenefiardata.getBenificaryPayoutCountryCode());
                            beneficiaryInfoListPojo.setBeneficiarynumber(bankbenefiardata.getBenificaryBeneficiaryNo());
                            beneficiaryInfoListPojo.setPayoutbranchcode(bankbenefiardata.getBenificaryPayOutBranchCode());

                            mIntent = new Intent(BeneficiaryPinVerificationActivity.this, BeneficiaryInfoSendActivity.class);
                            mIntent.putExtra("flagimage", flagimage);
                            mIntent.putExtra("countryshortcode", getUserData().getCountryShortCode());
                            mIntent.putExtra("beneficiary_data", beneficiaryInfoListPojo);
                            startActivity(mIntent);
                        }
                    } else {
                        Constants.showMessage(mainPinVerificationLinearLayout, BeneficiaryPinVerificationActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                    }
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
                mIntent = new Intent(BeneficiaryPinVerificationActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);

                break;
            case R.id.submitTextViewVerifiedPin:
                Constants.hideKeyboard(BeneficiaryPinVerificationActivity.this);
                if (checkValidation() == true) {
                    if (IsNetworkConnection.checkNetworkConnection(BeneficiaryPinVerificationActivity.this)) {
                        verifiedPinJsonCall();
                    } else {
                        Constants.showMessage(mainPinVerificationLinearLayout, BeneficiaryPinVerificationActivity.this, nointernetmsg);
                    }
                }
                break;
        }
    }

}


