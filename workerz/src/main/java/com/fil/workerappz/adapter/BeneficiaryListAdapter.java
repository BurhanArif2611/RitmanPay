package com.fil.workerappz.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.BeneficiaryPinVerificationActivity;
import com.fil.workerappz.R;
import com.fil.workerappz.pojo.BeneficiaryListPojo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HS on 17-Mar-18.
 * FIL AHM
 */

public class BeneficiaryListAdapter extends RecyclerView.Adapter<BeneficiaryListAdapter.ViewHolder> implements Filterable {

    private final Context context;

    private String comeFrom = "";
    private Intent mIntent;
    private List<BeneficiaryListPojo.Data> beneficiaryListPojoList;
    private List<BeneficiaryListPojo.Data> beneficiaryList;

    public BeneficiaryListAdapter(Context context, List<BeneficiaryListPojo.Data> beneficiaryListPojos, String comeFrom) {
        this.beneficiaryListPojoList = beneficiaryListPojos;
        this.beneficiaryList = beneficiaryListPojos;
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
        if (comeFrom.equalsIgnoreCase("")) {
            holder.bankNameBeneficiaryTextView.setText(beneficiaryListPojoList.get(holder.getAdapterPosition()).getBenificaryPaymentMode() + ":" + " " + beneficiaryListPojoList.get(listPosition).getBenificaryBankName().trim());
        }
        else
        {
            holder.bankNameBeneficiaryTextView.setText( beneficiaryListPojoList.get(listPosition).getBenificaryBankName().trim());
        }
        holder.nameTextViewSelectBeneficiary.setText(beneficiaryListPojoList.get(listPosition).getBenificaryFirstName().trim() + " " + beneficiaryListPojoList.get(listPosition).getBenificaryLastName().trim());
        if (comeFrom.equalsIgnoreCase("cash")) {
            holder.bankBranchNameBeneficiary.setVisibility(View.VISIBLE);
            holder.bankBranchNameBeneficiary.setText(beneficiaryListPojoList.get(listPosition).getBenificaryBankBranch().trim());
        }

        holder.mainBeneficiaryAdapterLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comeFrom.equalsIgnoreCase("CASH")) {
                    mIntent = new Intent(context, BeneficiaryPinVerificationActivity.class);
                    mIntent.putExtra("come_from", comeFrom);
                    mIntent.putExtra("search", "search");
                    mIntent.putExtra("beneficiary_object", beneficiaryListPojoList.get(holder.getAdapterPosition()));
                    context.startActivity(mIntent);
                } else if (comeFrom.equalsIgnoreCase("BANK")) {
                    mIntent = new Intent(context, BeneficiaryPinVerificationActivity.class);
                    mIntent.putExtra("come_from", comeFrom);
                    mIntent.putExtra("search", "search");
                    mIntent.putExtra("beneficiary_object", beneficiaryListPojoList.get(holder.getAdapterPosition()));
                    context.startActivity(mIntent);
                } else if (comeFrom.equalsIgnoreCase("")) {
                    mIntent = new Intent(context, BeneficiaryPinVerificationActivity.class);
                    if (beneficiaryListPojoList.get(holder.getAdapterPosition()).getBenificaryPaymentMode().equalsIgnoreCase("BANK")) {
                        mIntent.putExtra("come_from", "BANK");
                    } else if (beneficiaryListPojoList.get(holder.getAdapterPosition()).getBenificaryPaymentMode().equalsIgnoreCase("CASH")) {
                        mIntent.putExtra("come_from", "CASH");
                    }
                    mIntent.putExtra("search", "search");
                    mIntent.putExtra("beneficiary_object", beneficiaryListPojoList.get(holder.getAdapterPosition()));
                    context.startActivity(mIntent);
                }
            }
        });

        if (comeFrom.equalsIgnoreCase("")) {
            holder.editbeneficiary.setVisibility(View.VISIBLE);
        } else {
            holder.editbeneficiary.setVisibility(View.GONE);
        }

        holder.editbeneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comeFrom.equalsIgnoreCase("CASH")) {
                    mIntent = new Intent(context, BeneficiaryPinVerificationActivity.class);
                    mIntent.putExtra("come_from", comeFrom);
                    mIntent.putExtra("search", "search");
                    mIntent.putExtra("beneficiary_object", beneficiaryListPojoList.get(holder.getAdapterPosition()));
                    context.startActivity(mIntent);
                } else if (comeFrom.equalsIgnoreCase("BANK")) {
                    mIntent = new Intent(context, BeneficiaryPinVerificationActivity.class);
                    mIntent.putExtra("come_from", comeFrom);
                    mIntent.putExtra("search", "search");
                    mIntent.putExtra("beneficiary_object", beneficiaryListPojoList.get(holder.getAdapterPosition()));
                    context.startActivity(mIntent);
                } else if (comeFrom.equalsIgnoreCase("")) {
                    mIntent = new Intent(context, BeneficiaryPinVerificationActivity.class);
                    if (beneficiaryListPojoList.get(holder.getAdapterPosition()).getBenificaryPaymentMode().equalsIgnoreCase("BANK")) {
                        mIntent.putExtra("come_from", "BANK");
                    } else if (beneficiaryListPojoList.get(holder.getAdapterPosition()).getBenificaryPaymentMode().equalsIgnoreCase("CASH")) {
                        mIntent.putExtra("come_from", "CASH");
                    }
                    mIntent.putExtra("search", "");
                    mIntent.putExtra("beneficiary_object", beneficiaryListPojoList.get(holder.getAdapterPosition()));
                    context.startActivity(mIntent);
                }
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    beneficiaryListPojoList = beneficiaryList;
                } else {
                    List<BeneficiaryListPojo.Data> filteredList = new ArrayList<>();
                    for (BeneficiaryListPojo.Data row : beneficiaryList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

                        if (row.getBenificaryFirstName().trim().toLowerCase().contains(charString.toLowerCase()) || row.getBenificaryLastName().trim().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);

                        }
                    }

                    beneficiaryListPojoList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = beneficiaryListPojoList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                beneficiaryListPojoList = (ArrayList<BeneficiaryListPojo.Data>) filterResults.values;
                if (beneficiaryListPojoList.size() == 0) {

                } else {
                    notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return beneficiaryListPojoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nameTextViewSelectBeneficiary)
        TextView nameTextViewSelectBeneficiary;
        @BindView(R.id.bankNameBeneficiaryTextView)
        TextView bankNameBeneficiaryTextView;
        @BindView(R.id.bankBranchNameBeneficiary)
        TextView bankBranchNameBeneficiary;
        @BindView(R.id.editbeneficiary)
        ImageView editbeneficiary;
        @BindView(R.id.backarrowimageView)
        ImageView backarrowimageView;
        @BindView(R.id.mainBeneficiaryAdapterLinearLayout)
        LinearLayout mainBeneficiaryAdapterLinearLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}