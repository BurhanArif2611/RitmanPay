package com.fil.workerappz.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fil.workerappz.R;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.TransactionHistoryListJsonPojo;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.SessionManager;

import java.util.ArrayList;
import java.util.Currency;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HS on 27-Feb-18.
 * FIL AHM
 */

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.ViewHolder> {

    private final Activity mContext;
    private final ArrayList<TransactionHistoryListJsonPojo> transactionHistoryListJsonPojos;
    private Currency currency;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private String inprocess, orderid;

    public WalletHistoryAdapter(Activity mContext, ArrayList<TransactionHistoryListJsonPojo> transactionHistoryListJsonPojos) {
        this.mContext = mContext;
        this.transactionHistoryListJsonPojos = transactionHistoryListJsonPojos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_summary_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        sessionManager=new SessionManager(mContext);
        datumLable_languages=sessionManager.getAppLanguageLabel();
      holder.transactionStatusTextViewSummaryAdapter.setText(transactionHistoryListJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionStatus());

        holder.transactionDateTextViewSummaryAdapter.setText(Constants.formatDate(transactionHistoryListJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionDate(), "yyyy-MM-dd HH:mm:ss", "dd MMM yyyy"));
        if (transactionHistoryListJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionStatus().equalsIgnoreCase(datumLable_languages.getInProcess())) {
            holder.transactionStatusImageViewSummaryAdapter.setImageResource(R.drawable.in_pogress);
            holder.transactionStatusTextViewSummaryAdapter.setTextColor(mContext.getResources().getColor(R.color.Red));
        }
        else
        {
            holder.transactionStatusImageViewSummaryAdapter.setImageResource(R.drawable.successful_icon);
            holder.transactionStatusTextViewSummaryAdapter.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
        }

        currency = Currency.getInstance(transactionHistoryListJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionCurrency());

        holder.transactionAmountTextViewSummaryAdapter.setText(currency.getSymbol() + " " + (transactionHistoryListJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionAmount()));

        holder.transactionForDetailTextViewSummaryAdapter.setText(datumLable_languages.getOrderId() + transactionHistoryListJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionID());

        holder.transactionForTextViewSummaryAdapter.setText(transactionHistoryListJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getTransactionDescription());

        holder.transactionForImageViewSummaryAdapter.setImageResource(R.drawable.home_icon_electricity);
    }

    @Override
    public int getItemCount() {
        return transactionHistoryListJsonPojos.get(0).getData().size();
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}