package com.fil.workerappz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.fil.workerappz.fragments.MediaChooseFragmentForProfile;
import com.fil.workerappz.pojo.CountryData;
import com.fil.workerappz.pojo.CountryListPojo;
import com.fil.workerappz.pojo.ImageListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.UserListPojo;
import com.fil.workerappz.retrofit.RestApi;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.ApiClient;
import com.fil.workerappz.utils.CircleTransform;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.orm.SugarRecord;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HS on 24-Feb-18.
 * FIL AHM
 */

public class ProfileActivity extends ActionBarActivity {

    private static final int REQUEST_CODE_AUTOCOMPLETE = 0;
    private static final int TAKE_PICTURE = 1;
    private static final int SELECT_PICTURE = 2;
    @BindView(R.id.textviewgendeprofile)
    TextView textviewgendeprofile;
    private String countryCode;
    private int countryId;
    @BindView(R.id.backImageViewHeader)
    ImageView backImageViewHeader;
    @BindView(R.id.titleTextViewViewHeader)
    TextView titleTextViewViewHeader;
    @BindView(R.id.skipTextViewViewHeader)
    TextView skipTextViewViewHeader;
    @BindView(R.id.profilePictureImageView)
    ImageView profilePictureImageView;
    @BindView(R.id.editProfilePicture)
    TextView editProfilePicture;
    @BindView(R.id.firstNameEditTextProfile)
    MaterialEditText firstNameEditTextProfile;
    @BindView(R.id.lastNameEditTextProfile)
    MaterialEditText lastNameEditTextProfile;
    @BindView(R.id.mobileNumberEditTextProfile)
    MaterialEditText mobileNumberEditTextProfile;
    @BindView(R.id.emailEditTextProfile)
    MaterialEditText emailEditTextProfile;
    @BindView(R.id.maleRadioButtonProfile)
    RadioButton maleRadioButtonProfile;
    @BindView(R.id.femaleRadioButtonProfile)
    RadioButton femaleRadioButtonProfile;
    @BindView(R.id.maleFemaleRadioGroupProfile)
    RadioGroup maleFemaleRadioGroupProfile;
    @BindView(R.id.addressEditTextProfile)
    MaterialEditText addressEditTextProfile;
    @BindView(R.id.countryOfResidenceEditTextProfile)
    MaterialSpinner countryOfResidenceEditTextProfile;
    @BindView(R.id.passportNoEditTextProfile)
    MaterialEditText passportNoEditTextProfile;
    @BindView(R.id.emiratesIdEditTextProfile)
    MaterialEditText emiratesIdEditTextProfile;
    @BindView(R.id.changePinTextViewProfile)
    TextView changePinTextViewProfile;
    @BindView(R.id.mainProfileActivityLinearLayout)
    LinearLayout mainProfileActivityLinearLayout;
    @BindView(R.id.addressTextViewProfile)
    TextView addressTextViewProfile;
    @BindView(R.id.updateProfileTextView)
    TextView updateProfileTextView;

    private Intent mIntent;
    private boolean editable = false;
    private final ArrayList<CountryData> countryListPojos = new ArrayList<>();
    private File file;
    private long timeForImageName;
    private String imgName = "";
    private Uri pictureUri = null;
    private SessionManager sessionManager;
    private UserListPojo.Data userListPojo;
    private String gender = "Male";
    private String countryName = "";
    private String countryFlagImage = "";
    private String CountryCodegoogle = "";
    private File compressedImage;
    private static final int REQUEST_CODE_WRITE_PERMISSIONS = 003;

    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String firstname,lastname,address,selectcountry,passportmsg,emiratesidmsg,nointernetmsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile);
        ButterKnife.bind(this);


        sessionManager = new SessionManager(ProfileActivity.this);
        userListPojo = getUserData();


        editable(false);
        skipTextViewViewHeader.setVisibility(View.VISIBLE);


        try {

            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();
            if (datumLable_languages != null) {
                titleTextViewViewHeader.setText(datumLable_languages.getProfile());
                editProfilePicture.setText(datumLable_languages.getEdit());
                firstNameEditTextProfile.setHint(datumLable_languages.getFirstName());
                lastNameEditTextProfile.setHint(datumLable_languages.getLastName());
                mobileNumberEditTextProfile.setHint(datumLable_languages.getMobileNumber());
                emailEditTextProfile.setHint(datumLable_languages.getEmail());
                addressEditTextProfile.setHint(datumLable_languages.getAddress());
                countryOfResidenceEditTextProfile.setHint(datumLable_languages.getCountryOfResidence());
                passportNoEditTextProfile.setHint(datumLable_languages.getPassportNo());
                emiratesIdEditTextProfile.setHint(datumLable_languages.getEmiratesId());
                countryOfResidenceEditTextProfile.setFloatingLabelText(datumLable_languages.getCountryOfResidence());
                textviewgendeprofile.setText(datumLable_languages.getGender());
                maleRadioButtonProfile.setText(datumLable_languages.getMale());
                femaleRadioButtonProfile.setText(datumLable_languages.getFemale());
                changePinTextViewProfile.setText(datumLable_languages.getChangePIN());
                skipTextViewViewHeader.setText(datumLable_languages.getEdit());
                nointernetmsg=datumLable_languages.getNoInternetConnectionAvailable();

            } else {
                titleTextViewViewHeader.setText(getResources().getString(R.string.profile));
                editProfilePicture.setText(getResources().getString(R.string.edit));
                firstNameEditTextProfile.setHint(getResources().getString(R.string.first_name));
                lastNameEditTextProfile.setHint(getResources().getString(R.string.last_name));
                mobileNumberEditTextProfile.setHint(getResources().getString(R.string.mobile_number));
                emailEditTextProfile.setHint(getResources().getString(R.string.email));
                addressEditTextProfile.setHint(getResources().getString(R.string.address));
                countryOfResidenceEditTextProfile.setHint(getResources().getString(R.string.country_of_residence));
                passportNoEditTextProfile.setHint(getResources().getString(R.string.passport_no));
                emiratesIdEditTextProfile.setHint(getResources().getString(R.string.emirates_id));
                countryOfResidenceEditTextProfile.setFloatingLabelText(getResources().getString(R.string.country_of_residence));
                textviewgendeprofile.setText(getResources().getString(R.string.gender));
                maleRadioButtonProfile.setText(getResources().getString(R.string.male));
                femaleRadioButtonProfile.setText(getResources().getString(R.string.female));
                skipTextViewViewHeader.setText(getResources().getString(R.string.edit));
                changePinTextViewProfile.setText(getResources().getString(R.string.change_pin));
                nointernetmsg=getResources().getString(R.string.no_internet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (datumLable_languages_msg != null) {
            firstname=datumLable_languages_msg.getEnterFirstName();
            lastname=datumLable_languages_msg.getEnterLastName();
            selectcountry=datumLable_languages_msg.getSelectCountryOfResidence();
            passportmsg=datumLable_languages_msg.getEnterPassportNo();
            emiratesidmsg=datumLable_languages_msg.getEnterEmiratesId();
            address=datumLable_languages_msg.getEnterAddress();

        } else {

            firstname=getResources().getString(R.string.Please_Enter_First_Name);
            lastname=getResources().getString(R.string.Please_Enter_LAST_Name);
            selectcountry=getResources().getString(R.string.Please_Select_countryof_residence);
            passportmsg=getResources().getString(R.string.Please_enter_passport_no);
            emiratesidmsg=getResources().getString(R.string.Please_enter_emirates_id);
            address=getResources().getString(R.string.Please_Enter_address);


        }
        setProfileInformation();
        if (SugarRecord.count(CountryData.class) > 0) {
            countryListPojos.addAll(SugarRecord.listAll(CountryData.class));

            ArrayList<String> countryList = new ArrayList<>();
            for (int i = 0; i < countryListPojos.size(); i++) {
                countryList.add(new String(Base64.decode(countryListPojos.get(i).getCountryName().trim().getBytes(), Base64.DEFAULT)));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(ProfileActivity.this, android.R.layout.simple_spinner_item, countryList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            countryOfResidenceEditTextProfile.setAdapter(adapter);

//            for (int i = 0; i < countryListPojos.size(); i++) {
//                if (getUserData().getUserNationality().equalsIgnoreCase(new String(Base64.decode(countryListPojos.get(i).getCountryName().trim().getBytes(),Base64.DEFAULT))))
//                {
//                    countryId = getUserData().getCountryID();
//                    countryCode = getUserData().getUserCountryCode();
//                    countryOfResidenceEditTextProfile.setSelection(i + 1);
//                    countryFlagImage= sessionManager.getuserflagimage();
//                    break;
//                }
//            }
            for (int i = 0; i < countryListPojos.size(); i++) {
                if (getUserData().getUserNationality().equalsIgnoreCase(String.valueOf(countryListPojos.get(i).getCountryID()))) {
                    countryId = getUserData().getCountryID();
                    countryCode = getUserData().getUserCountryCode();
                    countryOfResidenceEditTextProfile.setSelection(i + 1);
                    countryFlagImage = sessionManager.getuserflagimage();
                    break;
                }
            }
            countryOfResidenceEditTextProfile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != -1) {
                        countryCode = countryListPojos.get(position).getCountryDialCode();
//                        countryId = countryListPojos.get(position).getCountryID();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            if (IsNetworkConnection.checkNetworkConnection(this)) {
                countryListJsonCall();
            } else {
                Constants.showMessage(mainProfileActivityLinearLayout, this, nointernetmsg);
            }
        }
        firstNameEditTextProfile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                OnlyCharacter(firstNameEditTextProfile);
            }
        });
        lastNameEditTextProfile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                OnlyCharacter(lastNameEditTextProfile);
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

    private void setProfileInformation() {
        firstNameEditTextProfile.setText(userListPojo.getUserFirstName());
        lastNameEditTextProfile.setText(userListPojo.getUserLastName());
        emailEditTextProfile.setText(userListPojo.getUserEmail());
        mobileNumberEditTextProfile.setText(userListPojo.getUserMobile());
        emiratesIdEditTextProfile.setText(userListPojo.getUserEmiratesID());
        passportNoEditTextProfile.setText(userListPojo.getUserPassportNo());

//        if (userListPojo.getUserGender().equalsIgnoreCase("Male")) {
//            maleRadioButtonProfile.setChecked(true);
//            gender = "Male";
//        } else {
//            femaleRadioButtonProfile.setChecked(true);
//            gender = "Female";
//        }

        if (userListPojo.getUserGender().equalsIgnoreCase(datumLable_languages.getMale())) {
            maleRadioButtonProfile.setChecked(true);
            gender = datumLable_languages.getMale();
        } else {
            femaleRadioButtonProfile.setChecked(true);
            gender = datumLable_languages.getFemale();
        }

        Constants.latitude = userListPojo.getUserLattitude();
        Constants.longitude = userListPojo.getUserLongitutde();

        maleFemaleRadioGroupProfile.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (R.id.maleRadioButtonSignUpSubmit == checkedId) {
//                    gender = "Male";
//                } else {
//                    gender = "Female";
//                }
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                String gender1 = radioButton.getText().toString();
//                if (gender1.equalsIgnoreCase("Male")) {
//                    gender = "Male";
//                } else {
//                    gender = "Female";
//                }
                if (gender1.equalsIgnoreCase(datumLable_languages.getMale())) {
                    gender = datumLable_languages.getMale();
                } else {
                    gender = datumLable_languages.getFemale();
                }
            }
        });

        addressEditTextProfile.setText(userListPojo.getUserAddress());

        Picasso.with(ProfileActivity.this).load(Constants.IMAGE_URL_USER + getUserData().getUserProfilePicture()).placeholder(R.drawable.user_profile_circle).error(R.drawable.user_profile_circle).transform(new CircleTransform()).into(profilePictureImageView);

    }

    @OnClick({R.id.backImageViewHeader, R.id.changePinTextViewProfile, R.id.skipTextViewViewHeader, R.id.profilePictureImageView, R.id.addressTextViewProfile, R.id.updateProfileTextView, R.id.editProfilePicture,R.id.changeLanguageTextViewProfile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImageViewHeader:
                mIntent = new Intent(ProfileActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                finish();
                break;
            case R.id.changePinTextViewProfile:
                mIntent = new Intent(ProfileActivity.this, ChangePinActivity.class);
                startActivity(mIntent);
                break;
            case R.id.changeLanguageTextViewProfile:
                mIntent = new Intent(ProfileActivity.this, ChangeLanguageActivity.class);
                startActivity(mIntent);
                break;
            case R.id.editProfilePicture:
                MediaChooseFragmentForProfile mediaChooseFragmentForProfile1 = new MediaChooseFragmentForProfile();
                mediaChooseFragmentForProfile1.show(getSupportFragmentManager(), "BottomSheet Fragment");
                break;
            case R.id.skipTextViewViewHeader:
                if (editable == false) {
                    editable(true);
                    editable = true;
//
                    if (datumLable_languages.getSave() != null) {
                        skipTextViewViewHeader.setText(datumLable_languages.getSave());
                    } else {
                        skipTextViewViewHeader.setText(getResources().getString(R.string.save));
                    }
                } else {
                    editable(false);
                    editable = false;

                    if (datumLable_languages.getEdit() != null) {
                        skipTextViewViewHeader.setText(datumLable_languages.getEdit());
                    } else {
                        skipTextViewViewHeader.setText(getResources().getString(R.string.edit));
                    }
                }
                if (skipTextViewViewHeader.getText().toString().equalsIgnoreCase(datumLable_languages.getEdit())) {
                    Constants.hideKeyboard(ProfileActivity.this);
                    if (IsNetworkConnection.checkNetworkConnection(ProfileActivity.this)) {
                        if (checkValidation() == true) {
                            profileUpdateJsonCall();
                        }
                    } else {
                        Constants.showMessage(mainProfileActivityLinearLayout, ProfileActivity.this,nointernetmsg);
                    }
                }
                break;
            case R.id.profilePictureImageView:
                if (editable == true) {
                    MediaChooseFragmentForProfile mediaChooseFragmentForProfile = new MediaChooseFragmentForProfile();
                    mediaChooseFragmentForProfile.show(getSupportFragmentManager(), "BottomSheet Fragment");
                }
                break;
            case R.id.addressTextViewProfile:
                openAutocompleteActivity();
                break;
            case R.id.updateProfileTextView:
                Constants.hideKeyboard(ProfileActivity.this);
                if (checkValidation() == true) {
                    profileUpdateJsonCall();
                }
                break;
        }
    }

    private boolean checkValidation() {
        boolean checkFlag = true;
        try {
            countryName = countryOfResidenceEditTextProfile.getSelectedItem().toString();
        } catch (Exception e) {
            countryName = "";
            e.printStackTrace();
        }
        if (firstNameEditTextProfile.getText().toString().length() == 0) {
            Constants.showMessage(mainProfileActivityLinearLayout, ProfileActivity.this, firstname);
            checkFlag = false;
        } else if (lastNameEditTextProfile.getText().toString().length() == 0) {
            Constants.showMessage(mainProfileActivityLinearLayout, ProfileActivity.this, lastname);
            checkFlag = false;
        } else if (countryName.length() == 0) {
            Constants.showMessage(mainProfileActivityLinearLayout, ProfileActivity.this, selectcountry);
            checkFlag = false;
        } else if (addressEditTextProfile.getText().toString().length() == 0) {
            Constants.showMessage(mainProfileActivityLinearLayout, ProfileActivity.this, address);
            checkFlag = false;
        } else if (passportNoEditTextProfile.getText().toString().length() == 0) {
            Constants.showMessage(mainProfileActivityLinearLayout, ProfileActivity.this, passportmsg);
            checkFlag = false;
        } else if (emiratesIdEditTextProfile.getText().toString().length() == 0) {
            Constants.showMessage(mainProfileActivityLinearLayout, ProfileActivity.this, emiratesidmsg);
            checkFlag = false;
        }

//        if (emailEditTextSignUpSubmit.getText().toString().length() != 0) {
//            signUpWith = "Email";
//        } else {
//            signUpWith = "Email";
//        }

        return checkFlag;
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            @SuppressLint("RestrictedApi") Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).zzit(addressEditTextProfile.getText().toString()).build(this);
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

    private void editable(boolean editable) {
        if (editable == true) {
            addressTextViewProfile.setEnabled(false);
            firstNameEditTextProfile.setEnabled(true);
            lastNameEditTextProfile.setEnabled(true);
            maleRadioButtonProfile.setEnabled(true);
            femaleRadioButtonProfile.setEnabled(true);
            emiratesIdEditTextProfile.setEnabled(false);
            passportNoEditTextProfile.setEnabled(false);
            addressEditTextProfile.setEnabled(false);
//            countryOfResidenceEditTextProfile.setEnabled(true);
            countryOfResidenceEditTextProfile.setEnabled(false);
        } else {
            mainProfileActivityLinearLayout.setFocusable(true);
            skipTextViewViewHeader.setFocusable(true);
            skipTextViewViewHeader.setFocusableInTouchMode(true);
            addressTextViewProfile.setEnabled(false);
            firstNameEditTextProfile.setEnabled(false);
            lastNameEditTextProfile.setEnabled(false);
            maleRadioButtonProfile.setEnabled(false);
            femaleRadioButtonProfile.setEnabled(false);
            emiratesIdEditTextProfile.setEnabled(false);
            mobileNumberEditTextProfile.setEnabled(false);
            emailEditTextProfile.setEnabled(false);
            passportNoEditTextProfile.setEnabled(false);
            addressEditTextProfile.setEnabled(false);
            countryOfResidenceEditTextProfile.setEnabled(false);
        }
    }

    public void openGallery() {
        mIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(mIntent, "Select Picture"), SELECT_PICTURE);
    }

    public void startCameraActivity() {

        mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        timeForImageName = System.currentTimeMillis();
        imgName = "img" + timeForImageName + ".jpeg";
        file = new File(getExternalFilesDir(null), imgName);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        } else {
            mIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(ProfileActivity.this, getPackageName() + ".provider", file));
        }


        pictureUri = Uri.fromFile(file);
        startActivityForResult(mIntent, TAKE_PICTURE);
    }

    private boolean addPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    CustomLog.d("System out", "pictureUri " + pictureUri);
                    CustomLog.d("System out", "pictureUri " + getExternalFilesDir(null).getParent());
                    CustomLog.d("System out", "pictureUri " + getExternalFilesDir(null).getAbsolutePath());
                    CustomLog.d("System out", "pictureUri " + getExternalFilesDir(null).getPath());
                    try {
                        compressedImage = new Compressor(this)
                                .setMaxWidth(640)
                                .setMaxHeight(480)
                                .setQuality(75)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                .compressToFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (IsNetworkConnection.checkNetworkConnection(ProfileActivity.this)) {
                        uploadProfilePicture();
                    } else {
                        Constants.showMessage(mainProfileActivityLinearLayout, ProfileActivity.this,nointernetmsg);
                    }
                    break;
                case SELECT_PICTURE:
                    timeForImageName = System.currentTimeMillis();
                    imgName = "img" + timeForImageName + ".jpg";
                    pictureUri = data.getData();

                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(pictureUri, projection, null, null, null);
                    cursor.moveToFirst();
                    CustomLog.d("System out", DatabaseUtils.dumpCursorToString(cursor));
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String picturePath = cursor.getString(columnIndex); // returns null

                    cursor.close();

                    file = new File(picturePath);
                    CustomLog.d("System out", "pictureUri " + pictureUri);


                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(new File(file.getPath()).getAbsolutePath(), options);
                    int imageHeight = options.outHeight;
                    int imageWidth = options.outWidth;

                    CustomLog.d("System out", "width " + imageWidth);
                    CustomLog.d("System out", "height " + imageHeight);

                    if (imageWidth < 400 || imageHeight < 700) {
                        compressedImage = file;
                    } else {
                        try {
                            compressedImage = new Compressor(this)
                                    .setMaxWidth(640)
                                    .setMaxHeight(480)
                                    .setQuality(75)
                                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                    .compressToFile(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (IsNetworkConnection.checkNetworkConnection(ProfileActivity.this)) {
                        uploadProfilePicture();
                    } else {
                        Constants.showMessage(mainProfileActivityLinearLayout, ProfileActivity.this,nointernetmsg);
                    }
                    break;
                case REQUEST_CODE_AUTOCOMPLETE:
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
//                        addressEditTextProfile.setText(place.getAddress());
//                        LatLng mLatLng = place.getLatLng();
//                        Constants.latitude = String.valueOf(mLatLng.latitude);
//                        Constants.longitude = String.valueOf(mLatLng.longitude);
//                    }
                    if (place != null) {
                        addressEditTextProfile.setText(place.getAddress());
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
                        }
                    } else {
                        addressEditTextProfile.setText("");
                    }
                    break;
            }
        }
    }


    private void uploadProfilePicture() {
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
//        File file = new File(String.valueOf(this.file));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("FileField", imgName, RequestBody.create(MediaType.parse("image*//*"), compressedImage));
        CustomLog.i("System out", "image body response" + filePart.toString());

        String json = "[{" +
                "\"api_type\":\"" + Constants.api_type + "\"," +
                "\"api_userid\":\"" + getUserData().getUserID() + "\"," +
                "\"api_devicetoken\":\"" + Constants.device_token + "\"," +
                "\"Version\":\"" + Constants.version + "\"" +
                "}]";

        Call<List<ImageListPojo>> call = apiService.uploadAttachment(filePart, RequestBody.create(MediaType.parse("text/plain"), "user"), RequestBody.create(MediaType.parse("text/plain"), json));

        CustomLog.i("System out", "json " + json);

        Constants.showProgress(ProfileActivity.this);

        call.enqueue(new Callback<List<ImageListPojo>>() {
            @Override
            public void onResponse(Call<List<ImageListPojo>> call, Response<List<ImageListPojo>> response) {
                CustomLog.d("System out", "response code " + response.body());
                if (response.body() != null) {
                    if (response.body().get(0).getStatus() == true) {
                        CustomLog.d("System out", "response true " + response.body().get(0).getInfo());
                        CustomLog.d("System out", "response true " + response.body().get(0).getFileName());

                        if (IsNetworkConnection.checkNetworkConnection(ProfileActivity.this)) {
                            userListPojo.setUserProfilePicture(imgName);
//                            sessionManager.setuserflagimage(countryFlagImage);
                            sessionManager.updateUserData(userListPojo);
                            setProfileInformation();
                            profileUpdateJsonCall();
                        } else {
                            Constants.showMessage(mainProfileActivityLinearLayout, ProfileActivity.this,nointernetmsg);
                        }

                    } else {
                        CustomLog.d("System out", "response false " + response.body().get(0).getInfo());
                    }
                } else {

                }
                Constants.closeProgress();
            }

            @Override
            public void onFailure(Call<List<ImageListPojo>> call, Throwable t) {
                // Log error here since request failed
                CustomLog.e("System out", t.getMessage());
                Constants.closeProgress();
            }
        });
    }

    private void profileUpdateJsonCall() {

        try {
            countryName = countryOfResidenceEditTextProfile.getSelectedItem().toString();
        } catch (Exception e) {
            countryName = "";
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", Constants.language_id);
            jsonObject.put("userFirstName", firstNameEditTextProfile.getText().toString().trim());
            jsonObject.put("userLastName", lastNameEditTextProfile.getText().toString().trim());
            jsonObject.put("userAddress", addressEditTextProfile.getText().toString().trim());
            jsonObject.put("cityID", userListPojo.getCityID());
            jsonObject.put("stateID", userListPojo.getStateID());
            jsonObject.put("countryID", String.valueOf(countryId));
            jsonObject.put("userLattitude", Constants.latitude);
            jsonObject.put("userLongitutde", Constants.longitude);
            jsonObject.put("userNationality", userListPojo.getUserNationality());
            jsonObject.put("userPassportNo", passportNoEditTextProfile.getText().toString().trim());
            jsonObject.put("userEmiratesID", emiratesIdEditTextProfile.getText().toString().trim());
            jsonObject.put("userDeviceType", Constants.device_type);
            jsonObject.put("userDeviceID", Constants.device_token);
            jsonObject.put("userID", getMyUserId());
            jsonObject.put("userProfilePicture", userListPojo.getUserProfilePicture());
            jsonObject.put("userGender", gender);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "profile json " + json);

        Constants.showProgress(ProfileActivity.this);

        Call<List<UserListPojo>> call = RestClient.get().updateUserJsonCall(json);

        call.enqueue(new Callback<List<UserListPojo>>() {

            @Override
            public void onResponse(Call<List<UserListPojo>> call, Response<List<UserListPojo>> response) {

                if (response.body() != null && response.body() instanceof ArrayList) {
                    ArrayList<UserListPojo> userListPojos = new ArrayList<>();
                    userListPojos.addAll(response.body());


                    if (userListPojos.get(0).getStatus() == true) {
                        Constants.closeProgress();
                        SessionManager sessionManager = new SessionManager(ProfileActivity.this);
//                        sessionManager.setuserflagimage(countryFlagImage);
                        sessionManager.setuserflagimage(countryFlagImage);
                        sessionManager.updateUserProfile(new Gson().toJson(userListPojos.get(0).getData().get(0)));
                        Constants.showMessage(mainProfileActivityLinearLayout, ProfileActivity.this, datumLable_languages_msg.getMessage(userListPojos.get(0).getInfo().toString()));
                    } else {
                        Constants.closeProgress();
                        Constants.showMessage(mainProfileActivityLinearLayout, ProfileActivity.this, datumLable_languages_msg.getMessage(userListPojos.get(0).getInfo().toString()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserListPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    private void countryListJsonCall() {
        JSONObject jsonObject = new JSONObject();
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
                    countryListPojos.addAll(response.body().get(0).getData());
                    if (response.body().get(0).getStatus() == true) {
                        ArrayList<String> countryList = new ArrayList<>();
                        for (int i = 0; i < countryListPojos.size(); i++) {
                            countryList.add(new String(Base64.decode(countryListPojos.get(i).getCountryName().trim().getBytes(), Base64.DEFAULT)));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ProfileActivity.this, android.R.layout.simple_spinner_item, countryList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        countryOfResidenceEditTextProfile.setAdapter(adapter);

                        for (int i = 0; i < countryListPojos.size(); i++) {
                            if (getUserData().getCountryID() == countryListPojos.get(i).getCountryID()) {
                                countryId = getUserData().getCountryID();
                                countryCode = getUserData().getUserCountryCode();
                                countryOfResidenceEditTextProfile.setSelection(i + 1);
                                break;
                            }
                        }

                        countryOfResidenceEditTextProfile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    countryCode = countryListPojos.get(position).getCountryDialCode();
                                    countryId = countryListPojos.get(position).getCountryID();
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
}
