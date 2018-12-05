package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.adapter.BeneficiaryListAdapter;
import com.fil.workerappz.pojo.BeneficiaryListPojo;
import com.fil.workerappz.pojo.LabelListData;
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
 * Created by HS on 09-Mar-18.
 * FIL AHM
 */

public class SelectBeneficiaryActivity extends ActionBarActivity {

    private static final int pagePerIndex = 20;
    private final ArrayList<BeneficiaryListPojo> beneficiaryListPojos = new ArrayList<>();
    @BindView(R.id.menuImageViewHeader2)
    ImageView menuImageViewHeader2;
    @BindView(R.id.appImageViewHeader2)
    ImageView appImageViewHeader2;
    @BindView(R.id.titleTextViewViewHeader2)
    TextView titleTextViewViewHeader2;
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
    @BindView(R.id.slideHolderSelectBeneficiary)
    SlideHolder slideHolderSelectBeneficiary;
    @BindView(R.id.appLeftImageViewHeader2)
    ImageView appLeftImageViewHeader2;
    @BindView(R.id.skipTextViewViewHeader2)
    TextView skipTextViewViewHeader2;
    @BindView(R.id.selectBeneficiaryRecyclerView)
    RecyclerView selectBeneficiaryRecyclerView;
    @BindView(R.id.mainSelectBeneficiaryLinearLayout)
    LinearLayout mainSelectBeneficiaryLinearLayout;
    @BindView(R.id.footerlinerselectbeneficiary)
    LinearLayout footerlinerselectbeneficiary;
    @BindView(R.id.textviewNoprecordFound)
    TextView textviewNoprecordFound;
    @BindView(R.id.inputSearch)
    EditText inputSearch;
    private BeneficiaryListAdapter beneficiaryListAdapter;
    private LinearLayoutManager layoutManager;
    private String comeFrom = "";
    private Intent mIntent;
    private int pageIndex = -1;
    private int pageSize = pagePerIndex;

    static int y;
    int pageNo = 0;
    int visibleItemCount, totalItemCount, firstVisibleItemPosition;
    boolean isLoading = false;
    boolean isLastpage = false;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private String nointernetmsg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_beneficiary);
        ButterKnife.bind(this);


        filterImageViewHeader2.setVisibility(View.INVISIBLE);
        layoutManager = new LinearLayoutManager(SelectBeneficiaryActivity.this);
        selectBeneficiaryRecyclerView.setLayoutManager(layoutManager);
        try {
            sessionManager = new SessionManager(SelectBeneficiaryActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();

            if (datumLable_languages != null) {

                titleTextViewViewHeader2.setText(datumLable_languages.getSelectBeneficiary());
                textviewNoprecordFound.setText(datumLable_languages.getNoRecordFound());
                quickPayTextViewFooter.setText(datumLable_languages.getQuickPay());
                beneficiaryTextViewFooter.setText(datumLable_languages.getBeneficiary());
                historyTextViewFooter.setText(datumLable_languages.getHistory());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();


            } else {
                titleTextViewViewHeader2.setText(getResources().getString(R.string.select_beneficiary));
                textviewNoprecordFound.setText(getResources().getString(R.string.no_record_found));
                titleTextViewViewHeader2.setText(getResources().getString(R.string.quick_pay));
                homeTextViewFooter.setText(getResources().getString(R.string.home));
                quickPayTextViewFooter.setText(getResources().getString(R.string.quick_pay));
                beneficiaryTextViewFooter.setText(getResources().getString(R.string.beneficiary));
                historyTextViewFooter.setText(getResources().getString(R.string.history));
                nointernetmsg = getResources().getString(R.string.no_internet);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mIntent = getIntent();
        if (mIntent != null) {
            comeFrom = mIntent.getStringExtra("come_from");
        }
        if (!comeFrom.equalsIgnoreCase("")) {
            titleTextViewViewHeader2.setText(datumLable_languages.getSelectBeneficiary());
            menuImageViewHeader2.setImageResource(R.drawable.back_btn);
            footerlinerselectbeneficiary.setVisibility(View.GONE);
            inputSearch.setVisibility(View.VISIBLE);

        } else {
//            titleTextViewViewHeader2.setText(datumLable_languages.getBeneficiaryList());
            titleTextViewViewHeader2.setText("Manage Beneficiary");
            footerlinerselectbeneficiary.setVisibility(View.VISIBLE);
            inputSearch.setVisibility(View.GONE);
            menuImageViewHeader2.setImageResource(R.drawable.back_btn);
        }

        if (IsNetworkConnection.checkNetworkConnection(SelectBeneficiaryActivity.this)) {
            beneficiaryListJsonCall();
        } else {
            Constants.showMessage(mainSelectBeneficiaryLinearLayout, SelectBeneficiaryActivity.this, nointernetmsg);
        }


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                    beneficiaryListAdapter.getFilter().filter(cs);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
        beneficiarySelection();

        selectBeneficiaryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if (selectBeneficiaryRecyclerView.SCROLL_STATE_IDLE == newState) {

                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                y = dy;
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading && !isLastpage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= 20) {

                        isLoading = true;
                        beneficiaryListJsonCall();
                    }
                }
            }
        });
    }


    private void beneficiarySelection() {
        beneficiaryImageViewFooter.setImageResource(R.drawable.footer_icon_beneficiary_selected);
        beneficiaryTextViewFooter.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    private void beneficiaryListJsonCall() {

        Constants.showProgress(SelectBeneficiaryActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", "" + getUserData().getUserID());
            jsonObject.put("benificaryPaymentMode", comeFrom);
            jsonObject.put("page", pageNo);
            jsonObject.put("pagesize", "20");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "list beneficiary " + json);
        Call<List<BeneficiaryListPojo>> call = RestClient.get().getBeneficiaryListJsonCall(json);
        call.enqueue(new Callback<List<BeneficiaryListPojo>>() {
            @Override
            public void onResponse(Call<List<BeneficiaryListPojo>> call, final Response<List<BeneficiaryListPojo>> response) {
                if (response.body() != null && response.body() instanceof ArrayList) {
                    try {
                        Constants.closeProgress();

                        if (response.body() != null && response.body().size() > 0) {
                            isLoading = false;
                            if (response.body().get(0).getStatus() == false) {
                                isLastpage = true;
                                if (beneficiaryListPojos.size() == 0) {
                                    selectBeneficiaryRecyclerView.setAdapter(null);
                                    selectBeneficiaryRecyclerView.setVisibility(View.GONE);
                                    textviewNoprecordFound.setVisibility(View.VISIBLE);
                                }
                            } else {

                                if (pageNo == 0) {
                                    selectBeneficiaryRecyclerView.setVisibility(View.VISIBLE);
                                    textviewNoprecordFound.setVisibility(View.GONE);
                                    CustomLog.d("System out", "true");
                                    beneficiaryListPojos.clear();
                                    beneficiaryListPojos.addAll(response.body());
//                                    layoutManager = new LinearLayoutManager(SelectBeneficiaryActivity.this);
//                                    selectBeneficiaryRecyclerView.setLayoutManager(layoutManager);
                                    beneficiaryListAdapter = new BeneficiaryListAdapter(SelectBeneficiaryActivity.this, beneficiaryListPojos.get(0).getData(), comeFrom);
                                    selectBeneficiaryRecyclerView.setAdapter(beneficiaryListAdapter);
                                } else {
                                    selectBeneficiaryRecyclerView.setVisibility(View.VISIBLE);
                                    textviewNoprecordFound.setVisibility(View.GONE);
                                    CustomLog.d("System out", "true");
                                    beneficiaryListPojos.get(0).getData().addAll(response.body().get(0).getData());
                                    beneficiaryListAdapter.notifyDataSetChanged();
                                }

                                pageNo = pageNo + 1;
                                if (beneficiaryListPojos.get(0).getData().size() < 20) {
                                    isLastpage = true;
                                } else {
                                    beneficiaryListAdapter.notifyDataSetChanged();
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BeneficiaryListPojo>> call, Throwable t) {
                Constants.closeProgress();
                t.printStackTrace();
            }
        });
    }

    @OnClick({R.id.homeLinearLayoutFooter, R.id.kycLinearLayoutFooter, R.id.quickPayLinearLayoutFooter, R.id.historyLinearLayoutFooter, R.id.menuImageViewHeader2, R.id.appImageViewHeader2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homeLinearLayoutFooter:
                mIntent = new Intent(SelectBeneficiaryActivity.this, HomeActivity.class);
                startActivity(mIntent);
                break;
            case R.id.appImageViewHeader2:
                mIntent = new Intent(SelectBeneficiaryActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
            case R.id.kycLinearLayoutFooter:
                mIntent = new Intent(SelectBeneficiaryActivity.this, UploadYourDocumentActivity.class);
                startActivity(mIntent);
                break;
            case R.id.quickPayLinearLayoutFooter:
                mIntent = new Intent(SelectBeneficiaryActivity.this, PinVerificationActivity.class);
                mIntent.putExtra("come_from", "quick_pay");
                startActivity(mIntent);
                break;
            case R.id.historyLinearLayoutFooter:
                mIntent = new Intent(SelectBeneficiaryActivity.this, PinVerificationActivity.class);
                mIntent.putExtra("come_from", "history");
                startActivity(mIntent);
                break;
            case R.id.menuImageViewHeader2: {
                if (!comeFrom.equalsIgnoreCase("")) {
                    finish();
                } else {
//                    slideHolderSelectBeneficiary.toggle();
                    finish();
                }
                break;
            }
        }
    }

}

