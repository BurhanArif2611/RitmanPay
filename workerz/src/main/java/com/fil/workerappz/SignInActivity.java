package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.fragments.CountrySelectionBottomSheet;
import com.fil.workerappz.pojo.CountryData;
import com.fil.workerappz.pojo.CountryListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.UserListPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.google.gson.Gson;
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

public class SignInActivity extends ActionBarActivity {

    @BindView(R.id.backImageViewHeader)
    ImageView backImageViewHeader;
    @BindView(R.id.titleTextViewViewHeader)
    TextView titleTextViewViewHeader;
    @BindView(R.id.skipTextViewViewHeader)
    TextView skipTextViewViewHeader;
    @BindView(R.id.emailMobileNoEditTextSignInActivity)
    MaterialEditText emailMobileNoEditTextSignInActivity;
    @BindView(R.id.pinEditTextSignInActivity)
    MaterialEditText pinEditTextSignInActivity;
    @BindView(R.id.forgotPinEditTextSignInActivity)
    TextView forgotPinEditTextSignInActivity;
    @BindView(R.id.signInTextViewSignInActivity)
    TextView signInTextViewSignInActivity;
    @BindView(R.id.createNewAccountTextViewSignInActivity)
    TextView createNewAccountTextViewSignInActivity;
    @BindView(R.id.mainLinearLayoutSignIn)
    LinearLayout mainLinearLayoutSignIn;
    @BindView(R.id.countrySpinnerSignIn)
    LinearLayout countrySpinnerSignIn;
    @BindView(R.id.countryCodeTextView)
    TextView countryCodeTextView;
    @BindView(R.id.rememberMeCheckBox)
    CheckBox rememberMeCheckBox;
    @BindView(R.id.countryCodeImageView)
    ImageView countryCodeImageView;
    @BindView(R.id.textviewwelcomeback)
    TextView textviewwelcomeback;
    @BindView(R.id.textviewsignincontinue)
    TextView textviewsignincontinue;
    @BindView(R.id.textviewdontaccount)
    TextView textviewdontaccount;
    @BindView(R.id.textViewSignInerror)
    TextView textViewSignInerror;
    @BindView(R.id.signInerrorLayoyt)
    LinearLayout signInerrorLayoyt;
    @BindView(R.id.appImageViewHeader1)
    ImageView appImageViewHeader1;
    @BindView(R.id.alreadyhaveotptextviewSignIn)
    TextView alreadyhaveotptextviewSignIn;

    private Intent mIntent;
    private boolean showFlag = false;
    private boolean inputType = false;
    private final ArrayList<UserListPojo> userListPojos = new ArrayList<>();
    private final ArrayList<CountryData> countryListPojos = new ArrayList<>();
    private String countryCode = "";
    private int countryId = 0;
    private String locale = "IND";
    private String signInWith = "Email";
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String emailmobilemsg, pinmsg, nointernetmsg;
    private String validemail;
    private String validmobilenumber;
    private String mobileno,pass,sccp;
    private ArrayList<MessagelistData> Messagearray = new ArrayList<MessagelistData>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(SignInActivity.this);


        //////////////////////////////////////
        //New Strings


        mobileno=getIntent().getStringExtra("mobileno");
        pass=getIntent().getStringExtra("pass");
        sccp=getIntent().getStringExtra("ccp");

        //////////////////////////////////////



        locale = getResources().getConfiguration().locale.getISO3Country();
        CustomLog.d("System out", locale);

        try {
            sessionManager = new SessionManager(SignInActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();
            Messagearray.add(datumLable_languages_msg);

            if (datumLable_languages != null) {

                titleTextViewViewHeader.setText(datumLable_languages.getSignIn());
                textviewwelcomeback.setText(datumLable_languages.getWelcomeBack());
                textviewsignincontinue.setText(datumLable_languages.getSignInToContinue());
                emailMobileNoEditTextSignInActivity.setHint(datumLable_languages.getEmailMobileNo()+"*");
                emailMobileNoEditTextSignInActivity.setFloatingLabelText(datumLable_languages.getEmailMobileNo()+"*");
                pinEditTextSignInActivity.setHint(datumLable_languages.getPIN()+"*");
                pinEditTextSignInActivity.setFloatingLabelText(
                        datumLable_languages.getPIN()+"*");
                forgotPinEditTextSignInActivity.setText(datumLable_languages.getForgotPIN() + "?");
                signInTextViewSignInActivity.setText(datumLable_languages.getSignIn());
                rememberMeCheckBox.setText(datumLable_languages.getRememberMe());
                textviewdontaccount.setText(datumLable_languages.getDontHaveAnAccount());
                createNewAccountTextViewSignInActivity.setText(datumLable_languages.getCreateNewAccount());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();


            } else {
                titleTextViewViewHeader.setText(getResources().getString(R.string.sign_in));
                textviewwelcomeback.setText(getResources().getString(R.string.welcome_back));
                textviewsignincontinue.setText(getResources().getString(R.string.sign_in_to_continue));
                emailMobileNoEditTextSignInActivity.setHint(getResources().getString(R.string.email_mobile_no));
                pinEditTextSignInActivity.setHint(getResources().getString(R.string.pin));
                forgotPinEditTextSignInActivity.setText(getResources().getString(R.string.forgot_pin));
                signInTextViewSignInActivity.setText(getResources().getString(R.string.sign_IN));
                rememberMeCheckBox.setText(getResources().getString(R.string.remember_me));
                textviewdontaccount.setText(getResources().getString(R.string.don_t_have_an_account));
                createNewAccountTextViewSignInActivity.setText(getResources().getString(R.string.create_new_account));
                nointernetmsg = getResources().getString(R.string.no_internet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (datumLable_languages_msg != null) {
            emailmobilemsg = datumLable_languages_msg.getEnterEmailOrMobileNumber();
            validemail = datumLable_languages_msg.getEnterValidEmail();
            validmobilenumber = datumLable_languages_msg.getEnterValidMobileNumber();
            pinmsg = datumLable_languages_msg.getEnter6DigitPIN();

        } else {
            emailmobilemsg = getResources().getString(R.string.Please_Enter_Email_mobile_number);
            validemail = getResources().getString(R.string.Please_Enter_vaild_email);
            validmobilenumber = getResources().getString(R.string.Please_Enter_valid_Mobile_number);
            pinmsg = getResources().getString(R.string.Please_Enter_pin);

        }


        if (IsNetworkConnection.checkNetworkConnection(this)) {
            if (SugarRecord.count(CountryData.class) > 0) {
                countryListPojos.addAll(SugarRecord.listAll(CountryData.class));
                for (int i = 0; i < countryListPojos.size(); i++) {
                    if (locale.equalsIgnoreCase(countryListPojos.get(i).getCountryShortCode())) {
                        countryCode = countryListPojos.get(i).getCountryDialCode();
                        countryCodeTextView.setText(countryListPojos.get(i).getCountryDialCode());
                        Picasso.with(SignInActivity.this).load(Constants.FLAG_URL + countryListPojos.get(i).getCountryFlagImage()).into(countryCodeImageView);
                        break;
                    }

                }
            } else {
                countryListJsonCall();
            }
        } else {
            Constants.showMessage(mainLinearLayoutSignIn, this, nointernetmsg);
        }
        if (sessionManager.getRememberMe() == true) {
            rememberMeCheckBox.setChecked(true);
            countryCode = sessionManager.userProfileData().getUserCountryCode();
            countryCodeTextView.setText(sessionManager.userProfileData().getUserCountryCode());
            Picasso.with(SignInActivity.this).load(Constants.FLAG_URL + sessionManager.getuserflagimage()).into(countryCodeImageView);
            if (sessionManager.getLoginWith().equalsIgnoreCase("Email")) {
                emailMobileNoEditTextSignInActivity.setText(sessionManager.userProfileData().getUserEmail());
            } else {
                emailMobileNoEditTextSignInActivity.setText(sessionManager.userProfileData().getUserMobile());
            }
        }
        pinEditTextSignInActivity.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (pinEditTextSignInActivity.getRight() - pinEditTextSignInActivity.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (showFlag == false) {
                            showFlag = true;
                            pinEditTextSignInActivity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password, 0);
                            pinEditTextSignInActivity.setTransformationMethod(null);
                        } else {
                            pinEditTextSignInActivity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hide_password, 0);
                            pinEditTextSignInActivity.setTransformationMethod(new PasswordTransformationMethod());
                            showFlag = false;
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        emailMobileNoEditTextSignInActivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                boolean inputTypevalidation = false;
                inputTypevalidation = emailMobileNoEditTextSignInActivity.getText().toString().trim().matches("^[0-9]+$");
                if (inputTypevalidation == true) {
                    emailMobileNoEditTextSignInActivity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                } else {
                    emailMobileNoEditTextSignInActivity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
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

        userLoginJsonCall();
    }

    @OnClick({R.id.backImageViewHeader, R.id.forgotPinEditTextSignInActivity, R.id.signInTextViewSignInActivity, R.id.createNewAccountTextViewSignInActivity, R.id.countrySpinnerSignIn, R.id.alreadyhaveotptextviewSignIn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImageViewHeader:
                finish();
                break;
            case R.id.alreadyhaveotptextviewSignIn:
                openOtpDialog();
                break;
            case R.id.forgotPinEditTextSignInActivity:
                mIntent = new Intent(SignInActivity.this, ForgotPinActivity.class);
                startActivity(mIntent);
                break;
            case R.id.signInTextViewSignInActivity:
                signInerrorLayoyt.setVisibility(View.INVISIBLE);
                if (checkValidation() == true) {
                    if (IsNetworkConnection.checkNetworkConnection(this)) {

                        userLoginJsonCall();
                    } else {
                        Constants.showMessage(mainLinearLayoutSignIn, this, nointernetmsg);
                    }
                }
                break;
            case R.id.createNewAccountTextViewSignInActivity:
                mIntent = new Intent(SignInActivity.this, SignUpSubmitActivity.class);
                mIntent.putExtra("email", "");
                mIntent.putExtra("google_id", "");
                mIntent.putExtra("fb_id", "");
                mIntent.putExtra("first_name", "");
                mIntent.putExtra("last_name", "");
                mIntent.putExtra("gender", "");
                startActivity(mIntent);
                break;
            case R.id.countrySpinnerSignIn:
                CountrySelectionBottomSheet countrySelectionBottomSheet = new CountrySelectionBottomSheet();
                Bundle bundle = new Bundle();
                bundle.putSerializable("country_list", countryListPojos);
                countrySelectionBottomSheet.setArguments(bundle);
                countrySelectionBottomSheet.show(getSupportFragmentManager(), "BottomSheet Fragment");
                break;
        }
    }

    private boolean checkValidation() {
        boolean checkFlag = true;

        inputType = emailMobileNoEditTextSignInActivity.getText().toString().trim().matches("^[0-9]+$");

        if (emailMobileNoEditTextSignInActivity.getText().toString().trim().length() == 0) {
            emailMobileNoEditTextSignInActivity.setError(emailmobilemsg);

//            Constants.showMessage(mainLinearLayoutSignIn, SignInActivity.this, emailmobilemsg);
            checkFlag = false;
        } else if (inputType == true && emailMobileNoEditTextSignInActivity.getText().toString().trim().length() < 7) {
            emailMobileNoEditTextSignInActivity.setError(validmobilenumber);

//            Constants.showMessage(mainLinearLayoutSignIn, SignInActivity.this, validmobilenumber);
            checkFlag = false;
        } else if (inputType == true && emailMobileNoEditTextSignInActivity.getText().toString().startsWith("0")) {
            emailMobileNoEditTextSignInActivity.setError(validmobilenumber);

//            Constants.showMessage(mainLinearLayoutSignIn, SignInActivity.this, validmobilenumber);
            checkFlag = false;
        } else if (inputType == false && Constants.validateEmail(emailMobileNoEditTextSignInActivity.getText().toString().trim()) == false) {

            emailMobileNoEditTextSignInActivity.setError(validemail);
//            Constants.showMessage(mainLinearLayoutSignIn, SignInActivity.this, validemail);
            checkFlag = false;
        } else if (pinEditTextSignInActivity.getText().toString().length() == 0) {
            pinEditTextSignInActivity.setError(pinmsg);
//            Constants.showMessage(mainLinearLayoutSignIn, SignInActivity.this, pinmsg);
            checkFlag = false;
        }

        if (inputType == true) {
            signInWith = "Mobile";
        } else {
            signInWith = "Email";

        }

        return checkFlag;
    }

    private void userLoginJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("loginID", mobileno);
            jsonObject.put("userCountryCode", sccp);
            jsonObject.put("loginMode", signInWith);
            jsonObject.put("userDeviceType", Constants.device_type);
            jsonObject.put("userDeviceID", Constants.device_token);
            jsonObject.put("userPassword", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "signIn json " + json);
        Constants.showProgress(SignInActivity.this);
        Call<List<UserListPojo>> call = RestClient.get().loginUserJsonCall(json);

        call.enqueue(new Callback<List<UserListPojo>>() {
            @Override
            public void onResponse(Call<List<UserListPojo>> call, Response<List<UserListPojo>> response) {


                if (response.body() != null && response.body() instanceof ArrayList) {
                    userListPojos.clear();
                    userListPojos.addAll(response.body());
                    if (userListPojos.get(0).getStatus() == true) {

                        SessionManager sessionManager = new SessionManager(SignInActivity.this);
                        sessionManager.updateUserProfile(new Gson().toJson(userListPojos.get(0).getData().get(0)));
                        sessionManager.setWalletBalance((float) 0.0);
                        sessionManager.setlanguageselection(Constants.language_id_label_msg);

                        if (rememberMeCheckBox.isChecked() == true) {

                            sessionManager.setRememberMe(true);
                            sessionManager.setLoginWith(signInWith);
                        } else {
                            sessionManager.setRememberMe(false);
                        }

                        if (IsNetworkConnection.checkNetworkConnection(SignInActivity.this)) {
//                            if (SugarRecord.count(CountryData.class) > 0) {
//                                countryListPojos.addAll(SugarRecord.listAll(CountryData.class));
                            for (int i = 0; i < countryListPojos.size(); i++) {
                                if (sessionManager.userProfileData().getCountryCurrencySymbol().equalsIgnoreCase(countryListPojos.get(i).getCountryCurrencySymbol())) {
                                    sessionManager.setuserflagimage(countryListPojos.get(i).getCountryFlagImage());
                                    break;
                                }


                            }
                        }
                        if (userListPojos.get(0).getData().get(0).getUserVerified().equalsIgnoreCase("false")) {
                            Constants.closeProgress();
                            sessionManager.setLogin(true);
                            mIntent = new Intent(SignInActivity.this, VerificationActivity.class);
                            startActivity(mIntent);
                        } else {
                            Constants.closeProgress();
                            sessionManager.setLogin(true);
                            sessionManager.setVerify(true);
                            sessionManager.setLogoutVerify(true);


                            mIntent = new Intent(SignInActivity.this, HomeActivity.class);
                            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mIntent);
                        }
                        finish();
                    } else {
                        Constants.closeProgress();
                        Object object = userListPojos.get(0).getInfo();
                        String s = String.valueOf(object);
                        String test = datumLable_languages_msg.getMessage(s);
                        if (test.equalsIgnoreCase("")) {
//                            Constants.showMessage(mainLinearLayoutSignIn, SignInActivity.this, s);
                            signInerrorLayoyt.setVisibility(View.VISIBLE);
                            textViewSignInerror.setText(s);
                        } else {
//                            Constants.showMessage(mainLinearLayoutSignIn, SignInActivity.this, datumLable_languages_msg.getMessage(s));
                            signInerrorLayoyt.setVisibility(View.VISIBLE);
                            textViewSignInerror.setText(datumLable_languages_msg.getMessage(s));
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserListPojo>> call, Throwable t) {
                Constants.closeProgress();

            }
        });
    }

    private void openOtpDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.mobile_number_custom_dialog, null);

        final MaterialEditText editText = (MaterialEditText) dialogView.findViewById(R.id.MobileNoEditTextSignUpActivity);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);
        final LinearLayout customDialogLayput = (LinearLayout) dialogView.findViewById(R.id.customDialogLayput);

//        editText.setHint(datumLable_languages.getMobileNumber());
//        editText.setFloatingLabelText(datumLable_languages.getMobileNumber());
        dialogBuilder.setTitle(datumLable_languages.getWorkerAppz());
        dialogBuilder.setIcon(R.drawable.app_icon);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.hideKeyboard(SignInActivity.this);
                if (editText.getText().toString().length() == 0 || editText.getText().toString().length() == 0) {
                    Constants.showMessage(customDialogLayput, SignInActivity.this, validmobilenumber);
                } else if (editText.getText().toString().length() > 0 && editText.getText().toString().length() < 7) {
                    Constants.hideKeyboard(SignInActivity.this);
                    Constants.showMessage(customDialogLayput, SignInActivity.this, validmobilenumber);
                } else if (editText.getText().toString().startsWith("0")) {
                    Constants.hideKeyboard(SignInActivity.this);
                    Constants.showMessage(customDialogLayput, SignInActivity.this, validmobilenumber);
                } else {
                    dialogBuilder.dismiss();
                    mIntent = new Intent(SignInActivity.this, VerificationActivity.class);
                    mIntent.putExtra("come_from", editText.getText().toString());
                    startActivity(mIntent);
                }
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private void countryListJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", Constants.language_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";

        Call<List<CountryListPojo>> call = RestClient.get().countryListJsonCall(json);

        call.enqueue(new Callback<List<CountryListPojo>>() {
            @Override
            public void onResponse(Call<List<CountryListPojo>> call, Response<List<CountryListPojo>> response) {

                if (response.body() != null && response.body() instanceof ArrayList) {
                    countryListPojos.addAll(response.body().get(0).getData());

                    if (countryListPojos.size() > 0) {
                        SugarRecord.saveInTx(countryListPojos);
                    }

                    if (countryListPojos.size() > 0) {
                        for (int i = 0; i < countryListPojos.size(); i++) {
                            if (locale.equalsIgnoreCase(countryListPojos.get(i).getCountryShortCode())) {
                                countryCode = countryListPojos.get(i).getCountryDialCode();
                                if (sessionManager.getRememberMe() == true) {
                                    countryCodeTextView.setText(sessionManager.userProfileData().getUserCountryCode());
                                } else {

                                    countryCodeTextView.setText(countryListPojos.get(i).getCountryDialCode());
                                }
                                Picasso.with(SignInActivity.this).load(Constants.FLAG_URL + countryListPojos.get(i).getCountryFlagImage()).into(countryCodeImageView);
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CountryListPojo>> call, Throwable t) {

            }
        });
    }

    public void updateCountrySelection(List<CountryData> countryListPojosupdated, int position) {
        countryCode = countryListPojosupdated.get(position).getCountryDialCode();
        countryCodeTextView.setText(countryListPojosupdated.get(position).getCountryDialCode());
        Picasso.with(SignInActivity.this).load(Constants.FLAG_URL + countryListPojosupdated.get(position).getCountryFlagImage()).into(countryCodeImageView);
    }
}