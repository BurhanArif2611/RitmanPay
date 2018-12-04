package com.fil.workerappz.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.BeneficiaryPinVerificationActivity;
import com.fil.workerappz.R;
import com.fil.workerappz.SelectBeneficiaryViewActivity;
import com.fil.workerappz.SendMoneyActivity;
import com.fil.workerappz.pojo.BeneficiaryListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.QuickPayJsonPojo;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.SessionManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HS on 27-Feb-18.
 * FIL AHM
 */

public class QuickPayAdapter extends RecyclerView.Adapter<QuickPayAdapter.ViewHolder> {

    private final Activity mContext;
    private final ArrayList<QuickPayJsonPojo> quickPayJsonPojos;
    private Currency currency;
    private Intent mIntent;
    private final SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();


    public QuickPayAdapter(Activity mContext, ArrayList<QuickPayJsonPojo> quickPayJsonPojos, LabelListData datumLable_languages) {
        this.mContext = mContext;
        this.quickPayJsonPojos = quickPayJsonPojos;
        this.datumLable_languages=datumLable_languages;
        sessionManager = new SessionManager(this.mContext);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_summary_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.transactionStatusTextViewSummaryAdapter.setText(quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionStatus());

        holder.transactionDateTextViewSummaryAdapter.setText(Constants.formatDate(quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionDate(), "yyyy-MM-dd HH:mm:ss", "dd MMM yyyy"));


        currency = Currency.getInstance(quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionCurrency());

        holder.transactionAmountTextViewSummaryAdapter.setText(quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionCurrency() + " " + quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionAmount());

        holder.transactionForDetailTextViewSummaryAdapter.setText(datumLable_languages.getOrderId() + quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionID());

        holder.transactionForTextViewSummaryAdapter.setText(quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionDescription());
        if (quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionStatus().equalsIgnoreCase(datumLable_languages.getInProcess())) {
            holder.transactionStatusImageViewSummaryAdapter.setImageResource(R.drawable.in_pogress);
            holder.transactionStatusTextViewSummaryAdapter.setTextColor(mContext.getResources().getColor(R.color.Red));
        }
        else
        {
            holder.transactionStatusImageViewSummaryAdapter.setImageResource(R.drawable.successful_icon);
            holder.transactionStatusTextViewSummaryAdapter.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
        }
        holder.transactionForImageViewSummaryAdapter.setImageResource(R.drawable.home_icon_electricity);
//        if (quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionStatus().equalsIgnoreCase("Success")&&quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionType().equalsIgnoreCase(datumLable_languages.getWalletTransfer())) {
//            holder.linearLayoutWalletSummaryAdapter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mIntent = new Intent(mContext, SendMoneyActivity.class);
//                    mIntent.putExtra("quick_pay_object", quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()));
//                    mContext.startActivity(mIntent);
//                    mContext.finish();
//                }
//            });
//        }
        if (quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionType().equalsIgnoreCase(datumLable_languages.getWalletTransfer())) {
            holder.linearLayoutWalletSummaryAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent = new Intent(mContext, SendMoneyActivity.class);
                    mIntent.putExtra("quick_pay_object", quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()));
                    mContext.startActivity(mIntent);
                    mContext.finish();
                }
            });
        }
//        else if (quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionType().equalsIgnoreCase(datumLable_languages.getCashTransfer())) {
//            holder.linearLayoutWalletSummaryAdapter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mIntent = new Intent(mContext, SelectBeneficiaryViewActivity.class);
//                    mIntent.putExtra("come_from", "cashquickpay");
//                    mIntent.putExtra("beneficiary_object", quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()));
//                    mContext.startActivity(mIntent);
//                    mContext.finish();
//                }
//            });
//        } else if (quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionType().equalsIgnoreCase(datumLable_languages.getBankTransfer())) {
//            holder.linearLayoutWalletSummaryAdapter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mIntent = new Intent(mContext, SelectBeneficiaryViewActivity.class);
//                    mIntent.putExtra("come_from", "bankquickpay");
//                    mIntent.putExtra("beneficiary_object", quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()));
//                    mContext.startActivity(mIntent);
//                    mContext.finish();
//                }
//            });
//        }

        else if (quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionType().equalsIgnoreCase(datumLable_languages.getCashTransfer())) {
            holder.linearLayoutWalletSummaryAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent = new Intent(mContext, BeneficiaryPinVerificationActivity.class);
                    mIntent.putExtra("come_from", "cashquickpay");
                    mIntent.putExtra("search", "search");
                    mIntent.putExtra("beneficiary_object", quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()));
                    mContext.startActivity(mIntent);
                    mContext.finish();
                }
            });
        } else if (quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionType().equalsIgnoreCase(datumLable_languages.getBankTransfer())) {
            holder.linearLayoutWalletSummaryAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent = new Intent(mContext, BeneficiaryPinVerificationActivity.class);
                    mIntent.putExtra("come_from", "bankquickpay");
                    mIntent.putExtra("search", "search");
                    mIntent.putExtra("beneficiary_object", quickPayJsonPojos.get(0).getData().get(holder.getAdapterPosition()));
                    mContext.startActivity(mIntent);
                    mContext.finish();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return quickPayJsonPojos.get(0).getData().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.transactionStatusImageViewSummaryAdapter)
        ImageView transactionStatusImageViewSummaryAdapter;
        @BindView(R.id.transactionStatusTextViewSummaryAdapter)
        TextView transactionStatusTextViewSummaryAdapter;
        @BindView(R.id.transactionDateTextViewSummaryAdapter)
        TextView transactionDateTextViewSummaryAdapter;
        @BindView(R.id.transactionForImageViewSummaryAdapter)
        ImageView transactionForImageViewSummaryAdapter;
        @BindView(R.id.transactionForTextViewSummaryAdapter)
        TextView transactionForTextViewSummaryAdapter;
        @BindView(R.id.transactionForDetailTextViewSummaryAdapter)
        TextView transactionForDetailTextViewSummaryAdapter;
        @BindView(R.id.transactionAmountTextViewSummaryAdapter)
        TextView transactionAmountTextViewSummaryAdapter;
        @BindView(R.id.linearLayoutWalletSummaryAdapter)
        LinearLayout linearLayoutWalletSummaryAdapter;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}