package com.fil.workerappz.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fil.workerappz.R;
import com.fil.workerappz.pojo.ding.GetProductsList;
import com.fil.workerappz.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HS on 17-Mar-18.
 * FIL AHM
 */

public class BrowsePlansListAdapter extends RecyclerView.Adapter<BrowsePlansListAdapter.ViewHolder> {

    private final Activity context;
    private String comeFrom = "";
    private Intent mIntent;
    private final List<GetProductsList> getProductsLists;

    public BrowsePlansListAdapter(Activity context, List<GetProductsList> getProductsLists) {
        this.getProductsLists = getProductsLists;
        this.context = context;
        this.comeFrom = comeFrom;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.browse_plan_adapter, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        if (getProductsLists.get(0).getData().get(holder.getAdapterPosition()).getValidityPeriodIso() == null) {
            holder.validityBrowsePlanAdapterTextView.setText("NA");
        } else {
            String validity= getProductsLists.get(0).getData().get(holder.getAdapterPosition()).getValidityPeriodIso();
            if (validity.startsWith("P")) {
                holder.validityBrowsePlanAdapterTextView.setText(validity.substring(1));
            } else {
                holder.validityBrowsePlanAdapterTextView.setText("NA");
            }
        }
        holder.valueBrowsePlanAdapterTextView.setText(getProductsLists.get(0).getData().get(holder.getAdapterPosition()).getDefaultDisplayText());
        holder.receiveValueBrowsePlanAdapterTextView.setText(getProductsLists.get(0).getData().get(holder.getAdapterPosition()).getMaximum().getReceiveCurrencyIso()+" "+getProductsLists.get(0).getData().get(holder.getAdapterPosition()).getMaximum().getReceiveValueExcludingTax());
        holder.globalValueBrowsePlanAdapterTextView.setText(getProductsLists.get(0).getData().get(holder.getAdapterPosition()).getMaximum().getSendCurrencyIso()+" "+getProductsLists.get(0).getData().get(holder.getAdapterPosition()).getMaximum().getSendValue());

        holder.globalValueBrowsePlanAdapterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.productListData = new GetProductsList.Data();
                Constants.productListData = getProductsLists.get(0).getData().get(holder.getAdapterPosition());
                context.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return getProductsLists.get(0).getData().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.valueBrowsePlanAdapterTextView)
        TextView valueBrowsePlanAdapterTextView;
        @BindView(R.id.validityBrowsePlanAdapterTextView)
        TextView validityBrowsePlanAdapterTextView;
        @BindView(R.id.receiveValueBrowsePlanAdapterTextView)
        TextView receiveValueBrowsePlanAdapterTextView;
        @BindView(R.id.globalValueBrowsePlanAdapterTextView)
        TextView globalValueBrowsePlanAdapterTextView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}