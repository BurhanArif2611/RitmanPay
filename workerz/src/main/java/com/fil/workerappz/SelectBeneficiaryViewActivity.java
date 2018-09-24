package com.fil.workerappz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fil.workerappz.pojo.BeneficiaryInfoListPojo;
import com.fil.workerappz.pojo.BeneficiaryListPojo;
import com.fil.workerappz.pojo.CountryListPojo;
import com.fil.workerappz.pojo.IdTypeListJsonPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.QuickPayDataPojo;
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
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
 * Created by HS on 22-Mar-18.
 * FIL AHM
 */

public class SelectBeneficiaryViewActivity extends ActionBarActivity {

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private final Calendar myCalendar = Calendar.getInstance();
    private final ArrayList<CountryListPojo> countryListPojos = new ArrayList<>();
    private final ArrayList<IdTypeListJsonPojo> idTypePojos = new ArrayList<>();
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
    @BindView(R.id.firstNameEditTextAddBeneficiary)
    MaterialEditText firstNameEditTextAddBeneficiary;
    @BindView(R.id.middleNameEditTextAddBeneficiary)
    MaterialEditText middleNameEditTextAddBeneficiary;
    @BindView(R.id.lastNameEditTextAddBeneficiary)
    MaterialEditText lastNameEditTextAddBeneficiary;
    @BindView(R.id.nickNameEditTextAddBeneficiary)
    MaterialEditText nickNameEditTextAddBeneficiary;
    @BindView(R.id.emailEditTextAddBeneficiary)
    MaterialEditText emailEditTextAddBeneficiary;
    @BindView(R.id.mobileNumberEditTextAddBeneficiary)
    MaterialEditText mobileNumberEditTextAddBeneficiary;
    @BindView(R.id.dateOfBirthEditTextAddBeneficiary)
    MaterialEditText dateOfBirthEditTextAddBeneficiary;
    @BindView(R.id.dateOfBirthTextViewAddBeneficiary)
    TextView dateOfBirthTextViewAddBeneficiary;
    @BindView(R.id.addressEditTextAddBeneficiary)
    MaterialEditText addressEditTextAddBeneficiary;
    @BindView(R.id.addressTextViewAddBeneficiary)
    TextView addressTextViewAddBeneficiary;
    @BindView(R.id.landmarkEditTextAddBeneficiary)
    MaterialEditText landmarkEditTextAddBeneficiary;
    @BindView(R.id.zipcodeEditTextAddBeneficiary)
    MaterialEditText zipcodeEditTextAddBeneficiary;
    @BindView(R.id.nationalitySpinnerAddBeneficiary)
    MaterialSpinner nationalitySpinnerAddBeneficiary;
    @BindView(R.id.idTypeSpinnerAddBeneficiary)
    MaterialSpinner idTypeSpinnerAddBeneficiary;
    @BindView(R.id.idNumberEditTextAddBeneficiary)
    MaterialEditText idNumberEditTextAddBeneficiary;
    @BindView(R.id.nextAddBeneficiaryTextView)
    TextView nextAddBeneficiaryTextView;
    @BindView(R.id.addBeneficiaryActivityLinearLayout)
    LinearLayout addBeneficiaryActivityLinearLayout;
    private String countryCode;
    private String countryshortcode;
    private String activitytype;
    private int countryId;
    private String idtypeId;
    private String IDtype_Description;
    private String dateofbirth;
    private String nationality;
    private String idType;
    private String countryFullName;
    private BeneficiaryListPojo.Data bankbenefiardata;
    private QuickPayDataPojo quickPayData = new QuickPayDataPojo();
    private String countryflagimage;
    private Intent mIntent;
    private String beneficiarnumber;
    private String countryCurrency;
    private String countryflagimagesender = "";
    private Calendar myCalendar1 = Calendar.getInstance();
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String firstname, lastname, mobilenumber, validmobilenumber, nationalitymsg, idtypemsg, email, validemail, address, dateofbirthmsg, idnumbermsg, valididnumbermsg;
    private String nickname;
    private String nointernetmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_beneficiary);
        ButterKnife.bind(this);
        activitytype = getIntent().getExtras().getString("come_from");
        menuImageViewHeader2.setImageResource(R.drawable.back_btn);

        filterImageViewHeader2.setVisibility(View.INVISIBLE);
        try {
            sessionManager = new SessionManager(SelectBeneficiaryViewActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {

                firstNameEditTextAddBeneficiary.setHint(datumLable_languages.getFirstName());
                firstNameEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getFirstName());
                lastNameEditTextAddBeneficiary.setHint(datumLable_languages.getLastName());
                lastNameEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getLastName());
                mobileNumberEditTextAddBeneficiary.setHint(datumLable_languages.getMobileNumber());
                mobileNumberEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getMobileNumber());
                middleNameEditTextAddBeneficiary.setHint(datumLable_languages.getMiddleName());
                middleNameEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getMiddleName());
                lastNameEditTextAddBeneficiary.setHint(datumLable_languages.getLastName());
                lastNameEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getLastName());
                nickNameEditTextAddBeneficiary.setHint(datumLable_languages.getNickName());
                nickNameEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getNickName());
                emailEditTextAddBeneficiary.setHint(datumLable_languages.getEmail());
                emailEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getEmail());
                dateOfBirthEditTextAddBeneficiary.setHint(datumLable_languages.getDateOfBirth());
                dateOfBirthEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getDateOfBirth());

                addressEditTextAddBeneficiary.setHint(datumLable_languages.getAddress());
                addressEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getAddress());
                landmarkEditTextAddBeneficiary.setHint(datumLable_languages.getLandmark());
                landmarkEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getLandmark());
                zipcodeEditTextAddBeneficiary.setHint(datumLable_languages.getZipCode());
                zipcodeEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getZipCode());
                idTypeSpinnerAddBeneficiary.setHint(datumLable_languages.getIdType());
                idTypeSpinnerAddBeneficiary.setFloatingLabelText(datumLable_languages.getIdType());
                idNumberEditTextAddBeneficiary.setHint(datumLable_languages.getIdNumber());
                idNumberEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getIdNumber());
                nextAddBeneficiaryTextView.setText(datumLable_languages.getNext());
                titleTextViewViewHeader2.setText(datumLable_languages.getBeneficiaryInfo());
                nationalitySpinnerAddBeneficiary.setHint(datumLable_languages.getNationality());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();

            } else {
                titleTextViewViewHeader2.setText("Beneficiary Info");
                firstNameEditTextAddBeneficiary.setHint(getResources().getString(R.string.first_name));
                lastNameEditTextAddBeneficiary.setHint(getResources().getString(R.string.last_name));
                mobileNumberEditTextAddBeneficiary.setHint(getResources().getString(R.string.mobile_number));
                middleNameEditTextAddBeneficiary.setHint(getResources().getString(R.string.middle_name));
                lastNameEditTextAddBeneficiary.setHint(getResources().getString(R.string.last_name));
                nickNameEditTextAddBeneficiary.setHint(getResources().getString(R.string.nick_name));
                emailEditTextAddBeneficiary.setHint(getResources().getString(R.string.email));
                dateOfBirthEditTextAddBeneficiary.setHint(getResources().getString(R.string.date_of_birth));

                addressEditTextAddBeneficiary.setHint(getResources().getString(R.string.address));
                landmarkEditTextAddBeneficiary.setHint(getResources().getString(R.string.landmark));
                zipcodeEditTextAddBeneficiary.setHint(getResources().getString(R.string.zipcode));
                nationalitySpinnerAddBeneficiary.setHint(getResources().getString(R.string.nationality));
                idTypeSpinnerAddBeneficiary.setHint(getResources().getString(R.string.id_type));
                idNumberEditTextAddBeneficiary.setHint(getResources().getString(R.string.id_number));
                nextAddBeneficiaryTextView.setText(getResources().getString(R.string.next));
                nointernetmsg = getResources().getString(R.string.no_internet);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (datumLable_languages_msg != null) {
            firstname = datumLable_languages_msg.getEnterFirstName();
            lastname = datumLable_languages_msg.getEnterLastName();
            nickname = datumLable_languages_msg.getEnterNickName();
            mobilenumber = datumLable_languages_msg.getEnterMobileNumber();
            validmobilenumber = datumLable_languages_msg.getMobileNumberShouldBeAtLeast9Digits();
            email = datumLable_languages_msg.getEnterEmail();
            validemail = datumLable_languages_msg.getEnterValidEmail();
            address = datumLable_languages_msg.getEnterAddress();
            dateofbirthmsg = datumLable_languages_msg.getSelectDateOfBirth();
            nationalitymsg = datumLable_languages_msg.getSelectNationality();
            idtypemsg = datumLable_languages_msg.getSelectIdType();
            idnumbermsg = datumLable_languages_msg.getEnterIdNumber();
            valididnumbermsg = datumLable_languages_msg.getEnterValidIdNumber();


        } else {

            firstname = getResources().getString(R.string.Please_Enter_First_Name);
            lastname = getResources().getString(R.string.Please_Enter_LAST_Name);
            nickname = getResources().getString(R.string.Please_Enter_Nick_Name);
            mobilenumber = getResources().getString(R.string.Please_Enter_Mobile_number);
            validmobilenumber = getResources().getString(R.string.Mobile_number_9_digits);
            email = getResources().getString(R.string.Please_Enter_email);
            validemail = getResources().getString(R.string.Please_Enter_vaild_email);
            address = getResources().getString(R.string.Please_Enter_address);
            dateofbirthmsg = getResources().getString(R.string.Please_select_Dob);
            nationalitymsg = getResources().getString(R.string.Please_select_nationality);
            idtypemsg = getResources().getString(R.string.Please_select_idType);
            idnumbermsg = getResources().getString(R.string.Please_Enter_id_number);
            valididnumbermsg = getResources().getString(R.string.Please_Enter_valid_id_number);


        }

        if (activitytype.equalsIgnoreCase("bank")) {
            bankbenefiardata = (BeneficiaryListPojo.Data) getIntent().getSerializableExtra("beneficiary_object");
            beneficiarnumber = bankbenefiardata.getBenificaryBeneficiaryNo();
            firstNameEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryFirstName());
            middleNameEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryMiddleName());
            lastNameEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryLastName());
            nickNameEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryNickName());
            addressEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryAddress());
            landmarkEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryLandMark());
            zipcodeEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryZipCode());
            emailEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryEmailID());
            mobileNumberEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryTelephone());
            dateOfBirthEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryDateOfBirth());
            idNumberEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryIDNumber());
            nationality = bankbenefiardata.getBenificaryNationality();
            idType = bankbenefiardata.getBenificaryIDType();
        } else if (activitytype.equalsIgnoreCase("bankquickpay")) {
            quickPayData = (QuickPayDataPojo) getIntent().getSerializableExtra("beneficiary_object");
            beneficiarnumber = quickPayData.getBenificaryBeneficiaryNo();
            firstNameEditTextAddBeneficiary.setText(quickPayData.getBenificaryFirstName());
            middleNameEditTextAddBeneficiary.setText(quickPayData.getBenificaryMiddleName());
            lastNameEditTextAddBeneficiary.setText(quickPayData.getBenificaryLastName());
            nickNameEditTextAddBeneficiary.setText(quickPayData.getBenificaryNickName());
            addressEditTextAddBeneficiary.setText(quickPayData.getBenificaryAddress());
            landmarkEditTextAddBeneficiary.setText(quickPayData.getBenificaryLandMark());
            zipcodeEditTextAddBeneficiary.setText(quickPayData.getBenificaryZipCode());
            emailEditTextAddBeneficiary.setText(quickPayData.getBenificaryEmailID());
            mobileNumberEditTextAddBeneficiary.setText(quickPayData.getBenificaryTelephone());
            dateOfBirthEditTextAddBeneficiary.setText(quickPayData.getBenificaryDateOfBirth());
            idNumberEditTextAddBeneficiary.setText(quickPayData.getBenificaryIDNumber());
            nationality = quickPayData.getBenificaryNationality();
            idType = quickPayData.getBenificaryIDType();
        } else if (activitytype.equalsIgnoreCase("cash")) {
            bankbenefiardata = (BeneficiaryListPojo.Data) getIntent().getSerializableExtra("beneficiary_object");
            firstNameEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryFirstName());
            beneficiarnumber = bankbenefiardata.getBenificaryBeneficiaryNo();
            middleNameEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryMiddleName());
            lastNameEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryLastName());
            nickNameEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryNickName());
            addressEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryAddress());
            landmarkEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryLandMark());
            zipcodeEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryZipCode());
            emailEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryEmailID());
            mobileNumberEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryTelephone());
            dateOfBirthEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryDateOfBirth());
            idNumberEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryIDNumber());
            nationality = bankbenefiardata.getBenificaryNationality();
            idType = bankbenefiardata.getBenificaryIDType();
        } else if (activitytype.equalsIgnoreCase("cashquickpay")) {
            quickPayData = (QuickPayDataPojo) getIntent().getSerializableExtra("beneficiary_object");
            firstNameEditTextAddBeneficiary.setText(quickPayData.getBenificaryFirstName());
            beneficiarnumber = quickPayData.getBenificaryBeneficiaryNo();
            middleNameEditTextAddBeneficiary.setText(quickPayData.getBenificaryMiddleName());
            lastNameEditTextAddBeneficiary.setText(quickPayData.getBenificaryLastName());
            nickNameEditTextAddBeneficiary.setText(quickPayData.getBenificaryNickName());
            addressEditTextAddBeneficiary.setText(quickPayData.getBenificaryAddress());
            landmarkEditTextAddBeneficiary.setText(quickPayData.getBenificaryLandMark());
            zipcodeEditTextAddBeneficiary.setText(quickPayData.getBenificaryZipCode());
            emailEditTextAddBeneficiary.setText(quickPayData.getBenificaryEmailID());
            mobileNumberEditTextAddBeneficiary.setText(quickPayData.getBenificaryTelephone());
            dateOfBirthEditTextAddBeneficiary.setText(quickPayData.getBenificaryDateOfBirth());
            idNumberEditTextAddBeneficiary.setText(quickPayData.getBenificaryIDNumber());
            nationality = quickPayData.getBenificaryNationality();
            idType = quickPayData.getBenificaryIDType();

        }

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateOfBirthEditTextAddBeneficiary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DataPickerDialog1();
            }
        });
        if (IsNetworkConnection.checkNetworkConnection(this)) {
            countryListJsonCall();
        } else {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, this, nointernetmsg);
        }


        firstNameEditTextAddBeneficiary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                OnlyCharacter(firstNameEditTextAddBeneficiary);
            }
        });
        lastNameEditTextAddBeneficiary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                OnlyCharacter(lastNameEditTextAddBeneficiary);
            }
        });
        nickNameEditTextAddBeneficiary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                OnlyCharacter(nickNameEditTextAddBeneficiary);
            }
        });
        middleNameEditTextAddBeneficiary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                OnlyCharacter(middleNameEditTextAddBeneficiary);
            }
        });
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

    private void DataPickerDialog1() {
        final Calendar myCalendar = Calendar.getInstance();
        int mYear = myCalendar.get(Calendar.YEAR) - 18;
        int mMonth = myCalendar.get(Calendar.MONTH);
        int mDay = myCalendar.get(Calendar.DAY_OF_MONTH);

        Calendar mincalendar = Calendar.getInstance();
        mincalendar.set(mYear, mMonth, mDay);

        DatePickerDialog dpd = new DatePickerDialog(SelectBeneficiaryViewActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d("year", year + "");
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Calendar minAdultAge = new GregorianCalendar();
                minAdultAge.add(Calendar.YEAR, -18);
                if (minAdultAge.before(myCalendar)) {
                    Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, "Please Enter Valid Date");
                } else {
                    myCalendar1 = myCalendar;
                    updateLabel();
                }
            }
        }, mYear, mMonth, mDay);

        dpd.getDatePicker().setMaxDate(mincalendar.getTimeInMillis());
        dpd.show();

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateOfBirthEditTextAddBeneficiary.setText(sdf.format(myCalendar1.getTime()));

        dateofbirth = Constants.formatDate(dateOfBirthEditTextAddBeneficiary.getText().toString(), "dd/MM/yyyy", "dd MM yyyy");

    }


    @OnClick({R.id.menuImageViewHeader2, R.id.nextAddBeneficiaryTextView, R.id.addressTextViewAddBeneficiary, R.id.dateOfBirthEditTextAddBeneficiary, R.id.appImageViewHeader2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
                finish();
                break;
            case R.id.appImageViewHeader2:
                mIntent = new Intent(SelectBeneficiaryViewActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
            case R.id.dateOfBirthEditTextAddBeneficiary:
                InputMethodManager inputMethodManager = (InputMethodManager) SelectBeneficiaryViewActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(SelectBeneficiaryViewActivity.this.getCurrentFocus().getWindowToken(), 0);
                break;
            case R.id.addressTextViewAddBeneficiary:
                openAutocompleteActivity();
                break;
            case R.id.nextAddBeneficiaryTextView:
                Constants.hideKeyboard(SelectBeneficiaryViewActivity.this);
                if (checkValidation() == true) {
                    SessionManager sessionManager = new SessionManager(SelectBeneficiaryViewActivity.this);
                    sessionManager.setuserflagimage(countryflagimagesender);
                    BeneficiaryInfoListPojo beneficiaryInfoListPojo = new BeneficiaryInfoListPojo();
                    beneficiaryInfoListPojo.setFirstName(firstNameEditTextAddBeneficiary.getText().toString());
                    beneficiaryInfoListPojo.setMiddleName(middleNameEditTextAddBeneficiary.getText().toString());
                    beneficiaryInfoListPojo.setLastName(lastNameEditTextAddBeneficiary.getText().toString());
                    beneficiaryInfoListPojo.setNickName(nickNameEditTextAddBeneficiary.getText().toString());
                    beneficiaryInfoListPojo.setAddress(addressEditTextAddBeneficiary.getText().toString());
                    beneficiaryInfoListPojo.setLandMark(landmarkEditTextAddBeneficiary.getText().toString());
                    beneficiaryInfoListPojo.setZipCode(zipcodeEditTextAddBeneficiary.getText().toString());
                    beneficiaryInfoListPojo.setEmailID(emailEditTextAddBeneficiary.getText().toString());
                    beneficiaryInfoListPojo.setDateOfBirth(dateofbirth);
                    beneficiaryInfoListPojo.setCountryFlagImage(countryflagimage);
                    beneficiaryInfoListPojo.setTelephone(mobileNumberEditTextAddBeneficiary.getText().toString());
                    beneficiaryInfoListPojo.setIDType(idtypeId);
                    beneficiaryInfoListPojo.setIDNumber(idNumberEditTextAddBeneficiary.getText().toString());
                    beneficiaryInfoListPojo.setIDtype_Description(IDtype_Description);
//                    beneficiaryInfoListPojo.setNationality(nationalitySpinnerAddBeneficiary.getSelectedItem().toString());
                    beneficiaryInfoListPojo.setNationality(countryshortcode);
                    beneficiaryInfoListPojo.setPayoutcurrency(countryCurrency);
                    beneficiaryInfoListPojo.setPayoutcountry(countryshortcode);

                    if (activitytype.equalsIgnoreCase("bank")) {
                        Intent intent = new Intent(SelectBeneficiaryViewActivity.this, AddBeneficiaryBankNextActivity.class);
                        beneficiaryInfoListPojo.setActivitytype("BANK");
                        intent.putExtra("countrycode", countryCode);
                        intent.putExtra("beneficiarnumber", beneficiarnumber);
                        intent.putExtra("countryshortcode", countryshortcode);
                        intent.putExtra("beneficiarydata", beneficiaryInfoListPojo);
                        intent.putExtra("beneficiaryapidata", bankbenefiardata);
                        startActivity(intent);

                    } else if (activitytype.equalsIgnoreCase("bankquickpay")) {
                        Intent intent = new Intent(SelectBeneficiaryViewActivity.this, AddBeneficiaryBankNextActivity.class);
                        beneficiaryInfoListPojo.setActivitytype("BANK");
                        intent.putExtra("countrycode", countryCode);
                        intent.putExtra("quick_pay", "bankquickpay");
                        intent.putExtra("beneficiarnumber", beneficiarnumber);
                        intent.putExtra("countryshortcode", countryshortcode);
                        intent.putExtra("beneficiarydata", beneficiaryInfoListPojo);
                        intent.putExtra("beneficiaryapidata", quickPayData);
                        startActivity(intent);

                    } else if (activitytype.equalsIgnoreCase("cash")) {
                        Intent intent = new Intent(SelectBeneficiaryViewActivity.this, AddBeneficiaryCashNextActivity.class);
                        beneficiaryInfoListPojo.setActivitytype("CASH");
                        intent.putExtra("countrycode", countryCode);
                        intent.putExtra("countryshortcode", countryshortcode);
                        intent.putExtra("beneficiarnumber", beneficiarnumber);
                        intent.putExtra("countryshortcode", countryshortcode);
                        intent.putExtra("beneficiarydata", beneficiaryInfoListPojo);
                        intent.putExtra("beneficiaryapidata", bankbenefiardata);
                        startActivity(intent);

                    } else if (activitytype.equalsIgnoreCase("cashquickpay")) {
                        Intent intent = new Intent(SelectBeneficiaryViewActivity.this, AddBeneficiaryCashNextActivity.class);
                        beneficiaryInfoListPojo.setActivitytype("CASH");
                        intent.putExtra("countrycode", countryCode);
                        intent.putExtra("quick_pay", "cashquickpay");
                        intent.putExtra("countryshortcode", countryshortcode);
                        intent.putExtra("beneficiarnumber", beneficiarnumber);
                        intent.putExtra("countryshortcode", countryshortcode);
                        intent.putExtra("beneficiarydata", beneficiaryInfoListPojo);
                        intent.putExtra("beneficiaryapidata", quickPayData);
                        startActivity(intent);
                    }
                }

                break;
        }
    }

    private boolean checkValidation() {
        boolean checkFlag = true;
        String name = null;
        name = (String) idTypeSpinnerAddBeneficiary.getSelectedItem();

        if (firstNameEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, firstname);
            checkFlag = false;
        } else if (lastNameEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, lastname);
            checkFlag = false;
        } else if (nickNameEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, nickname);
            checkFlag = false;
        } else if (emailEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, email);
            checkFlag = false;
        } else if (Constants.validateEmail(emailEditTextAddBeneficiary.getText().toString().trim()) == false) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, validemail);
            checkFlag = false;
        } else if (mobileNumberEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, mobilenumber);
            checkFlag = false;
        } else if (mobileNumberEditTextAddBeneficiary.getText().toString().length() < 7) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, validmobilenumber);

            checkFlag = false;
        } else if (dateOfBirthEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, dateofbirthmsg);
            checkFlag = false;
        } else if (addressEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, address);
            checkFlag = false;
        } else if (nationalitySpinnerAddBeneficiary == null && nationalitySpinnerAddBeneficiary.getSelectedItem() == null) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, nationalitymsg);
            checkFlag = false;
        } else if (nationalitySpinnerAddBeneficiary.getSelectedItem() == null) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, nationalitymsg);
            checkFlag = false;
        } else if (idTypeSpinnerAddBeneficiary == null && idTypeSpinnerAddBeneficiary.getSelectedItem() == null) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, idtypemsg);
            checkFlag = false;
        } else if (idTypeSpinnerAddBeneficiary.getSelectedItem() == null) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, idtypemsg);

            checkFlag = false;
        } else if (idNumberEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, idnumbermsg);
            checkFlag = false;
        } else if (idNumberEditTextAddBeneficiary.getText().toString().length() < 10) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, valididnumbermsg);
            checkFlag = false;
        }
        return checkFlag;
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            @SuppressLint("RestrictedApi") Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).zzit(addressEditTextAddBeneficiary.getText().toString()).build(this);
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
                    if (place != null) {
                        addressEditTextAddBeneficiary.setText(place.getName());
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
                        String addr = "";
                        String zipcode = "";
                        String city = "";
                        String state = "";
                        if (addresses != null && addresses.size() > 0) {

                            addr = addresses.get(0).getAddressLine(0) + "," + addresses.get(0).getSubAdminArea();
                            city = addresses.get(0).getLocality();
                            state = addresses.get(0).getAdminArea();

                            for (int i = 0; i < addresses.size(); i++) {
                                address = addresses.get(i);
                                if (address.getPostalCode() != null) {
                                    zipcode = address.getPostalCode();
                                    city = address.getLocality();
                                    CustomLog.i("System out", "Place Addresszip: " + zipcode);
                                    CustomLog.i("System out", "Place Addresszip: " + city);
                                    zipcodeEditTextAddBeneficiary.setText(zipcode);
                                    landmarkEditTextAddBeneficiary.setText(city);
                                    break;
                                }

                            }
                        }

                    } else {
                        addressEditTextAddBeneficiary.setText("");
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

    private void countryListJsonCall() {
        JSONObject jsonObject = new JSONObject();
        Constants.showProgress(SelectBeneficiaryViewActivity.this);
        try {
            jsonObject.put("languageID", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
//        Constants.showProgress(ProfileActivity.this);
        Call<List<CountryListPojo>> call = RestClient.get().countryListJsonCall(json);

        call.enqueue(new Callback<List<CountryListPojo>>() {

            @Override
            public void onResponse(Call<List<CountryListPojo>> call, Response<List<CountryListPojo>> response) {
//                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    countryListPojos.addAll(response.body());
                    if (countryListPojos.get(0).getStatus() == true) {
                        ArrayList<String> countryList = new ArrayList<>();
                        for (int i = 0; i < countryListPojos.get(0).getData().size(); i++) {
                            Constants.closeProgress();
                            countryList.add(new String(Base64.decode(countryListPojos.get(0).getData().get(i).getCountryName().trim().getBytes(), Base64.DEFAULT)));
//                            countryList.add(countryListPojos.get(0).getData().get(i).getCountryShortCode().trim());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SelectBeneficiaryViewActivity.this, android.R.layout.simple_spinner_item, countryList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        nationalitySpinnerAddBeneficiary.setAdapter(adapter);

                        for (int i = 0; i < countryListPojos.get(0).getData().size(); i++) {
                            if (nationality.equalsIgnoreCase(countryListPojos.get(0).getData().get(i).getCountryShortCode())) {
                                countryId = getUserData().getCountryID();
                                countryCode = getUserData().getUserCountryCode();
                                countryshortcode = getUserData().getCountryShortCode();
                                nationalitySpinnerAddBeneficiary.setSelection(i + 1);
                                countryCurrency = getUserData().getCountryCurrencySymbol();

                            } else if (getUserData().getCountryCurrencySymbol().equalsIgnoreCase(countryListPojos.get(0).getData().get(i).getCountryCurrencySymbol())) {
                                countryflagimagesender = countryListPojos.get(0).getData().get(i).getCountryFlagImage();

                            }
                        }

                        nationalitySpinnerAddBeneficiary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    countryCode = countryListPojos.get(0).getData().get(position).getCountryDialCode();
                                    countryId = countryListPojos.get(0).getData().get(position).getCountryID();
                                    countryflagimage = countryListPojos.get(0).getData().get(position).getCountryFlagImage();
                                    countryshortcode = countryListPojos.get(0).getData().get(position).getCountryShortCode();
                                    countryFullName = countryListPojos.get(0).getData().get(position).getCountryName();
                                    countryCurrency = countryListPojos.get(0).getData().get(position).getCountryCurrencySymbol();

                                    for (int i = 0; i < countryListPojos.get(0).getData().size(); i++) {
                                        if (getUserData().getCountryCurrencySymbol().equalsIgnoreCase(countryListPojos.get(0).getData().get(i).getCountryCurrencySymbol())) {
                                            countryflagimagesender = countryListPojos.get(0).getData().get(i).getCountryFlagImage();
                                            break;
                                        }
                                    }
                                    if (nationalitySpinnerAddBeneficiary.getSelectedItem().toString() != null) {
                                        if (IsNetworkConnection.checkNetworkConnection(SelectBeneficiaryViewActivity.this)) {
                                            idTypeJsonCall();
                                        } else {
                                            Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, nointernetmsg);
                                        }
                                    } else {
                                        Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, nationalitymsg);

                                    }
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
//                Constants.closeProgress();
            }
        });
    }

    private void idTypeJsonCall() {
        JSONObject jsonObject = new JSONObject();
        Constants.showProgress(SelectBeneficiaryViewActivity.this);
        try {
            jsonObject.put("countryCode", countryFullName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
//        Constants.showProgress(ProfileActivity.this);
        Call<List<IdTypeListJsonPojo>> call = RestClient.get().getIdTypeJsonCall(json);

        call.enqueue(new Callback<List<IdTypeListJsonPojo>>() {

            @Override
            public void onResponse(Call<List<IdTypeListJsonPojo>> call, Response<List<IdTypeListJsonPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    idTypePojos.addAll(response.body());
                    if (idTypePojos.get(0).getStatus() == true) {
                        ArrayList<String> countryList = new ArrayList<>();
                        for (int i = 0; i < idTypePojos.get(0).getData().size(); i++) {
                            Constants.closeProgress();
                            countryList.add(new String(idTypePojos.get(0).getData().get(i).getIDType().trim()));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SelectBeneficiaryViewActivity.this, android.R.layout.simple_spinner_item, countryList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        idTypeSpinnerAddBeneficiary.setAdapter(adapter);

                        for (int i = 0; i < idTypePojos.get(0).getData().size(); i++) {
                            if (idType.equalsIgnoreCase(idTypePojos.get(0).getData().get(i).getIDType_ID())) {
//                                countryId = getUserData().getCountryID();
//                                countryCode = getUserData().getUserCountryCode();
                                idTypeSpinnerAddBeneficiary.setSelection(i + 1);
                                break;
                            }
                        }

                        idTypeSpinnerAddBeneficiary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    idtypeId = idTypePojos.get(0).getData().get(position).getIDType_ID();
                                    IDtype_Description = idTypePojos.get(0).getData().get(position).getIDType();
//                                    countryId = idTypePojos.get(0).getData().get(position).getCountryID();
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
            public void onFailure(Call<List<IdTypeListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
                t.printStackTrace();
            }
        });
    }
}
