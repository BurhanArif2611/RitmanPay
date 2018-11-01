package com.fil.workerappz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fil.workerappz.fragments.CountrySelectionBottomSheet;
import com.fil.workerappz.pojo.CityListPojo;
import com.fil.workerappz.pojo.CountryData;
import com.fil.workerappz.pojo.CountryListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.StateListPojo;
import com.fil.workerappz.pojo.UserListPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.orm.SugarRecord;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HS on 24-Feb-18.
 * FIL AHM
 */

public class SignUpSubmitActivity extends ActionBarActivity {

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    @BindView(R.id.backImageViewHeader)
    ImageView backImageViewHeader;
    @BindView(R.id.titleTextViewViewHeader)
    TextView titleTextViewViewHeader;
    @BindView(R.id.skipTextViewViewHeader)
    TextView skipTextViewViewHeader;
    @BindView(R.id.firstNameEditTextSignUpSubmit)
    MaterialEditText firstNameEditTextSignUpSubmit;
    @BindView(R.id.lastNameEditTextSignUpSubmit)
    MaterialEditText lastNameEditTextSignUpSubmit;
    @BindView(R.id.mobileNoEditTextSignUpSubmit)
    MaterialEditText mobileNoEditTextSignUpSubmit;
    @BindView(R.id.emailEditTextSignUpSubmit)
    MaterialEditText emailEditTextSignUpSubmit;
    @BindView(R.id.maleRadioButtonSignUpSubmit)
    RadioButton maleRadioButtonSignUpSubmit;
    @BindView(R.id.femaleRadioButtonSignUpSubmit)
    RadioButton femaleRadioButtonSignUpSubmit;
    @BindView(R.id.addressEditTextSignUpSubmit)
    MaterialEditText addressEditTextSignUpSubmit;
    @BindView(R.id.countryOfResidenceSpinnerSignUpSubmit)
    MaterialSpinner countryOfResidenceSpinnerSignUpSubmit;
    @BindView(R.id.passportNoEditTextSignUpSubmit)
    MaterialEditText passportNoEditTextSignUpSubmit;
    @BindView(R.id.emiratesIdEditTextSignUpSubmit)
    MaterialEditText emiratesIdEditTextSignUpSubmit;
    @BindView(R.id.tvCreateAccountSignUp)
    TextView tvCreateAccountSignUp;
    @BindView(R.id.submitTextViewSignUpSubmit)
    TextView submitTextViewSignUpSubmit;
    @BindView(R.id.addressTextViewSignUpSubmit)
    TextView addressTextViewSignUpSubmit;
    @BindView(R.id.mainLinearLayoutSignUpSubmit)
    LinearLayout mainLinearLayoutSignUpSubmit;
    @BindView(R.id.maleFemaleRadioGroupSignUpSubmit)
    RadioGroup maleFemaleRadioGroupSignUpSubmit;
    @BindView(R.id.countryCodeTextViewSignUp)
    TextView countryCodeTextViewSignUp;
    @BindView(R.id.countryCodeImageViewSignUp)
    ImageView countryCodeImageViewSignUp;
    @BindView(R.id.countrySpinnerSignUp)
    LinearLayout countrySpinnerSignUp;
    @BindView(R.id.textviewgender)
    TextView textviewgender;
    @BindView(R.id.stateSpinnerSignUpSubmit)
    MaterialSpinner stateSpinnerSignUpSubmit;
    @BindView(R.id.citySpinnerSignUpSubmit)
    MaterialSpinner citySpinnerSignUpSubmit;
    @BindView(R.id.addressEditTextSignUp)
    MaterialEditText addressEditTextSignUp;
    @BindView(R.id.landmarkEditTextSignUp)
    MaterialEditText landmarkEditTextSignUp;
    @BindView(R.id.zipcodeEditTextSignUp)
    MaterialEditText zipcodeEditTextSignUp;
    @BindView(R.id.streetEditTextSignUp)
    MaterialEditText streetEditTextSignUp;
    private Intent mIntent;
    private String gender = "Male";
    private final String signUpWith = "Email";
    private int countryId = 0;
    private String countryName = "";
    private int stateId = 0;
    private int cityId = 0;
    private String countryCode = "0", countryFlagImage = "";
    private final ArrayList<CountryData> countryListPojos = new ArrayList<>();
    private final ArrayList<StateListPojo.DataStateList> stateListPojos = new ArrayList<>();
    private final ArrayList<CityListPojo.Data> cityListPojos = new ArrayList<>();
    private final ArrayList<UserListPojo> userListPojos = new ArrayList<>();
    private String fbId = "";
    private String googleId = "";
    private String locale = "IND";
    private boolean inputType = false;
    private String CountryCodegoogle = "";
    private String countryIdNationality = "";
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String firstname, lastname, mobilenumber, validmobilenumber, email, validemail, address, gendermsg, selectcountry, passportmsg, passportvalidationmsg, emiratesvalidationmsg, emiratesidmsg, registrationdonemsg, nointernetmessage;


    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        CustomLog.e("System out", res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_submit);
        ButterKnife.bind(this);


        mIntent = getIntent();
        if (mIntent != null) {
            emailEditTextSignUpSubmit.setText(mIntent.getStringExtra("email"));
            googleId = mIntent.getStringExtra("google_id");
            fbId = mIntent.getStringExtra("fb_id");
            firstNameEditTextSignUpSubmit.setText(mIntent.getStringExtra("first_name"));
            lastNameEditTextSignUpSubmit.setText(mIntent.getStringExtra("last_name"));
            gender = mIntent.getStringExtra("gender");
        }
        try {
            SessionManager sessionManager = new SessionManager(SignUpSubmitActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {
                firstNameEditTextSignUpSubmit.setHint(datumLable_languages.getFirstName());
                firstNameEditTextSignUpSubmit.setFloatingLabelText(datumLable_languages.getFirstName());
                lastNameEditTextSignUpSubmit.setHint(datumLable_languages.getLastName());
                lastNameEditTextSignUpSubmit.setFloatingLabelText(datumLable_languages.getLastName());
                mobileNoEditTextSignUpSubmit.setHint(datumLable_languages.getMobileNumber());
                mobileNoEditTextSignUpSubmit.setFloatingLabelText(datumLable_languages.getMobileNumber());
                emailEditTextSignUpSubmit.setHint(datumLable_languages.getEmail());
                emailEditTextSignUpSubmit.setFloatingLabelText(datumLable_languages.getEmail());
                addressEditTextSignUpSubmit.setHint(datumLable_languages.getAddress());
                addressEditTextSignUpSubmit.setFloatingLabelText(datumLable_languages.getAddress());
                countryOfResidenceSpinnerSignUpSubmit.setHint(datumLable_languages.getCountryOfResidence());
                passportNoEditTextSignUpSubmit.setHint(datumLable_languages.getPassportNo());
                passportNoEditTextSignUpSubmit.setFloatingLabelText(datumLable_languages.getPassportNo());
                emiratesIdEditTextSignUpSubmit.setHint(datumLable_languages.getEmiratesId());
                emiratesIdEditTextSignUpSubmit.setFloatingLabelText(datumLable_languages.getEmiratesId());
                countryOfResidenceSpinnerSignUpSubmit.setFloatingLabelText(datumLable_languages.getCountryOfResidence());
                textviewgender.setText(datumLable_languages.getGender());
                maleRadioButtonSignUpSubmit.setText(datumLable_languages.getMale());
                femaleRadioButtonSignUpSubmit.setText(datumLable_languages.getFemale());
                submitTextViewSignUpSubmit.setText(datumLable_languages.getNext());
                titleTextViewViewHeader.setText(datumLable_languages.getSignUp());
                nointernetmessage = datumLable_languages.getNoInternetConnectionAvailable();


            } else {
                firstNameEditTextSignUpSubmit.setHint(getResources().getString(R.string.first_name));
                lastNameEditTextSignUpSubmit.setHint(getResources().getString(R.string.last_name));
                mobileNoEditTextSignUpSubmit.setHint(getResources().getString(R.string.mobile_number));
                emailEditTextSignUpSubmit.setHint(getResources().getString(R.string.email));
                addressEditTextSignUpSubmit.setHint(getResources().getString(R.string.address));
                countryOfResidenceSpinnerSignUpSubmit.setHint(getResources().getString(R.string.country_of_residence));
                passportNoEditTextSignUpSubmit.setHint(getResources().getString(R.string.passport_no));
                emiratesIdEditTextSignUpSubmit.setHint(getResources().getString(R.string.emirates_id));
                countryOfResidenceSpinnerSignUpSubmit.setFloatingLabelText(getResources().getString(R.string.country_of_residence));
                textviewgender.setText(getResources().getString(R.string.gender));
                maleRadioButtonSignUpSubmit.setText(getResources().getString(R.string.male));
                femaleRadioButtonSignUpSubmit.setText(getResources().getString(R.string.female));
                submitTextViewSignUpSubmit.setText(getResources().getString(R.string.next));
                titleTextViewViewHeader.setText(getResources().getString(R.string.sign_up));
                nointernetmessage = getResources().getString(R.string.no_internet);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (datumLable_languages_msg != null) {
            firstname = datumLable_languages_msg.getEnterFirstName();
            lastname = datumLable_languages_msg.getEnterLastName();
            mobilenumber = datumLable_languages_msg.getEnterMobileNumber();
            validmobilenumber = datumLable_languages_msg.getEnterValidMobileNumber();
            email = datumLable_languages_msg.getEnterEmail();
            validemail = datumLable_languages_msg.getEnterValidEmail();
            address = datumLable_languages_msg.getEnterAddress();
            gendermsg = datumLable_languages_msg.getSelectGender();
            selectcountry = datumLable_languages_msg.getSelectCountryOfResidence();
            passportmsg = datumLable_languages_msg.getEnterPassportNo();
            emiratesidmsg = datumLable_languages_msg.getEnterEmiratesId();
            registrationdonemsg = datumLable_languages_msg.getYourRegistrationIsDone();
            passportvalidationmsg = datumLable_languages_msg.getPassportNoNumberShouldBe10Digits();
            emiratesvalidationmsg = datumLable_languages_msg.getEmiratesIdShouldBe10Digits();


        } else {
            firstname = getResources().getString(R.string.Please_Enter_First_Name);
            lastname = getResources().getString(R.string.Please_Enter_LAST_Name);
            mobilenumber = getResources().getString(R.string.Please_Enter_Mobile_number);
            validmobilenumber = getResources().getString(R.string.Please_Enter_valid_Mobile_number);
            email = getResources().getString(R.string.Please_Enter_email);
            validemail = getResources().getString(R.string.Please_Enter_vaild_email);
            address = getResources().getString(R.string.Please_Enter_address);
            gendermsg = getResources().getString(R.string.Please_Select_gender);
            selectcountry = getResources().getString(R.string.Please_Select_countryof_residence);
            passportmsg = getResources().getString(R.string.Please_enter_passport_no);
            emiratesidmsg = getResources().getString(R.string.Please_enter_emirates_id);
            registrationdonemsg = getResources().getString(R.string.registration_done_msg);
            passportvalidationmsg = getResources().getString(R.string.passport_validation_msg);
            emiratesvalidationmsg = getResources().getString(R.string.emirates_validation_msg);

        }

        if (gender.equalsIgnoreCase(datumLable_languages.getFemale())) {
            femaleRadioButtonSignUpSubmit.setChecked(true);
        } else {
            maleRadioButtonSignUpSubmit.setChecked(true);
        }
        if (gender.equalsIgnoreCase("")) {
            gender = datumLable_languages.getMale();
        }

        locale = getResources().getConfiguration().locale.getISO3Country();

        if (IsNetworkConnection.checkNetworkConnection(this)) {
            if (SugarRecord.count(CountryData.class) > 0) {
                countryListPojos.addAll(SugarRecord.listAll(CountryData.class));
                for (int i = 0; i < countryListPojos.size(); i++) {
                    if (locale.equalsIgnoreCase(countryListPojos.get(i).getCountryShortCode())) {
                        countryCodeTextViewSignUp.setText(countryListPojos.get(i).getCountryDialCode());
                        Picasso.with(SignUpSubmitActivity.this).load(Constants.FLAG_URL + countryListPojos.get(i).getCountryFlagImage()).into(countryCodeImageViewSignUp);
                        break;
                    }

                }

                ArrayList<String> countryList = new ArrayList<>();
                for (int i = 0; i < countryListPojos.size(); i++) {
                    countryList.add(new String(Base64.decode(countryListPojos.get(i).getCountryName().trim().getBytes(), Base64.DEFAULT)));
                    if(locale.equalsIgnoreCase(countryListPojos.get(i).getCountryShortCode())) {
                        countryCode = countryListPojos.get(i).getCountryDialCode();
                        countryId = countryListPojos.get(i).getCountryID();
                        countryIdNationality = String.valueOf(countryListPojos.get(i).getCountryID());
                        countryOfResidenceSpinnerSignUpSubmit.setSelection(i+1);
                        break;
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUpSubmitActivity.this, android.R.layout.simple_spinner_item, countryList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                countryOfResidenceSpinnerSignUpSubmit.setAdapter(adapter);

                countryOfResidenceSpinnerSignUpSubmit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != -1) {
                            countryCode = countryListPojos.get(position).getCountryDialCode();
                            countryId = countryListPojos.get(position).getCountryID();
                            countryIdNationality = String.valueOf(countryListPojos.get(position).getCountryID());
                            stateId = 0;
                            cityId = 0;
                            stateListJsonCall();
//                            countryId = countryListPojos.get(position).getCountryID();

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            } else {
                countryListJsonCall();
            }
        } else {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, this, nointernetmessage);
        }

        customTextView(tvCreateAccountSignUp);
        stateListJsonCall();
        cityListJsonCall();

        maleFemaleRadioGroupSignUpSubmit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                String gender1 = radioButton.getText().toString();
//                if (gender1.equalsIgnoreCase("Male")) {
//                    gender = "Male";
//                } else {
//                    gender = "Female";
//                }
                if (gender1.equalsIgnoreCase(datumLable_languages.getMale())) {
                    gender = "Male";
                } else {
                    gender = "Female";
                }
            }
        });
        firstNameEditTextSignUpSubmit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                OnlyCharacter(firstNameEditTextSignUpSubmit);
            }
        });

        lastNameEditTextSignUpSubmit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                OnlyCharacter(lastNameEditTextSignUpSubmit);
            }
        });
        passportNoEditTextSignUpSubmit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(!charSequence.equals("") ) {
//                    //do your work here
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                OnlyCharacter1(passportNoEditTextSignUpSubmit);
            }
        });


    }

    @OnClick({R.id.backImageViewHeader, R.id.addressTextViewSignUpSubmit, R.id.submitTextViewSignUpSubmit, R.id.countrySpinnerSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImageViewHeader:
                finish();
                break;
            case R.id.addressTextViewSignUpSubmit:
//                openAutocompleteActivity();
                break;
            case R.id.submitTextViewSignUpSubmit:
                if (checkValidation() == true) {
                    if (IsNetworkConnection.checkNetworkConnection(this)) {
                        submitSignUpJsonCall();
                    } else {
                        Constants.showMessage(mainLinearLayoutSignUpSubmit, this, nointernetmessage);

                    }
                }
                break;
            case R.id.countrySpinnerSignUp:
                CountrySelectionBottomSheet countrySelectionBottomSheet = new CountrySelectionBottomSheet();
                Bundle bundle = new Bundle();
                bundle.putSerializable("country_list", countryListPojos);
                countrySelectionBottomSheet.setArguments(bundle);
                countrySelectionBottomSheet.show(getSupportFragmentManager(), "BottomSheet Fragment");
                break;
        }
    }

    public static void OnlyCharacter(MaterialEditText editText) {
        if (editText.getText().toString().length() > 0) {

            char x;
            int[] t = new int[editText.getText().toString()
                    .length()];

            for (int i = 0; i < editText.getText().toString()
                    .length(); i++) {
                x = editText.getText().toString().charAt(i);
                int z = (int) x;
                t[i] = z;

                if ((z > 64 && z < 91)
                        || (z > 96 && z < 123)) {

                } else {

                    editText.setText(editText
                            .getText()
                            .toString()
                            .substring(
                                    0,
                                    (editText.getText()
                                            .toString().length()) - 1));
                    editText.setSelection(editText
                            .getText().length());
                }
                Log.d("System out", "decimal value of character" + z);

            }
        }
    }

    public static void OnlyCharacter1(MaterialEditText editText) {
//        if (editText.getText().toString().length() > 0) {
//
//            char x;
//            int[] t = new int[editText.getText().toString()
//                    .length()];
//
//            for (int i = 0; i < editText.getText().toString()
//                    .length(); i++) {
//                x = editText.getText().toString().charAt(i);
//                int z = (int) x;
//                t[i] = z;
//
//                if ((z > 64 && z < 91)
//                        || (z > 96 && z < 123)|| (z > 47 && z < 58) ) {
//
//                }
//                else {
//
//                    editText.setText(editText
//                            .getText()
//                            .toString()
//                            .substring(
//                                    0,
//                                    (editText.getText()
//                                            .toString().length()) - 1));
//                    editText.setSelection(editText
//                            .getText().length());
//                }
//                Log.d("System out", "decimal value of character" + z);
//
//            }
//        }

        if (editText.getText().toString().length() > 0) {

            char x;
            int[] t = new int[editText.getText().toString()
                    .length()];

            for (int i = 0; i < editText.getText().toString()
                    .length(); i++) {
                x = editText.getText().toString().charAt(i);
                int z = (int) x;
                t[i] = z;

                if ((z > 64 && z < 91)
                        || (z > 96 && z < 123) || (z >= 48 && z <= 57)) {

                } else {

                    editText.setText(editText
                            .getText()
                            .toString()
                            .substring(
                                    0,
                                    (editText.getText()
                                            .toString().length()) - 1));
                    editText.setSelection(editText
                            .getText().length());
                }

            }
        }

    }

    private void customTextView(TextView view) {
        SpannableStringBuilder spanTxt;
        if (datumLable_languages.getByClicking() != null) {
            spanTxt = new SpannableStringBuilder(
                    datumLable_languages.getByClicking() + " ");
        } else {
            spanTxt = new SpannableStringBuilder("By Clicking Submit You are Agree With our");

        }
        spanTxt.append(datumLable_languages.getTermsConditions());
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                mIntent = new Intent(SignUpSubmitActivity.this, TermsAndPrivacyActivity.class);
                mIntent.putExtra("come_from", "terms");
                startActivity(mIntent);
            }
        }, spanTxt.length() - datumLable_languages.getTermsConditions().length(), spanTxt.length(), 0);
        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), spanTxt.length() - datumLable_languages.getTermsConditions().length(), spanTxt.length(), 0);
        spanTxt.append(" " + datumLable_languages.getAnd() + " ");
        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGrey)), spanTxt.length() - datumLable_languages.getAnd().length(), spanTxt.length(), 0);
        spanTxt.append(datumLable_languages.getPrivacyPolicy());
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                mIntent = new Intent(SignUpSubmitActivity.this, TermsAndPrivacyActivity.class);
                mIntent.putExtra("come_from", "privacy");
                startActivity(mIntent);
            }
        }, spanTxt.length() - datumLable_languages.getPrivacyPolicy().length(), spanTxt.length(), 0);
        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), spanTxt.length() - datumLable_languages.getPrivacyPolicy().length(), spanTxt.length(), 0);
        spanTxt.append(" ");
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    private boolean checkValidation() {
        boolean checkFlag = true;
        inputType = emailEditTextSignUpSubmit.getText().toString().trim().matches("^[0-9]+$");
        try {
            countryName = countryOfResidenceSpinnerSignUpSubmit.getSelectedItem().toString();
        } catch (Exception e) {
            countryName = "";
            e.printStackTrace();
        }
        if (firstNameEditTextSignUpSubmit.getText().toString().length() == 0) {

            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, firstname);

            checkFlag = false;
        } else if (lastNameEditTextSignUpSubmit.getText().toString().length() == 0) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, lastname);
            checkFlag = false;
        } else if (mobileNoEditTextSignUpSubmit.getText().toString().length() == 0 || mobileNoEditTextSignUpSubmit.getText().toString().length() == 0) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, mobilenumber);
            checkFlag = false;
        } else if (mobileNoEditTextSignUpSubmit.getText().toString().length() > 0 && mobileNoEditTextSignUpSubmit.getText().toString().length() < 7) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, validmobilenumber);
            checkFlag = false;
        } else if (mobileNoEditTextSignUpSubmit.getText().toString().startsWith("0")) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, validmobilenumber);
            checkFlag = false;
        } else if (emailEditTextSignUpSubmit.getText().toString().length() == 0) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, email);
            checkFlag = false;
        } else if (emailEditTextSignUpSubmit.getText().toString().length() > 0 && Constants.validateEmail(emailEditTextSignUpSubmit.getText().toString()) == false) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, validemail);
            checkFlag = false;
        } else if (countryName.length() == 0) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, selectcountry);
            checkFlag = false;
        } else if (stateId == 0) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, "Please select state");
            checkFlag = false;
        } else if (cityId == 0) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, "Please select city");
            checkFlag = false;
        } else if (addressEditTextSignUp.getText().toString().length() == 0) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, address);
            checkFlag = false;
        }
        else if (streetEditTextSignUp.getText().toString().length() == 0) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, "Please enter street");
            checkFlag = false;
        } else if (landmarkEditTextSignUp.getText().toString().length() == 0) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, "Please enter landmark");
            checkFlag = false;
        } else if (zipcodeEditTextSignUp.getText().toString().length() == 0) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, "Please enter zipcode");
            checkFlag = false;
        } else if (maleFemaleRadioGroupSignUpSubmit.getCheckedRadioButtonId() == -1) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, gendermsg);
            checkFlag = false;
        } else if (passportNoEditTextSignUpSubmit.getText().toString().length() == 0) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, passportmsg);
            checkFlag = false;
        } else if (passportNoEditTextSignUpSubmit.getText().toString().length() < 8) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, passportvalidationmsg);
            checkFlag = false;
        } else if (emiratesIdEditTextSignUpSubmit.getText().toString().length() == 0) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, emiratesidmsg);
            checkFlag = false;
        } else if (emiratesIdEditTextSignUpSubmit.getText().toString().length() < 7) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, emiratesvalidationmsg);
            checkFlag = false;
        } else if (inputType == false && Constants.validateEmail(emailEditTextSignUpSubmit.getText().toString().trim()) == false) {
            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, validemail);
            checkFlag = false;
        }


        return checkFlag;
    }

    private void submitSignUpJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userFirstName", firstNameEditTextSignUpSubmit.getText().toString().trim());
            jsonObject.put("userLastName", lastNameEditTextSignUpSubmit.getText().toString().trim());
            jsonObject.put("userGender", gender);
            jsonObject.put("userAddress", addressEditTextSignUp.getText().toString().trim());
            jsonObject.put("cityID", cityId);
            jsonObject.put("stateID", stateId);
            jsonObject.put("countryID", String.valueOf(countryId));
            jsonObject.put("userLattitude", Constants.latitude);
            jsonObject.put("userLongitutde", Constants.longitude);
            jsonObject.put("userCountryCode", countryCode);
            jsonObject.put("userMobile", mobileNoEditTextSignUpSubmit.getText().toString().trim());
            jsonObject.put("userPassword", "123456");
//            jsonObject.put("userNationality", countryName);
            jsonObject.put("userNationality", countryIdNationality);
            jsonObject.put("userPassportNo", passportNoEditTextSignUpSubmit.getText().toString().trim());
            jsonObject.put("userEmiratesID", emiratesIdEditTextSignUpSubmit.getText().toString());
            jsonObject.put("userDeviceType", Constants.device_type);
            jsonObject.put("userDeviceID", Constants.device_token);
            jsonObject.put("userType", Constants.user_type);
            jsonObject.put("userEmail", emailEditTextSignUpSubmit.getText().toString().trim());
            jsonObject.put("userLanguageID", Constants.language_id);
            jsonObject.put("userSignupWith", signUpWith);
            jsonObject.put("userFBID", "");
            jsonObject.put("userGPlusID", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "signUp json " + json);
        Constants.showProgress(SignUpSubmitActivity.this);
        Call<List<UserListPojo>> call = RestClient.get().registerUserJsonCall(json);

        call.enqueue(new Callback<List<UserListPojo>>() {
            @Override
            public void onResponse(Call<List<UserListPojo>> call, Response<List<UserListPojo>> response) {
                Constants.closeProgress();

                if (response.body() != null && response.body() instanceof ArrayList) {
                    userListPojos.clear();
                    userListPojos.addAll(response.body());
                    if (userListPojos.get(0).getStatus() == true) {
                        Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, registrationdonemsg);
                        SessionManager sessionManager = new SessionManager(SignUpSubmitActivity.this);
                        sessionManager.setuserflagimage(countryFlagImage);
                        sessionManager.updateUserProfile(new Gson().toJson(userListPojos.get(0).getData().get(0)));
                        sessionManager.setLogin(true);
                        sessionManager.setVerify(false);
                        sessionManager.setLogoutVerify(false);


                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.removeCallbacks(this);
//                                finish();
//                                onRestart();
                                mIntent = new Intent(SignUpSubmitActivity.this, UploadYourDocumentActivity.class);
                                mIntent.putExtra("come_from", "signUp");
//                        mIntent = new Intent(SignUpSubmitActivity.this, VerificationActivity.class);
                                startActivity(mIntent);
                                finish();
                            }
                        }, 3500);

//                        finish();
                    } else {
                        Object object = userListPojos.get(0).getInfo();
                        String s = String.valueOf(object);
                        s = s.substring(s.indexOf("[") + 1);
                        s = s.substring(0, s.indexOf("]"));
                        CustomLog.d("System out", "false string " + s);
//                        Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, s);
                        String test = datumLable_languages_msg.getMessage(s);
                        if (test.equalsIgnoreCase("")) {
                            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, s);
                        } else {
                            Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this, datumLable_languages_msg.getMessage(s));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserListPojo>> call, Throwable t) {
                Constants.closeProgress();
                t.printStackTrace();

            }
        });
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            @SuppressLint("RestrictedApi") Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).zzit(addressEditTextSignUpSubmit.getText().toString()).build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            CustomLog.e("System out", message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void countryListJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", Constants.language_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        Constants.showProgress(SignUpSubmitActivity.this);
        Call<List<CountryListPojo>> call = RestClient.get().countryListJsonCall(json);

        call.enqueue(new Callback<List<CountryListPojo>>() {
            @Override
            public void onResponse(Call<List<CountryListPojo>> call, Response<List<CountryListPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    if (response.body().get(0).getStatus() == true) {
                        countryListPojos.addAll(response.body().get(0).getData());
                        ArrayList<String> countryList = new ArrayList<>();
                        for (int i = 0; i < countryListPojos.size(); i++) {
                            countryList.add(new String(Base64.decode(countryListPojos.get(i).getCountryName().trim().getBytes(), Base64.DEFAULT)));
                            if (CountryCodegoogle.equalsIgnoreCase(new String(Base64.decode(countryListPojos.get(i).getCountryName().trim().getBytes(), Base64.DEFAULT)))) {
                                countryCode = countryListPojos.get(i).getCountryDialCode();
                                countryId = countryListPojos.get(i).getCountryID();
                                countryIdNationality = String.valueOf(countryListPojos.get(i).getCountryID());
                                countryOfResidenceSpinnerSignUpSubmit.setSelection(i+1);
                                break;
                            }



                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUpSubmitActivity.this, android.R.layout.simple_spinner_item, countryList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        countryOfResidenceSpinnerSignUpSubmit.setAdapter(adapter);

                        countryOfResidenceSpinnerSignUpSubmit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    countryCode = countryListPojos.get(position).getCountryDialCode();
                                    countryId = countryListPojos.get(position).getCountryID();
                                    countryIdNationality = String.valueOf(countryListPojos.get(position).getCountryID());
                                    stateId = 0;
                                    cityId = 0;
                                    stateListJsonCall();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CountryListPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    /**
     * Called after the autocomplete activity has finished to return its result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            switch (resultCode) {
                case RESULT_OK:
                    // Get the user's selected place from the Intent.
                    Place place = PlaceAutocomplete.getPlace(this, data);
                    CustomLog.i("System out", "Place Selected: " + place.getName());
                    CustomLog.i("System out", "Place Address: " + place.getAddress());

                    // Format the place's details and display them in the TextView.
//                addressEditTextSignUpSubmit.setText(formatPlaceDetails(getResources(), place.getName(),
//                        place.getId(), place.getAddress(), place.getPhoneNumber(),
//                        place.getWebsiteUri()));

                    // Display attributions if required.
//                CharSequence attributions = place.getAttributions();
//                    if (place != null) {
//                        addressEditTextSignUpSubmit.setText(place.getAddress());
//                        LatLng mLatLng = place.getLatLng();
//                        Constants.latitude = String.valueOf(mLatLng.latitude);
//                        Constants.longitude = String.valueOf(mLatLng.longitude);
//                    }
                    if (place != null) {
                        addressEditTextSignUpSubmit.setText(place.getAddress());
                        LatLng mLatLng = place.getLatLng();
                        Constants.latitude = String.valueOf(mLatLng.latitude);
                        Constants.longitude = String.valueOf(mLatLng.longitude);
                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
// lat,lng, your current location
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(mLatLng.latitude, mLatLng.longitude, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Address address = null;
                        String add = "";
                        String zipCode = "";
                        String city = "";
                        String state = "";
                        if (addresses != null && addresses.size() > 0) {

                            add = addresses.get(0).getAddressLine(0) + "," + addresses.get(0).getSubAdminArea();

                            CustomLog.i("System out", "place address zip: " + addresses.get(0).getPostalCode());
                            CountryCodegoogle = addresses.get(0).getCountryName();

//                            CustomLog.i("System out", "place address zip: " + CountryCode);
                            CustomLog.i("System out", "place address country: " + addresses.get(0).getCountryName());


                            for (int i = 0; i < countryListPojos.size(); i++) {
                                if (CountryCodegoogle.equalsIgnoreCase(new String(Base64.decode(countryListPojos.get(i).getCountryName().trim().getBytes(), Base64.DEFAULT)))) {
                                    countryId = countryListPojos.get(i).getCountryID();
                                    countryFlagImage = countryListPojos.get(i).getCountryFlagImage();

                                }

                            }

//                            for (int i = 0; i < addresses.size(); i++) {
//                                address = addresses.get(i);
//                                if (address.getPostalCode() != null) {
//                                    zipCode = address.getPostalCode();
//                                    city = address.getLocality();
//
//
//
//
//                                }
//                            }
                        }
                    } else {
                        addressEditTextSignUpSubmit.setText("");
                    }
                    break;
                case PlaceAutocomplete.RESULT_ERROR:
                    Status status = PlaceAutocomplete.getStatus(this, data);
                    CustomLog.e("System out", "Error: Status = " + status.toString());
                    break;
                case RESULT_CANCELED:
                    // Indicates that the activity closed before a selection was made. For example if
                    // the user pressed the back button.
                    break;
            }
        }
    }

    public void updateCountrySelection(List<CountryData> countryListPojosupdated, int position) {
        countryCodeTextViewSignUp.setText(countryListPojosupdated.get(position).getCountryDialCode());
        Picasso.with(SignUpSubmitActivity.this).load(Constants.FLAG_URL + countryListPojosupdated.get(position).getCountryFlagImage()).into(countryCodeImageViewSignUp);
        countryId = countryListPojosupdated.get(position).getCountryID();
        stateId = 0;
        cityId = 0;
        countryListJsonCall();
    }

    private void stateListJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", Constants.language_id);
            jsonObject.put("countryID", countryId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        Constants.showProgress(SignUpSubmitActivity.this);
        CustomLog.d("System out", "state json " + json);
        Call<List<StateListPojo>> call = RestClient.get().stateListJsonCall(json);

        call.enqueue(new Callback<List<StateListPojo>>() {
            @Override
            public void onResponse(Call<List<StateListPojo>> call, Response<List<StateListPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    stateListPojos.clear();
                    ArrayList<String> stateList = new ArrayList<>();
                    stateList.clear();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUpSubmitActivity.this, android.R.layout.simple_spinner_item, stateList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    stateSpinnerSignUpSubmit.setAdapter(adapter);
                    if (response.body().get(0).getStatus() == true) {
                        stateListPojos.addAll(response.body().get(0).getData());
                        for (int i = 0; i < stateListPojos.size(); i++) {
                            stateList.add(new String(Base64.decode(stateListPojos.get(i).getStateName().trim().getBytes(), Base64.DEFAULT)));
//                            if (CountryCodegoogle.equalsIgnoreCase(new String(Base64.decode(stateListPojos.get(i).getCountryName().trim().getBytes(), Base64.DEFAULT)))) {
//                                countryId = stateListPojos.get(i).getCountryID();
//                                break;
//                            }


                        }
                        adapter = new ArrayAdapter<>(SignUpSubmitActivity.this, android.R.layout.simple_spinner_item, stateList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        stateSpinnerSignUpSubmit.setAdapter(adapter);

                        stateSpinnerSignUpSubmit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    stateId = stateListPojos.get(position).getStateID();
                                    cityId = 0;
                                    cityListJsonCall();

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
//                        Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this,"sorry,record not found");
                        adapter.notifyDataSetChanged();
                        stateId = 0;
                        cityListJsonCall();


                    }
                }
            }

            @Override
            public void onFailure(Call<List<StateListPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    private void cityListJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {

            if (stateId == 0 && countryId==(0)) {
                jsonObject.put("languageID", Constants.language_id);
            } else if (stateId == 0) {
                jsonObject.put("languageID", Constants.language_id);
                jsonObject.put("countryID", countryId);
            } else {
                jsonObject.put("languageID", Constants.language_id);
                jsonObject.put("countryID", countryId);
                jsonObject.put("stateID", stateId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        Constants.showProgress(SignUpSubmitActivity.this);
        CustomLog.d("System out", "city json " + json);
        Call<List<CityListPojo>> call = RestClient.get().cityListJsonCall(json);

        call.enqueue(new Callback<List<CityListPojo>>() {
            @Override
            public void onResponse(Call<List<CityListPojo>> call, Response<List<CityListPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    cityListPojos.clear();
                    ArrayList<String> cityList = new ArrayList<>();
                    cityList.clear();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUpSubmitActivity.this, android.R.layout.simple_spinner_item, cityList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    citySpinnerSignUpSubmit.setAdapter(adapter);

                    if (response.body().get(0).getStatus() == true) {
                        cityListPojos.addAll(response.body().get(0).getData());

                        for (int i = 0; i < cityListPojos.size(); i++) {
                            cityList.add(new String(Base64.decode(cityListPojos.get(i).getCityName().trim().getBytes(), Base64.DEFAULT)));
//                            if (CountryCodegoogle.equalsIgnoreCase(new String(Base64.decode(cityListPojos.get(i).getCountryName().trim().getBytes(), Base64.DEFAULT)))) {
//                                countryId = cityListPojos.get(i).getCountryID();
//                                break;
//                            }


                        }
                        adapter = new ArrayAdapter<>(SignUpSubmitActivity.this, android.R.layout.simple_spinner_item, cityList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        citySpinnerSignUpSubmit.setAdapter(adapter);

                        citySpinnerSignUpSubmit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    cityId = cityListPojos.get(position).getCityID();

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
//                        Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this,"sorry,record not found");
                        adapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<List<CityListPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }
}

