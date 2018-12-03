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

import com.fil.workerappz.pojo.AddWalletListJsonPojo;
import com.fil.workerappz.pojo.CountryData;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.DrawerMenu;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
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

public class AddMoneyToWalletActivity extends ActionBarActivity {

    @BindView(R.id.backImageViewHeader)
    ImageView backImageViewHeader;
    @BindView(R.id.titleTextViewViewHeader)
    TextView titleTextViewViewHeader;
    @BindView(R.id.skipTextViewViewHeader)
    TextView skipTextViewViewHeader;
    @BindView(R.id.addMoneyTextViewToWallet)
    TextView addMoneyTextViewToWallet;
    @BindView(R.id.accountBalanceTextViewAddMoney)
    TextView accountBalanceTextViewAddMoney;
    @BindView(R.id.pointsTextViewAddMoney)
    TextView pointsTextViewAddMoney;
    @BindView(R.id.addMoneyAmountEditText)
    MaterialEditText addMoneyAmountEditText;
    @BindView(R.id.mainAddMoneyToWalletLinearLayout)
    LinearLayout mainAddMoneyToWalletLinearLayout;
    @BindView(R.id.image_SenderflagLoadMoney)
    ImageView imageSenderflagLoadMoney;
    @BindView(R.id.text_SenderCountryNameLoadMoney)
    TextView textSenderCountryNameLoadMoney;
    @BindView(R.id.textviewbalnce)
    TextView textviewbalnce;
    @BindView(R.id.textviewpoints)
    TextView textviewpoints;
    @BindView(R.id.appImageViewHeader1)
    ImageView appImageViewHeader1;

    private Intent mIntent;
    private SessionManager sessionManager;
    private final ArrayList<AddWalletListJsonPojo> addWalletListJsonPojos = new ArrayList<>();
    private Currency currency;
    private final ArrayList<CountryData> countryListPojos = new ArrayList<>();
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String amountmsg, vaildamountmsg;
    private String nointernetmsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_money_to_wallet);
        ButterKnife.bind(this);


        sessionManager = new SessionManager(AddMoneyToWalletActivity.this);

        currency = Currency.getInstance(sessionManager.userProfileData().getCountryCurrencySymbol());
        accountBalanceTextViewAddMoney.setText(sessionManager.userProfileData().getCountryCurrencySymbol() + " " + getWalletBalance());


        Constants.showProgress(AddMoneyToWalletActivity.this);
//        if (IsNetworkConnection.checkNetworkConnection(this)) {
//            if (SugarRecord.count(CountryData.class) > 0) {
//                countryListPojos.addAll(SugarRecord.listAll(CountryData.class));
//                for (int i = 0; i < countryListPojos.size(); i++) {
//                    if (sessionManager.userProfileData().getCountryCurrencySymbol().equalsIgnoreCase(countryListPojos.get(i).getCountryCurrencySymbol())) {
//                        sessionManager.setuserflagimage(countryListPojos.get(i).getCountryFlagImage());
//                        break;
//                    }
//
//                }
//            }
//        }

        try {

            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {
                textviewbalnce.setText(datumLable_languages.getBalance() + ":");
                textviewpoints.setText(datumLable_languages.getPoints() + ":");
                addMoneyAmountEditText.setHint(datumLable_languages.getEnterAmount());
                addMoneyAmountEditText.setFloatingLabelText(datumLable_languages.getEnterAmount());
                addMoneyTextViewToWallet.setText(datumLable_languages.getAddMoney());
                titleTextViewViewHeader.setText(datumLable_languages.getAddMoneyToWallet());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();
            } else {
                titleTextViewViewHeader.setText(getResources().getString(R.string.add_money_to_wallet));
                textviewbalnce.setText(getResources().getString(R.string.balance));
                textviewpoints.setText(getResources().getString(R.string.points));
                addMoneyAmountEditText.setHint(getResources().getString(R.string.enter_amount));
                addMoneyTextViewToWallet.setText(getResources().getString(R.string.add_money));
                nointernetmsg = getResources().getString(R.string.no_internet);

            }

        } catch (
                Exception e)

        {
            e.printStackTrace();
        }
        if (datumLable_languages_msg != null) {
            amountmsg = datumLable_languages_msg.getEnterAmount();
            vaildamountmsg = datumLable_languages_msg.getEnterValidAmount();


        } else {

            amountmsg = getResources().getString(R.string.Please_Enter_amount);
            vaildamountmsg = getResources().getString(R.string.Please_Enter_valid_amount);


        }

        Picasso.with(this)
                .load(Constants.FLAG_URL + sessionManager.getuserflagimage())
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image)
                .into(imageSenderflagLoadMoney);
        Constants.closeProgress();
        textSenderCountryNameLoadMoney.setText(getUserData().getCountryCurrencySymbol());
        appImageViewHeader1.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.backImageViewHeader, R.id.addMoneyTextViewToWallet, R.id.appImageViewHeader1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImageViewHeader:
                finish();
                break;
            case R.id.appImageViewHeader1:
                mIntent = new Intent(AddMoneyToWalletActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                finish();
                break;
            case R.id.addMoneyTextViewToWallet:
//                mIntent = new Intent(AddMoneyToWalletActivity.this, SelectPaymentMethodActivity.class);
//                startActivity(mIntent);
                Constants.hideKeyboard(AddMoneyToWalletActivity.this);
                if (checkValidation() == true) {
                    if (IsNetworkConnection.checkNetworkConnection(this)) {
                        addMoneyToWalletJsonCall();
                    } else {
                        Constants.showMessage(mainAddMoneyToWalletLinearLayout, this, nointernetmsg);
                    }
                }
                break;
        }
    }

    private boolean checkValidation() {
        boolean checkFlag = true;
        if (addMoneyAmountEditText.getText().toString().trim().length() == 0) {
            Constants.showMessage(mainAddMoneyToWalletLinearLayout, AddMoneyToWalletActivity.this, amountmsg);
            return false;
        } else if (Constants.findNumericValue(addMoneyAmountEditText.getText().toString().trim()) <= 0 || addMoneyAmountEditText.getText().toString().trim().equalsIgnoreCase(".") || addMoneyAmountEditText.getText().toString().trim().equalsIgnoreCase("0") || addMoneyAmountEditText.getText().toString().startsWith(".")) {
            Constants.showMessage(mainAddMoneyToWalletLinearLayout, AddMoneyToWalletActivity.this, vaildamountmsg);
            return false;
        }
        return checkFlag;
    }

    private void addMoneyToWalletJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", Constants.language_id);
            jsonObject.put("userID", getMyUserId());
            jsonObject.put("amount", addMoneyAmountEditText.getText().toString().trim());
            jsonObject.put("transactionCurrency", getUserData().getCountryCurrencySymbol());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "addMoney json " + json);
        Constants.showProgress(AddMoneyToWalletActivity.this);
        Call<List<AddWalletListJsonPojo>> call = RestClient.get().addBalanceInToWalletJsonCall(json);

        call.enqueue(new Callback<List<AddWalletListJsonPojo>>() {
            @Override
            public void onResponse(Call<List<AddWalletListJsonPojo>> call, Response<List<AddWalletListJsonPojo>> response) {
                Constants.closeProgress();

                if (response.body() != null && response.body() instanceof ArrayList) {
                    addWalletListJsonPojos.clear();
                    addWalletListJsonPojos.addAll(response.body());
                    if (addWalletListJsonPojos.get(0).getStatus() == true) {
                        float balance = Float.parseFloat(addMoneyAmountEditText.getText().toString());
                        Log.d("System out", "balance" + String.valueOf(getWalletBalance()));
                        Log.d("System out", "balance" + String.valueOf(balance));

                        float balanceold = Float.parseFloat(String.valueOf(getWalletBalance()));
                        float updatedBalance = Float.parseFloat(new DecimalFormat("##.##").format(Constants.findNumericValue(String.valueOf(balanceold + balance))));
                        if ((addWalletListJsonPojos.get(0).getInfo().toString()).equalsIgnoreCase("TOPUP_SUCCESS")) {
                            String test = datumLable_languages_msg.getMessage(addWalletListJsonPojos.get(0).getInfo().toString());
                            String lastWord = test.substring(test.lastIndexOf(" ") + 1);

                            Log.d("System out", "text" + String.valueOf(lastWord));
                            String finalmsg = test.replace(lastWord, String.valueOf(updatedBalance));
                            Constants.showMessage(mainAddMoneyToWalletLinearLayout, AddMoneyToWalletActivity.this, finalmsg);
                        } else {
                            Constants.showMessage(mainAddMoneyToWalletLinearLayout, AddMoneyToWalletActivity.this, datumLable_languages_msg.getMessage(addWalletListJsonPojos.get(0).getInfo().toString()));
                        }
                        sessionManager.setWalletBalance(updatedBalance);
                        DrawerMenu.accountBalanceTextViewDrawerMenu.setText(datumLable_languages.getBalance() + ":" + " " + sessionManager.userProfileData().getCountryCurrencySymbol() + " " + sessionManager.getWalletBalance());
                        accountBalanceTextViewAddMoney.setText(sessionManager.userProfileData().getCountryCurrencySymbol() + " " + getWalletBalance());
                        addMoneyAmountEditText.setText("");


                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.removeCallbacks(this);
//                                finish();
//                                onRestart();
                            }
                        }, 2000);
//                        finish();
//                        mIntent = new Intent(AddMoneyToWalletActivity.this, HomeActivity.class);
//                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(mIntent);
                    } else {
                        Constants.showMessage(mainAddMoneyToWalletLinearLayout, AddMoneyToWalletActivity.this, datumLable_languages_msg.getMessage(addWalletListJsonPojos.get(0).getInfo().toString()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AddWalletListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();

            }
        });
    }
}
