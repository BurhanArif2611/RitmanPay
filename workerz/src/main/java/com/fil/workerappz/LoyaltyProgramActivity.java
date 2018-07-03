package com.fil.workerappz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    @BindView(R.id.appLeftImageViewHeader2)
    ImageView appLeftImageViewHeader2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loyalty_program);
        ButterKnife.bind(this);

        titleTextViewViewHeader2.setText(getResources().getString(R.string.loyalty_program));
    }

    @OnClick(R.id.menuImageViewHeader2)
    public void onViewClicked() {
        slideHolderLoyaltyProgram.toggle();
    }
}
