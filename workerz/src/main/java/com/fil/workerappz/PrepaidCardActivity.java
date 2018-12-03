package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.fil.workerappz.utils.SlideHolder;

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
    @BindView(R.id.homeImageViewFooter)
    ImageView homeImageViewFooter;
    @BindView(R.id.homeTextViewFooter)
    TextView homeTextViewFooter;
    @BindView(R.id.homeLinearLayoutFooter)
    LinearLayout homeLinearLayoutFooter;
    @BindView(R.id.kycImageViewFooter)
    ImageView kycImageViewFooter;
    @BindView(R.id.kycTextViewFooter)
    TextView kycTextViewFooter;
    @BindView(R.id.kycLinearLayoutFooter)
    LinearLayout kycLinearLayoutFooter;
    @BindView(R.id.quickPayImageViewFooter)
    ImageView quickPayImageViewFooter;
    @BindView(R.id.quickPayTextViewFooter)
    TextView quickPayTextViewFooter;
    @BindView(R.id.quickPayLinearLayoutFooter)
    LinearLayout quickPayLinearLayoutFooter;
    @BindView(R.id.beneficiaryImageViewFooter)
    ImageView beneficiaryImageViewFooter;
    @BindView(R.id.beneficiaryTextViewFooter)
    TextView beneficiaryTextViewFooter;
    @BindView(R.id.beneficiaryLinearLayoutFooter)
    LinearLayout beneficiaryLinearLayoutFooter;
    @BindView(R.id.historyImageViewFooter)
    ImageView historyImageViewFooter;
    @BindView(R.id.historyTextViewFooter)
    TextView historyTextViewFooter;
    @BindView(R.id.historyLinearLayoutFooter)
    LinearLayout historyLinearLayoutFooter;
    @BindView(R.id.slideHolderPrepaiedCard)
    SlideHolder slideHolderPrepaiedCard;
    private ArrayList<CustomerCardJsonPojo> customerCardJsonPojos = new ArrayList<>();
    private ArrayList<CustomerCardBalanceJsonPojo> customerCardBalanceJsonPojos = new ArrayList<>();
    private String idCustomer = "", nointernetmsg;
    private String idCard = "";
    Boolean flag = false;
    private PrepaiedCardAdapter prepaiedCardAdapter;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private SessionManager sessionManager;
    private String comeFrom = "";
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.prepaidcard);
        ButterKnife.bind(this);
        filterImageViewHeader2.setVisibility(View.INVISIBLE);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(PrepaidCardActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        cardRecycleview.setLayoutManager(layoutManager);
//        prepaiedCardAdapter = new PrepaiedCardAdapter(PrepaidCardActivity.this);
//        cardRecycleview.setAdapter(prepaiedCardAdapter);

        try {
            sessionManager = new SessionManager(PrepaidCardActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {

                titleTextViewViewHeader2.setText(datumLable_languages.getPrepaidCard());
//                textviewcardnumber.setText(datumLable_languages.getCardNo());
//                textviewbalance.setText(datumLable_languages.getBalance());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();

            } else {
                titleTextViewViewHeader2.setText(getResources().getString(R.string.prepaid_card));
//                textviewcardnumber.setText(getResources().getString(R.string.card_no));
//                textviewbalance.setText(getResources().getString(R.string.balance));
                nointernetmsg = getResources().getString(R.string.no_internet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        homeSelection();

        mIntent = getIntent();
        if (mIntent != null) {
            comeFrom = mIntent.getStringExtra("come_from");


        }
        if (comeFrom.equalsIgnoreCase("")) {
            menuImageViewHeader2.setImageResource(R.drawable.back_btn);


        } else {
        }
    }


//    private void customerCardJsonCall() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("userID", "" + getMyUserId());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String json = "[" + jsonObject + "]";
//        CustomLog.d("System out", "customer card json " + json);
//        Call<List<CustomerCardJsonPojo>> call = RestClient.get().customerCardJsonCall(json);
//
//        call.enqueue(new Callback<List<CustomerCardJsonPojo>>() {
//            @Override
//            public void onResponse(Call<List<CustomerCardJsonPojo>> call, Response<List<CustomerCardJsonPojo>> response) {
//                customerCardJsonPojos.clear();
//                if (response.body() != null && response.body() instanceof ArrayList) {
//                    customerCardJsonPojos.addAll(response.body());
//                    if (customerCardJsonPojos.get(0).getStatus() == true) {
//                        linearcard.setVisibility(View.VISIBLE);
//                        textviewbalance.setVisibility(View.VISIBLE);
//                        TextviewBalancePrepaidCard.setText(" " + customerCardJsonPojos.get(0).getData().get(0).getCurrencyCode() + " " + customerCardJsonPojos.get(0).getData().get(0).getBalance());
//                        TextviewCardNumber.setText(customerCardJsonPojos.get(0).getData().get(0).getCardNumber());
//                        idCustomer = customerCardJsonPojos.get(0).getData().get(0).getIdCustomer();
//                        idCard = customerCardJsonPojos.get(0).getData().get(0).getIdCard();
//                        flag = true;
////                        Constants.showMessage(prepaiedcardActivityLinearLayout, PrepaidCardActivity.this, response.body().get(0).getInfo());
//                    } else {
//                        linearcard.setVisibility(View.GONE);
//                        textviewbalance.setVisibility(View.GONE);
//                        Constants.showMessage(prepaiedcardActivityLinearLayout, PrepaidCardActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo()));
//                        flag = false;
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<CustomerCardJsonPojo>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }


//    private void customerCardBalanceJsonCall() {
//        Constants.showProgress(PrepaidCardActivity.this);
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("id_customer", idCustomer);
//            jsonObject.put("userID", "" + getMyUserId());
//            jsonObject.put("id_card", idCard);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String json = "[" + jsonObject + "]";
//        CustomLog.d("System out", "customer card balance json " + json);
//        Call<List<CustomerCardBalanceJsonPojo>> call = RestClient.get().customerCardBalanceJsonCall(json);
//
//        call.enqueue(new Callback<List<CustomerCardBalanceJsonPojo>>() {
//            @Override
//            public void onResponse(Call<List<CustomerCardBalanceJsonPojo>> call, Response<List<CustomerCardBalanceJsonPojo>> response) {
//                Constants.closeProgress();
//                customerCardBalanceJsonPojos.clear();
//                if (response.body() != null && response.body() instanceof ArrayList) {
//                    customerCardBalanceJsonPojos.addAll(response.body());
//                    if (customerCardBalanceJsonPojos.get(0).getStatus() == true) {
//
//                        Constants.showMessage(prepaiedcardActivityLinearLayout, PrepaidCardActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo()));
//                    } else {
//
//                        Constants.closeProgress();
//                        Constants.showMessage(prepaiedcardActivityLinearLayout, PrepaidCardActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo()));
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<CustomerCardBalanceJsonPojo>> call, Throwable t) {
//                t.printStackTrace();
//                Constants.closeProgress();
//            }
//        });
//    }

    private void homeSelection() {
        homeImageViewFooter.setImageResource(R.drawable.footer_icon_home_selected);
        homeTextViewFooter.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    @OnClick({R.id.kycLinearLayoutFooter, R.id.quickPayLinearLayoutFooter, R.id.beneficiaryLinearLayoutFooter, R.id.historyLinearLayoutFooter, R.id.menuImageViewHeader2, R.id.appImageViewHeader2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
                if (comeFrom.equalsIgnoreCase("")) {
                    finish();
                } else {
                    slideHolderPrepaiedCard.toggle();
                }
                break;
            case R.id.kycLinearLayoutFooter:
                mIntent = new Intent(PrepaidCardActivity.this, UploadYourDocumentActivity.class);
                startActivity(mIntent);
                break;
            case R.id.quickPayLinearLayoutFooter:
                mIntent = new Intent(PrepaidCardActivity.this, PinVerificationActivity.class);
                mIntent.putExtra("come_from", "quick_pay");
                startActivity(mIntent);
                break;
            case R.id.beneficiaryLinearLayoutFooter:
                mIntent = new Intent(PrepaidCardActivity.this, SelectBeneficiaryActivity.class);
                mIntent.putExtra("come_from", "");
                startActivity(mIntent);
                break;
            case R.id.historyLinearLayoutFooter:
                mIntent = new Intent(PrepaidCardActivity.this, PinVerificationActivity.class);
                mIntent.putExtra("come_from", "history");
                startActivity(mIntent);
                break;
            case R.id.appImageViewHeader2:
                mIntent = new Intent(PrepaidCardActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
        }
    }
}
