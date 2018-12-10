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
import com.fil.workerappz.pojo.CreateBeneficiaryJsonPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.PurposeOfTransferListPojo;
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
 * Created by HS on 19-Mar-18.
 * FIL AHM
 */

public class AddBeneficiaryNextActivity extends ActionBarActivity {

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
    @BindView(R.id.bankAccountNumberEditTextAddBeneficiaryNext)
    MaterialEditText bankAccountNumberEditTextAddBeneficiaryNext;
    @BindView(R.id.mainAddBeneficiaryNextActivityLinearLayout)
    LinearLayout mainAddBeneficiaryNextActivityLinearLayout;
    @BindView(R.id.bankNameEditTextAddBeneficiary)
    AutoCompleteTextView bankNameEditTextAddBeneficiary;
    @BindView(R.id.skipTextViewViewHeader2)
    TextView skipTextViewViewHeader2;
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

    private String countryCode, purposeCode, countryShortCode, bankBranchName, customerNumber, BankBranchCode, BankbranchAddress, BankBranchNamevalidation;
    private BeneficiaryInfoListPojo beneficiaryInfoListPojo;
    private final ArrayList<PurposeOfTransferListPojo> purposeOfTransferListPojos = new ArrayList<>();
    private final ArrayList<CreateBeneficiaryJsonPojo.Data> createBeneficiaryJsonPojosdata = new ArrayList<CreateBeneficiaryJsonPojo.Data>();
    private final ArrayList<BankNetworkListJsonPojo> bankNetworkListJsonPojos = new ArrayList<>();
    private final ArrayList<CreateBeneficiaryJsonPojo> createBeneficiaryJsonPojos = new ArrayList<>();
    private Intent mIntent;
    ArrayList<String> stringArrayList = new ArrayList<>();
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String accountnumbermsg, minimumaccountmsg, banknamemsg, branchcodemsg, branchaddmsg, validbankmsg, mobilenumber, validmobilenumber, purposetransfermsg;
    private String nointernetmsg, cityname;
    ArrayList<BankDataPojo> data;
    boolean spinnerflag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_beneficiary_next);
        ButterKnife.bind(this);

        countryCode = getIntent().getExtras().getString("country_code");
        cityname = getIntent().getExtras().getString("cityname");
        countryShortCode = getIntent().getExtras().getString("country_short_code");
        customerNumber = getIntent().getExtras().getString("customer_number");
        beneficiaryInfoListPojo = (BeneficiaryInfoListPojo) getIntent().getSerializableExtra("beneficiary_data");

        menuImageViewHeader2.setImageResource(R.drawable.back_btn);

        filterImageViewHeader2.setVisibility(View.INVISIBLE);

        try {
            sessionManager = new SessionManager(AddBeneficiaryNextActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {
                bankAccountNumberEditTextAddBeneficiaryNext.setHint(datumLable_languages.getAccountNumber()+"*");
                bankAccountNumberEditTextAddBeneficiaryNext.setFloatingLabelText(datumLable_languages.getAccountNumber()+"*");
                bankNameEditTextAddBeneficiary.setHint(datumLable_languages.getBankName()+"*");
                bankNameNewEditTextAddBeneficiary.setHint(datumLable_languages.getBankName()+"*");
                bankCodeEditTextAddBeneficiary.setHint(datumLable_languages.getBranchCode()+"*");
                bankCodeEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getBranchCode()+"*");
                bankAddressEditTextAddBeneficiary.setHint(datumLable_languages.getBranchAddress()+"*");
                bankAddressEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getBranchAddress()+"*");
                phoneNumberEditTextAddBeneficiary.setHint(datumLable_languages.getMobileNumber()+"*");
                bankNameSpinner.setHint(datumLable_languages.getBankName()+"*");
                phoneNumberEditTextAddBeneficiary.setFloatingLabelText(datumLable_languages.getMobileNumber());
                bankNameSpinner.setFloatingLabelText(datumLable_languages.getBankName()+"*");
                purposeOfTransferSpinnerAddBeneficiary.setHint(datumLable_languages.getPurposeOfTransfer()+"*");
                titleTextViewViewHeader2.setText(datumLable_languages.getBeneficiaryInfo());
                addTextViewAddBeneficiary.setText(datumLable_languages.getAdd());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();

            } else {
                titleTextViewViewHeader2.setText("Beneficiary Info");

                bankAccountNumberEditTextAddBeneficiaryNext.setHint(getResources().getString(R.string.bank_account_number));
                bankNameEditTextAddBeneficiary.setHint(getResources().getString(R.string.bank_name));
                bankNameNewEditTextAddBeneficiary.setHint(getResources().getString(R.string.bank_name));
                bankCodeEditTextAddBeneficiary.setHint(getResources().getString(R.string.bank_code));
                bankAddressEditTextAddBeneficiary.setHint(getResources().getString(R.string.bank_address));
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
        if (IsNetworkConnection.checkNetworkConnection(AddBeneficiaryNextActivity.this)) {
//            bankListJsonCall("");
        }
        if (IsNetworkConnection.checkNetworkConnection(this)) {
            purposeOfTransferJsonCall();
        } else {
            Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, this, nointernetmsg);
        }
//        bankNameEditTextAddBeneficiary.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//
//                String str = bankNameEditTextAddBeneficiary.getText().toString().trim();
//                if (IsNetworkConnection.checkNetworkConnection(AddBeneficiaryNextActivity.this)) {
//                    if (str.length() ==2)
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
//            }
//        });
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

//        bankNameEditTextAddBeneficiary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItem = (String) parent.getItemAtPosition(position);
//                Log.d("System out", selectedItem);
//
//                int index = stringArrayList.indexOf(bankNameEditTextAddBeneficiary.getText().toString());
//                Log.d("System out", String.valueOf(index));
//                bankCodeEditTextAddBeneficiary.setText(bankNetworkListJsonPojos.get(0).getData().get(index).getBankCode());
//                bankAddressEditTextAddBeneficiary.setText(bankNetworkListJsonPojos.get(0).getData().get(index).getBankAddress());
//                bankBranchName = bankNetworkListJsonPojos.get(0).getData().get(index).getBranchName();
//                BankBranchCode = bankNetworkListJsonPojos.get(0).getData().get(index).getBankCode();
//                BankBranchNamevalidation = bankNetworkListJsonPojos.get(0).getData().get(index).getBankName() + "," + bankNetworkListJsonPojos.get(0).getData().get(index).getBranchName();
////                bankBranchName = bankNetworkListJsonPojos.get(0).getData().get(position).get;
////                receiverId = walletSuggestionListPojos.get(0).getData().get(position).getUserID();
//            }
//        });
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
                Constants.hideKeyboard(AddBeneficiaryNextActivity.this);
                int index = stringArrayList.indexOf(bankNameEditTextAddBeneficiary.getText().toString());
                Log.d("System out", String.valueOf(index));
//                bankCodeEditTextAddBeneficiary.setText(data.get(index).getBankCode());
                bankAddressEditTextAddBeneficiary.setText(data.get(index).getBankAddress());
                bankBranchName = data.get(index).getBranchName();
                BankBranchCode = data.get(index).getBankCode();
                BankBranchNamevalidation = data.get(index).getBankName() + "," + data.get(index).getBranchName();
//                receiverId = walletSuggestionListPojos.get(0).getData().get(position).getUserID();
            }
        });
        bankNameEditTextAddBeneficiary.setEnabled(false);
        bankNameNewEditTextAddBeneficiary.setEnabled(false);
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
                    bankAddressEditTextAddBeneficiary.setText("");
                    bankNameEditTextAddBeneficiary.setEnabled(false);
                    bankNameNewEditTextAddBeneficiary.setEnabled(false);
                } else if (main_length > 3) {
                    bankNameNewEditTextAddBeneficiary.setText("");
                    bankAddressEditTextAddBeneficiary.setText("");
                    bankAddressEditTextAddBeneficiary.setText("");
                    bankNameNewEditTextAddBeneficiary.setEnabled(true);
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


    }

    private void purposeOfTransferJsonCall() {
        JSONObject jsonObject = new JSONObject();
        Constants.showProgress(AddBeneficiaryNextActivity.this);
        try {
            jsonObject.put("countryCode", countryShortCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";

//        Constants.showProgress(ProfileActivity.this);
        Call<List<PurposeOfTransferListPojo>> call = RestClient.get().getPurposeOfTransferJsonCall(json);

        call.enqueue(new Callback<List<PurposeOfTransferListPojo>>() {

            @Override
            public void onResponse(Call<List<PurposeOfTransferListPojo>> call, Response<List<PurposeOfTransferListPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    purposeOfTransferListPojos.addAll(response.body());
                    if (purposeOfTransferListPojos.get(0).getStatus() == true) {
                        Constants.closeProgress();
                        ArrayList<String> countryList = new ArrayList<>();
                        for (int i = 0; i < purposeOfTransferListPojos.get(0).getData().size(); i++) {
                            countryList.add(new String(purposeOfTransferListPojos.get(0).getData().get(i).getPurposeOfTranfer().trim()));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBeneficiaryNextActivity.this, android.R.layout.simple_spinner_item, countryList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        purposeOfTransferSpinnerAddBeneficiary.setAdapter(adapter);

//                        for (int i = 0; i < purposeOfTransferListPojos.get(0).getData().size(); i++) {
//                            if (getUserData().getCountryID() == purposeOfTransferListPojos.get(0).getData().get(i).getCountryID()) {
//                                countryId = getUserData().getCountryID();
//                                countryCode = getUserData().getUserCountryCode();
//                                purposeOfTransferSpinnerAddBeneficiary.setSelection(i + 1);
//                                break;
//                            }
//                        }

                        purposeOfTransferSpinnerAddBeneficiary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    purposeCode = purposeOfTransferListPojos.get(0).getData().get(position).getPurposeOfTransferID();
//                                    countryId = purposeOfTransferListPojos.get(0).getData().get(position).getCountryID();
//                                    if (purposeCode.equalsIgnoreCase("12")) {
//                                        otherPurposeOfTransferEditTextAddBeneficiary.setVisibility(View.VISIBLE);
//                                        otherPurposeOfTransferEditTextAddBeneficiary.setFloatingLabelText(purposeOfTransferSpinnerAddBeneficiary.getSelectedItem().toString());
//                                        otherPurposeOfTransferEditTextAddBeneficiary.setHint(purposeOfTransferSpinnerAddBeneficiary.getSelectedItem().toString());
//                                    } else {
//                                        otherPurposeOfTransferEditTextAddBeneficiary.setVisibility(View.GONE);
//                                    }

                                    if (purposeCode.equalsIgnoreCase("12")||purposeCode.equalsIgnoreCase("13")) {
                                        otherPurposeOfTransferEditTextAddBeneficiary.setVisibility(View.VISIBLE);
                                        otherPurposeOfTransferEditTextAddBeneficiary.setFloatingLabelText(purposeOfTransferSpinnerAddBeneficiary.getSelectedItem().toString()+"*");
                                        otherPurposeOfTransferEditTextAddBeneficiary.setHint(purposeOfTransferSpinnerAddBeneficiary.getSelectedItem().toString()+"*");
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
                mIntent = new Intent(AddBeneficiaryNextActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);

                break;
            case R.id.addTextViewAddBeneficiary:
                if (bankAccountNumberEditTextAddBeneficiaryNext.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this, accountnumbermsg);
                } else if (bankAccountNumberEditTextAddBeneficiaryNext.getText().toString().length() < 8) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this, minimumaccountmsg);
                } else if (bankReAccountNumberEditTextAddBeneficiaryNext.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this
                            , "Please Enter bankReAccountNumber");
                } else if (!bankAccountNumberEditTextAddBeneficiaryNext.getText().toString().equalsIgnoreCase(bankReAccountNumberEditTextAddBeneficiaryNext.getText().toString())) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this
                            , "Account number and Reaccount number does not match");
                }
                else if (accountHolderNameEditTextAddBeneficiaryNext.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this, "Please Enter Accountholdername");
                }
                 else if (ifscCodeEditTextAddBeneficiaryNext.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this, branchcodemsg);

                } else if (bankAddressEditTextAddBeneficiary.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this, branchaddmsg);

                }
                else if (spinnerflag &&bankNameEditTextAddBeneficiary.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this
                            , banknamemsg);
                }
                else if (!spinnerflag && bankNameNewEditTextAddBeneficiary.getText().toString().length() == 0) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this
                            , banknamemsg);

                }
//                else if (!bankCodeEditTextAddBeneficiary.getText().toString().equalsIgnoreCase(BankBranchCode)) {
//                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this, validbankmsg);
//                }
//                else if (!bankAddressEditTextAddBeneficiary.getText().toString().equalsIgnoreCase(BankbranchAddress)) {
//                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this, "Please Enter Valid Branch Address");
//                }
//                else if (phoneNumberEditTextAddBeneficiary.getText().toString().length() == 0) {
//                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this, mobilenumber);
//
//                } else if (phoneNumberEditTextAddBeneficiary.getText().toString().length() < 7) {
//                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this, validmobilenumber);
//
//                }
                else if (purposeOfTransferSpinnerAddBeneficiary == null && purposeOfTransferSpinnerAddBeneficiary.getSelectedItem() == null) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this, purposetransfermsg);
                } else if (purposeOfTransferSpinnerAddBeneficiary.getSelectedItem() == null) {
                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this, purposetransfermsg);
                }
//                else if (purposeCode.equalsIgnoreCase("12")&& otherPurposeOfTransferEditTextAddBeneficiary.getText().toString().length()==0) {
//
//                        Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this
//                                , "Please specify purpose of transfer");
//                }
                else if ((purposeCode.equalsIgnoreCase("12")||purposeCode.equalsIgnoreCase("13"))&&otherPurposeOfTransferEditTextAddBeneficiary.getText().toString().length()==0) {

                    Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this
                            , "Please specify other purpose of transfer");
                }
                else {
                    if (IsNetworkConnection.checkNetworkConnection(this)) {
                        addBeneficiaryJsonCall();
                    } else {
                        Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, this, nointernetmsg);
                    }
                }

                break;
        }
    }

    private void addBeneficiaryJsonCall() {
        JSONObject jsonObject = new JSONObject();
        Constants.showProgress(AddBeneficiaryNextActivity.this);
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
//            jsonObject.put("DateOfBirth",Constants.formatDate(beneficiaryInfoListPojo.getDateOfBirth(), "dd /MM /yyyy", "MM/dd/yyyy"));
            jsonObject.put("DateOfBirth", Constants.formatDate(beneficiaryInfoListPojo.getDateOfBirth(), "dd MM yyyy", "MM/dd/yyyy"));
//            jsonObject.put("DateOfBirth", beneficiaryInfoListPojo.getDateOfBirth());
            jsonObject.put("Telephone", beneficiaryInfoListPojo.getTelephone());
            jsonObject.put("Nationality", beneficiaryInfoListPojo.getNationality());
            jsonObject.put("IDNumber", beneficiaryInfoListPojo.getIDNumber());
            jsonObject.put("IDType", beneficiaryInfoListPojo.getIDType());
            jsonObject.put("idTypeDescription", beneficiaryInfoListPojo.getIDtype_Description());
//            jsonObject.put("PayOutCurrency", "INR");
            jsonObject.put("PayOutCurrency", beneficiaryInfoListPojo.getPayoutcurrency());
            jsonObject.put("PaymentMode", "BANK");
//            jsonObject.put("PayOutBranchCode", bankCodeEditTextAddBeneficiary.getText().toString());
            jsonObject.put("PayOutBranchCode", ifscCodeEditTextAddBeneficiaryNext.getText().toString());
            if (spinnerflag) {
                jsonObject.put("BankName",bankNameEditTextAddBeneficiary.getText().toString());
            } else {
                jsonObject.put("BankName", bankNameNewEditTextAddBeneficiary.getText().toString());
            }
//            jsonObject.put("BankCountry", "IND");
            jsonObject.put("BankCountry", countryShortCode);
            jsonObject.put("BranchNameAndAddress", bankAddressEditTextAddBeneficiary.getText().toString());
//            jsonObject.put("BankCode", bankCodeEditTextAddBeneficiary.getText().toString());
            if (spinnerflag) {
                jsonObject.put("BankCode",BankBranchCode);
            } else {
                jsonObject.put("BankCode", ifscCodeEditTextAddBeneficiaryNext.getText().toString());
            }
            jsonObject.put("BankBranch", bankBranchName);
            jsonObject.put("AccountNumber", bankAccountNumberEditTextAddBeneficiaryNext.getText().toString());
            jsonObject.put("PurposeCode", purposeCode);
//            jsonObject.put("PayoutCountryCode", "IND");
            jsonObject.put("PayoutCountryCode", countryShortCode);
            jsonObject.put("BeneficiaryNo", "0");
//            jsonObject.put("CustomerRelation", "0");
            jsonObject.put("CustomerRelation", beneficiaryInfoListPojo.getRelation());
            jsonObject.put("userID", getUserData().getUserID());
            jsonObject.put("benificaryState", beneficiaryInfoListPojo.getState());
            jsonObject.put("benificaryCity", beneficiaryInfoListPojo.getCity());
            jsonObject.put("benificaryIDTypeIssueDate",beneficiaryInfoListPojo.getIdIssueDate());
            jsonObject.put("benificaryIDTypeExpiryDate",beneficiaryInfoListPojo.getIdExpireyDate());
            if (purposeCode.equalsIgnoreCase("12")||purposeCode.equalsIgnoreCase("13"))
            {
                jsonObject.put("benificaryPurposeDescription", otherPurposeOfTransferEditTextAddBeneficiary.getText().toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "add beneficiary json " + json);
//        Constants.showProgress(ProfileActivity.this);
        Call<List<CreateBeneficiaryJsonPojo>> call = RestClient.get().createBeneficiaryJsonCall(json);

        call.enqueue(new Callback<List<CreateBeneficiaryJsonPojo>>() {

            @Override
            public void onResponse(Call<List<CreateBeneficiaryJsonPojo>> call, Response<List<CreateBeneficiaryJsonPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    createBeneficiaryJsonPojos.clear();
                    createBeneficiaryJsonPojos.addAll(response.body());
                    if (createBeneficiaryJsonPojos.get(0).getStatus() == true) {
                        Constants.closeProgress();
                        if (createBeneficiaryJsonPojos.get(0).getData() != null) {

                            if (datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()) != null) {
                                Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                            } else {
                                Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this, response.body().get(0).getInfo().toString());
                            }

                            Constants.bankNextBenificaryCount++;
                            createBeneficiaryJsonPojosdata.addAll(response.body().get(0).getData());
                            beneficiaryInfoListPojo.setBeneficiarynumber(createBeneficiaryJsonPojosdata.get(0).getBeneficiaryNo());
//                            beneficiaryInfoListPojo.setPayoutbranchcode(bankCodeEditTextAddBeneficiary.getText().toString());
                            beneficiaryInfoListPojo.setPayoutbranchcode(ifscCodeEditTextAddBeneficiaryNext.getText().toString());
                            mIntent = new Intent(AddBeneficiaryNextActivity.this, PinVerificationActivity.class);
                            mIntent.putExtra("flagimage", beneficiaryInfoListPojo.getCountryFlagImage());
                            mIntent.putExtra("countryshortcode", countryShortCode);
                            mIntent.putExtra("come_from", "selectcashnext");
                            mIntent.putExtra("beneficiary_data", beneficiaryInfoListPojo);
                            startActivity(mIntent);
                        }
                    } else {
                        Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this, response.body().get(0).getInfo().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CreateBeneficiaryJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    private void bankListJsonCall(String str) {
        String foo = ifscCodeEditTextAddBeneficiaryNext.getText().toString();
        if (foo.length() > 0) { //just checks that there is something. You may want to check that length is greater than or equal to 3
            String bar = foo.substring(0, 2);
        }
        JSONObject jsonObject = new JSONObject();
//        Constants.showProgress(AddBeneficiaryNextActivity.this);
        try {
            jsonObject.put("countryCode", countryShortCode);
            jsonObject.put("bankName", "");
            jsonObject.put("bankIFSC", ifscCodeEditTextAddBeneficiaryNext.getText().toString().trim());
//            jsonObject.put("branchName", beneficiaryinfoPojo.getLandMark());
            jsonObject.put("branchName", "");
            jsonObject.put("city", cityname);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "bank list json " + json);
//        Constants.showProgress(ProfileActivity.this);
        Call<List<BankNetworkListJsonPojo>> call = RestClient.get().getBankNetworkJsonCall(json);
        call.enqueue(new Callback<List<BankNetworkListJsonPojo>>() {

            @Override
            public void onResponse(Call<List<BankNetworkListJsonPojo>> call, Response<List<BankNetworkListJsonPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    bankNetworkListJsonPojos.clear();
                    bankNetworkListJsonPojos.addAll(response.body());
                    if (!bankNetworkListJsonPojos.get(0).equals(null)) {
                        if (bankNetworkListJsonPojos.get(0).getStatus()) {
                            if (bankNetworkListJsonPojos.get(0).getData() != null) {
                                Constants.showProgress(AddBeneficiaryNextActivity.this);
                                Object object = bankNetworkListJsonPojos.get(0).getData();
//                                String jsonStr = String.valueOf(object);
//                                jsonStr = jsonStr.substring(jsonStr.indexOf("[") + 1);
                                Log.d("system out", object.toString());
                                Log.d("system out banknetwork", new Gson().toJson(bankNetworkListJsonPojos.get(0).getData().toString()));

                                Constants.closeProgress();
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
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBeneficiaryNextActivity.this, android.R.layout.simple_spinner_item, stringArrayList);
//                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                    bankNameSpinner.setAdapter(adapter);
                                    bankNameEditTextAddBeneficiary.setAdapter(adapter);
                                    bankNameEditTextAddBeneficiary.setEnabled(true);

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
                                    bankNameNewEditTextAddBeneficiary.setVisibility(View.VISIBLE);
                                    bankNameEditTextAddBeneficiary.setEnabled(false);
                                    bankAddressEditTextAddBeneficiary.setEnabled(false);
                                    BankDataPojo data = new Gson().fromJson(new Gson().toJson(bankNetworkListJsonPojos.get(0).getData()), BankDataPojo.class);
                                    bankNameNewEditTextAddBeneficiary.setText(data.getBankName());
                                    bankAddressEditTextAddBeneficiary.setText(data.getBankAddress());

                                }
                            }


                        } else {
                            Constants.showMessage(mainAddBeneficiaryNextActivityLinearLayout, AddBeneficiaryNextActivity.this
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
                bankNameNewEditTextAddBeneficiary.setText("");
                bankAddressEditTextAddBeneficiary.setText("");
            }
        });
    }
}
