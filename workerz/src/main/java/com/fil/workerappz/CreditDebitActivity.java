package com.fil.workerappz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HS on 24-Feb-18.
 * FIL AHM
 */

public class CreditDebitActivity extends ActionBarActivity {

    @BindView(R.id.backImageViewHeader)
    ImageView backImageViewHeader;
    @BindView(R.id.titleTextViewViewHeader)
    TextView titleTextViewViewHeader;
    @BindView(R.id.skipTextViewViewHeader)
    TextView skipTextViewViewHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.credit_debit);
        ButterKnife.bind(this);

        titleTextViewViewHeader.setText(getResources().getString(R.string.credit_card_debit_card));
    }

    @OnClick(R.id.backImageViewHeader)
    public void onViewClicked() {
        finish();
    }
}
