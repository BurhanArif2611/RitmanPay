package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fil.workerappz.pojo.DingTransferPayJsonPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.ding.GetCountryList;
import com.fil.workerappz.pojo.ding.GetProductsList;
import com.fil.workerappz.pojo.ding.GetProvidersList;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.fil.workerappz.utils.SlideHolder;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
 * Created by FUSION on 22/03/2018.
 */

public class MobileTopUpActivity extends ActionBarActivity {

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
    @BindView(R.id.mobileNumberEditTextRecharge)
    MaterialEditText mobileNumberEditTextRecharge;

    @BindView(R.id.browserPlansEditTextMobileTopUp)
    MaterialEditText BrowserPlansEditTextMobileTopUp;
    @BindView(R.id.submit_textview)
    TextView submitTextview;
    @BindView(R.id.slideHolderMobileTopUp)
    SlideHolder slideHolderMobileTopUp;
    @BindView(R.id.mainMobileTopUpLinearLayout)
    LinearLayout mainMobileTopUpLinearLayout;
    @BindView(R.id.validityMobileTopUpProductDetailLinearLayout)
    TextView validityMobileTopUpProductDetailLinearLayout;
    @BindView(R.id.chargeMobileTopUpProductDetailLinearLayout)
    TextView chargeMobileTopUpProductDetailLinearLayout;
    @BindView(R.id.receiveValueMobileTopUpProductDetailLinearLayout)
    TextView receiveValueMobileTopUpProductDetailLinearLayout;
    @BindView(R.id.mobileTopUpProductDetailLinearLayout)
    LinearLayout mobileTopUpProductDetailLinearLayout;

    private final ArrayList<GetCountryList> dingCountryListJsonPojos = new ArrayList<>();
    private final ArrayList<GetProvidersList> serviceProviderJsonPojos = new ArrayList<>();
    @BindView(R.id.textcontrycode)
    TextView textcontrycode;
    @BindView(R.id.countrySpinnerMobileTopUpSpinner)
    Spinner countrySpinnerMobileTopUpSpinner;
    @BindView(R.id.linearspinner)
    LinearLayout linearspinner;
    @BindView(R.id.validityTextview)
    TextView validityTextview;
    @BindView(R.id.chargetextview)
    TextView chargetextview;
    @BindView(R.id.receivevaluetextview)
    TextView receivevaluetextview;
    @BindView(R.id.serviceProviderEditTextMobileTopUp)
    EditText serviceProviderEditTextMobileTopUp;

    @BindView(R.id.walletBalance)
    TextView walletBalance;
    @BindView(R.id.serviceProviderSpinnerMobileTopup)
    MaterialSpinner serviceProviderSpinnerMobileTopup;


    //        @BindView(R.id.imagecountrycode)
//    ImageView imagecountrycode;
    private String countryIso = "";
    private String countryPrefix = "";
    private Intent mIntent;
    private GetProductsList.Data productListData = new GetProductsList.Data();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
    private final ArrayList<DingTransferPayJsonPojo> dingTransferPayJsonPojos = new ArrayList<>();
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String selectcountry, selectplanmsg, nointernetmsg, nodatafound;
    private String mobilenumber;
    private String validmobilenumber;
    private int selctedProviderposition = -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_topup);
        ButterKnife.bind(this);


        filterImageViewHeader2.setVisibility(View.INVISIBLE);
//        textcontrycode.setVisibility(View.GONE);
//        imagecountrycode.setVisibility(View.GONE);

        try {
            sessionManager = new SessionManager(MobileTopUpActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {

                titleTextViewViewHeader2.setText(datumLable_languages.getMobileTopup());
                textcontrycode.setText(datumLable_languages.getCountry());
                mobileNumberEditTextRecharge.setHint(datumLable_languages.getRechargePhoneNumber());
                mobileNumberEditTextRecharge.setFloatingLabelText(datumLable_languages.getRechargePhoneNumber());
                serviceProviderEditTextMobileTopUp.setHint(datumLable_languages.getServiceProvider());
                BrowserPlansEditTextMobileTopUp.setHint(datumLable_languages.getBrowsePlans());
                validityTextview.setText(datumLable_languages.getValidity());
                receivevaluetextview.setText(datumLable_languages.getReceiveValue());
                submitTextview.setText(datumLable_languages.getProceedToPay());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();
                nodatafound = datumLable_languages.getNoRecordFound();
                chargetextview.setText(datumLable_languages.getCharge() + ":");

            } else {

                titleTextViewViewHeader2.setText(getResources().getString(R.string.mobile_recharge));
                textcontrycode.setText(getResources().getString(R.string.country));
                mobileNumberEditTextRecharge.setHint(getResources().getString(R.string.recharge_phone_number));
                serviceProviderEditTextMobileTopUp.setHint(getResources().getString(R.string.service_provider));
                BrowserPlansEditTextMobileTopUp.setHint(getResources().getString(R.string.browse_plans));
                validityTextview.setText(getResources().getString(R.string.validity));
                receivevaluetextview.setText(getResources().getString(R.string.receive_value));
                chargetextview.setText(getResources().getString(R.string.charge));
                submitTextview.setText(getResources().getString(R.string.proceed_to_pay));
                nointernetmsg = getResources().getString(R.string.no_internet);
                nodatafound = getResources().getString(R.string.no_record_found);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (datumLable_languages_msg != null) {
            selectcountry = datumLable_languages_msg.getSelectCountry();
            mobilenumber = datumLable_languages_msg.getEnterMobileNumber();
            validmobilenumber = datumLable_languages_msg.getEnterValidMobileNumber();
            selectplanmsg = datumLable_languages_msg.getSelectPlan();

        } else {

            selectcountry = getResources().getString(R.string.Please_select_country);
            mobilenumber = getResources().getString(R.string.Please_Enter_Mobile_number);
            validmobilenumber = getResources().getString(R.string.Please_Enter_valid_Mobile_number);
            selectplanmsg = getResources().getString(R.string.Please_select_plan);

        }
        if (IsNetworkConnection.checkNetworkConnection(this)) {
            dingCountryListJsonCall();
        } else {
            Constants.showMessage(mainMobileTopUpLinearLayout, this, nointernetmsg);
        }
        walletBalance.setText(datumLable_languages.getBALANCE() + ": " + getWalletBalance());

        mobileNumberEditTextRecharge.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {

                }
            }
        });

        mobileNumberEditTextRecharge.addTextChangedListener(new TextWatcher() {

            // the user's changes are saved here
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                if (c.length() >= 7) {
                    serviceProviderJsonCall();
                }
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });
    }
//        mobileNumberEditTextRecharge.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    Constants.hideKeyboard(MobileTopUpActivity.this);
//                    serviceProviderEditTextMobileTopUp.setText("");
//                    mobileTopUpProductDetailLinearLayout.setVisibility(View.GONE);
////                    linearbrowserplans.setVisibility(View.GONE);
//                    BrowserPlansEditTextMobileTopUp.setText("");
//                    if (textcontrycode.getText().toString().equals(datumLable_languages.getCountry())) {
//                        Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, selectcountry);
//                    } else if (mobileNumberEditTextRecharge.getText().toString().length() == 0) {
//                        Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, mobilenumber);
//                    } else if (mobileNumberEditTextRecharge.getText().toString().length() < 7) {
//                        Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, validmobilenumber);
//                    } else if (mobileNumberEditTextRecharge.getText().toString().startsWith("0")) {
//                        Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, validmobilenumber);
//                    } else {
//                        if (IsNetworkConnection.checkNetworkConnection(MobileTopUpActivity.this)) {
//                            serviceProviderJsonCall();
//                        } else {
//                            Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, nointernetmsg);
//                        }
//                    }
//
//                    return true;
//                }
//                return false;
//            }
//        });


    private void dingCountryListJsonCall() {
        Constants.showProgress(MobileTopUpActivity.this);
        Call<List<GetCountryList>> call = RestClient.get().getDingCountryListJsonCall();
        call.enqueue(new Callback<List<GetCountryList>>() {
            @Override
            public void onResponse(Call<List<GetCountryList>> call, Response<List<GetCountryList>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    dingCountryListJsonPojos.clear();
                    dingCountryListJsonPojos.addAll(response.body());
                    if (dingCountryListJsonPojos.get(0).getStatus() == true) {
                        final ArrayList<String> countryList = new ArrayList<>();
                        for (int i = 0; i < dingCountryListJsonPojos.get(0).getData().size(); i++) {
//                            countryList.add(dingCountryListJsonPojos.get(0).getData().get(i).getCountryName());
                            countryList.add(dingCountryListJsonPojos.get(0).getData().get(i).getCountryName());
                        }
                        final ArrayAdapter<String> adapterCountryName = new ArrayAdapter<>(MobileTopUpActivity.this, android.R.layout.simple_spinner_item, countryList);
                        adapterCountryName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        countrySpinnerMobileTopUpSpinner.setAdapter(adapterCountryName);


                        countrySpinnerMobileTopUpSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {

                                    parent.getChildAt(0).setVisibility(View.GONE);

                                    textcontrycode.setVisibility(View.VISIBLE);
//                                    imagecountrycode.setVisibility(View.VISIBLE);
                                    if (dingCountryListJsonPojos.get(0).getData().get(position).getInternationalDialingInformation().size() > 0) {
                                        textcontrycode.setText("+" + dingCountryListJsonPojos.get(0).getData().get(position).getInternationalDialingInformation().get(0).getPrefix());
                                        countryPrefix = dingCountryListJsonPojos.get(0).getData().get(position).getInternationalDialingInformation().get(0).getPrefix();
                                        countryIso = dingCountryListJsonPojos.get(0).getData().get(position).getCountryIso();
                                        serviceProviderJsonCall();
                                    } else {
                                        textcontrycode.setText(datumLable_languages.getCountry());

//                                        Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, nodatafound);
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
            public void onFailure(Call<List<GetCountryList>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    private void serviceProviderJsonCall() {
        JSONObject jsonObject = new JSONObject();
//        Constants.showProgress(MobileTopUpActivity.this);
        try {
            jsonObject.put("countryIsos", countryIso);
            jsonObject.put("regionCodes", "");

            jsonObject.put("mobNo", String.valueOf(countryPrefix + mobileNumberEditTextRecharge.getText().toString()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", json);
//        Constants.showProgress(ProfileActivity.this);
        Call<List<GetProvidersList>> call = RestClient.get().getProvidersJsonCall(json);

        call.enqueue(new Callback<List<GetProvidersList>>() {

            @Override
            public void onResponse(Call<List<GetProvidersList>> call, Response<List<GetProvidersList>> response) {
//                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    ArrayList<String> cityList = new ArrayList<>();
                    cityList.clear();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MobileTopUpActivity.this, android.R.layout.simple_spinner_item, cityList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    serviceProviderSpinnerMobileTopup.setAdapter(adapter);
                    serviceProviderJsonPojos.clear();
                    serviceProviderJsonPojos.addAll(response.body());
                    if (serviceProviderJsonPojos.get(0).getStatus() == true) {
                        if (serviceProviderJsonPojos.get(0).getData().size() != 0) {
//                            serviceProviderEditTextMobileTopUp.setText(serviceProviderJsonPojos.get(0).getData().get(0).getName());
                            for (int i = 0; i < serviceProviderJsonPojos.get(0).getData().size(); i++) {
                                cityList.add(serviceProviderJsonPojos.get(0).getData().get(i).getName());
//                            if (CountryCodegoogle.equalsIgnoreCase(new String(Base64.decode(cityListPojos.get(i).getCountryName().trim().getBytes(), Base64.DEFAULT)))) {
//                                countryId = cityListPojos.get(i).getCountryID();
//                                break;
//                            }


                            }
                            adapter = new ArrayAdapter<>(MobileTopUpActivity.this, android.R.layout.simple_spinner_item, cityList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            serviceProviderSpinnerMobileTopup.setAdapter(adapter);

                            serviceProviderSpinnerMobileTopup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != -1) {
                                        selctedProviderposition = position;


                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
//                            Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, datumLable_languages.getNoRecordFound());
                        }


                    } else {
                        Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, datumLable_languages_msg.getMessage(serviceProviderJsonPojos.get(0).getInfo().toString()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetProvidersList>> call, Throwable t) {
                Constants.closeProgress();
                t.printStackTrace();
            }
        });


    }

    @OnClick({R.id.menuImageViewHeader2, R.id.serviceProviderEditTextMobileTopUp, R.id.browserPlansEditTextMobileTopUp, R.id.submit_textview, R.id.appImageViewHeader2, R.id.linearspinner})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
                slideHolderMobileTopUp.toggle();
                break;
            case R.id.serviceProviderEditTextMobileTopUp:
                break;
//            case R.id.textcontrycode:
//                countrySpinnerMobileTopUpSpinner.performClick();
//                break;
            case R.id.linearspinner:
                countrySpinnerMobileTopUpSpinner.performClick();
                break;
//            case R.id.linearspinnerServiceProvider:
//                serviceProviderEditTextMobileTopUp.setText("");
//                mobileTopUpProductDetailLinearLayout.setVisibility(View.GONE);
////                    linearbrowserplans.setVisibility(View.GONE);
//                BrowserPlansEditTextMobileTopUp.setText("");
//                if (textcontrycode.getText().toString().equals(datumLable_languages.getCountry())) {
//                    Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, selectcountry);
//                } else if (mobileNumberEditTextRecharge.getText().toString().length() == 0) {
//                    Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, mobilenumber);
//                } else if (mobileNumberEditTextRecharge.getText().toString().length() < 7) {
//                    Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, validmobilenumber);
//                } else if (mobileNumberEditTextRecharge.getText().toString().startsWith("0")) {
//                    Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, validmobilenumber);
//                } else {
//                    if (IsNetworkConnection.checkNetworkConnection(MobileTopUpActivity.this)) {
//                        serviceProviderJsonCall();
//                    } else {
//                        Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, nointernetmsg);
//                    }
//                }
//
//                break;
//            case R.id.imagecountrycode:
//                countrySpinnerMobileTopUpSpinner.performClick();
//                break;
            case R.id.appImageViewHeader2:
                mIntent = new Intent(MobileTopUpActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
            case R.id.browserPlansEditTextMobileTopUp:
                Constants.hideKeyboard(MobileTopUpActivity.this
                );
                if (textcontrycode.getText().toString().equals(datumLable_languages.getCountry())) {
                    Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, selectcountry);
                } else if (mobileNumberEditTextRecharge.getText().toString().length() == 0) {
                    Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, mobilenumber);
                } else if (mobileNumberEditTextRecharge.getText().toString().length() < 10) {
                    Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, validmobilenumber);
                } else if (mobileNumberEditTextRecharge.getText().toString().startsWith("0")) {
                    Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, validmobilenumber);
                } else if (selctedProviderposition == -1) {
                    Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, datumLable_languages.getNoRecordFound());
                } else {

                    mIntent = new Intent(MobileTopUpActivity.this, BrowsePlansActivity.class);
                    mIntent.putExtra("provider", serviceProviderJsonPojos.get(0).getData().get(selctedProviderposition));
                    mIntent.putExtra("mobile_no", countryPrefix + mobileNumberEditTextRecharge.getText().toString().trim());
                    mIntent.putExtra("country_iso", countryIso);
                    startActivity(mIntent);

                }
                break;
            case R.id.submit_textview:
                if (textcontrycode.getText().toString().equals(datumLable_languages.getCountry())) {
                    Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, selectcountry);
                } else if (mobileNumberEditTextRecharge.getText().toString().length() == 0) {
                    Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, mobilenumber);
                } else if (mobileNumberEditTextRecharge.getText().toString().length() < 10) {
                    Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, validmobilenumber);
                } else if (BrowserPlansEditTextMobileTopUp.getText().toString().length() == 0) {
                    Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, selectplanmsg);
                } else {
                    if (IsNetworkConnection.checkNetworkConnection(MobileTopUpActivity.this)) {
                        dingTransferPayJsonCall();
                    } else {
                        Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, nointernetmsg);
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Constants.productListData != null) {
            productListData = Constants.productListData;
            mobileTopUpProductDetailLinearLayout.setVisibility(View.VISIBLE);
            chargeMobileTopUpProductDetailLinearLayout.setText(productListData.getMaximum().getSendCurrencyIso() + " " + productListData.getMaximum().getSendValue());
            if (productListData.getValidityPeriodIso() == null) {
                validityMobileTopUpProductDetailLinearLayout.setText("NA");
            } else {
                String validity = productListData.getValidityPeriodIso();
                if (validity.startsWith("P")) {
                    validityMobileTopUpProductDetailLinearLayout.setText(validity.substring(1));
                } else {
                    validityMobileTopUpProductDetailLinearLayout.setText("NA");
                }
            }

            receiveValueMobileTopUpProductDetailLinearLayout.setText(productListData.getMaximum().getReceiveCurrencyIso() + " " + productListData.getMaximum().getReceiveValue());
            BrowserPlansEditTextMobileTopUp.setText(productListData.getDefaultDisplayText());
            Constants.productListData = null;
        }

    }

    private void dingTransferPayJsonCall() {
        String sendervalue = chargeMobileTopUpProductDetailLinearLayout.getText().toString();
        String[] separated = sendervalue.split(" ");
        String Currencyiso = separated[0];
        String sendvalue = separated[1];
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CustomLog.d("System out", sdf.format(timestamp));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SkuCode", productListData.getSkuCode());
            jsonObject.put("SendValue", sendvalue);
            jsonObject.put("SendCurrencyIso", Currencyiso);
            if (getUserData().getDingmode().equalsIgnoreCase("UAT")) {
                jsonObject.put("MobNo", String.valueOf(productListData.getUatNumber()));
            } else {
                jsonObject.put("MobNo", String.valueOf(countryPrefix + mobileNumberEditTextRecharge.getText().toString()));

            }
            jsonObject.put("DistributorRef", String.valueOf(sdf.format(timestamp)));
            jsonObject.put("userID", getUserData().getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "Transfer Pay json " + json);
        Constants.showProgress(MobileTopUpActivity.this);
        Call<List<DingTransferPayJsonPojo>> call = RestClient.get().dingPaymentTransferJsonCall(json);

        call.enqueue(new Callback<List<DingTransferPayJsonPojo>>() {
            @Override
            public void onResponse(Call<List<DingTransferPayJsonPojo>> call, Response<List<DingTransferPayJsonPojo>> response) {
                Constants.closeProgress();
                dingTransferPayJsonPojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    dingTransferPayJsonPojos.addAll(response.body());
                    if (response.body().get(0).getStatus() == true) {
                        Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, datumLable_languages_msg.getRequestSuccessful());
//                        Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, dingTransferPayJsonPojos.get(0).getInfo());
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.removeCallbacks(this);
//                                finish();
//                                onRestart();
                                mIntent = new Intent(MobileTopUpActivity.this, HomeActivity.class);
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mIntent);
                                finish();
                            }
                        }, 3300);

                    } else {
                        Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, datumLable_languages_msg.getMessage(dingTransferPayJsonPojos.get(0).getInfo().toString()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DingTransferPayJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
                Constants.showMessage(mainMobileTopUpLinearLayout, MobileTopUpActivity.this, datumLable_languages_msg.getMessage(dingTransferPayJsonPojos.get(0).getInfo().toString()));
                t.printStackTrace();

            }
        });
    }


}