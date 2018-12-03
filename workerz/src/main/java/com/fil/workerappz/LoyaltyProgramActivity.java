package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.utils.SessionManager;
import com.fil.workerappz.utils.SlideHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HS on 12-Mar-18.
 * FIL AHM
 */

public class LoyaltyProgramActivity extends ActionBarActivity {


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
    @BindView(R.id.slideHolderLoyaltyProgram)
    SlideHolder slideHolderLoyaltyProgram;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private Intent mIntent;
    private String comeFrom = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loyalty_program);
        ButterKnife.bind(this);
        filterImageViewHeader2.setVisibility(View.INVISIBLE);

        try {
            sessionManager = new SessionManager(LoyaltyProgramActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();

            if (datumLable_languages != null) {


                titleTextViewViewHeader2.setText(datumLable_languages.getLoyaltyPoints());


            } else {

                titleTextViewViewHeader2.setText(getResources().getString(R.string.loyalty_program));

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
                    slideHolderLoyaltyProgram.toggle();
                }
                break;
            case R.id.kycLinearLayoutFooter:
                mIntent = new Intent(LoyaltyProgramActivity.this, UploadYourDocumentActivity.class);
                startActivity(mIntent);
                break;
            case R.id.quickPayLinearLayoutFooter:
                mIntent = new Intent(LoyaltyProgramActivity.this, PinVerificationActivity.class);
                mIntent.putExtra("come_from", "quick_pay");
                startActivity(mIntent);
                break;
            case R.id.beneficiaryLinearLayoutFooter:
                mIntent = new Intent(LoyaltyProgramActivity.this, SelectBeneficiaryActivity.class);
                mIntent.putExtra("come_from", "");
                startActivity(mIntent);
                break;
            case R.id.historyLinearLayoutFooter:
                mIntent = new Intent(LoyaltyProgramActivity.this, PinVerificationActivity.class);
                mIntent.putExtra("come_from", "history");
                startActivity(mIntent);
                break;
            case R.id.appImageViewHeader2:
                mIntent = new Intent(LoyaltyProgramActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
        }
    }
}
