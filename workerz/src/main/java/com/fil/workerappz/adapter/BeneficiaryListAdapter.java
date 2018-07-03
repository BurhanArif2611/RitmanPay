package com.fil.workerappz.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.R;
import com.fil.workerappz.SelectBeneficiaryViewActivity;
import com.fil.workerappz.pojo.BeneficiaryListPojo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HS on 17-Mar-18.
 * FIL AHM
 */

public class BeneficiaryListAdapter extends RecyclerView.Adapter<BeneficiaryListAdapter.ViewHolder> {

    private final Context context;
    private String comeFrom = "";
    private Intent mIntent;
    private final List<BeneficiaryListPojo> beneficiaryListPojoList;

    public BeneficiaryListAdapter(Context context, List<BeneficiaryListPojo> beneficiaryListPojos, String comeFrom) {
        this.beneficiaryListPojoList = beneficiaryListPojos;
        this.context = context;
        this.comeFrom = comeFrom;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_beneficiary_adapter, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        holder.bankNameBeneficiaryTextView.setText(beneficiaryListPojoList.get(0).getData().get(listPosition).getBenificaryBankName().trim());
        holder.nameTextViewSelectBeneficiary.setText(beneficiaryListPojoList.get(0).getData().get(listPosition).getBenificaryFirstName().trim() + " " + beneficiaryListPojoList.get(0).getData().get(listPosition).getBenificaryLastName().trim());
        if (comeFrom.equalsIgnoreCase("cash")) {
            holder.bankBranchNameBeneficiary.setVisibility(View.VISIBLE);
            holder.bankBranchNameBeneficiary.setText(beneficiaryListPojoList.get(0).getData().get(listPosition).getBenificaryBankBranch().trim());
        }

        holder.mainBeneficiaryAdapterLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comeFrom.equalsIgnoreCase("CASH")) {
                    mIntent = new Intent(context, SelectBeneficiaryViewActivity.class);
                    mIntent.putExtra("come_from", comeFrom);
                    mIntent.putExtra("beneficiary_object", beneficiaryListPojoList.get(0).getData().get(holder.getAdapterPosition()));
                    context.startActivity(mIntent);
                } else if (comeFrom.equalsIgnoreCase("BANK")) {
                    mIntent = new Intent(context, SelectBeneficiaryViewActivity.class);
                    mIntent.putExtra("come_from", comeFrom);
                    mIntent.putExtra("beneficiary_object", beneficiaryListPojoList.get(0).getData().get(holder.getAdapterPosition()));
                    context.startActivity(mIntent);
                } else if (comeFrom.equalsIgnoreCase("")) {
                    mIntent = new Intent(context, SelectBeneficiaryViewActivity.class);
                    if (beneficiaryListPojoList.get(0).getData().get(holder.getAdapterPosition()).getBenificaryPaymentMode().equalsIgnoreCase("BANK")) {
                        mIntent.putExtra("come_from", "BANK");
                    } else if (beneficiaryListPojoList.get(0).getData().get(holder.getAdapterPosition()).getBenificaryPaymentMode().equalsIgnoreCase("CASH")) {
                        mIntent.putExtra("come_from", "CASH");
                    }
                    mIntent.putExtra("beneficiary_object", beneficiaryListPojoList.get(0).getData().get(holder.getAdapterPosition()));
                    context.startActivity(mIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return beneficiaryListPojoList.get(0).getData().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nameTextViewSelectBeneficiary)
        TextView nameTextViewSelectBeneficiary;
        @BindView(R.id.bankNameBeneficiaryTextView)
        TextView bankNameBeneficiaryTextView;
        @BindView(R.id.bankBranchNameBeneficiary)
        TextView bankBranchNameBeneficiary;
        @BindView(R.id.mainBeneficiaryAdapterLinearLayout)
        LinearLayout mainBeneficiaryAdapterLinearLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}