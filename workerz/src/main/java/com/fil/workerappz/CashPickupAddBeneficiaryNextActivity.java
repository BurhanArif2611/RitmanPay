package com.fil.workerappz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fil.workerappz.pojo.BeneficiaryInfoListPojo;
import com.fil.workerappz.pojo.CashNetworkListJsonPojo;
import com.fil.workerappz.pojo.CreateBeneficiaryJsonPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.PurposeOfTransferListPojo;
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
 * Created by HS on 19-Mar-18.
 * FIL AHM
 */

public class CashPickupAddBeneficiaryNextActivity extends ActionBarActivity {
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    @BindView(R.id.menuImageViewHeader2)
    ImageView menuImageViewHeader2;
    @BindView(R.id.appImageViewHeader2)
    ImageView appImageViewHeader2;
    @BindView(R.id.titleTextViewViewHeader2)
    TextView titleTextViewViewHeader2;
    @BindView(R.id.appLeftImageViewHeader2)
    ImageView appLeftImageViewHeader2;
    @BindView(R.id.filterImageViewHeader2)
    ImageView filterImageViewHeader2;
    @BindView(R.id.destinationAddressEditTextAddBeneficiaryNext)
    MaterialEditText destinationAddressEditTextAddBeneficiaryNext;
    @BindView(R.id.destinationLandmarkEditTextAddBeneficiary)
    MaterialEditText destinationLandmarkEditTextAddBeneficiary;
    @BindView(R.id.destinationZipCodeEditTextAddBeneficiary)
    MaterialEditText destinationZipCodeEditTextAddBeneficiary;
    @BindView(R.id.agentsSpinnerAddBeneficiary)
    MaterialSpinner agentsSpinnerAddBeneficiary;
    @BindView(R.id.addTextViewAddBeneficiary)
    TextView addTextViewAddBeneficiary;
    @BindView(R.id.purposeOfTransferSpinnerCashAddBeneficiary)
    MaterialSpinner purposeOfTransferSpinnerCashAddBeneficiary;
    @BindView(R.id.addressTextViewAddBeneficiary)
    TextView addressTextViewAddBeneficiary;
    @BindView(R.id.mainAddBeneficiaryCashNextActivityLinearLayout)
    LinearLayout addBeneficiaryCashNextActivityLinearLayout;
    @BindView(R.id.skipTextViewViewHeader2)
    TextView skipTextViewViewHeader2;
    @BindView(R.id.destinationAddressEditTextAddBeneficiaryNext1)
    MaterialEditText destinationAddressEditTextAddBeneficiaryNext1;
    private String countryCode, purposeCode, payoutBranchCode, payoutCurrencyCode, payoutCountry, branchName, address, branchCode, payoutCountryCode, customerNumber;
    private BeneficiaryInfoListPojo beneficiaryInfoListPojo;
    private final ArrayList<PurposeOfTransferListPojo> purposeOfTransferListPojos = new ArrayList<>();
    private final ArrayList<CashNetworkListJsonPojo> cashNetworkListJsonPojos = new ArrayList<>();
    private final ArrayList<CreateBeneficiaryJsonPojo> createBeneficiaryJsonPojos = new ArrayList<>();
    private final ArrayList<CreateBeneficiaryJsonPojo.Data> createBeneficiaryJsonPojosdata = new ArrayList<>();
    private String agentName;
    private Intent mIntent;
    private String countryShortCode;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String purposetransfermsg, agentmsg, zipcodemsg, landmarkmsg, destinationmsg;
    private String nointernetmsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_pickup_addbeneficiary_next);
        ButterKnife.bind(this);
        countryCode = getIntent().getExtras().getString("country_code");
        customerNumber = getIntent().getExtras().getString("customer_number");
        countryShortCode = getIntent().getExtras().getString("country_short_code");
        beneficiaryInfoListPojo = (BeneficiaryInfoListPojo) getIntent().getSerializableExtra("beneficiary_data");

        menuImageViewHeader2.setImageResource(R.drawable.back_btn);

        filterImageViewHeader2.setVisibility(View.INVISIBLE);

        try {
            sessionManager = new SessionManager(CashPickupAddBeneficiaryNextActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {

                destinationAddressEditTextAddBeneficiaryNext.setHint(datumLable_languages.getDestinationAddress());
                destinationAddressEditTextAddBeneficiaryNext.setFloatingLabelText(datumLable_languages.getDestinationAddress());
                destinationLandmarkEditTextAddBeneficiary.setHint(datumLable_languages.getDestinationLandmark());
                destinationLandmarkEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getDestinationLandmark());
                destinationZipCodeEditTextAddBeneficiary.setHint(datumLable_languages.getDestinationZipcode());
                destinationZipCodeEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getDestinationZipcode());
                agentsSpinnerAddBeneficiary.setHint(datumLable_languages.getAvailableAgents());
                agentsSpinnerAddBeneficiary.setFloatingLabelText(datumLable_languages.getSelectAvailableAgent());

                purposeOfTransferSpinnerCashAddBeneficiary.setHint(datumLable_languages.getPurposeOfTransfer());
                purposeOfTransferSpinnerCashAddBeneficiary.setFloatingLabelText(datumLable_languages.getPurposeOfTransfer());

                addTextViewAddBeneficiary.setText(datumLable_languages.getAdd());
                titleTextViewViewHeader2.setText(datumLable_languages.getBeneficiaryInfo());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();
//                nationalitySpinnerAddBeneficiary.setHint(datumLable_languages.get());

            } else {
                titleTextViewViewHeader2.setText("Beneficiary Info");
                destinationAddressEditTextAddBeneficiaryNext.setHint(getResources().getString(R.string.dest_add));
                destinationLandmarkEditTextAddBeneficiary.setHint(getResources().getString(R.string.dest_land));
                destinationZipCodeEditTextAddBeneficiary.setHint(getResources().getString(R.string.dest_zip));
                agentsSpinnerAddBeneficiary.setHint(getResources().getString(R.string.availiable_agents));
                purposeOfTransferSpinnerCashAddBeneficiary.setHint(getResources().getString(R.string.purpose_of_transfer));
                addTextViewAddBeneficiary.setText(getResources().getString(R.string.add));
                nointernetmsg = getResources().getString(R.string.no_internet);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (datumLable_languages_msg != null) {
            purposetransfermsg = datumLable_languages_msg.getSelectPurposeOfTransfer();
            agentmsg = datumLable_languages_msg.getSelectAvailableAgents();
            zipcodemsg = datumLable_languages_msg.getEnterDestinationZipCode();
            landmarkmsg = datumLable_languages_msg.getEnterDestinationLandmark();
            destinationmsg = datumLable_languages_msg.getEnterDestinationAddress();
        } else {
            purposetransfermsg = getResources().getString(R.string.Please_select_purpose_transfer);
            agentmsg = getResources().getString(R.string.Please_select_agents);
            zipcodemsg = getResources().getString(R.string.Please_Enter_zip_code);
            landmarkmsg = getResources().getString(R.string.Please_Enter_landmark);
            destinationmsg = getResources().getString(R.string.Please_Enter_destination);


        }
        if (IsNetworkConnection.checkNetworkConnection(this)) {
            cashAgentNetworkListJsonCall();
            purposeOfTransferJsonCall();
        } else {
            Constants.showMessage(addBeneficiaryCashNextActivityLinearLayout, this, nointernetmsg);
        }
    }

    private void purposeOfTransferJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("countryCode", countryCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";

        Constants.showProgress(CashPickupAddBeneficiaryNextActivity.this);
        Call<List<PurposeOfTransferListPojo>> call = RestClient.get().getPurposeOfTransferJsonCall(json);
        call.enqueue(new Callback<List<PurposeOfTransferListPojo>>() {

            @Override
            public void onResponse(Call<List<PurposeOfTransferListPojo>> call, Response<List<PurposeOfTransferListPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    purposeOfTransferListPojos.addAll(response.body());
                    if (purposeOfTransferListPojos.get(0).getStatus() == true) {
                        ArrayList<String> countryList = new ArrayList<>();
                        for (int i = 0; i < purposeOfTransferListPojos.get(0).getData().size(); i++) {
                            countryList.add(new String(purposeOfTransferListPojos.get(0).getData().get(i).getPurposeOfTranfer().trim()));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(CashPickupAddBeneficiaryNextActivity.this, android.R.layout.simple_spinner_item, countryList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        purposeOfTransferSpinnerCashAddBeneficiary.setAdapter(adapter);
//                        for (int i = 0; i < purposeOfTransferListPojos.get(0).getData().size(); i++) {
//                            if (getUserData().getCountryID() == purposeOfTransferListPojos.get(0).getData().get(i).getCountryID()) {
//                                countryId = getUserData().getCountryID();
//                                countryCode = getUserData().getUserCountryCode();
//                                purposeOfTransferSpinnerCashAddBeneficiary.setSelection(i + 1);
//                                break;
//                            }
//                        }

                        purposeOfTransferSpinnerCashAddBeneficiary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    purposeCode = purposeOfTransferListPojos.get(0).getData().get(position).getPurposeOfTransferID();
//                                    countryId = purposeOfTransferListPojos.get(0).getData().get(position).getCountryID();
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
            public void onFailure(Call<List<PurposeOfTransferListPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    private void cashAgentNetworkListJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("countryCode", beneficiaryInfoListPojo.getNationality());
            jsonObject.put("bankName", "");
            jsonObject.put("branch", "");
//            jsonObject.put("countryCode", "NPL");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "add beneficiary cash agent json " + json);

        Constants.showProgress(CashPickupAddBeneficiaryNextActivity.this);
        Call<List<CashNetworkListJsonPojo>> call = RestClient.get().getCashNetworkJsonCall(json);

        call.enqueue(new Callback<List<CashNetworkListJsonPojo>>() {

            @Override
            public void onResponse(Call<List<CashNetworkListJsonPojo>> call, Response<List<CashNetworkListJsonPojo>> response) {
//                Constants.closeProgress();
                cashNetworkListJsonPojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    Constants.closeProgress();
                    cashNetworkListJsonPojos.addAll(response.body());
                    if (cashNetworkListJsonPojos.get(0).getStatus() == true) {
                        if (cashNetworkListJsonPojos.get(0).getInfo().equalsIgnoreCase("NORECORD")) {

                        } else {
                            ArrayList<String> countryList = new ArrayList<>();
                            for (int i = 0; i < cashNetworkListJsonPojos.get(0).getData().size(); i++) {
                                countryList.add(new String(cashNetworkListJsonPojos.get(0).getData().get(i).getPayOutAgent().trim()));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(CashPickupAddBeneficiaryNextActivity.this, android.R.layout.simple_spinner_item, countryList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            agentsSpinnerAddBeneficiary.setAdapter(adapter);

//                        for (int i = 0; i < purposeOfTransferListPojos.get(0).getData().size(); i++) {
//                            if (getUserData().getCountryID() == purposeOfTransferListPojos.get(0).getData().get(i).getCountryID()) {
//                                countryId = getUserData().getCountryID();
//                                countryCode = getUserData().getUserCountryCode();
//                                agentsSpinnerAddBeneficiary.setSelection(i + 1);
//                                break;
//                            }
//                        }

                            agentsSpinnerAddBeneficiary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != -1) {
                                        payoutBranchCode = cashNetworkListJsonPojos.get(0).getData().get(position).getPayOutBranchCode();
                                        payoutCountry = cashNetworkListJsonPojos.get(0).getData().get(position).getCountry();
                                        payoutCountryCode = cashNetworkListJsonPojos.get(0).getData().get(position).getCountryCode();
                                        branchName = cashNetworkListJsonPojos.get(0).getData().get(position).getBranchName();
                                        address = cashNetworkListJsonPojos.get(0).getData().get(position).getAddress().toString();
                                        branchCode = cashNetworkListJsonPojos.get(0).getData().get(position).getPayOutBranchCode();
                                        payoutCurrencyCode = cashNetworkListJsonPojos.get(0).getData().get(position).getPayOutCurrencyCode();
                                        agentName = cashNetworkListJsonPojos.get(0).getData().get(position).getPayOutAgent();
//                                    countryId = purposeOfTransferListPojos.get(0).getData().get(position).getCountryID();
//                                    if (IsNetworkConnection.checkNetworkConnection(CashPickupAddBeneficiaryNextActivity.this)) {
//
//                                    } else {
//                                        Constants.showMessage(addBeneficiaryCashNextActivityLinearLayout, CashPickupAddBeneficiaryNextActivity.this, getResources().getString(R.string.no_internet));
//                                    }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CashNetworkListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }


    @OnClick({R.id.menuImageViewHeader2, R.id.addTextViewAddBeneficiary, R.id.appImageViewHeader2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
                finish();
                break;
            case R.id.appImageViewHeader2:
                mIntent = new Intent(CashPickupAddBeneficiaryNextActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
//            case R.id.addressTextViewAddBeneficiary:
//                openAutocompleteActivity();
//                break;
            case R.id.addTextViewAddBeneficiary:
                if (destinationAddressEditTextAddBeneficiaryNext.getText().toString().length() == 0) {
                    Constants.showMessage(addBeneficiaryCashNextActivityLinearLayout, CashPickupAddBeneficiaryNextActivity.this, destinationmsg);
                } else if (destinationLandmarkEditTextAddBeneficiary.getText().toString().length() == 0) {
                    Constants.showMessage(addBeneficiaryCashNextActivityLinearLayout, CashPickupAddBeneficiaryNextActivity.this, landmarkmsg);

                } else if (destinationZipCodeEditTextAddBeneficiary.getText().toString().length() == 0) {
                    Constants.showMessage(addBeneficiaryCashNextActivityLinearLayout, CashPickupAddBeneficiaryNextActivity.this, zipcodemsg);
                } else if (agentsSpinnerAddBeneficiary == null && agentsSpinnerAddBeneficiary.getSelectedItem() == null) {
                    Constants.showMessage(addBeneficiaryCashNextActivityLinearLayout, CashPickupAddBeneficiaryNextActivity.this, agentmsg);
                } else if (agentsSpinnerAddBeneficiary.getSelectedItem() == null) {
                    Constants.showMessage(addBeneficiaryCashNextActivityLinearLayout, CashPickupAddBeneficiaryNextActivity.this, agentmsg);

                } else if (purposeOfTransferSpinnerCashAddBeneficiary == null && purposeOfTransferSpinnerCashAddBeneficiary.getSelectedItem() == null) {
                    Constants.showMessage(addBeneficiaryCashNextActivityLinearLayout, CashPickupAddBeneficiaryNextActivity.this, purposetransfermsg);
                } else if (purposeOfTransferSpinnerCashAddBeneficiary.getSelectedItem() == null) {
                    Constants.showMessage(addBeneficiaryCashNextActivityLinearLayout, CashPickupAddBeneficiaryNextActivity.this, purposetransfermsg);

                } else {
//                    Intent intent = new Intent(AddBeneficiaryActivity.this, AddBeneficiaryNextActivity.class);
//                    startActivity(intent);
                    if (IsNetworkConnection.checkNetworkConnection(this)) {
                        addBeneficiaryJsonCall();
                    } else {
                        Constants.showMessage(addBeneficiaryCashNextActivityLinearLayout, this, nointernetmsg);
                    }
                }

                break;
        }

    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            @SuppressLint("RestrictedApi") Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).zzit(destinationAddressEditTextAddBeneficiaryNext.getText().toString()).build(this);
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
                        destinationAddressEditTextAddBeneficiaryNext.setText(place.getName());
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
                                    destinationZipCodeEditTextAddBeneficiary.setText(zipCode);
                                    destinationLandmarkEditTextAddBeneficiary.setText(city);
                                    break;
                                }

                            }
                        }

                    } else {
                        destinationAddressEditTextAddBeneficiaryNext.setText("");
                    }
                    break;
                case PlaceAutocomplete.RESULT_ERROR:
                    Status status = PlaceAutocomplete.getStatus(this, data);
                    CustomLog.e("System out", "error: status = " + status.toString());
                    break;
                case RESULT_CANCELED:
                    // Indicates that the activity closed before a selection was made. For example if
                    // the user pressed the back button.
                    break;
            }
        }
    }

    private void addBeneficiaryJsonCall() {
        JSONObject jsonObject = new JSONObject();
        Constants.showProgress(CashPickupAddBeneficiaryNextActivity.this);
        try {
            jsonObject.put("CustomerNo", String.valueOf(customerNumber));
            jsonObject.put("FirstName", beneficiaryInfoListPojo.getFirstName());
            jsonObject.put("MiddleName", beneficiaryInfoListPojo.getMiddleName());
            jsonObject.put("LastName", beneficiaryInfoListPojo.getLastName());
            jsonObject.put("NickName", beneficiaryInfoListPojo.getNickName());
            jsonObject.put("Address", beneficiaryInfoListPojo.getAddress());
            jsonObject.put("LandMark", beneficiaryInfoListPojo.getLandMark());
            jsonObject.put("ZipCode", beneficiaryInfoListPojo.getZipCode());
            jsonObject.put("EmailID", beneficiaryInfoListPojo.getEmailID());
//            jsonObject.put("DateOfBirth", beneficiaryInfoListPojo.getDateOfBirth());
//            jsonObject.put("DateOfBirth",Constants.formatDate(beneficiaryInfoListPojo.getDateOfBirth(), "dd /MM /yyyy", "MM/dd/yyyy"));
            jsonObject.put("DateOfBirth", Constants.formatDate(beneficiaryInfoListPojo.getDateOfBirth(), "dd MM yyyy", "MM/dd/yyyy"));
            jsonObject.put("Telephone", beneficiaryInfoListPojo.getTelephone());
            jsonObject.put("Nationality", beneficiaryInfoListPojo.getNationality());
            jsonObject.put("IDNumber", beneficiaryInfoListPojo.getIDNumber());
            jsonObject.put("IDType", beneficiaryInfoListPojo.getIDType());
            jsonObject.put("idTypeDescription", beneficiaryInfoListPojo.getIDtype_Description());
            jsonObject.put("PayOutCurrency", payoutCurrencyCode);
            jsonObject.put("PaymentMode", "CASH");
            jsonObject.put("PayOutBranchCode", payoutBranchCode);
            jsonObject.put("BankName", agentName);
//            jsonObject.put("BankCountry", payoutCountry);
            jsonObject.put("BankCountry", countryShortCode);
            jsonObject.put("CustomerRelation", "0");
            jsonObject.put("BranchNameAndAddress", address);
            jsonObject.put("BankCode", branchCode);
            jsonObject.put("BankBranch", branchName);
            jsonObject.put("AccountNumber", "");
            jsonObject.put("PurposeCode", purposeCode);
            jsonObject.put("PayoutCountryCode", payoutCountryCode);
            jsonObject.put("BeneficiaryNo", "0");
            jsonObject.put("userID", getUserData().getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "addbeneficiary json " + json);

        Call<List<CreateBeneficiaryJsonPojo>> call = RestClient.get().createBeneficiaryJsonCall(json);

        call.enqueue(new Callback<List<CreateBeneficiaryJsonPojo>>() {

            @Override
            public void onResponse(Call<List<CreateBeneficiaryJsonPojo>> call, Response<List<CreateBeneficiaryJsonPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    createBeneficiaryJsonPojos.addAll(response.body());
                    if (createBeneficiaryJsonPojos.get(0).getStatus() == true) {

                        if (datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()) != null) {
                            Constants.showMessage(addBeneficiaryCashNextActivityLinearLayout, CashPickupAddBeneficiaryNextActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                        } else {
                            Constants.showMessage(addBeneficiaryCashNextActivityLinearLayout, CashPickupAddBeneficiaryNextActivity.this, response.body().get(0).getInfo().toString());
                        }

                        Constants.cashBenificaryCount++;
                        beneficiaryInfoListPojo.setBeneficiarynumber(createBeneficiaryJsonPojos.get(0).getData().get(0).getBeneficiaryNo());
                        beneficiaryInfoListPojo.setPayoutbranchcode(payoutBranchCode);
                        Intent intent = new Intent(CashPickupAddBeneficiaryNextActivity.this, PinVerificationActivity.class);
                        intent.putExtra("come_from", "selectcashnext");
                        intent.putExtra("flagimage", beneficiaryInfoListPojo.getCountryFlagImage());
                        intent.putExtra("countryshortcode", countryShortCode);
                        intent.putExtra("beneficiary_data", beneficiaryInfoListPojo);
                        startActivity(intent);
                    } else {
                        Constants.showMessage(addBeneficiaryCashNextActivityLinearLayout, CashPickupAddBeneficiaryNextActivity.this, response.body().get(0).getInfo().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CreateBeneficiaryJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }
}
