package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.pojo.CustomerCardBalanceJsonPojo;
import com.fil.workerappz.pojo.CustomerCardJsonPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;

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
 * Created by FUSION on 09/04/2018.
 */

public class PrepaidCardActivity extends ActionBarActivity {

    @BindView(R.id.TextviewBalancePrepaidCard)
    TextView TextviewBalancePrepaidCard;
    @BindView(R.id.TextviewCardNumber)
    TextView TextviewCardNumber;
    @BindView(R.id.prepaiedcardActivityLinearLayout)
    LinearLayout prepaiedcardActivityLinearLayout;
    @BindView(R.id.AddCardTextView)
    TextView AddCardTextView;
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
    @BindView(R.id.textviewbalance)
    TextView textviewbalance;
    @BindView(R.id.linearcard)
    LinearLayout linearcard;
    @BindView(R.id.card_recycleview)
    RecyclerView cardRecycleview;
    @BindView(R.id.textviewcardnumber)
    TextView textviewcardnumber;


    private ArrayList<CustomerCardJsonPojo> customerCardJsonPojos = new ArrayList<>();
    private ArrayList<CustomerCardBalanceJsonPojo> customerCardBalanceJsonPojos = new ArrayList<>();
    private String idCustomer = "",nointernetmsg;
    private String idCard = "";
    Boolean flag = false;
    private PrepaiedCardAdapter prepaiedCardAdapter;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.prepaidcard);
        ButterKnife.bind(this);

        menuImageViewHeader2.setImageResource(R.drawable.back_btn);
        filterImageViewHeader2.setVisibility(View.INVISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(PrepaidCardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        cardRecycleview.setLayoutManager(layoutManager);
        prepaiedCardAdapter = new PrepaiedCardAdapter(PrepaidCardActivity.this);
        cardRecycleview.setAdapter(prepaiedCardAdapter);

        try {
            sessionManager = new SessionManager(PrepaidCardActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {

                titleTextViewViewHeader2.setText(datumLable_languages.getPrepaidCard());
                textviewcardnumber.setText(datumLable_languages.getCardNo());
                textviewbalance.setText(datumLable_languages.getBalance());
                nointernetmsg=datumLable_languages.getNoInternetConnectionAvailable();

            } else {
                titleTextViewViewHeader2.setText(getResources().getString(R.string.prepaid_card));
                textviewcardnumber.setText(getResources().getString(R.string.card_no));
                textviewbalance.setText(getResources().getString(R.string.balance));
                nointernetmsg=getResources().getString(R.string.no_internet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (IsNetworkConnection.checkNetworkConnection(PrepaidCardActivity.this)) {
            customerCardJsonCall();
        } else {
            Constants.showMessage(prepaiedcardActivityLinearLayout, PrepaidCardActivity.this,nointernetmsg);
        }
    }

    private void customerCardJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", "" + getMyUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "customer card json " + json);
        Call<List<CustomerCardJsonPojo>> call = RestClient.get().customerCardJsonCall(json);

        call.enqueue(new Callback<List<CustomerCardJsonPojo>>() {
            @Override
            public void onResponse(Call<List<CustomerCardJsonPojo>> call, Response<List<CustomerCardJsonPojo>> response) {
                customerCardJsonPojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    customerCardJsonPojos.addAll(response.body());
                    if (customerCardJsonPojos.get(0).getStatus() == true) {
                        linearcard.setVisibility(View.VISIBLE);
                        textviewbalance.setVisibility(View.VISIBLE);
                        TextviewBalancePrepaidCard.setText(" " + customerCardJsonPojos.get(0).getData().get(0).getCurrencyCode() + " " + customerCardJsonPojos.get(0).getData().get(0).getBalance());
                        TextviewCardNumber.setText(customerCardJsonPojos.get(0).getData().get(0).getCardNumber());
                        idCustomer = customerCardJsonPojos.get(0).getData().get(0).getIdCustomer();
                        idCard = customerCardJsonPojos.get(0).getData().get(0).getIdCard();
                        flag = true;
//                        Constants.showMessage(prepaiedcardActivityLinearLayout, PrepaidCardActivity.this, response.body().get(0).getInfo());
                    } else {
                        linearcard.setVisibility(View.GONE);
                        textviewbalance.setVisibility(View.GONE);
                        Constants.showMessage(prepaiedcardActivityLinearLayout, PrepaidCardActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo()));
                        flag = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CustomerCardJsonPojo>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void customerCardBalanceJsonCall() {
        Constants.showProgress(PrepaidCardActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id_customer", idCustomer);
            jsonObject.put("userID", "" + getMyUserId());
            jsonObject.put("id_card", idCard);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "customer card balance json " + json);
        Call<List<CustomerCardBalanceJsonPojo>> call = RestClient.get().customerCardBalanceJsonCall(json);

        call.enqueue(new Callback<List<CustomerCardBalanceJsonPojo>>() {
            @Override
            public void onResponse(Call<List<CustomerCardBalanceJsonPojo>> call, Response<List<CustomerCardBalanceJsonPojo>> response) {
                Constants.closeProgress();
                customerCardBalanceJsonPojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    customerCardBalanceJsonPojos.addAll(response.body());
                    if (customerCardBalanceJsonPojos.get(0).getStatus() == true) {

                        Constants.showMessage(prepaiedcardActivityLinearLayout, PrepaidCardActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo()));
                    } else {

                        Constants.closeProgress();
                        Constants.showMessage(prepaiedcardActivityLinearLayout, PrepaidCardActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CustomerCardBalanceJsonPojo>> call, Throwable t) {
                t.printStackTrace();
                Constants.closeProgress();
            }
        });
    }

    @OnClick({R.id.menuImageViewHeader2, R.id.AddCardTextView, R.id.appImageViewHeader2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
                finish();
                break;
            case R.id.appImageViewHeader2:
                Intent mIntent = new Intent(PrepaidCardActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
            case R.id.AddCardTextView:
                if (flag) {
                    if (IsNetworkConnection.checkNetworkConnection(PrepaidCardActivity.this)) {
                        customerCardBalanceJsonCall();
                    } else {
                        Constants.showMessage(prepaiedcardActivityLinearLayout, PrepaidCardActivity.this,nointernetmsg);
                    }
                }
                break;
        }
    }
}
