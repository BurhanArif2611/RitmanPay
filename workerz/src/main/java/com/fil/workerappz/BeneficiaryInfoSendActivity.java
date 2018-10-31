package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.pojo.BeneficiaryInfoListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.SendMoneyBeneficiaryJsonPojo;
import com.fil.workerappz.pojo.SendReceiveMoneyJsonPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
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
 * Created by FUSION on 15/03/2018.
 */

public class BeneficiaryInfoSendActivity extends ActionBarActivity {
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
    @BindView(R.id.send_now_textview)
    TextView sendNowTextview;
    @BindView(R.id.image_Senderflag)
    ImageView imageSenderflag;
    @BindView(R.id.text_SenderCountryName)
    TextView textSenderCountryName;
    @BindView(R.id.image_ReceiverFlag)
    ImageView imageReceiverFlag;
    @BindView(R.id.text_ReceiverCountryName)
    TextView textReceiverCountryName;
    @BindView(R.id.skipTextViewViewHeader2)
    TextView skipTextViewViewHeader2;
    @BindView(R.id.EdittextSendMoney)
    EditText EdittextSendMoney;
    @BindView(R.id.EdittextReceiveMoney)
    EditText EdittextReceiveMoney;
    @BindView(R.id.LinearBeneficiarSendLayout)
    LinearLayout LinearBeneficiarSendLayout;
    @BindView(R.id.TextviewSendingAmount)
    TextView TextviewSendingAmount;
    @BindView(R.id.TextViewExchangeRate)
    TextView TextViewExchangeRate;
    @BindView(R.id.TextViewServicecharge)
    TextView TextViewServicecharge;
    @BindView(R.id.TextviewReceivingAmount)
    TextView TextviewReceivingAmount;
    @BindView(R.id.TextviewTotalPayble)
    TextView TextviewTotalPayble;
    @BindView(R.id.sendingamountTextview)
    TextView sendingamountTextview;
    @BindView(R.id.exchangeratetextview)
    TextView exchangeratetextview;
    @BindView(R.id.servicechargetextview)
    TextView servicechargetextview;
    @BindView(R.id.receivingamounttextview)
    TextView receivingamounttextview;
    @BindView(R.id.totalpayabletextview)
    TextView totalpayabletextview;
    @BindView(R.id.converttextview)
    TextView converttextview;
    private ArrayList<SendReceiveMoneyJsonPojo> sendReceiveMoneyJsonPojos = new ArrayList<>();
    private ArrayList<SendMoneyBeneficiaryJsonPojo> sendReceiveMoneyBeneficiaryJsonPojos = new ArrayList<>();
    private BeneficiaryInfoListPojo beneficiaryInfoListPojo;
    private Boolean CheckFlagData = false;
    private Boolean CheckFlag = false;
    private String payoutcurrency = "", payincurrency = "", totalpayable = "";
    private SessionManager sessionManager;
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private LabelListData datumLable_languages = new LabelListData();
    private String vaildamountmsg, sendmoneymsg, receivemoneymsg;
    private String nointernetmsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beneficiaryinfo_send_layout);
        ButterKnife.bind(this);
        menuImageViewHeader2.setImageResource(R.drawable.back_btn);


        textSenderCountryName.setText(getUserData().getCountryCurrencySymbol());
        sessionManager = new SessionManager(BeneficiaryInfoSendActivity.this);

        try {

            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {

                titleTextViewViewHeader2.setText(datumLable_languages.getBeneficiaryInfo());
                EdittextSendMoney.setHint(datumLable_languages.getSendMoney());
                EdittextReceiveMoney.setHint(datumLable_languages.getReceiveMoney());
                sendNowTextview.setText(datumLable_languages.getSendNow());
                sendingamountTextview.setText(datumLable_languages.getSendingAmount() + ":");
                exchangeratetextview.setText(datumLable_languages.getExchangeRate() + ":");
                servicechargetextview.setText(datumLable_languages.getServiceCharges() + ":");
                receivingamounttextview.setText(datumLable_languages.getReceivingAmount() + ":");
                totalpayabletextview.setText(datumLable_languages.getTotalPayble() + ":");
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();


            } else {
                sendNowTextview.setText("Send Now");
                titleTextViewViewHeader2.setText("Beneficiary Info");
                EdittextSendMoney.setHint(getResources().getString(R.string.send_money));
                EdittextReceiveMoney.setHint(getResources().getString(R.string.receive_money));
                sendingamountTextview.setText(getResources().getString(R.string.service_charges));
                receivingamounttextview.setText(getResources().getString(R.string.receiving_amount));
                totalpayabletextview.setText(getResources().getString(R.string.total_payable));
                nointernetmsg = getResources().getString(R.string.no_internet);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        titleTextViewViewHeader2.setText("Transfer Details");

        if (datumLable_languages_msg != null) {

            vaildamountmsg = datumLable_languages_msg.getEnterValidAmount();
            sendmoneymsg = datumLable_languages_msg.getEnterSendMoney();
            receivemoneymsg = datumLable_languages_msg.getEnterReceiveMoney();


        } else {
            vaildamountmsg = getResources().getString(R.string.valid_amount);
            sendmoneymsg = getResources().getString(R.string.Please_Enter_send_money);
            receivemoneymsg = getResources().getString(R.string.Please_Enter_receive_money);

        }
//        setupUI(LinearBeneficiarSendLayout);
        Picasso.with(this)
                .load(Constants.FLAG_URL + getIntent().getExtras().getString("flagimage"))
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image)
                .into(imageReceiverFlag);

        Picasso.with(this)
                .load(Constants.FLAG_URL + sessionManager.getuserflagimage())
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image)
                .into(imageSenderflag);

        filterImageViewHeader2.setVisibility(View.INVISIBLE);
        beneficiaryInfoListPojo = (BeneficiaryInfoListPojo) getIntent().getSerializableExtra("beneficiary_data");
        textReceiverCountryName.setText(beneficiaryInfoListPojo.getPayoutcurrency());




        EdittextSendMoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean hasfocus) {
                if (hasfocus) {
                    CheckFlag = false;
                } else {
                    CheckFlag = true;
                }
            }
        });
        EdittextReceiveMoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean hasfocus) {
                if (hasfocus) {
                    CheckFlag = true;
                } else {
                    CheckFlag = false;
                }
            }
        });
        EdittextSendMoney.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    //do something
                    if (IsNetworkConnection.checkNetworkConnection(BeneficiaryInfoSendActivity.this)) {
                        CheckFlag = false;
                        if (EdittextSendMoney.getText().toString().length() == 0) {
                            Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, vaildamountmsg);
                        } else if (EdittextSendMoney.getText().toString().trim().equalsIgnoreCase(".") || EdittextReceiveMoney.getText().toString().trim().equalsIgnoreCase("0") || EdittextSendMoney.getText().toString().startsWith(".")) {
                            Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, vaildamountmsg);
                        } else {
                            sendReceiveMoneyJsonCall(EdittextSendMoney.getText().toString(), getUserData().getCountryCurrencySymbol());
                        }
                    } else {
                        Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, nointernetmsg);
                    }

//                    exerciseintensitylowimageview.setImageResource(R.drawable.tab_selected_exercies_);

                }
                return false;
            }
        });

        EdittextReceiveMoney.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    //do something
                    if (IsNetworkConnection.checkNetworkConnection(BeneficiaryInfoSendActivity.this)) {
                        CheckFlag = true;
                        if (EdittextReceiveMoney.getText().toString().length() == 0) {

                            Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, vaildamountmsg);
                        } else if (EdittextReceiveMoney.getText().toString().trim().equalsIgnoreCase(".") || EdittextReceiveMoney.getText().toString().trim().equalsIgnoreCase("0") || EdittextReceiveMoney.getText().toString().startsWith(".") || EdittextReceiveMoney.getText().toString().startsWith("0")) {
                            Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, vaildamountmsg);
                        } else {
                            sendReceiveMoneyJsonCall(EdittextReceiveMoney.getText().toString(), beneficiaryInfoListPojo.getPayoutcurrency());
                        }
                    } else {
                        Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, nointernetmsg);
                    }

//                    exerciseintensitylowimageview.setImageResource(R.drawable.tab_selected_exercies_);

                }
                return false;
            }
        });
    }

    @OnClick({R.id.menuImageViewHeader2, R.id.send_now_textview, R.id.appImageViewHeader2,R.id.converttextview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
                finish();
                break;
                case R.id.converttextview:
                    if (IsNetworkConnection.checkNetworkConnection(BeneficiaryInfoSendActivity.this)) {
                        if (CheckFlag) {
                            Constants.hideKeyboard(BeneficiaryInfoSendActivity.this);
                            if (EdittextReceiveMoney.getText().toString().length() == 0) {

                                Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, vaildamountmsg);
                            } else if (EdittextReceiveMoney.getText().toString().trim().equalsIgnoreCase(".") || EdittextReceiveMoney.getText().toString().trim().equalsIgnoreCase("0") || EdittextReceiveMoney.getText().toString().startsWith(".") || EdittextReceiveMoney.getText().toString().startsWith("0")) {
                                Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, vaildamountmsg);
                            } else {
                                sendReceiveMoneyJsonCall(EdittextReceiveMoney.getText().toString(), beneficiaryInfoListPojo.getPayoutcurrency());
                            }

                        } else {
                            Constants.hideKeyboard(BeneficiaryInfoSendActivity.this);
                            if (EdittextSendMoney.getText().toString().length() == 0) {
                                Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, vaildamountmsg);
                            } else if (EdittextSendMoney.getText().toString().trim().equalsIgnoreCase(".") || EdittextReceiveMoney.getText().toString().trim().equalsIgnoreCase("0") || EdittextSendMoney.getText().toString().startsWith(".")) {
                                Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, vaildamountmsg);
                            } else {
                                sendReceiveMoneyJsonCall(EdittextSendMoney.getText().toString(), getUserData().getCountryCurrencySymbol());
                            }

                        }
                    } else {
                        Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, nointernetmsg);
                    }
                break;
            case R.id.appImageViewHeader2:
                Intent mIntent = new Intent(BeneficiaryInfoSendActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
            case R.id.send_now_textview:
                Constants.hideKeyboard(BeneficiaryInfoSendActivity.this);
                if (checkValidation() == true && CheckFlagData) {
                    if (IsNetworkConnection.checkNetworkConnection(BeneficiaryInfoSendActivity.this)) {
                        sendMoneyJsonCall();
                    } else {
                        Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, nointernetmsg);
                    }
                }
                break;
        }
    }

    private boolean checkValidation() {
        boolean checkFlag = true;
        if (EdittextSendMoney.getText().toString().length() == 0) {
            Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, sendmoneymsg);
            checkFlag = false;
        } else if (EdittextReceiveMoney.getText().toString().length() == 0) {
            Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, receivemoneymsg);
            checkFlag = false;
        }
        return checkFlag;
    }

    private void sendReceiveMoneyJsonCall(String s, String countryCurrencySymbol) {
        JSONObject jsonObject = new JSONObject();
        Constants.showProgress(BeneficiaryInfoSendActivity.this);
        try {
            jsonObject.put("productType", "0");
            jsonObject.put("payoutBranchCode", String.valueOf(beneficiaryInfoListPojo.getPayoutbranchcode()));
            jsonObject.put("payInCurrency", getUserData().getCountryCurrencySymbol());
            jsonObject.put("payoutCurrency", beneficiaryInfoListPojo.getPayoutcurrency());
//            jsonObject.put("transferCurrency", beneficiaryInfoListPojo.getPayoutcurrency());
            jsonObject.put("transferCurrency", countryCurrencySymbol);
            jsonObject.put("transferAmount", s);
            jsonObject.put("customerNo", String.valueOf(Constants.bankBenificaryCount));
            jsonObject.put("beneficiaryNo", String.valueOf(beneficiaryInfoListPojo.getBeneficiarynumber()));
            jsonObject.put("txnNo", "RP0875361893");
            jsonObject.put("nickName", beneficiaryInfoListPojo.getNickName());
//            jsonObject.put("nickName", "");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "sendreceivemoneyjson " + json);

        Call<List<SendReceiveMoneyJsonPojo>> call = RestClient.get().getSendReceiveMoneyJsonCall(json);

        call.enqueue(new Callback<List<SendReceiveMoneyJsonPojo>>() {

            @Override
            public void onResponse(Call<List<SendReceiveMoneyJsonPojo>> call, Response<List<SendReceiveMoneyJsonPojo>> response) {
                Constants.closeProgress();
                sendReceiveMoneyJsonPojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    sendReceiveMoneyJsonPojos.addAll(response.body());
                    if (sendReceiveMoneyJsonPojos.get(0).getStatus() == true) {
                        if (CheckFlag == false) {
//                            Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, response.body().get(0).getInfo());
                            String ab = (sendReceiveMoneyJsonPojos.get(0).getData().getExchangeRate().trim());
                            String cd = (EdittextSendMoney.getText().toString().trim());
                            Double aa, bb, abcd = 0.0;
                            if (!ab.equalsIgnoreCase("") && !cd.equalsIgnoreCase("")) {
                                aa = Double.parseDouble(ab);
                                bb = Double.parseDouble(cd);
                                abcd = aa * bb;
                            }

                            EdittextReceiveMoney.setText(String.valueOf(String.format("%.2f", abcd)));
                            TextviewSendingAmount.setText(sendReceiveMoneyJsonPojos.get(0).getData().getPayInCurrency() + " " + sendReceiveMoneyJsonPojos.get(0).getData().getPayInAmount());
                            TextViewServicecharge.setText(sendReceiveMoneyJsonPojos.get(0).getData().getCommission());
                            TextViewExchangeRate.setText(sendReceiveMoneyJsonPojos.get(0).getData().getExchangeRate() + " " + sendReceiveMoneyJsonPojos.get(0).getData().getPayoutCurrency() + " " + "=" + "1" + " " + sendReceiveMoneyJsonPojos.get(0).getData().getPayInCurrency());
                            TextviewReceivingAmount.setText(sendReceiveMoneyJsonPojos.get(0).getData().getPayoutCurrency() + " " + (String.valueOf(String.format("%.2f", abcd))));
                            Double a = Double.valueOf(sendReceiveMoneyJsonPojos.get(0).getData().getPayoutAmount());
                            Double b = Double.valueOf(sendReceiveMoneyJsonPojos.get(0).getData().getCommission());
                            Double c = a + b;
//                            TextviewTotalPayble.setText((sendReceiveMoneyJsonPojos.get(0).getData().getPayInCurrency()) + " " + String.valueOf(c));
                            TextviewTotalPayble.setText((sendReceiveMoneyJsonPojos.get(0).getData().getPayInCurrency()) + " " + String.valueOf(sendReceiveMoneyJsonPojos.get(0).getData().getTotalPayable()));
                            payoutcurrency = sendReceiveMoneyJsonPojos.get(0).getData().getPayoutCurrency();
                            payincurrency = sendReceiveMoneyJsonPojos.get(0).getData().getPayInCurrency();
                            totalpayable = sendReceiveMoneyJsonPojos.get(0).getData().getTotalPayable();
                            if (EdittextReceiveMoney.getText().toString().length() == 0 && TextviewSendingAmount.getText().toString().length() == 0 && TextViewServicecharge.getText().toString().length() == 0 && TextViewExchangeRate.getText().toString().length() == 0 && TextviewReceivingAmount.getText().toString().length() == 0 && TextviewTotalPayble.getText().toString().length() == 0) {
                                CheckFlagData = false;
                            } else {
                                CheckFlagData = true;
                            }

                        } else if (CheckFlag == true) {
//                            Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, response.body().get(0).getInfo());
                            String abreceive = (sendReceiveMoneyJsonPojos.get(0).getData().getExchangeRate().trim());
                            String cdreceive = (EdittextReceiveMoney.getText().toString().trim());
                            Double aareceive, bbreceive, abcdreceive = 0.0;
                            if (!abreceive.equalsIgnoreCase("") && !cdreceive.equalsIgnoreCase("")) {
                                aareceive = Double.parseDouble(abreceive);
                                bbreceive = Double.parseDouble(cdreceive);
                                abcdreceive = aareceive * bbreceive;
                            }
                            EdittextSendMoney.setText(String.valueOf(sendReceiveMoneyJsonPojos.get(0).getData().getPayInAmount()));
                            TextviewSendingAmount.setText(sendReceiveMoneyJsonPojos.get(0).getData().getPayInCurrency() + " " + sendReceiveMoneyJsonPojos.get(0).getData().getPayInAmount());
                            TextViewServicecharge.setText(sendReceiveMoneyJsonPojos.get(0).getData().getCommission());
                            TextViewExchangeRate.setText(sendReceiveMoneyJsonPojos.get(0).getData().getExchangeRate() + " " + sendReceiveMoneyJsonPojos.get(0).getData().getPayoutCurrency() + " " + "=" + "1" + " " + sendReceiveMoneyJsonPojos.get(0).getData().getPayInCurrency());
                            TextviewReceivingAmount.setText(sendReceiveMoneyJsonPojos.get(0).getData().getPayoutCurrency() + " " + sendReceiveMoneyJsonPojos.get(0).getData().getPayoutAmount());
                            Double areceive = Double.valueOf(sendReceiveMoneyJsonPojos.get(0).getData().getPayoutAmount());
                            Double breceive = Double.valueOf(sendReceiveMoneyJsonPojos.get(0).getData().getCommission());
                            Double creceive = areceive + breceive;
                            TextviewTotalPayble.setText((sendReceiveMoneyJsonPojos.get(0).getData().getPayInCurrency()) + " " + sendReceiveMoneyJsonPojos.get(0).getData().getTotalPayable());
                            payoutcurrency = sendReceiveMoneyJsonPojos.get(0).getData().getPayoutCurrency();
                            payincurrency = sendReceiveMoneyJsonPojos.get(0).getData().getPayInCurrency();
                            totalpayable = sendReceiveMoneyJsonPojos.get(0).getData().getTotalPayable();

                            if (EdittextReceiveMoney.getText().toString().length() == 0 && TextviewSendingAmount.getText().toString().length() == 0 && TextViewServicecharge.getText().toString().length() == 0 && TextViewExchangeRate.getText().toString().length() == 0 && TextviewReceivingAmount.getText().toString().length() == 0 && TextviewTotalPayble.getText().toString().length() == 0) {
                                CheckFlagData = false;
                            } else {
                                CheckFlagData = true;
                            }


                        }
                    }
                } else {
                    Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                }

            }

            @Override
            public void onFailure(Call<List<SendReceiveMoneyJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    if (IsNetworkConnection.checkNetworkConnection(BeneficiaryInfoSendActivity.this)) {
                        if (CheckFlag) {
                            Constants.hideKeyboard(BeneficiaryInfoSendActivity.this);
                            if (EdittextReceiveMoney.getText().toString().length() == 0) {

                                Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, vaildamountmsg);
                            } else if (EdittextReceiveMoney.getText().toString().trim().equalsIgnoreCase(".") || EdittextReceiveMoney.getText().toString().trim().equalsIgnoreCase("0") || EdittextReceiveMoney.getText().toString().startsWith(".") || EdittextReceiveMoney.getText().toString().startsWith("0")) {
                                Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, vaildamountmsg);
                            } else {
                                sendReceiveMoneyJsonCall(EdittextReceiveMoney.getText().toString(), beneficiaryInfoListPojo.getPayoutcurrency());
                            }

                        } else {
                            Constants.hideKeyboard(BeneficiaryInfoSendActivity.this);
                            if (EdittextSendMoney.getText().toString().length() == 0) {
                                Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, vaildamountmsg);
                            } else if (EdittextSendMoney.getText().toString().trim().equalsIgnoreCase(".") || EdittextReceiveMoney.getText().toString().trim().equalsIgnoreCase("0") || EdittextSendMoney.getText().toString().startsWith(".")) {
                                Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, vaildamountmsg);
                            } else {
                                sendReceiveMoneyJsonCall(EdittextSendMoney.getText().toString(), getUserData().getCountryCurrencySymbol());
                            }

                        }
                    } else {
                        Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, nointernetmsg);
                    }

//                    exerciseintensitylowimageview.setImageResource(R.drawable.tab_selected_exercies_);


                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

//                setupUI(innerView);
            }
        }
    }

    private void sendMoneyJsonCall() {
        JSONObject jsonObject = new JSONObject();
        Constants.showProgress(BeneficiaryInfoSendActivity.this);
        try {
            jsonObject.put("userID", String.valueOf(getUserData().getUserID()));
            jsonObject.put("BeneficiaryNo", String.valueOf(beneficiaryInfoListPojo.getBeneficiarynumber()));
            jsonObject.put("PayOutCurrency", payoutcurrency);
            jsonObject.put("TransferAmount", EdittextReceiveMoney.getText().toString());
            jsonObject.put("Payout_Partner_Unique_Code", "");
            jsonObject.put("Payout_Country", beneficiaryInfoListPojo.getPayoutcountry());
            jsonObject.put("PaymentMode", beneficiaryInfoListPojo.getActivitytype());
            jsonObject.put("Settlement_Currency", getUserData().getCountryCurrencySymbol());
            jsonObject.put("Remitter_PayIn_Amt", totalpayable);
            jsonObject.put("Message_ToBeneficiary", "");
            jsonObject.put("transactionCurrency", payincurrency);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "send money json " + json);

        Call<List<SendMoneyBeneficiaryJsonPojo>> call = RestClient.get().sendMoneyJsonCall(json);

        call.enqueue(new Callback<List<SendMoneyBeneficiaryJsonPojo>>() {

            @Override
            public void onResponse(Call<List<SendMoneyBeneficiaryJsonPojo>> call, Response<List<SendMoneyBeneficiaryJsonPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    sendReceiveMoneyBeneficiaryJsonPojos.addAll(response.body());
                    if (sendReceiveMoneyBeneficiaryJsonPojos.get(0).getStatus() == true) {
//                        Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, response.body().get(0).getInfo());
//
//                        mIntent = new Intent(BeneficiaryInfoSendActivity.this, HomeActivity.class);
//                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(mIntent);
//                        Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, datumLable_languages_msg.getRequestSuccessful()+sendReceiveMoneyBeneficiaryJsonPojos.get(0).getBalance());

                        Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, TextviewTotalPayble.getText().toString()+" "+"deducted from your account and receiver will get"+" "+TextviewReceivingAmount.getText().toString());


                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.removeCallbacks(this);
                                finish();
                                Intent mIntent;
//                                mIntent = new Intent(BeneficiaryInfoSendActivity.this, HomeActivity.class);
//                                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(mIntent);
                                mIntent = new Intent(BeneficiaryInfoSendActivity.this, TransactionHistoryActivity.class);
                                startActivity(mIntent);
//                                onRestart();
                            }
                        }, 3000);


                    } else {
                        if (response.body().get(0).getInfo() != null) {
                            if (datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()).equalsIgnoreCase("")) {
                                Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, response.body().get(0).getInfo().toString());
                            } else {
                                Constants.showMessage(LinearBeneficiarSendLayout, BeneficiaryInfoSendActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SendMoneyBeneficiaryJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

}