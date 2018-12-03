package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.pojo.BankDataPojo;
import com.fil.workerappz.pojo.BankNetworkListJsonPojo;
import com.fil.workerappz.pojo.BeneficiaryInfoListPojo;
import com.fil.workerappz.pojo.BeneficiaryListPojo;
import com.fil.workerappz.pojo.CreateBeneficiaryJsonPojo;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

public class AddBeneficiaryBankNextActivity extends ActionBarActivity {


    @BindView(R.id.bankReAccountNumberEditTextAddBeneficiaryNext)
    MaterialEditText bankReAccountNumberEditTextAddBeneficiaryNext;
    @BindView(R.id.accountHolderNameEditTextAddBeneficiaryNext)
    MaterialEditText accountHolderNameEditTextAddBeneficiaryNext;
    @BindView(R.id.ifscCodeEditTextAddBeneficiaryNext)
    MaterialEditText ifscCodeEditTextAddBeneficiaryNext;
    @BindView(R.id.bankNameSpinner)
    MaterialSpinner bankNameSpinner;
    @BindView(R.id.bankNameNewEditTextAddBeneficiary)
    MaterialEditText bankNameNewEditTextAddBeneficiary;
    @BindView(R.id.otherPurposeOfTransferEditTextAddBeneficiary)
    MaterialEditText otherPurposeOfTransferEditTextAddBeneficiary;
    @BindView(R.id.autocompletelayout)
    TextInputLayout autocompletelayout;
    private String Countrycode;
    private String statename;
    private String cityname;
    private String purposecode;
    private String Countryshortcode;
    private String bankbranchname, BankBranchCode, BankBranchNamevalidation;
    private BeneficiaryInfoListPojo beneficiaryinfoPojo;
    private BeneficiaryListPojo.Data bankbenefiardata;
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
    @BindView(R.id.bankAccountNumberEditTextAddBeneficiaryNext)
    MaterialEditText bankAccountNumberEditTextAddBeneficiaryNext;
    @BindView(R.id.bankNameEditTextAddBeneficiary)
    AutoCompleteTextView bankNameEditTextAddBeneficiary;
    @BindView(R.id.bankCodeEditTextAddBeneficiary)
    MaterialEditText bankCodeEditTextAddBeneficiary;
    @BindView(R.id.bankAddressEditTextAddBeneficiary)
    MaterialEditText bankAddressEditTextAddBeneficiary;
    @BindView(R.id.phoneNumberEditTextAddBeneficiary)
    MaterialEditText phoneNumberEditTextAddBeneficiary;
    @BindView(R.id.purposeOfTransferSpinnerAddBeneficiary)
    MaterialSpinner purposeOfTransferSpinnerAddBeneficiary;
    @BindView(R.id.addTextViewAddBeneficiary)
    TextView addTextViewAddBeneficiary;
    @BindView(R.id.mainAddBeneficiaryNextActivityLinearLayout)
    LinearLayout mainAddBeneficiaryNextActivityLinearLayout;
    private Intent mIntent;

    private final ArrayList<PurposeOfTransferListPojo> purposeOfTransferListPojos = new ArrayList<>();
    private final ArrayList<BankNetworkListJsonPojo> bankNetworkListJsonPojos = new ArrayList<>();
    private final ArrayList<CreateBeneficiaryJsonPojo> createBeneficiaryJsonPojos = new ArrayList<>();
    private final ArrayList<EditBeneficiaryJsonPojo> editBeneficiaryJsonPojos = new ArrayList<>();
    private String Beneficiarnumber;
    private String str = "";
    private QuickPayDataPojo quickPayData = new QuickPayDataPojo();
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String accountnumbermsg, minimumaccountmsg, banknamemsg, branchcodemsg, purposedescription,branchaddmsg, validbankmsg, mobilenumber, validmobilenumber, purposetransfermsg;
    private String nointernetmsg;
    ArrayList<BankDataPojo> data;
    ArrayList<String> stringArrayList = new ArrayList<>();
    boolean spinnerflag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_beneficiary_next);
        ButterKnife.bind(this);
        Countrycode = getIntent().getExtras().getString("countrycode");
        cityname = getIntent().getExtras().getString("cityname");
        Countryshortcode = getIntent().getExtras().getString("countryshortcode");
        if (getIntent().getExtras().getString("quick_pay") != null) {
            quickPayData = (QuickPayDataPojo) getIntent().getSerializableExtra("beneficiaryapidata");
            bankAccountNumberEditTextAddBeneficiaryNext.setText(quickPayData.getBenificaryAccountNumber());
            bankReAccountNumberEditTextAddBeneficiaryNext.setText(quickPayData.getBenificaryAccountNumber());
            bankNameEditTextAddBeneficiary.setText(quickPayData.getBenificaryBankName());
            bankCodeEditTextAddBeneficiary.setText(quickPayData.getBenificaryBankCode());
            bankNameNewEditTextAddBeneficiary.setText(quickPayData.getBenificaryBankName());
            BankBranchCode = quickPayData.getBenificaryBankCode();
            bankAddressEditTextAddBeneficiary.setText(quickPayData.getBenificaryBranchNameAndAddress());
            phoneNumberEditTextAddBeneficiary.setText(quickPayData.getBenificaryTelephone());
            ifscCodeEditTextAddBeneficiaryNext.setText(quickPayData.getBenificaryBankCode());
            purposecode = quickPayData.getBenificaryPurposeCode();
        } else {
            bankbenefiardata = (BeneficiaryListPojo.Data) getIntent().getSerializableExtra("beneficiaryapidata");
            bankAccountNumberEditTextAddBeneficiaryNext.setText(bankbenefiardata.getBenificaryAccountNumber());
            bankReAccountNumberEditTextAddBeneficiaryNext.setText(bankbenefiardata.getBenificaryAccountNumber());
            bankNameEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryBankName());
            bankNameNewEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryBankName());
            bankCodeEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryBankCode());
            ifscCodeEditTextAddBeneficiaryNext.setText(bankbenefiardata.getBenificaryBankCode());
            BankBranchCode = bankbenefiardata.getBenificaryBankCode();
            bankAddressEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryBranchNameAndAddress());
            phoneNumberEditTextAddBeneficiary.setText(bankbenefiardata.getBenificaryTelephone());
            purposecode = bankbenefiardata.getBenificaryPurposeCode();
            if (purposecode.equalsIgnoreCase("12")||purposecode.equalsIgnoreCase("13"))
            {
                purposedescription= bankbenefiardata.getBenificaryPurposeDescription();
            }

        }
//        beneficiaryinfoPojo = (BeneficiaryInfoListPojo) getIntent().getSerializableExtra("beneficiarydata");
//        bankbenefiardata = (StaticBeneficiaryInfoPojo) getIntent().getSerializableExtra("beneficiaryapidata");
        beneficiaryinfoPojo = (BeneficiaryInfoListPojo) getIntent().getSerializableExtra("beneficiarydata");

        Beneficiarnumber = getIntent().getExtras().getString("beneficiarnumber");
        menuImageViewHeader2.setImageResource(R.drawable.back_btn);
        menuImageViewHeader2.setColorFilter(R.color.colorBlack);
        titleTextViewViewHeader2.setText("Beneficiary Info");
        addTextViewAddBeneficiary.setText("Next");
        filterImageViewHeader2.setVisibility(View.INVISIBLE);

        try {
            sessionManager = new SessionManager(AddBeneficiaryBankNextActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {
                bankAccountNumberEditTextAddBeneficiaryNext.setHint(datumLable_languages.getAccountNumber());
                bankAccountNumberEditTextAddBeneficiaryNext.setFloatingLabelText(datumLable_languages.getAccountNumber());
                bankNameEditTextAddBeneficiary.setHint(datumLable_languages.getBankName());
                bankNameNewEditTextAddBeneficiary.setHint(datumLable_languages.getBankName());
                bankCodeEditTextAddBeneficiary.setHint(datumLable_languages.getBranchCode());
                bankCodeEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getBranchCode());
                bankAddressEditTextAddBeneficiary.setHint(datumLable_languages.getBranchAddress());
                bankAddressEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getBranchAddress());
                phoneNumberEditTextAddBeneficiary.setHint(datumLable_languages.getMobileNumber());
                phoneNumberEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getMobileNumber());
                purposeOfTransferSpinnerAddBeneficiary.setHint(datumLable_languages.getPurposeOfTransfer());
                bankNameSpinner.setHint(datumLable_languages.getBankName());
                purposeOfTransferSpinnerAddBeneficiary.setFloatingLabelText(datumLable_languages.getPurposeOfTransfer());
                bankNameSpinner.setFloatingLabelText(datumLable_languages.getBankName());
                titleTextViewViewHeader2.setText(datumLable_languages.getBeneficiaryInfo());
                addTextViewAddBeneficiary.setText(datumLable_languages.getAdd());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();


            } else {
                titleTextViewViewHeader2.setText("Beneficiary Info");

                bankAccountNumberEditTextAddBeneficiaryNext.setHint(getResources().getString(R.string.bank_account_number));
                bankNameEditTextAddBeneficiary.setHint(getResources().getString(R.string.bank_name));
                bankCodeEditTextAddBeneficiary.setHint(getResources().getString(R.string.bank_code));
                bankAddressEditTextAddBeneficiary.setHint(getResources().getString(R.string.bank_address));
                bankNameEditTextAddBeneficiary.setHint(getResources().getString(R.string.bank_name));
                addTextViewAddBeneficiary.setHint(getResources().getString(R.string.add));
                phoneNumberEditTextAddBeneficiary.setHint(getResources().getString(R.string.phone_number));
                purposeOfTransferSpinnerAddBeneficiary.setHint(getResources().getString(R.string.purpose_of_transfer));
                bankNameSpinner.setHint(getResources().getString(R.string.bank_name));
                nointernetmsg = getResources().getString(R.string.no_internet);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (datumLable_languages_msg != null) {
            accountnumbermsg = datumLable_languages_msg.getEnterAccountNo();
            minimumaccountmsg = datumLable_languages_msg.getEnterMinimum12DigitAccountNumber();
            banknamemsg = datumLable_languages_msg.getEnterBankName();
            branchcodemsg = datumLable_languages_msg.getEnterBranchCode();
            branchaddmsg = datumLable_languages_msg.getEnterBranchAddress();
            validbankmsg = datumLable_languages_msg.getEnterValidBankDetails();
            mobilenumber = datumLable_languages_msg.getEnterMobileNumber();
            validmobilenumber = datumLable_languages_msg.getMobileNumberShouldBeAtLeast9Digits();
            purposetransfermsg = datumLable_languages_msg.getSelectPurposeOfTransfer();
        } else {

            accountnumbermsg = getResources().getString(R.string.Please_Enter_account_number);
            minimumaccountmsg = getResources().getString(R.string.Please_Enter_minimum_account_number);
            banknamemsg = getResources().getString(R.string.Please_Enter_bank_name);
            branchcodemsg = getResources().getString(R.string.Please_Enter_branch_code);
            branchaddmsg = getResources().getString(R.string.Please_Enter_branch_address);
            validbankmsg = getResources().getString(R.string.Please_Enter_valid_bank_details);
            mobilenumber = getResources().getString(R.string.Please_Enter_Mobile_number);
            validmobilenumber = getResources().getString(R.string.Mobile_number_9_digits);
            purposetransfermsg = getResources().getString(R.string.Please_select_purpose_transfer);


        }
        if (IsNetworkConnection.checkNetworkConnection(AddBeneficiaryBankNextActivity.this)) {
//            bankListJsonCall("");
        }
        if (IsNetworkConnection.checkNetworkConnection(this)) {
            purposeOfTransferJsonCall();
        } else {
            Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, this, nointernetmsg);
        }

        bankReAccountNumberEditTextAddBeneficiaryNext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                CustomLog.d("System out", "substring " + bankAccountNumberEditTextAddBeneficiaryNext.getText().toString().substring(0, editable.length()));
                CustomLog.d("System out", "editable " + editable);

                int main_length = bankAccountNumberEditTextAddBeneficiaryNext.getText().toString().length();

                if (main_length > editable.length()) {
                    if (!bankAccountNumberEditTextAddBeneficiaryNext.getText().toString().substring(0, editable.length()).equalsIgnoreCase(bankReAccountNumberEditTextAddBeneficiaryNext.getText().toString())) {
                        bankReAccountNumberEditTextAddBeneficiaryNext.setError("Account number not match");
                        accountHolderNameEditTextAddBeneficiaryNext.setEnabled(false);
                        ifscCodeEditTextAddBeneficiaryNext.setEnabled(false);
                    }
                } else if (main_length == editable.length()) {
                    if (!bankAccountNumberEditTextAddBeneficiaryNext.getText().toString().substring(0, editable.length()).equalsIgnoreCase(bankReAccountNumberEditTextAddBeneficiaryNext.getText().toString())) {
                        bankReAccountNumberEditTextAddBeneficiaryNext.setError("Account number not match");
                        accountHolderNameEditTextAddBeneficiaryNext.setEnabled(false);
                        ifscCodeEditTextAddBeneficiaryNext.setEnabled(false);
                    } else {
                        accountHolderNameEditTextAddBeneficiaryNext.setEnabled(true);
                        ifscCodeEditTextAddBeneficiaryNext.setEnabled(true);
                    }
                } else {
                    bankReAccountNumberEditTextAddBeneficiaryNext.setError("Account number not match");
                    accountHolderNameEditTextAddBeneficiaryNext.setEnabled(false);
                    ifscCodeEditTextAddBeneficiaryNext.setEnabled(false);
                }
            }
        });

//        bankReAccountNumberEditTextAddBeneficiaryNext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    if (!bankAccountNumberEditTextAddBeneficiaryNext.getText().toString().equalsIgnoreCase(bankReAccountNumberEditTextAddBeneficiaryNext.getText().toString())) {
//                        Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this, "Account number not match");
//                    }
//                }
//            }
//        });

//        bankNameEditTextAddBeneficiary.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//               str= bankNameEditTextAddBeneficiary.getText().toString().trim();
//                if (bankNameEditTextAddBeneficiary.isPerformingCompletion()) {
//                    // An item has been selected from the list. Ignore.
//                    return;
//                }
//               else if (IsNetworkConnection.checkNetworkConnection(AddBeneficiaryBankNextActivity.this)) {
//                    if (str.length() ==2||str.length()==3)
//                        bankListJsonCall(str);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//        });


        ifscCodeEditTextAddBeneficiaryNext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int main_length = ifscCodeEditTextAddBeneficiaryNext.getText().toString().length();

                if (main_length == 0) {
                    bankNameNewEditTextAddBeneficiary.setText("");
                    bankAddressEditTextAddBeneficiary.setText("");
                    bankNameEditTextAddBeneficiary.setText("");
                } else if (main_length > 3) {
                    bankNameNewEditTextAddBeneficiary.setText("");
                    bankNameEditTextAddBeneficiary.setText("");
                    bankAddressEditTextAddBeneficiary.setText("");
                    bankListJsonCall("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                CustomLog.d("System out", "substring " + bankAccountNumberEditTextAddBeneficiaryNext.getText().toString().substring(0, editable.length()));
                CustomLog.d("System out", "editable " + editable);

                int main_length = ifscCodeEditTextAddBeneficiaryNext.getText().toString().length();

//                if (main_length < 11) {
//                    ifscCodeEditTextAddBeneficiaryNext.setError("Enter Valid IFSC/IBAN Code");
//                } else if (main_length > 4) {
//                    String foo = ifscCodeEditTextAddBeneficiaryNext.getText().toString();
//                    if (foo.length() > 0) { //just checks that there is something. You may want to check that length is greater than or equal to 3
//                        String bar = foo.substring(0, 3);
//                        if (bar.toString().matches("[a-zA-Z ]+")) {
//
//                        } else {
//                            ifscCodeEditTextAddBeneficiaryNext.setError("Enter Valid IFSC/IBAN Code");
//                        }
//
//                    }
//
//                } else {
//
//                }
            }
        });

        bankNameEditTextAddBeneficiary.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (bankNameEditTextAddBeneficiary.isPerformingCompletion()) {
                    // An item has been selected from the list. Ignore.
                    return;
                }
                String selectedItem = (String) parent.getItemAtPosition(position);
                Log.d("System out", selectedItem);
                Constants.hideKeyboard(AddBeneficiaryBankNextActivity.this);
                int index = stringArrayList.indexOf(bankNameEditTextAddBeneficiary.getText().toString());
                Log.d("System out", String.valueOf(index));
//                bankCodeEditTextAddBeneficiary.setText(data.get(index).getBankCode());
                bankAddressEditTextAddBeneficiary.setText(data.get(index).getBankAddress());
                bankbranchname = data.get(index).getBranchName();
                BankBranchCode = data.get(index).getBankCode();
                BankBranchNamevalidation = data.get(index).getBankName() + "," + data.get(index).getBranchName();
//                receiverId = walletSuggestionListPojos.get(0).getData().get(position).getUserID();
            }
        });


    }

    private void purposeOfTransferJsonCall() {
        JSONObject jsonObject = new JSONObject();
        Constants.showProgress(AddBeneficiaryBankNextActivity.this);
        try {
            jsonObject.put("countryCode", Countryshortcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "add beneficiary purpose of transfer json " + json);
//        Constants.showProgress(ProfileActivity.this);
        Call<List<PurposeOfTransferListPojo>> call = RestClient.get().getPurposeOfTransferJsonCall(json);

        call.enqueue(new Callback<List<PurposeOfTransferListPojo>>() {

            @Override
            public void onResponse(Call<List<PurposeOfTransferListPojo>> call, Response<List<PurposeOfTransferListPojo>> response) {
//                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    purposeOfTransferListPojos.addAll(response.body());
                    if (purposeOfTransferListPojos.get(0).getStatus() == true) {
                        Constants.closeProgress();
                        ArrayList<String> countryList = new ArrayList<>();
                        for (int i = 0; i < purposeOfTransferListPojos.get(0).getData().size(); i++) {
                            countryList.add(new String(purposeOfTransferListPojos.get(0).getData().get(i).getPurposeOfTranfer().trim()));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBeneficiaryBankNextActivity.this, android.R.layout.simple_spinner_item, countryList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        purposeOfTransferSpinnerAddBeneficiary.setAdapter(adapter);

                        for (int i = 0; i < purposeOfTransferListPojos.get(0).getData().size(); i++) {
                            if (purposecode.equalsIgnoreCase(purposeOfTransferListPojos.get(0).getData().get(i).getPurposeOfTransferID())) {
                                purposeOfTransferSpinnerAddBeneficiary.setSelection(i + 1);
                                if (purposecode.equalsIgnoreCase("12")||purposecode.equalsIgnoreCase("13")) {
                                    otherPurposeOfTransferEditTextAddBeneficiary.setVisibility(View.VISIBLE);
                                    otherPurposeOfTransferEditTextAddBeneficiary.setFloatingLabelText(purposeOfTransferListPojos.get(0).getData().get(i).getPurposeOfTranfer());
                                    otherPurposeOfTransferEditTextAddBeneficiary.setHint(purposeOfTransferListPojos.get(0).getData().get(i).getPurposeOfTranfer());
                                    otherPurposeOfTransferEditTextAddBeneficiary.setText(purposedescription);
                                } else {
                                    otherPurposeOfTransferEditTextAddBeneficiary.setVisibility(View.GONE);
                                }
                                break;
                            }
                        }

                        purposeOfTransferSpinnerAddBeneficiary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    purposecode = purposeOfTransferListPojos.get(0).getData().get(position).getPurposeOfTransferID();
                                    if (purposecode.equalsIgnoreCase("12")||purposecode.equalsIgnoreCase("13")) {
                                        otherPurposeOfTransferEditTextAddBeneficiary.setVisibility(View.VISIBLE);
                                        otherPurposeOfTransferEditTextAddBeneficiary.setFloatingLabelText(purposeOfTransferSpinnerAddBeneficiary.getSelectedItem().toString());
                                        otherPurposeOfTransferEditTextAddBeneficiary.setHint(purposeOfTransferSpinnerAddBeneficiary.getSelectedItem().toString());
                                    } else {
                                        otherPurposeOfTransferEditTextAddBeneficiary.setVisibility(View.GONE);
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
            public void onFailure(Call<List<PurposeOfTransferListPojo>> call, Throwable t) {
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
                mIntent = new Intent(AddBeneficiaryBankNextActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
            case R.id.addTextViewAddBeneficiary:
                if (bankAccountNumberEditTextAddBeneficiaryNext.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
                            , accountnumbermsg);
                } else if (bankAccountNumberEditTextAddBeneficiaryNext.getText().toString().length() < 8) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
                            , minimumaccountmsg);
                } else if (bankReAccountNumberEditTextAddBeneficiaryNext.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
                            , "Please Enter bankReAccountNumber");
                } else if (!bankAccountNumberEditTextAddBeneficiaryNext.getText().toString().equalsIgnoreCase(bankReAccountNumberEditTextAddBeneficiaryNext.getText().toString())) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
                            , "Account number and Reaccount number does not match");
                } else if (spinnerflag &&bankNameEditTextAddBeneficiary.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
                            , banknamemsg);
                } else if (!spinnerflag && bankNameNewEditTextAddBeneficiary.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
                            , banknamemsg);

                } else if (ifscCodeEditTextAddBeneficiaryNext.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
                            , branchcodemsg);

                } else if (bankAddressEditTextAddBeneficiary.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
                            , branchaddmsg);

                }
//                else if (!ifscCodeEditTextAddBeneficiaryNext.getText().toString().equalsIgnoreCase(BankBranchCode)) {
//                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
//                            , validbankmsg);
//                }
//                else if (!bankAddressEditTextAddBeneficiary.getText().toString().equalsIgnoreCase(BankbranchAddress)) {
//                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
// , "Please Enter Valid Branch Address");
//                }
//                else if (phoneNumberEditTextAddBeneficiary.getText().toString().length() == 0) {
//                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
//                            , mobilenumber);
//
//                } else if (phoneNumberEditTextAddBeneficiary.getText().toString().length() < 7) {
//                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
//                            , validmobilenumber);
//
//                }
                else if (purposeOfTransferSpinnerAddBeneficiary == null && purposeOfTransferSpinnerAddBeneficiary.getSelectedItem() == null) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
                            , purposetransfermsg);
                } else if (purposeOfTransferSpinnerAddBeneficiary.getSelectedItem() == null) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
                            , purposetransfermsg);
                }
//                else if  (purposecode.equalsIgnoreCase("12")&&otherPurposeOfTransferEditTextAddBeneficiary.getText().toString().length()==0)  {
//
//                        Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
//                                , "Please specify purpose of transfer");
//                }
                else if ((purposecode.equalsIgnoreCase("12")||purposecode.equalsIgnoreCase("13"))&&otherPurposeOfTransferEditTextAddBeneficiary.getText().toString().length()==0) {

                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
                            , "Please specify other purpose of transfer");
                }
                else {

                    if (IsNetworkConnection.checkNetworkConnection(this)) {
                        editBeneficiaryJsonCall();
                    } else {
                        Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, this, nointernetmsg);
                    }
//                    Intent intent=new Intent(AddBeneficiaryBankNextActivity.this,VerifiedPinActivity.class);
//                    startActivity(intent);
//                }else {
//                    Intent intent = new Intent(AddBeneficiaryBankNextActivity.this, PinVerificationActivity.class);
//                    startActivity(intent);
//                    if (IsNetworkConnection.checkNetworkConnection(this)) {
//                        addBeneficiaryJsonCall();
//                    } else {
//                        Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, this, );
//                    }
                }

                break;
        }
    }

    private void editBeneficiaryJsonCall() {
        JSONObject jsonObject = new JSONObject();
        Constants.showProgress(AddBeneficiaryBankNextActivity.this);
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
            if (beneficiaryinfoPojo.getDateOfBirth() == null) {
                if (getIntent().getExtras().getString("quick_pay") != null) {
                    jsonObject.put("DateOfBirth", quickPayData.getBenificaryDateOfBirth());
                } else {
                    jsonObject.put("DateOfBirth", bankbenefiardata.getBenificaryDateOfBirth());
                }

            } else {
                jsonObject.put("DateOfBirth", Constants.formatDate(beneficiaryinfoPojo.getDateOfBirth(), "dd MM yyyy", "MM/dd/yyyy"));
            }
            jsonObject.put("Telephone", beneficiaryinfoPojo.getTelephone());
            jsonObject.put("Nationality", beneficiaryinfoPojo.getNationality());
            jsonObject.put("IDNumber", beneficiaryinfoPojo.getIDNumber());
            jsonObject.put("IDType", beneficiaryinfoPojo.getIDType());
            jsonObject.put("IDtype_Description", beneficiaryinfoPojo.getIDtype_Description());
            jsonObject.put("PayOutCurrency", "INR");
            jsonObject.put("PaymentMode", "BANK");
//            jsonObject.put("PayOutBranchCode", bankCodeEditTextAddBeneficiary.getText().toString());
            jsonObject.put("PayOutBranchCode", ifscCodeEditTextAddBeneficiaryNext.getText().toString());
            if (spinnerflag) {
                jsonObject.put("BankName",bankNameEditTextAddBeneficiary.getText().toString());
            } else {
                jsonObject.put("BankName", bankNameNewEditTextAddBeneficiary.getText().toString());
            }
            jsonObject.put("BankCountry", Countryshortcode);
            jsonObject.put("BranchNameAndAddress", bankAddressEditTextAddBeneficiary.getText().toString());
//            jsonObject.put("BankCode", bankCodeEditTextAddBeneficiary.getText().toString());
            if (spinnerflag) {
                jsonObject.put("BankCode",BankBranchCode);
            } else {
                jsonObject.put("BankCode", ifscCodeEditTextAddBeneficiaryNext.getText().toString());
            }
            jsonObject.put("BankBranch", bankbranchname);
            jsonObject.put("AccountNumber", bankAccountNumberEditTextAddBeneficiaryNext.getText().toString());
            jsonObject.put("PurposeCode", purposecode);
            jsonObject.put("PayoutCountryCode", Countryshortcode);
            jsonObject.put("BeneficiaryNo", Beneficiarnumber);
//            jsonObject.put("CustomerRelation", "0");
            jsonObject.put("CustomerRelation", beneficiaryinfoPojo.getRelation());
            jsonObject.put("userID", getUserData().getUserID());
            jsonObject.put("benificaryState", beneficiaryinfoPojo.getState());
            jsonObject.put("benificaryCity", beneficiaryinfoPojo.getCity());
            if (purposecode.equalsIgnoreCase("12")||purposecode.equalsIgnoreCase("13"))
            {
                jsonObject.put("benificaryPurposeDescription", otherPurposeOfTransferEditTextAddBeneficiary.getText().toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "addbeneficiary json " + json);
//        Constants.showProgress(ProfileActivity.this);
        Call<List<EditBeneficiaryJsonPojo>> call = RestClient.get().editBeneficiaryJsonCall(json);

        call.enqueue(new Callback<List<EditBeneficiaryJsonPojo>>() {

            @Override
            public void onResponse(Call<List<EditBeneficiaryJsonPojo>> call, Response<List<EditBeneficiaryJsonPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    editBeneficiaryJsonPojos.clear();
                    editBeneficiaryJsonPojos.addAll(response.body());
                    if (editBeneficiaryJsonPojos.get(0).getStatus() == true) {
                        if (datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()) != null) {
                            Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                        } else {
                            Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this, response.body().get(0).getInfo().toString());
                        }
                        Constants.closeProgress();
                        beneficiaryinfoPojo.setBeneficiarynumber(editBeneficiaryJsonPojos.get(0).getData().get(0).getBeneficiaryNo());
//                        beneficiaryinfoPojo.setPayoutbranchcode(bankCodeEditTextAddBeneficiary.getText().toString());
                        beneficiaryinfoPojo.setPayoutbranchcode(ifscCodeEditTextAddBeneficiaryNext.getText().toString());
                        Intent intent = new Intent(AddBeneficiaryBankNextActivity.this, PinVerificationActivity.class);
                        intent.putExtra("come_from", "selectcashnext");
                        intent.putExtra("flagimage", beneficiaryinfoPojo.getCountryFlagImage());
                        intent.putExtra("countryshortcode", Countryshortcode);
                        intent.putExtra("beneficiary_data", beneficiaryinfoPojo);
                        startActivity(intent);


                    } else {
                        if (response.body().get(0).getInfo() != null) {
                            Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this, response.body().get(0).getInfo());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<EditBeneficiaryJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }


    private void bankListJsonCall(String str) {
        JSONObject jsonObject = new JSONObject();
//        Constants.showProgress(AddBeneficiaryBankNextActivity.this);
        try {
            jsonObject.put("countryCode", Countryshortcode);
            jsonObject.put("bankName", "");
            jsonObject.put("bankIFSC", ifscCodeEditTextAddBeneficiaryNext.getText().toString().trim());
//            jsonObject.put("branchName", beneficiaryinfoPojo.getLandMark());
            jsonObject.put("branchName", "");
            jsonObject.put("city", cityname);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "Bank lIST JSON" + json);

//        Constants.showProgress(ProfileActivity.this);
        Call<List<BankNetworkListJsonPojo>> call = RestClient.get().getBankNetworkJsonCall(json);

        call.enqueue(new Callback<List<BankNetworkListJsonPojo>>() {
            @Override
            public void onResponse(Call<List<BankNetworkListJsonPojo>> call, Response<List<BankNetworkListJsonPojo>> response) {
                Constants.closeProgress();
                bankNetworkListJsonPojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    Log.d("success", "success");
                    bankNetworkListJsonPojos.addAll(response.body());
                    if (!bankNetworkListJsonPojos.get(0).equals(null)) {
                        if (bankNetworkListJsonPojos.get(0).getStatus()) {
                            if (bankNetworkListJsonPojos.get(0).getData() != null) {
                                Object object = bankNetworkListJsonPojos.get(0).getData();
//                                String jsonStr = String.valueOf(object);
//                                jsonStr = jsonStr.substring(jsonStr.indexOf("[") + 1);
                                Log.d("system out", object.toString());
                                Log.d("system out banknetwork", new Gson().toJson(bankNetworkListJsonPojos.get(0).getData().toString()));

                                if (object instanceof ArrayList) {
                                    data = new Gson().fromJson(new Gson().toJson(bankNetworkListJsonPojos.get(0).getData()), new TypeToken<ArrayList<BankDataPojo>>() {
                                    }.getType());
                                    for (int i = 0; i < data.size(); i++) {
                                        stringArrayList.add(data.get(i).getBankName());
                                    }

                                    bankAddressEditTextAddBeneficiary.setEnabled(false);
                                    autocompletelayout.setVisibility(View.VISIBLE);
                                    bankNameNewEditTextAddBeneficiary.setVisibility(View.GONE);
//                                    for (int i = 0; i < data.size(); i++) {
//                                        stringArrayList.add(data.get(i).getBankName() + "," + data.get(i).getBranchName());
//                                    }
                                    spinnerflag = true;
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBeneficiaryBankNextActivity.this, android.R.layout.simple_spinner_item, stringArrayList);
//                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                    bankNameSpinner.setAdapter(adapter);
                                    bankNameEditTextAddBeneficiary.setAdapter(adapter);

                                    bankNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if (position != -1) {
//                                                bankNameEditTextAddBeneficiary.setText(data.get(position).getBankName());
                                                bankAddressEditTextAddBeneficiary.setText(data.get(position).getBankAddress());
//                                                ifscCodeEditTextAddBeneficiaryNext.setText(data.get(position).getBankCode());

                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                } else {
                                    spinnerflag = false;
                                    bankNameSpinner.setVisibility(View.GONE);
                                    autocompletelayout.setVisibility(View.GONE);
                                    bankNameNewEditTextAddBeneficiary.setVisibility(View.VISIBLE);
                                    bankNameEditTextAddBeneficiary.setEnabled(false);
                                    bankAddressEditTextAddBeneficiary.setEnabled(false);
                                    BankDataPojo data = new Gson().fromJson(new Gson().toJson(bankNetworkListJsonPojos.get(0).getData()), BankDataPojo.class);
                                    bankNameNewEditTextAddBeneficiary.setText(data.getBankName());
                                    bankAddressEditTextAddBeneficiary.setText(data.getBankAddress());

                                }
                            }


                        } else {
                            Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
                                    , datumLable_languages.getNoRecordFound());
                            bankNameNewEditTextAddBeneficiary.setText("");
                            bankAddressEditTextAddBeneficiary.setText("");
                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<List<BankNetworkListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
                Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryBankNextActivity.this
                        , datumLable_languages.getNoRecordFound());
                bankNameNewEditTextAddBeneficiary.setText("");
                bankAddressEditTextAddBeneficiary.setText("");
                Log.d("fail", "fail");
            }
        });
    }

    private void addBeneficiaryJsonCall() {
        JSONObject jsonObject = new JSONObject();
        Constants.showProgress(AddBeneficiaryBankNextActivity.this);
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
            jsonObject.put("DateOfBirth", Constants.formatDate(beneficiaryinfoPojo.getDateOfBirth(), "dd MM yyyy", "MM/dd/yyyy"));
            jsonObject.put("Telephone", beneficiaryinfoPojo.getTelephone());
            jsonObject.put("Nationality", beneficiaryinfoPojo.getNationality());
            jsonObject.put("IDNumber", beneficiaryinfoPojo.getIDNumber());
            jsonObject.put("IDType", beneficiaryinfoPojo.getIDType());
            jsonObject.put("IDtype_Description", beneficiaryinfoPojo.getIDtype_Description());
            jsonObject.put("PayOutCurrency", "INR");
            jsonObject.put("PaymentMode", "BANK");
            jsonObject.put("PayOutBranchCode", "");
            if (spinnerflag) {
                jsonObject.put("BankName",bankNameEditTextAddBeneficiary.getText().toString());
            } else {
                jsonObject.put("BankName", bankNameNewEditTextAddBeneficiary.getText().toString());
            }
            jsonObject.put("BankCountry", "IND");
            jsonObject.put("BranchNameAndAddress", bankAddressEditTextAddBeneficiary.getText().toString());
//            jsonObject.put("BankCode", bankCodeEditTextAddBeneficiary.getText().toString());
            jsonObject.put("BankCode", ifscCodeEditTextAddBeneficiaryNext.getText().toString());
            jsonObject.put("BankBranch", bankbranchname);
            jsonObject.put("AccountNumber", bankAccountNumberEditTextAddBeneficiaryNext.getText().toString());
            jsonObject.put("PurposeCode", purposecode);
            jsonObject.put("PayoutCountryCode", "IND");
            jsonObject.put("BeneficiaryNo", "0");
//            jsonObject.put("CustomerRelation", "0");
            jsonObject.put("CustomerRelation", beneficiaryinfoPojo.getRelation());
            jsonObject.put("userID", getUserData().getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "addbeneficiary json " + json);
//        Constants.showProgress(ProfileActivity.this);
        Call<List<CreateBeneficiaryJsonPojo>> call = RestClient.get().createBeneficiaryJsonCall(json);

        call.enqueue(new Callback<List<CreateBeneficiaryJsonPojo>>() {

            @Override
            public void onResponse(Call<List<CreateBeneficiaryJsonPojo>> call, Response<List<CreateBeneficiaryJsonPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    createBeneficiaryJsonPojos.addAll(response.body());
                    if (createBeneficiaryJsonPojos.get(0).getStatus() == true) {
                        Constants.closeProgress();


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