package com.fil.workerappz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.adapter.BillPaymentPagerAdapter;
import com.fil.workerappz.pojo.HomeDataBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HS on 26-Feb-18.
 * FIL AHM
 */

public class BillPaymentActivity extends ActionBarActivity {

    @BindView(R.id.backImageViewHeader)
    ImageView backImageViewHeader;
    @BindView(R.id.titleTextViewViewHeader)
    TextView titleTextViewViewHeader;
    @BindView(R.id.skipTextViewViewHeader)
    TextView skipTextViewViewHeader;
    @BindView(R.id.billPaymentTabLayout)
    TabLayout billPaymentTabLayout;
    @BindView(R.id.billPaymentViewPager)
    ViewPager billPaymentViewPager;

    private ArrayList<HomeDataBean> homeDataBeans;
    private int position = 0;
    private Intent mIntent = null;
    private BillPaymentPagerAdapter billPaymentPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bill_payment);
        ButterKnife.bind(this);

        titleTextViewViewHeader.setText(getResources().getString(R.string.bill_payment));

        mIntent = getIntent();

        if (mIntent != null) {
            homeDataBeans = (ArrayList<HomeDataBean>) mIntent.getSerializableExtra("home_bean");
            position = mIntent.getIntExtra("home_bean_selection", 0);

            billPaymentTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            billPaymentTabLayout.setTabMode(TabLayout.GRAVITY_FILL);

            billPaymentPagerAdapter = new BillPaymentPagerAdapter(getSupportFragmentManager(), homeDataBeans);
            billPaymentViewPager.setAdapter(billPaymentPagerAdapter);
            billPaymentTabLayout.setupWithViewPager(billPaymentViewPager);
            billPaymentViewPager.setCurrentItem(position);

            for (int i = 0; i < homeDataBeans.size(); i++) {
                View view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.home_adapter2, null, false);

                ImageView imageViewHomeAdapter2 = view.findViewById(R.id.imageViewHomeAdapter2);
                TextView textViewHomeAdapter2 = view.findViewById(R.id.textViewHomeAdapter2);
                LinearLayout linearLayoutHomeAdapter2 = view.findViewById(R.id.linearLayoutHomeAdapter2);
                imageViewHomeAdapter2.setImageResource(homeDataBeans.get(i).getImageId());
                textViewHomeAdapter2.setText(homeDataBeans.get(i).getDataName());
                billPaymentTabLayout.getTabAt(i).setCustomView(view);
            }
        }
    }

    @OnClick(R.id.backImageViewHeader)
    public void onViewClicked() {
        finish();
    }
}
