package com.fil.workerappz;

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
import com.fil.workerappz.pojo.BeneficiaryListPojo;
import com.fil.workerappz.pojo.CashNetworkListJsonPojo;
import com.fil.workerappz.pojo.EditBeneficiaryJsonPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.PurposeOfTransferListPojo;
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
import java.text.ParseException;
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
 * Created by HS on 22-Mar-18.
 * FIL AHM
 */

public class AddBeneficiaryCashNextActivity extends ActionBarActivity {

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
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
    @BindView(R.id.destinationAddressEditTextAddBeneficiaryNext)
    MaterialEditText destinationAddressEditTextAddBeneficiaryNext;
    @BindView(R.id.addressTextViewAddBeneficiary)
    TextView addressTextViewAddBeneficiary;
    @BindView(R.id.destinationLandmarkEditTextAddBeneficiary)
    MaterialEditText destinationLandmarkEditTextAddBeneficiary;
    @BindView(R.id.destinationZipCodeEditTextAddBeneficiary)
    MaterialEditText destinationZipCodeEditTextAddBeneficiary;
    @BindView(R.id.agentsSpinnerAddBeneficiary)
    MaterialSpinner agentsSpinnerAddBeneficiary;
    @BindView(R.id.purposeOfTransferSpinnerCashAddBeneficiary)
    MaterialSpinner purposeOfTransferSpinnerCashAddBeneficiary;
    @BindView(R.id.addTextViewAddBeneficiary)
    TextView addTextViewAddBeneficiary;
    @BindView(R.id.mainAddBeneficiaryCashNextActivityLinearLayout)
    LinearLayout mainAddBeneficiaryCashNextActivityLinearLayout;

    private String Countrycode;
    private String purposecode;
    private BeneficiaryInfoListPojo beneficiaryinfoPojo;
    private BeneficiaryListPojo.Data cashbeneficiarinfopojo;
    private final ArrayList<PurposeOfTransferListPojo> purposeOfTransferListPojos = new ArrayList<>();
    private final ArrayList<CashNetworkListJsonPojo> cashnetworklistListPojos = new ArrayList<>();
    private String countryCode, payoutBranchCode, payoutCurrencyCode, payoutCountry, branchName, address, branchCode, payoutCountryCode, Beneficiarnumber, agentname, CountryShortCode;
    private Intent mIntent;
    private final ArrayList<EditBeneficiaryJsonPojo> editbeneficiaryJsonPojos = new ArrayList<>();
    private QuickPayDataPojo quickPayData = new QuickPayDataPojo();
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
        Countrycode = getIntent().getExtras().getString("countrycode");

//        beneficiaryinfoPojo = (BeneficiaryInfoListPojo) getIntent().getSerializableExtra("beneficiarydata");
//        cashbeneficiarinfopojo = (StaticBeneficiaryInfoPojo) getIntent().getSerializableExtra("beneficiaryapidata");
        if (getIntent().getExtras().getString("quick_pay") != null) {
            quickPayData = (QuickPayDataPojo) getIntent().getSerializableExtra("beneficiaryapidata");
            destinationAddressEditTextAddBeneficiaryNext.setText(quickPayData.getBenificaryDestiantionAddress());
            destinationLandmarkEditTextAddBeneficiary.setText(quickPayData.getBenificaryDestinationLandmark());
            destinationZipCodeEditTextAddBeneficiary.setText(quickPayData.getBenificaryDestinationZipCode());
            agentname = quickPayData.getBenificaryPayOutBranchCode();
            purposecode = quickPayData.getBenificaryPurposeCode();
        } else {
            cashbeneficiarinfopojo = (BeneficiaryListPojo.Data) getIntent().getSerializableExtra("beneficiaryapidata");
            destinationAddressEditTextAddBeneficiaryNext.setText(cashbeneficiarinfopojo.getBenificaryDestiantionAddress());
            destinationLandmarkEditTextAddBeneficiary.setText(cashbeneficiarinfopojo.getBenificaryDestinationLandmark());
            destinationZipCodeEditTextAddBeneficiary.setText(cashbeneficiarinfopojo.getBenificaryDestinationZipCode());
            agentname = cashbeneficiarinfopojo.getBenificaryPayOutBranchCode();
            purposecode = cashbeneficiarinfopojo.getBenificaryPurposeCode();
        }
        beneficiaryinfoPojo = (BeneficiaryInfoListPojo) getIntent().getSerializableExtra("beneficiarydata");

        Beneficiarnumber = getIntent().getExtras().getString("beneficiarnumber");
        CountryShortCode = getIntent().getExtras().getString("countryshortcode");
        menuImageViewHeader2.setImageResource(R.drawable.back_btn);

        filterImageViewHeader2.setVisibility(View.INVISIBLE);


        try {
            sessionManager = new SessionManager(AddBeneficiaryCashNextActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {

                destinationAddressEditTextAddBeneficiaryNext.setHint(datumLable_languages.getDestinationAddress());
                destinationLandmarkEditTextAddBeneficiary.setHint(datumLable_languages.getDestinationLandmark());
                destinationZipCodeEditTextAddBeneficiary.setHint(datumLable_languages.getDestinationZipcode());
                agentsSpinnerAddBeneficiary.setHint(datumLable_languages.getAvailableAgents());
                agentsSpinnerAddBeneficiary.setFloatingLabelText(datumLable_languages.getSelectAvailableAgent());

                purposeOfTransferSpinnerCashAddBeneficiary.setHint(datumLable_languages.getPurposeOfTransfer());

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
        } else {
            Constants.showMessage(mainAddBeneficiaryCashNextActivityLinearLayout, this, nointernetmsg);
        }

    }

    private void purposeOfTransferJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("countryCode", Countrycode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";

        Constants.showProgress(AddBeneficiaryCashNextActivity.this);
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBeneficiaryCashNextActivity.this, android.R.layout.simple_spinner_item, countryList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        purposeOfTransferSpinnerCashAddBeneficiary.setAdapter(adapter);

                        for (int i = 0; i < purposeOfTransferListPojos.get(0).getData().size(); i++) {
                            if (purposecode.equalsIgnoreCase(purposeOfTransferListPojos.get(0).getData().get(i).getPurposeOfTransferID())) {
                                purposeOfTransferSpinnerCashAddBeneficiary.setSelection(i + 1);
                                purposecode = purposeOfTransferListPojos.get(0).getData().get(i).getPurposeOfTransferID();
                                break;
                            }
                        }

                        purposeOfTransferSpinnerCashAddBeneficiary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    purposecode = purposeOfTransferListPojos.get(0).getData().get(position).getPurposeOfTransferID();


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
//            jsonObject.put("countryCode", countryCode);
            jsonObject.put("countryCode", "NPL");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "add beneficiary cash agent json " + json);

        Constants.showProgress(AddBeneficiaryCashNextActivity.this);
        Call<List<CashNetworkListJsonPojo>> call = RestClient.get().getCashNetworkJsonCall(json);

        call.enqueue(new Callback<List<CashNetworkListJsonPojo>>() {

            @Override
            public void onResponse(Call<List<CashNetworkListJsonPojo>> call, Response<List<CashNetworkListJsonPojo>> response) {
                Constants.closeProgress();
                cashnetworklistListPojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    cashnetworklistListPojos.addAll(response.body());
                    if (cashnetworklistListPojos.get(0).getStatus() == true) {
                        ArrayList<String> countryList1 = new ArrayList<>();


                        for (int i = 0; i < cashnetworklistListPojos.get(0).getData().size(); i++) {
                            countryList1.add(cashnetworklistListPojos.get(0).getData().get(i).getPayOutAgent().trim());
                        }

                        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(AddBeneficiaryCashNextActivity.this, android.R.layout.simple_spinner_dropdown_item, countryList1);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        agentsSpinnerAddBeneficiary.setAdapter(adapter1);


                        for (int i = 0; i < cashnetworklistListPojos.get(0).getData().size(); i++) {
                            if (agentname.equalsIgnoreCase(cashnetworklistListPojos.get(0).getData().get(i).getPayOutBranchCode())) {
//                                countryId = getUserData().getCountryID();
//                                countryCode = getUserData().getUserCountryCode();
                                agentsSpinnerAddBeneficiary.setSelection(i + 1);
                                payoutBranchCode = cashnetworklistListPojos.get(0).getData().get(i).getPayOutBranchCode();
                                payoutCountry = cashnetworklistListPojos.get(0).getData().get(i).getCountry();
                                payoutCountryCode = cashnetworklistListPojos.get(0).getData().get(i).getCountryCode();
                                branchName = cashnetworklistListPojos.get(0).getData().get(i).getBranchName();
                                address = cashnetworklistListPojos.get(0).getData().get(i).getAddress().toString();
                                branchCode = cashnetworklistListPojos.get(0).getData().get(i).getPayOutBranchCode();
                                payoutCurrencyCode = cashnetworklistListPojos.get(0).getData().get(i).getPayOutCurrencyCode();
                                agentname = cashnetworklistListPojos.get(0).getData().get(i).getPayOutAgent();
                                break;
                            }
                        }
                        agentsSpinnerAddBeneficiary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    payoutBranchCode = cashnetworklistListPojos.get(0).getData().get(position).getPayOutBranchCode();
                                    payoutCountry = cashnetworklistListPojos.get(0).getData().get(position).getCountry();
                                    payoutCountryCode = cashnetworklistListPojos.get(0).getData().get(position).getCountryCode();
                                    branchName = cashnetworklistListPojos.get(0).getData().get(position).getBranchName();
                                    address = cashnetworklistListPojos.get(0).getData().get(position).getAddress().toString();
                                    branchCode = cashnetworklistListPojos.get(0).getData().get(position).getPayOutBranchCode();
                                    payoutCurrencyCode = cashnetworklistListPojos.get(0).getData().get(position).getPayOutCurrencyCode();
                                    agentname = cashnetworklistListPojos.get(0).getData().get(position).getPayOutAgent();
//                                    countryId = purposeOfTransferListPojos.get(0).getData().get(position).getCountryID();
                                    if (IsNetworkConnection.checkNetworkConnection(AddBeneficiaryCashNextActivity.this)) {
                                        purposeOfTransferJsonCall();
                                    } else {
                                        Constants.showMessage(mainAddBeneficiaryCashNextActivityLinearLayout, AddBeneficiaryCashNextActivity.this, nointernetmsg);
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
            public void onFailure(Call<List<CashNetworkListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    @OnClick({R.id.menuImageViewHeader2, R.id.addressTextViewAddBeneficiary, R.id.addTextViewAddBeneficiary, R.id.appImageViewHeader2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
                finish();
                break;
            case R.id.addressTextViewAddBeneficiary:
                openAutocompleteActivity();
                break;
            case R.id.appImageViewHeader2:
                mIntent = new Intent(AddBeneficiaryCashNextActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
            case R.id.addTextViewAddBeneficiary:
                if (destinationAddressEditTextAddBeneficiaryNext.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryCashNextActivityLinearLayout, AddBeneficiaryCashNextActivity.this, destinationmsg);
                } else if (destinationLandmarkEditTextAddBeneficiary.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryCashNextActivityLinearLayout, AddBeneficiaryCashNextActivity.this, landmarkmsg);

                } else if (destinationZipCodeEditTextAddBeneficiary.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryCashNextActivityLinearLayout, AddBeneficiaryCashNextActivity.this, zipcodemsg);
                } else if (agentsSpinnerAddBeneficiary == null && agentsSpinnerAddBeneficiary.getSelectedItem() == null) {
                    Constants.showMessage(mainAddBeneficiaryCashNextActivityLinearLayout, AddBeneficiaryCashNextActivity.this, agentmsg);
                } else if (agentsSpinnerAddBeneficiary.getSelectedItem() == null) {
                    Constants.showMessage(mainAddBeneficiaryCashNextActivityLinearLayout, AddBeneficiaryCashNextActivity.this, agentmsg);

                } else if (purposeOfTransferSpinnerCashAddBeneficiary == null && purposeOfTransferSpinnerCashAddBeneficiary.getSelectedItem() == null) {
                    Constants.showMessage(mainAddBeneficiaryCashNextActivityLinearLayout, AddBeneficiaryCashNextActivity.this, purposetransfermsg);
                } else if (purposeOfTransferSpinnerCashAddBeneficiary.getSelectedItem() == null) {
                    Constants.showMessage(mainAddBeneficiaryCashNextActivityLinearLayout, AddBeneficiaryCashNextActivity.this, purposetransfermsg);

                } else {
//                    Intent intent = new Intent(AddBeneficiaryActivity.this, AddBeneficiaryNextActivity.class);
//                    startActivity(intent);
                    if (IsNetworkConnection.checkNetworkConnection(this)) {
                        editBeneficiaryJsonCall();
                    } else {
                        Constants.showMessage(mainAddBeneficiaryCashNextActivityLinearLayout, this, nointernetmsg);
                    }
                }

                break;
        }
    }

    private void editBeneficiaryJsonCall() {
        JSONObject jsonObject = new JSONObject();
        Constants.showProgress(AddBeneficiaryCashNextActivity.this);
        try {
            jsonObject.put("CustomerNo", String.valueOf(getUserData().getUserCustomerNo()));
            jsonObject.put("FirstName", beneficiaryinfoPojo.getFirstName());
            jsonObject.put("MiddleName", beneficiaryinfoPojo.getMiddleName());
            jsonObject.put("LastName", beneficiaryinfoPojo.getLastName());
            jsonObject.put("NickName", beneficiaryinfoPojo.getNickName());
            jsonObject.put("Address", beneficiaryinfoPojo.getAddress());
            jsonObject.put("LandMark", beneficiaryinfoPojo.getLandMark());
            jsonObject.put("ZipCode", beneficiaryinfoPojo.getZipCode());
            jsonObject.put("EmailID", beneficiaryinfoPojo.getEmailID());
//            jsonObject.put("DateOfBirth", beneficiaryinfoPojo.getDateOfBirth());
            if (beneficiaryinfoPojo.getDateOfBirth() == null) {
                if (getIntent().getExtras().getString("quick_pay") != null) {
                    jsonObject.put("DateOfBirth", quickPayData.getBenificaryDateOfBirth());
                } else {
                    jsonObject.put("DateOfBirth", cashbeneficiarinfopojo.getBenificaryDateOfBirth());
                }
            } else {
                jsonObject.put("DateOfBirth", Constants.formatDate(beneficiaryinfoPojo.getDateOfBirth(), "dd MM yyyy", "MM/dd/yyyy"));
            }
            jsonObject.put("Telephone", beneficiaryinfoPojo.getTelephone());
            jsonObject.put("Nationality", beneficiaryinfoPojo.getNationality());
            jsonObject.put("IDNumber", beneficiaryinfoPojo.getIDNumber());
            jsonObject.put("IDType", beneficiaryinfoPojo.getIDType());
            jsonObject.put("IDtype_Description", beneficiaryinfoPojo.getIDtype_Description());
            jsonObject.put("PayOutCurrency", payoutCurrencyCode);
            jsonObject.put("PaymentMode", "CASH");
            jsonObject.put("PayOutBranchCode", payoutBranchCode);
            jsonObject.put("BankName", agentname);
//            jsonObject.put("BankCountry", payoutCountry);
            jsonObject.put("BankCountry", CountryShortCode);
            jsonObject.put("CustomerRelation", "0");
            jsonObject.put("BranchNameAndAddress", address);
            jsonObject.put("BankCode", branchCode);
            jsonObject.put("BankBranch", branchName);
            jsonObject.put("AccountNumber", "");
            jsonObject.put("PurposeCode", purposecode);
            jsonObject.put("PayoutCountryCode", payoutCountryCode);
            jsonObject.put("BeneficiaryNo", Beneficiarnumber);
            jsonObject.put("userID", getUserData().getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "addbeneficiary json " + json);

        Call<List<EditBeneficiaryJsonPojo>> call = RestClient.get().editBeneficiaryJsonCall(json);

        call.enqueue(new Callback<List<EditBeneficiaryJsonPojo>>() {

            @Override
            public void onResponse(Call<List<EditBeneficiaryJsonPojo>> call, Response<List<EditBeneficiaryJsonPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    editbeneficiaryJsonPojos.addAll(response.body());
                    if (editbeneficiaryJsonPojos.get(0).getStatus() == true) {


                        if (datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()) != null) {
                            Constants.showMessage(mainAddBeneficiaryCashNextActivityLinearLayout, AddBeneficiaryCashNextActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                        } else {
                            Constants.showMessage(mainAddBeneficiaryCashNextActivityLinearLayout, AddBeneficiaryCashNextActivity.this, response.body().get(0).getInfo().toString());
                        }
                        beneficiaryinfoPojo.setBeneficiarynumber(editbeneficiaryJsonPojos.get(0).getData().get(0).getBeneficiaryNo());
                        beneficiaryinfoPojo.setPayoutbranchcode(payoutBranchCode);
                        Intent intent = new Intent(AddBeneficiaryCashNextActivity.this, PinVerificationActivity.class);

                        intent.putExtra("come_from", "selectcashnext");
                        intent.putExtra("flagimage", beneficiaryinfoPojo.getCountryFlagImage());
                        intent.putExtra("countryshortcode", CountryShortCode);
                        intent.putExtra("beneficiary_data", beneficiaryinfoPojo);
                        startActivity(intent);
                    } else {
                        Constants.showMessage(mainAddBeneficiaryCashNextActivityLinearLayout, AddBeneficiaryCashNextActivity.this, response.body().get(0).getInfo().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<EditBeneficiaryJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).zzit(destinationAddressEditTextAddBeneficiaryNext.getText().toString()).build(this);
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
                                    destinationZipCodeEditTextAddBeneficiary.setText(zipcode);
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
                    CustomLog.e("System out", "Error: Status = " + status.toString());
                    break;
                case RESULT_CANCELED:
                    // Indicates that the activity closed before a selection was made. For example if

                    // the user pressed the back button.
                    break;
            }
        }
    }
}
