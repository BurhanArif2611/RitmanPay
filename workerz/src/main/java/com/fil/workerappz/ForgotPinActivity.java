package com.fil.workerappz;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.fragments.CountrySelectionBottomSheet;
import com.fil.workerappz.pojo.CountryData;
import com.fil.workerappz.pojo.JsonListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
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

/**
 * Created by HS on 24-Feb-18.
 * FIL AHM
 */

public class ForgotPinActivity extends ActionBarActivity {

    @BindView(R.id.backImageViewHeader)
    ImageView backImageViewHeader;
    @BindView(R.id.titleTextViewViewHeader)
    TextView titleTextViewViewHeader;
    @BindView(R.id.skipTextViewViewHeader)
    TextView skipTextViewViewHeader;
    @BindView(R.id.emailMobileNoEditTextForgotPinActivity)
    MaterialEditText emailMobileNoEditTextForgotPinActivity;
    @BindView(R.id.sendTextViewForgotPinActivity)
    TextView sendTextViewForgotPinActivity;
    @BindView(R.id.mainLinearLayoutForgotPIN)
    LinearLayout mainLinearLayoutForgotPIN;
    @BindView(R.id.countryCodeTextViewForgot)
    TextView countryCodeTextViewForgot;
    @BindView(R.id.countryCodeImageViewForgot)
    ImageView countryCodeImageViewForgot;
    @BindView(R.id.countrySpinnerForgot)
    LinearLayout countrySpinnerForgot;

    private final ArrayList<JsonListPojo> jsonListPojos = new ArrayList<>();
    @BindView(R.id.dontwrrytextview)
    TextView dontwrrytextview;
    private boolean inputType = false;
    private final ArrayList<CountryData> countryListPojos = new ArrayList<>();
    private String locale = "IND";
    private String countryCode = "";
    private String signInWith = "Email";
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg=new MessagelistData();
    private String emailmobilemsg;
    private String validemail;
    private String validmobilenumber;
    private String nointernetmsg;
    private String validemailormobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.forgot_pin);
        ButterKnife.bind(this);

        locale = getResources().getConfiguration().locale.getISO3Country();
        if (SugarRecord.count(CountryData.class) > 0) {
            countryListPojos.addAll(SugarRecord.listAll(CountryData.class));
            for (int i = 0; i < countryListPojos.size(); i++) {
                if (locale.equalsIgnoreCase(countryListPojos.get(i).getCountryShortCode())) {
                    countryCode = countryListPojos.get(i).getCountryDialCode();
                    countryCodeTextViewForgot.setText(countryListPojos.get(i).getCountryDialCode());
                    Picasso.with(ForgotPinActivity.this).load(Constants.FLAG_URL + countryListPojos.get(i).getCountryFlagImage()).into(countryCodeImageViewForgot);
                    break;
                }
            }
        }


        try {
            sessionManager = new SessionManager(ForgotPinActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {

                titleTextViewViewHeader.setText(datumLable_languages.getForgotPIN());
                dontwrrytextview.setText(datumLable_languages.getResetYourPIN());
                emailMobileNoEditTextForgotPinActivity.setHint(datumLable_languages.getEmailMobileNo());
                sendTextViewForgotPinActivity.setText(datumLable_languages.getSend());
                nointernetmsg=datumLable_languages.getNoInternetConnectionAvailable();

            } else {
                titleTextViewViewHeader.setText(getResources().getString(R.string.forgot_pin_));
                dontwrrytextview.setText(getResources().getString(R.string.don_t_worry_just_fill_in_your_email_mobile_no_and_we_ll_help_your_reset_your_pin));
                emailMobileNoEditTextForgotPinActivity.setHint(getResources().getString(R.string.email_mobile_no));
                sendTextViewForgotPinActivity.setText(getResources().getString(R.string.send));
                nointernetmsg=getResources().getString(R.string.no_internet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (datumLable_languages_msg != null) {
            emailmobilemsg=datumLable_languages_msg.getEnterEmailOrMobileNumber();
            validemail=datumLable_languages_msg.getEnterValidEmail();
            validmobilenumber=datumLable_languages_msg.getEnterValidMobileNumber();
            validemailormobile=datumLable_languages_msg.getEnterValidEmailOrMobileNumber();


        } else {
            emailmobilemsg=getResources().getString(R.string.Please_Enter_Email_mobile_number);
            validemail=getResources().getString(R.string.Please_Enter_vaild_email);
            validmobilenumber=getResources().getString(R.string.Please_Enter_valid_Mobile_number);

        }
        emailMobileNoEditTextForgotPinActivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                boolean inputTypevalidation = false;
                inputTypevalidation = emailMobileNoEditTextForgotPinActivity.getText().toString().trim().matches("^[0-9]+$");
                if (inputTypevalidation == true) {
                    emailMobileNoEditTextForgotPinActivity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
                } else {
                    emailMobileNoEditTextForgotPinActivity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });


    }

    @OnClick({R.id.backImageViewHeader, R.id.sendTextViewForgotPinActivity, R.id.countrySpinnerForgot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImageViewHeader:
                finish();
                break;
            case R.id.sendTextViewForgotPinActivity:
                Constants.hideKeyboard(ForgotPinActivity.this);
                if (checkValidation() == true) {
                    if (IsNetworkConnection.checkNetworkConnection(this)) {
                        getForgotPIN();
                    } else {
                        Constants.showMessage(mainLinearLayoutForgotPIN, this, nointernetmsg);
                    }
                }
                break;
            case R.id.countrySpinnerForgot:
                CountrySelectionBottomSheet countrySelectionBottomSheet = new CountrySelectionBottomSheet();
                Bundle bundle = new Bundle();
                bundle.putSerializable("country_list", countryListPojos);
                countrySelectionBottomSheet.setArguments(bundle);
                countrySelectionBottomSheet.show(getSupportFragmentManager(), "BottomSheet Fragment");
                break;
        }
    }

    public void updateCountrySelection(int position) {
        countryCodeTextViewForgot.setText(countryListPojos.get(position).getCountryDialCode());
        countryCode = countryListPojos.get(position).getCountryDialCode();
        Picasso.with(ForgotPinActivity.this).load(Constants.FLAG_URL + countryListPojos.get(position).getCountryFlagImage()).into(countryCodeImageViewForgot);
    }

    private void getForgotPIN() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("loginID", emailMobileNoEditTextForgotPinActivity.getText().toString().trim());
            jsonObject.put("userCountryCode", countryCode);
            jsonObject.put("loginMode", signInWith);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";

        CustomLog.d("System out", "forgot json " + json);

        Constants.showProgress(ForgotPinActivity.this);
        Call<List<JsonListPojo>> call = null;
        if (inputType == false) {
            call = RestClient.get().forgotPasswordJsonCall(json);
        } else {
            call = RestClient.get().forgotPinJsonCall(json);
        }

        call.enqueue(new Callback<List<JsonListPojo>>() {
            @Override
            public void onResponse(Call<List<JsonListPojo>> call, Response<List<JsonListPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    jsonListPojos.clear();
                    jsonListPojos.addAll(response.body());
                    if (jsonListPojos != null) {
                        if (jsonListPojos.get(0).getStatus() == true) {
                            Constants.showMessage(mainLinearLayoutForgotPIN, ForgotPinActivity.this, datumLable_languages_msg.getMessage(jsonListPojos.get(0).getInfo()));
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    handler.removeCallbacks(this);
                                    finish();
                                }
                            }, 2000);
                        } else {
                            Constants.showMessage(mainLinearLayoutForgotPIN, ForgotPinActivity.this, datumLable_languages_msg.getMessage(jsonListPojos.get(0).getInfo()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JsonListPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    private boolean checkValidation() {
        boolean checkFlag = true;
        inputType = emailMobileNoEditTextForgotPinActivity.getText().toString().trim().matches("^[0-9]+$");

        if (emailMobileNoEditTextForgotPinActivity.getText().toString().trim().length() == 0) {
            Constants.showMessage(mainLinearLayoutForgotPIN, ForgotPinActivity.this, emailmobilemsg);
            checkFlag = false;
        } else if (inputType == true && emailMobileNoEditTextForgotPinActivity.getText().toString().trim().length() < 10) {
            Constants.showMessage(mainLinearLayoutForgotPIN, ForgotPinActivity.this, validemailormobile);
            checkFlag = false;
        } else if (inputType == false && Constants.validateEmail(emailMobileNoEditTextForgotPinActivity.getText().toString().trim()) == false) {
            Constants.showMessage(mainLinearLayoutForgotPIN, ForgotPinActivity.this, validemailormobile);
            checkFlag = false;
        }

        if (inputType == true) {
            signInWith = "Mobile";
        } else {
            signInWith = "Email";
        }

        return checkFlag;
    }
}