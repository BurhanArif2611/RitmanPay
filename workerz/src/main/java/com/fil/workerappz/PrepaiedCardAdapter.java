package com.fil.workerappz;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;

/**
 * Created by FUSION on 05/05/2018.
 */

public class PrepaiedCardAdapter extends RecyclerView.Adapter<PrepaiedCardAdapter.ViewHolder> {

    private final Activity mContext;


    public PrepaiedCardAdapter(Activity context) {
        this.mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prepaied_card_adapter, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int listPosition) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_prepaiedcard)
        ImageView imagePrepaiedcard;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}