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

import com.fil.workerappz.BillPaymentActivity;
import com.fil.workerappz.LoyaltyProgramActivity;
import com.fil.workerappz.MobileTopUpActivity;
import com.fil.workerappz.PinVerificationActivity;
import com.fil.workerappz.PrepaidCardActivity;
import com.fil.workerappz.ProfileActivity;
import com.fil.workerappz.R;
import com.fil.workerappz.WalletActivity;
import com.fil.workerappz.pojo.HomeDataBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HS on 26-Feb-18.
 * FIL AHM
 */

public class HomeGridAdapter extends RecyclerView.Adapter<HomeGridAdapter.ViewHolder> {

    private final Activity mContext;
    private final ArrayList<HomeDataBean> homeDataBeans;
    private Intent mIntent;

    public HomeGridAdapter(Activity mContext, ArrayList<HomeDataBean> homeDataBeans) {
        this.mContext = mContext;
        this.homeDataBeans = homeDataBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.imageViewHomeAdapter.setImageResource(homeDataBeans.get(position).getImageId());
        holder.textViewHomeAdapter.setText(homeDataBeans.get(position).getDataName());

        holder.linearLayoutHomeAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mIntent = new Intent(mContext, BillPaymentActivity.class);
//                mIntent.putExtra("home_bean", homeDataBeans);
//                mIntent.putExtra("home_bean_selection", holder.getAdapterPosition());
//                mContext.startActivity(mIntent);
                switch (holder.getAdapterPosition()) {
                    case 0:
                        mIntent = new Intent(mContext, ProfileActivity.class);
                        mContext.startActivity(mIntent);
                        break;
                    case 1:
                        mIntent = new Intent(mContext, PinVerificationActivity.class);
                        mIntent.putExtra("come_from", "wallet_summary");
                        mContext.startActivity(mIntent);
                        break;
                    case 2:
                    mIntent = new Intent(mContext, PrepaidCardActivity.class);
                        mIntent.putExtra("come_from", "");
                        mContext.startActivity(mIntent);
                        break;
                    case 3:
                        mIntent = new Intent(mContext, MobileTopUpActivity.class);
                        mContext.startActivity(mIntent);
                        break;
                    case 4:
                        mIntent = new Intent(mContext, BillPaymentActivity.class);
                        mIntent.putExtra("come_from", "");
                        mContext.startActivity(mIntent);
                        break;
                    case 5:
                        mIntent = new Intent(mContext, LoyaltyProgramActivity.class);
                        mIntent.putExtra("come_from", "");
                        mContext.startActivity(mIntent);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeDataBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewHomeAdapter)
        ImageView imageViewHomeAdapter;
        @BindView(R.id.textViewHomeAdapter)
        TextView textViewHomeAdapter;
        @BindView(R.id.linearLayoutHomeAdapter)
        LinearLayout linearLayoutHomeAdapter;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}