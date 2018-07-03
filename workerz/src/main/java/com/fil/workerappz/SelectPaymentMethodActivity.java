package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HS on 26-Feb-18.
 * FIL AHM
 */

public class SelectPaymentMethodActivity extends ActionBarActivity {

    @BindView(R.id.backImageViewHeader)
    ImageView backImageViewHeader;
    @BindView(R.id.titleTextViewViewHeader)
    TextView titleTextViewViewHeader;
    @BindView(R.id.skipTextViewViewHeader)
    TextView skipTextViewViewHeader;
    @BindView(R.id.creditCardLinearLayoutSelectPaymentMethod)
    LinearLayout creditCardLinearLayoutSelectPaymentMethod;
    @BindView(R.id.bankAccountLinearLayoutSelectPaymentMethod)
    LinearLayout bankAccountLinearLayoutSelectPaymentMethod;
    @BindView(R.id.walletLinearLayoutSelectPaymentMethod)
    LinearLayout walletLinearLayoutSelectPaymentMethod;

    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_payment_method);
        ButterKnife.bind(this);

        titleTextViewViewHeader.setText(getResources().getString(R.string.select_payment_method));
    }

    @OnClick({R.id.backImageViewHeader, R.id.creditCardLinearLayoutSelectPaymentMethod, R.id.bankAccountLinearLayoutSelectPaymentMethod, R.id.walletLinearLayoutSelectPaymentMethod})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImageViewHeader:
                finish();
                break;
            case R.id.creditCardLinearLayoutSelectPaymentMethod:
                mIntent = new Intent(SelectPaymentMethodActivity.this, CreditDebitActivity.class);
                startActivity(mIntent);
                break;
            case R.id.bankAccountLinearLayoutSelectPaymentMethod:
                mIntent = new Intent(SelectPaymentMethodActivity.this, BankAccountActivity.class);
                startActivity(mIntent);
                break;
            case R.id.walletLinearLayoutSelectPaymentMethod:
                mIntent = new Intent(SelectPaymentMethodActivity.this, WalletActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
