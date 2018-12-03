package com.fil.workerappz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.adapter.BillPaymentPagerAdapter;
import com.fil.workerappz.pojo.HomeDataBean;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.utils.SessionManager;
import com.fil.workerappz.utils.SlideHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HS on 26-Feb-18.
 * FIL AHM
 */

public class BillPaymentActivity extends ActionBarActivity {


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
    @BindView(R.id.slideHolderBillPayment)
    SlideHolder slideHolderBillPayment;
    private ArrayList<HomeDataBean> homeDataBeans;
    private int position = 0;
    private BillPaymentPagerAdapter billPaymentPagerAdapter;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private Intent mIntent;
    private String comeFrom = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bill_payment);
        ButterKnife.bind(this);

        filterImageViewHeader2.setVisibility(View.INVISIBLE);

        try {
            sessionManager = new SessionManager(BillPaymentActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();

            if (datumLable_languages != null) {


                titleTextViewViewHeader2.setText(datumLable_languages.getBillPayment());


            } else {

                titleTextViewViewHeader2.setText(getResources().getString(R.string.bill_payment));

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

//        mIntent = getIntent();
//
//        if (mIntent != null) {
//            homeDataBeans = (ArrayList<HomeDataBean>) mIntent.getSerializableExtra("home_bean");
//            position = mIntent.getIntExtra("home_bean_selection", 0);
//
//            billPaymentTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//            billPaymentTabLayout.setTabMode(TabLayout.GRAVITY_FILL);
//
//            billPaymentPagerAdapter = new BillPaymentPagerAdapter(getSupportFragmentManager(), homeDataBeans);
//            billPaymentViewPager.setAdapter(billPaymentPagerAdapter);
//            billPaymentTabLayout.setupWithViewPager(billPaymentViewPager);
//            billPaymentViewPager.setCurrentItem(position);
//
//            for (int i = 0; i < homeDataBeans.size(); i++) {
//                View view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
//                        .inflate(R.layout.home_adapter2, null, false);
//
//                ImageView imageViewHomeAdapter2 = view.findViewById(R.id.imageViewHomeAdapter2);
//                TextView textViewHomeAdapter2 = view.findViewById(R.id.textViewHomeAdapter2);
//                LinearLayout linearLayoutHomeAdapter2 = view.findViewById(R.id.linearLayoutHomeAdapter2);
//                imageViewHomeAdapter2.setImageResource(homeDataBeans.get(i).getImageId());
//                textViewHomeAdapter2.setText(homeDataBeans.get(i).getDataName());
//                billPaymentTabLayout.getTabAt(i).setCustomView(view);
//            }


    }

    private void homeSelection() {
        homeImageViewFooter.setImageResource(R.drawable.footer_icon_home_selected);
        homeTextViewFooter.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    @OnClick({R.id.kycLinearLayoutFooter, R.id.quickPayLinearLayoutFooter, R.id.beneficiaryLinearLayoutFooter, R.id.historyLinearLayoutFooter, R.id.menuImageViewHeader2,R.id.appImageViewHeader2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
                if (comeFrom.equalsIgnoreCase("")) {
                    finish();
                } else {
                    slideHolderBillPayment.toggle();
                }
                break;
            case R.id.kycLinearLayoutFooter:
                mIntent = new Intent(BillPaymentActivity.this, UploadYourDocumentActivity.class);
                startActivity(mIntent);
                break;
            case R.id.quickPayLinearLayoutFooter:
                mIntent = new Intent(BillPaymentActivity.this, PinVerificationActivity.class);
                mIntent.putExtra("come_from", "quick_pay");
                startActivity(mIntent);
                break;
            case R.id.beneficiaryLinearLayoutFooter:
                mIntent = new Intent(BillPaymentActivity.this, SelectBeneficiaryActivity.class);
                mIntent.putExtra("come_from", "");
                startActivity(mIntent);
                break;
            case R.id.historyLinearLayoutFooter:
                mIntent = new Intent(BillPaymentActivity.this, PinVerificationActivity.class);
                mIntent.putExtra("come_from", "history");
                startActivity(mIntent);
                break;
            case R.id.appImageViewHeader2:
                mIntent = new Intent(BillPaymentActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
        }
    }
}
