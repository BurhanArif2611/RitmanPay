package com.fil.workerappz.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fil.workerappz.R;
import com.fil.workerappz.TransactionHistoryActivity;
import com.fil.workerappz.WalletSummaryActivity;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HS on 14-Mar-18.
 * FIL AHM
 */

public class FilterBottomSheetFragment extends BottomSheetDialogFragment {

    @BindView(R.id.filterTransactionTextView)
    ImageView filterTransactionTextView;
    @BindView(R.id.allRadioButtonFilterTransaction)
    RadioButton allRadioButtonFilterTransaction;
    @BindView(R.id.bankRadioButtonFilterTransaction)
    RadioButton bankRadioButtonFilterTransaction;
    @BindView(R.id.cashRadioButtonFilterTransaction)
    RadioButton cashRadioButtonFilterTransaction;
    @BindView(R.id.topUpRadioButtonFilterTransaction)
    RadioButton topUpRadioButtonFilterTransaction;
    @BindView(R.id.radioGroupFilterTransaction)
    RadioGroup radioGroupFilterTransaction;

    private String come_from = "", all, banktransfer, cashtransfer, topup;
    private Activity activity;
    private TransactionHistoryActivity transactionHistoryActivity = null;
    private WalletSummaryActivity walletSummaryActivity;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.transaction_filter, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        try {
            sessionManager = new SessionManager(getActivity());
            datumLable_languages = sessionManager.getAppLanguageLabel();
            if (datumLable_languages != null) {
                all = datumLable_languages.getAll();
                banktransfer = datumLable_languages.getBankTransfer();
                cashtransfer = datumLable_languages.getCashTransfer();
                topup = datumLable_languages.getTopup();
            }
            else
            {
                all=getResources().getString(R.string.all);
                banktransfer=getResources().getString(R.string.bank_transfer);
                cashtransfer=getResources().getString(R.string.cash_transfer);
                topup=getResources().getString(R.string.topup);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        filterTransactionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (Constants.filter.equalsIgnoreCase(all)) {
            allRadioButtonFilterTransaction.setChecked(true);
        } else if (Constants.filter.equalsIgnoreCase(banktransfer)) {
            bankRadioButtonFilterTransaction.setChecked(true);
        } else if (Constants.filter.equalsIgnoreCase(cashtransfer)) {
            cashRadioButtonFilterTransaction.setChecked(true);
        } else if (Constants.filter.equalsIgnoreCase(topup)) {
            topUpRadioButtonFilterTransaction.setChecked(true);
        }

        radioGroupFilterTransaction.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.allRadioButtonFilterTransaction:
                        Constants.filter = all;
                        break;
                    case R.id.bankRadioButtonFilterTransaction:
                        Constants.filter = banktransfer;
                        break;
                    case R.id.cashRadioButtonFilterTransaction:
                        Constants.filter = cashtransfer;
                        break;
                    case R.id.topUpRadioButtonFilterTransaction:
                        Constants.filter = topup;
                        break;
                }

                if (come_from.equalsIgnoreCase("history")) {
                    transactionHistoryActivity.filterApplied();
                } else {
                    walletSummaryActivity.filterApplied();
                }

                dismiss();
            }
        });

        return rootView;
    }

    public void setComeFrom(String history, LabelListData datumLable_languages, Activity activity) {
        come_from = history;
        this.datumLable_languages = datumLable_languages;
        this.activity = activity;

        CustomLog.d("System out", "come_from " + come_from);
        CustomLog.d("System out", "context " + this.activity);

        if (come_from.equalsIgnoreCase("history")) {
            transactionHistoryActivity = (TransactionHistoryActivity) this.activity;
        } else {
            walletSummaryActivity = (WalletSummaryActivity) this.activity;
        }
    }
}