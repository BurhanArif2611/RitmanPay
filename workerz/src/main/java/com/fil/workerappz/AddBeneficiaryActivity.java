package com.fil.workerappz;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fil.workerappz.pojo.BeneficiaryInfoListPojo;
import com.fil.workerappz.pojo.CountryListPojo;
import com.fil.workerappz.pojo.IdTypeListJsonPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.fil.workerappz.utils.SlideHolder;
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
 * Created by HS on 19-Mar-18.
 * FIL AHM
 */

public class AddBeneficiaryActivity extends ActionBarActivity {

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private final Calendar calendar = Calendar.getInstance();
    private final ArrayList<CountryListPojo> countryListPojos = new ArrayList<>();
    private final ArrayList<IdTypeListJsonPojo> idTypePojos = new ArrayList<>();

    @BindView(R.id.appImageViewHeader2)
    ImageView appImageViewHeader2;
    @BindView(R.id.titleTextViewViewHeader2)
    TextView titleTextViewViewHeader2;
    @BindView(R.id.appLeftImageViewHeader2)
    ImageView appLeftImageViewHeader2;
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
    @BindView(R.id.addBeneficiaryActivityLinearLayout)
    LinearLayout addBeneficiaryActivityLinearLayout;
    @BindView(R.id.nextAddBeneficiaryTextView)
    TextView nextAddBeneficiaryTextView;
    @BindView(R.id.addressEditTextAddBeneficiary)
    MaterialEditText addressEditTextAddBeneficiary;
    @BindView(R.id.addressTextViewAddBeneficiary)
    TextView addressTextViewAddBeneficiary;
    @BindView(R.id.dateOfBirthEditTextAddBeneficiary)
    MaterialEditText dateOfBirthEditTextAddBeneficiary;
    @BindView(R.id.dateOfBirthTextViewAddBeneficiary)
    TextView dateOfBirthTextViewAddBeneficiary;
    @BindView(R.id.menuImageViewHeader2)
    ImageView menuImageViewHeader2;
    @BindView(R.id.skipTextViewViewHeader2)
    TextView skipTextViewViewHeader2;
    @BindView(R.id.slideHolderAddBeneficiary)
    SlideHolder slideHolderAddBeneficiary;
    private String countryCode;
    private String countryShortCode, countryCurrency;
    private String countryFlagImage;
    private int countryId;
    private String idType;
    private String idTypeDescription;
    private String comeFrom, Customerno;
    private String dateOfBirth, type;
    private Intent mIntent;
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
        comeFrom = getIntent().getExtras().getString("come_from");
        Customerno = getIntent().getExtras().getString("customernumber");
        type = getIntent().getExtras().getString("activitytype");

        filterImageViewHeader2.setVisibility(View.INVISIBLE);

        if (!type.equalsIgnoreCase("CustomerInfo")) {
            menuImageViewHeader2.setImageResource(R.drawable.back_btn);
        }


        try {
            sessionManager = new SessionManager(AddBeneficiaryActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {

                firstNameEditTextAddBeneficiary.setHint(datumLable_languages.getFirstName());
                lastNameEditTextAddBeneficiary.setHint(datumLable_languages.getLastName());
                mobileNumberEditTextAddBeneficiary.setHint(datumLable_languages.getMobileNumber());
                middleNameEditTextAddBeneficiary.setHint(datumLable_languages.getMiddleName());
                lastNameEditTextAddBeneficiary.setHint(datumLable_languages.getLastName());
                nickNameEditTextAddBeneficiary.setHint(datumLable_languages.getNickName());
                emailEditTextAddBeneficiary.setHint(datumLable_languages.getEmail());
                dateOfBirthEditTextAddBeneficiary.setHint(datumLable_languages.getDateOfBirth());

                addressEditTextAddBeneficiary.setHint(datumLable_languages.getAddress());
                landmarkEditTextAddBeneficiary.setHint(datumLable_languages.getLandmark());
                zipcodeEditTextAddBeneficiary.setHint(datumLable_languages.getZipCode());
                idTypeSpinnerAddBeneficiary.setHint(datumLable_languages.getIdType());
                idNumberEditTextAddBeneficiary.setHint(datumLable_languages.getIdNumber());
                nextAddBeneficiaryTextView.setHint(datumLable_languages.getNext());
                nationalitySpinnerAddBeneficiary.setHint(datumLable_languages.getNationality());
                titleTextViewViewHeader2.setText(datumLable_languages.getBeneficiaryInfo());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();
//                nationalitySpinnerAddBeneficiary.setHint(datumLable_languages.get());

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
                nextAddBeneficiaryTextView.setHint(getResources().getString(R.string.next));
                nationalitySpinnerAddBeneficiary.setHint(getResources().getString(R.string.nationality));
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

    private void dateOfBirthDialog() {
        DatePickerDialog dialog = new DatePickerDialog(AddBeneficiaryActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day_of_month) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, (month));
                calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                updateLabel();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        calendar.add(Calendar.YEAR, 0);
        dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis() - 1000);
        dialog.show();
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateOfBirthEditTextAddBeneficiary.setText(sdf.format(myCalendar1.getTime()));

        dateOfBirth = Constants.formatDate(dateOfBirthEditTextAddBeneficiary.getText().toString(), "dd/MM/yyyy", "dd MM yyyy");

    }


    @OnClick({R.id.menuImageViewHeader2, R.id.nextAddBeneficiaryTextView, R.id.addressTextViewAddBeneficiary, R.id.dateOfBirthEditTextAddBeneficiary, R.id.appImageViewHeader2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
                if (!type.equalsIgnoreCase("CustomerInfo")) {
                    finish();
                } else {
                    slideHolderAddBeneficiary.toggle();
                }
                break;
            case R.id.appImageViewHeader2:
                mIntent = new Intent(AddBeneficiaryActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
            case R.id.dateOfBirthEditTextAddBeneficiary:
                Constants.hideKeyboard(AddBeneficiaryActivity.this);
//                dateOfBirthDialog();
                DataPickerDialog1();
                break;
            case R.id.addressTextViewAddBeneficiary:
                openAutocompleteActivity();
                break;
            case R.id.nextAddBeneficiaryTextView:
                Constants.hideKeyboard(AddBeneficiaryActivity.this);
                if (checkValidation() == true) {
                    SessionManager sessionManager = new SessionManager(AddBeneficiaryActivity.this);
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
                    beneficiaryInfoListPojo.setCountryFlagImage(countryFlagImage);
                    beneficiaryInfoListPojo.setDateOfBirth(dateOfBirth);
                    beneficiaryInfoListPojo.setTelephone(mobileNumberEditTextAddBeneficiary.getText().toString());
                    beneficiaryInfoListPojo.setIDType(idType);
                    beneficiaryInfoListPojo.setIDNumber(idNumberEditTextAddBeneficiary.getText().toString());
                    beneficiaryInfoListPojo.setIDtype_Description(idTypeDescription);
                    beneficiaryInfoListPojo.setNationality(nationalitySpinnerAddBeneficiary.getSelectedItem().toString());
                    if (comeFrom.equalsIgnoreCase("bank")) {
                        Intent intent = new Intent(AddBeneficiaryActivity.this, AddBeneficiaryNextActivity.class);
                        beneficiaryInfoListPojo.setPayoutcurrency(countryCurrency);
                        beneficiaryInfoListPojo.setPayoutcountry(countryShortCode);
                        beneficiaryInfoListPojo.setActivitytype("BANK");
                        intent.putExtra("country_code", countryCode);
                        intent.putExtra("country_short_code", countryShortCode);
                        intent.putExtra("customer_number", Customerno);
                        intent.putExtra("beneficiary_data", beneficiaryInfoListPojo);
                        startActivity(intent);
                    } else if (comeFrom.equalsIgnoreCase("cash")) {
                        Intent intent = new Intent(AddBeneficiaryActivity.this, CashPickupAddBeneficiaryNextActivity.class);
                        beneficiaryInfoListPojo.setPayoutcurrency(countryCurrency);
                        beneficiaryInfoListPojo.setPayoutcountry(countryShortCode);
                        beneficiaryInfoListPojo.setActivitytype("CASH");
                        intent.putExtra("country_code", countryCode);
                        intent.putExtra("customer_number", Customerno);
                        intent.putExtra("country_short_code", countryShortCode);
                        intent.putExtra("beneficiary_data", beneficiaryInfoListPojo);
                        startActivity(intent);
                    }

                }

                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void disableAutoFill() {
        getWindow().getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
    }

    private void DataPickerDialog1() {
        final Calendar myCalendar = Calendar.getInstance();
        int mYear = myCalendar.get(Calendar.YEAR) - 18;
        int mMonth = myCalendar.get(Calendar.MONTH);
        int mDay = myCalendar.get(Calendar.DAY_OF_MONTH);

        Calendar mincalendar = Calendar.getInstance();
        mincalendar.set(mYear, mMonth, mDay);

        DatePickerDialog dpd = new DatePickerDialog(AddBeneficiaryActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d("year", year + "");
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Calendar minAdultAge = new GregorianCalendar();
                minAdultAge.add(Calendar.YEAR, -18);
                if (minAdultAge.before(myCalendar)) {
                    Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, "Please Enter Valid Date");
                } else {
                    myCalendar1 = myCalendar;
                    updateLabel();
                }
            }
        }, mYear, mMonth, mDay);

        dpd.getDatePicker().setMaxDate(mincalendar.getTimeInMillis());
        dpd.show();

    }

    private boolean checkValidation() {
        boolean checkFlag = true;
        String name = null;
        name = (String) idTypeSpinnerAddBeneficiary.getSelectedItem();

        if (firstNameEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, firstname);
            checkFlag = false;
        } else if (lastNameEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, lastname);
            checkFlag = false;
        } else if (nickNameEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, nickname);
            checkFlag = false;
        } else if (emailEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, email);
            checkFlag = false;
        } else if (Constants.validateEmail(emailEditTextAddBeneficiary.getText().toString().trim()) == false) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, validemail);
            checkFlag = false;
        } else if (mobileNumberEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, mobilenumber);
            checkFlag = false;
        } else if (mobileNumberEditTextAddBeneficiary.getText().toString().length() < 9) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, validmobilenumber);

            checkFlag = false;
        } else if (dateOfBirthEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, dateofbirthmsg);
            checkFlag = false;
        } else if (addressEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, address);
            checkFlag = false;
        } else if (nationalitySpinnerAddBeneficiary == null && nationalitySpinnerAddBeneficiary.getSelectedItem() == null) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, nationalitymsg);
            checkFlag = false;
        } else if (nationalitySpinnerAddBeneficiary.getSelectedItem() == null) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, nationalitymsg);
            checkFlag = false;
        } else if (idTypeSpinnerAddBeneficiary == null && idTypeSpinnerAddBeneficiary.getSelectedItem() == null) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, idtypemsg);
            checkFlag = false;
        } else if (idTypeSpinnerAddBeneficiary.getSelectedItem() == null) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, idtypemsg);

            checkFlag = false;
        } else if (idNumberEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, idnumbermsg);
            checkFlag = false;
        } else if (idNumberEditTextAddBeneficiary.getText().toString().length() < 10) {
            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, valididnumbermsg);
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
                    CustomLog.i("System out", "place selected: " + place.getName());
                    CustomLog.i("System out", "place address: " + place.getAddress());

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
                        String add = "";
                        String zipCode = "";
                        String city = "";
                        String state = "";
                        if (addresses != null && addresses.size() > 0) {

                            add = addresses.get(0).getAddressLine(0) + "," + addresses.get(0).getSubAdminArea();
                            city = addresses.get(0).getLocality();
                            state = addresses.get(0).getAdminArea();

                            for (int i = 0; i < addresses.size(); i++) {
                                address = addresses.get(i);
                                if (address.getPostalCode() != null) {
                                    zipCode = address.getPostalCode();
                                    city = address.getLocality();
                                    CustomLog.i("System out", "place address zip: " + zipCode);
                                    CustomLog.i("System out", "place address zip: " + city);
                                    zipcodeEditTextAddBeneficiary.setText(zipCode);
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
        Constants.showProgress(AddBeneficiaryActivity.this);
        try {
            jsonObject.put("languageID", Constants.language_id);
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
//                            countryList.add(new String(Base64.decode(countryListPojos.get(0).getData().get(i).getCountryName().trim().getBytes(), Base64.DEFAULT)));
                            countryList.add(countryListPojos.get(0).getData().get(i).getCountryShortCode().trim());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBeneficiaryActivity.this, android.R.layout.simple_spinner_item, countryList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        nationalitySpinnerAddBeneficiary.setAdapter(adapter);

                        for (int i = 0; i < countryListPojos.get(0).getData().size(); i++) {
                            if (getUserData().getCountryID() == countryListPojos.get(0).getData().get(i).getCountryID()) {
                                countryId = getUserData().getCountryID();
                                countryCode = getUserData().getUserCountryCode();
                                countryShortCode = getUserData().getCountryShortCode();
                                countryCurrency = getUserData().getCountryCurrencySymbol();
                                nationalitySpinnerAddBeneficiary.setSelection(i + 1);

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
                                    countryFlagImage = countryListPojos.get(0).getData().get(position).getCountryFlagImage();
                                    countryCurrency = countryListPojos.get(0).getData().get(position).getCountryCurrencySymbol();
                                    countryShortCode = countryListPojos.get(0).getData().get(position).getCountryShortCode();

                                    for (int i = 0; i < countryListPojos.get(0).getData().size(); i++) {
                                        if (getUserData().getCountryCurrencySymbol().equalsIgnoreCase(countryListPojos.get(0).getData().get(i).getCountryCurrencySymbol())) {
                                            countryflagimagesender = countryListPojos.get(0).getData().get(i).getCountryFlagImage();
                                            break;
                                        }
                                    }
                                    if (nationalitySpinnerAddBeneficiary.getSelectedItem().toString() != null) {
                                        if (IsNetworkConnection.checkNetworkConnection(AddBeneficiaryActivity.this)) {
                                            idTypeJsonCall();
                                        } else {
                                            Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, nointernetmsg);
                                        }
                                    } else {
                                        Constants.showMessage(addBeneficiaryActivityLinearLayout, AddBeneficiaryActivity.this, nationalitymsg);

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
        Constants.showProgress(AddBeneficiaryActivity.this);
        try {
            jsonObject.put("countryCode", countryShortCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
//        Constants.showProgress(ProfileActivity.this);
        CustomLog.d("System out", json);
        Call<List<IdTypeListJsonPojo>> call = RestClient.get().getIdTypeJsonCall(json);

        call.enqueue(new Callback<List<IdTypeListJsonPojo>>() {

            @Override
            public void onResponse(Call<List<IdTypeListJsonPojo>> call, Response<List<IdTypeListJsonPojo>> response) {
//                Constants.closeProgress();
                idTypePojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    idTypePojos.addAll(response.body());
                    if (idTypePojos.get(0).getStatus() == true) {
                        ArrayList<String> countryList = new ArrayList<>();
                        for (int i = 0; i < idTypePojos.get(0).getData().size(); i++) {
                            Constants.closeProgress();
                            countryList.add(new String(idTypePojos.get(0).getData().get(i).getIDType().trim()));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBeneficiaryActivity.this, android.R.layout.simple_spinner_item, countryList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        idTypeSpinnerAddBeneficiary.setAdapter(adapter);

//                        for (int i = 0; i < idTypePojos.get(0).getData().size(); i++) {
//                            if (getUserData().getCountryID() == idTypePojos.get(0).getData().get(i).getIDType_ID()) {
//                                countryId = getUserData().getCountryID();
//                                countryCode = getUserData().getUserCountryCode();
//                                idTypeSpinnerAddBeneficiary.setSelection(i+1);
//                                break;
//                            }
//                        }

                        idTypeSpinnerAddBeneficiary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    idType = idTypePojos.get(0).getData().get(position).getIDType_ID();
                                    idTypeDescription = idTypePojos.get(0).getData().get(position).getIDType();
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