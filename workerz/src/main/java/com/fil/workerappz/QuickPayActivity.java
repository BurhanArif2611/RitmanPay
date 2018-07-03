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

import com.fil.workerappz.adapter.QuickPayAdapter;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.QuickPayJsonPojo;
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
 * Created by HS on 13-Mar-18.
 * FIL AHM
 */

public class QuickPayActivity extends ActionBarActivity {


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
    @BindView(R.id.quickPayRecyclerView)
    RecyclerView quickPayRecyclerView;
    @BindView(R.id.slideHolderQuickPay)
    SlideHolder slideHolderQuickPay;
    @BindView(R.id.mainQuickPayLinearLayout)
    LinearLayout mainQuickPayLinearLayout;
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
    private RecyclerView.LayoutManager layoutManager;
    private QuickPayAdapter quickPayAdapter;
    private final ArrayList<QuickPayJsonPojo> quickPayJsonPojos = new ArrayList<>();
    private Intent mIntent;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String nointernetmsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.quick_pay);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(QuickPayActivity.this);


        filterImageViewHeader2.setVisibility(View.INVISIBLE);

        quickPaySelection();


        try {
            sessionManager = new SessionManager(QuickPayActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {


                homeTextViewFooter.setText(datumLable_languages.getHome());
                quickPayTextViewFooter.setText(datumLable_languages.getQuickPay());
                beneficiaryTextViewFooter.setText(datumLable_languages.getBeneficiary());
                historyTextViewFooter.setText(datumLable_languages.getHistory());
                titleTextViewViewHeader2.setText(datumLable_languages.getQuickPay());
                nointernetmsg=datumLable_languages.getNoInternetConnectionAvailable();


            } else {
                titleTextViewViewHeader2.setText(getResources().getString(R.string.quick_pay));
                homeTextViewFooter.setText(getResources().getString(R.string.home));
                quickPayTextViewFooter.setText(getResources().getString(R.string.quick_pay));
                beneficiaryTextViewFooter.setText(getResources().getString(R.string.beneficiary));
                historyTextViewFooter.setText(getResources().getString(R.string.history));
                nointernetmsg=getResources().getString(R.string.no_internet);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (IsNetworkConnection.checkNetworkConnection(QuickPayActivity.this)) {
            getQuickPayJsonCall();
        } else {
            Constants.showMessage(mainQuickPayLinearLayout, QuickPayActivity.this,nointernetmsg);
        }
    }

    private void getQuickPayJsonCall() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", getMyUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "quick pay json " + json);

        Constants.showProgress(QuickPayActivity.this);
        Call<List<QuickPayJsonPojo>> call = RestClient.get().getQuickPayJsonCall(json);

        call.enqueue(new Callback<List<QuickPayJsonPojo>>() {
            @Override
            public void onResponse(Call<List<QuickPayJsonPojo>> call, Response<List<QuickPayJsonPojo>> response) {
                Constants.closeProgress();

                if (response.body() != null && response.body() instanceof ArrayList) {
                    if (response.body().get(0).getStatus() == true) {
                        quickPayJsonPojos.addAll(response.body());
                        if (quickPayJsonPojos.get(0).getStatus() == true) {
                            quickPayRecyclerView.setLayoutManager(layoutManager);
                            quickPayAdapter = new QuickPayAdapter(QuickPayActivity.this, quickPayJsonPojos, datumLable_languages);
                            quickPayRecyclerView.setAdapter(quickPayAdapter);
                        } else {
                            Constants.showMessage(mainQuickPayLinearLayout, QuickPayActivity.this, datumLable_languages_msg.getMessage(quickPayJsonPojos.get(0).getInfo().toString()));
                        }
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<QuickPayJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    private void quickPaySelection() {
        quickPayImageViewFooter.setImageResource(R.drawable.footer_icon_quick_pay_selected);
        quickPayTextViewFooter.setTextColor(getResources().getColor(R.color.colorWhite));
    }


    @OnClick({R.id.appImageViewHeader2, R.id.menuImageViewHeader2, R.id.homeLinearLayoutFooter, R.id.kycLinearLayoutFooter, R.id.quickPayLinearLayoutFooter, R.id.beneficiaryLinearLayoutFooter, R.id.historyLinearLayoutFooter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homeLinearLayoutFooter:
                mIntent = new Intent(QuickPayActivity.this, HomeActivity.class);
                startActivity(mIntent);
                break;
            case R.id.kycLinearLayoutFooter:
                mIntent = new Intent(QuickPayActivity.this, UploadYourDocumentActivity.class);
                startActivity(mIntent);
                break;
            case R.id.quickPayLinearLayoutFooter:
                mIntent = new Intent(QuickPayActivity.this, PinVerificationActivity.class);
                mIntent.putExtra("come_from", "quick_pay");
                startActivity(mIntent);
                break;
            case R.id.beneficiaryLinearLayoutFooter:
                mIntent = new Intent(QuickPayActivity.this, SelectBeneficiaryActivity.class);
                mIntent.putExtra("come_from", "");
                startActivity(mIntent);
                break;
            case R.id.historyLinearLayoutFooter:
                mIntent = new Intent(QuickPayActivity.this, PinVerificationActivity.class);
                mIntent.putExtra("come_from", "history");
                startActivity(mIntent);
                break;
            case R.id.appImageViewHeader2:
                Intent mIntent = new Intent(QuickPayActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
            case R.id.menuImageViewHeader2:
                slideHolderQuickPay.toggle();
                break;
        }
    }


}
