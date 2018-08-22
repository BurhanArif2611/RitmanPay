package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.pojo.JsonListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.WalletToWalletTransferListPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HS on 24-Feb-18.
 * FIL AHM
 */

public class WalletActivity extends ActionBarActivity {

    @BindView(R.id.backImageViewHeader)
    ImageView backImageViewHeader;
    @BindView(R.id.titleTextViewViewHeader)
    TextView titleTextViewViewHeader;
    @BindView(R.id.skipTextViewViewHeader)
    TextView skipTextViewViewHeader;
    @BindView(R.id.amountNeedToTransferTextViewWallet)
    TextView amountNeedToTransferTextViewWallet;
    @BindView(R.id.payNowTextViewWallet)
    TextView payNowTextViewWallet;
    @BindView(R.id.pinEditTextWallet)
    MaterialEditText pinEditTextWallet;
    @BindView(R.id.mainWalletLinearLayout)
    LinearLayout mainWalletLinearLayout;
    @BindView(R.id.walletsuretextview)
    TextView walletsuretextview;

    private Intent mIntent;
    private String amount = "";
    private int receiverId;
    private Currency currency;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private SessionManager sessionManager;
    private String verifiedpinmsg;
    private String nointernetmsg;
    private String receivername="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wallet);
        ButterKnife.bind(this);


        try {
            sessionManager = new SessionManager(WalletActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {
                walletsuretextview.setText(datumLable_languages.getDeductMoneyFromWallet());
                pinEditTextWallet.setHint(datumLable_languages.getEnterWalletPIN());
                pinEditTextWallet.setFloatingLabelText(datumLable_languages.getEnterWalletPIN());
                payNowTextViewWallet.setText(datumLable_languages.getPayNow());
                titleTextViewViewHeader.setText(datumLable_languages.getWallet());
                nointernetmsg=datumLable_languages.getNoInternetConnectionAvailable();
            } else {
                titleTextViewViewHeader.setText(getResources().getString(R.string.wallet));
                walletsuretextview.setText(getResources().getString(R.string.are_you_sure_want_to_deduct_money_from_wallet));
                pinEditTextWallet.setHint(getResources().getString(R.string.enter_wallet_pin));
                payNowTextViewWallet.setText(getResources().getString(R.string.pay_now));
                nointernetmsg=getResources().getString(R.string.no_internet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (datumLable_languages_msg != null) {
            verifiedpinmsg = datumLable_languages_msg.getEnter6DigitPIN();
        } else {
            verifiedpinmsg = getResources().getString(R.string.Please_Enter_pin);

        }
        mIntent = getIntent();
        if (mIntent != null) {
            amount = mIntent.getStringExtra("amount");
            receiverId = mIntent.getIntExtra("receiverWalletUserID", 0);
            receivername = mIntent.getStringExtra("receiverWalletname");

            currency = Currency.getInstance(getUserData().getCountryCurrencySymbol());
            amountNeedToTransferTextViewWallet.setText(currency.getSymbol() + " " + (amount));
        }
    }

    private void walletToWalletTransferJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", Constants.language_id);
            jsonObject.put("userID", getMyUserId());
            jsonObject.put("amount", amount);
            jsonObject.put("receiverWalletUserID", receiverId);
            jsonObject.put("transactionCurrency", getUserData().getCountryCurrencySymbol());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "profile json " + json);
//        Constants.showProgress(WalletActivity.this);
        Call<List<WalletToWalletTransferListPojo>> call = RestClient.get().walletToWalletTransferJsonCall(json);

        call.enqueue(new Callback<List<WalletToWalletTransferListPojo>>() {

            @Override
            public void onResponse(Call<List<WalletToWalletTransferListPojo>> call, Response<List<WalletToWalletTransferListPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    if (response.body().get(0).getStatus() == true) {

                        if (response.body().get(0).getInfo().toString().equalsIgnoreCase("TRANSFER_SUCCESS"))
                        {
                            String test = datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString());

                            String str = datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString());
                            String [] twoStringArray= str.split(",", 2);
                           Log.d("System out","String befor comma = "+twoStringArray[0]);

                            String lastWord = test.substring(test.lastIndexOf(" ") + 1);

                            Log.d("System out", "text" + String.valueOf(lastWord));
                            String finalmsg = test.replace(twoStringArray[0].substring(twoStringArray[0].lastIndexOf(" ")+1),receivername).replace(lastWord, String.valueOf(response.body().get(0).getData().get(0).getBalance()));
                            Constants.showMessage(mainWalletLinearLayout, WalletActivity.this, finalmsg);
                        }
                        else
                        {
                            Constants.showMessage(mainWalletLinearLayout, WalletActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                        }

                        SessionManager sessionManager = new SessionManager(WalletActivity.this);
                        sessionManager.setWalletBalance(response.body().get(0).getData().get(0).getBalance());
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.removeCallbacks(this);
                                finish();
                                mIntent = new Intent(WalletActivity.this, HomeActivity.class);
//                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mIntent);
//                                onRestart();
                            }
                        }, 3500);
//                        finish();

                    } else {
                        Constants.showMessage(mainWalletLinearLayout, WalletActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));

                    }
                }
            }

            @Override
            public void onFailure(Call<List<WalletToWalletTransferListPojo>> call, Throwable t) {
                Constants.closeProgress();
                t.printStackTrace();
            }
        });
    }

    @OnClick({R.id.backImageViewHeader, R.id.payNowTextViewWallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImageViewHeader:
                finish();
                break;
            case R.id.payNowTextViewWallet:
                Constants.hideKeyboard(WalletActivity.this);
                if (checkValidation() == true) {
                    if (IsNetworkConnection.checkNetworkConnection(WalletActivity.this)) {
                        verifiedPinJsonCall();
                    } else {
                        Constants.showMessage(mainWalletLinearLayout, WalletActivity.this,nointernetmsg);
                    }
                }
                break;
        }
    }

    private boolean checkValidation() {
        boolean checkFlag = true;
        if (pinEditTextWallet.getText().toString().trim().length() == 0) {
            Constants.showMessage(mainWalletLinearLayout, WalletActivity.this, verifiedpinmsg);
            checkFlag = false;
        } else if (pinEditTextWallet.getText().toString().trim().length() < 6) {
            Constants.showMessage(mainWalletLinearLayout, WalletActivity.this, verifiedpinmsg);
            checkFlag = false;
        }
        return checkFlag;
    }

    private void verifiedPinJsonCall() {
        Constants.showProgress(WalletActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userPin", pinEditTextWallet.getText().toString().trim());
            jsonObject.put("userMobile", getUserData().getUserMobile());
            jsonObject.put("userID", getMyUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "pin json " + json);

        Call<List<JsonListPojo>> call = RestClient.get().verifyPinJsonCall(json);

        call.enqueue(new Callback<List<JsonListPojo>>() {
            @Override
            public void onResponse(Call<List<JsonListPojo>> call, Response<List<JsonListPojo>> response) {
//                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    if (response.body().get(0).getStatus() == true) {
                        walletToWalletTransferJsonCall();
                    } else {
                        Constants.showMessage(mainWalletLinearLayout, WalletActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                        Constants.closeProgress();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JsonListPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }
}
