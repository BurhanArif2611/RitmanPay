package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fil.workerappz.adapter.SendMoneyPagerAdapter;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.QuickPayDataPojo;
import com.fil.workerappz.utils.SessionManager;
import com.fil.workerappz.utils.SlideHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HS on 27-Feb-18.
 * FIL AHM
 */

public class SendMoneyActivity extends ActionBarActivity {

    @BindView(R.id.menuImageViewHeader2)
    ImageView menuImageViewHeader2;
    @BindView(R.id.titleTextViewViewHeader2)
    TextView titleTextViewViewHeader2;
    @BindView(R.id.filterImageViewHeader2)
    ImageView filterImageViewHeader2;
    @BindView(R.id.sendMoneyTabLayout)
    TabLayout sendMoneyTabLayout;
    @BindView(R.id.sendMoneyViewPager)
    ViewPager sendMoneyViewPager;
    @BindView(R.id.slideHolderSendMoney)
    SlideHolder slideHolderSendMoney;
    @BindView(R.id.appImageViewHeader2)
    ImageView appImageViewHeader2;

    private final ArrayList<String> arrayList = new ArrayList<>();
    @BindView(R.id.appLeftImageViewHeader2)
    ImageView appLeftImageViewHeader2;
    @BindView(R.id.skipTextViewViewHeader2)
    TextView skipTextViewViewHeader2;
    private Intent mIntent;
    private QuickPayDataPojo quickPayData = new QuickPayDataPojo();
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.send_money);
        ButterKnife.bind(this);


        sessionManager = new SessionManager(SendMoneyActivity.this);
        mIntent = getIntent();
        if (mIntent != null) {
            quickPayData = (QuickPayDataPojo) mIntent.getSerializableExtra("quick_pay_object");
        }


        filterImageViewHeader2.setVisibility(View.INVISIBLE);


        try {

            datumLable_languages = sessionManager.getAppLanguageLabel();

            if (datumLable_languages != null) {
                arrayList.add(datumLable_languages.getWallet());
                arrayList.add(datumLable_languages.getBank());
                arrayList.add(datumLable_languages.getCashPickup());
//                arrayList.add(datumLable_languages.getDoorToDoorDelivery());
                arrayList.add(datumLable_languages.getDoorToDoor());
                titleTextViewViewHeader2.setText(datumLable_languages.getSendMoney());
            } else {
                arrayList.add(getResources().getString(R.string.wallet));
                arrayList.add(getResources().getString(R.string.bank));
                arrayList.add(getResources().getString(R.string.cash_pickup));
                arrayList.add(getResources().getString(R.string.door_to_door_delivery));
                titleTextViewViewHeader2.setText(getResources().getString(R.string.send_money));
            }
        } catch (
                Exception e)

        {
            e.printStackTrace();
        }
        for (int i = 0; i < arrayList.size(); i++) {
            sendMoneyTabLayout.addTab(sendMoneyTabLayout.newTab().setText(arrayList.get(i)));
        }

        sendMoneyTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        sendMoneyTabLayout.setTabMode(TabLayout.GRAVITY_FILL);

        final SendMoneyPagerAdapter sendMoneyPagerAdapter = new SendMoneyPagerAdapter(getSupportFragmentManager(), arrayList, getMyUserId(), quickPayData,getUserData().getuserDateOfBirth());

        sendMoneyViewPager.setAdapter(sendMoneyPagerAdapter);
        sendMoneyTabLayout.post(new Runnable() {
            @Override
            public void run() {
                sendMoneyTabLayout.setupWithViewPager(sendMoneyViewPager);
                sendMoneyViewPager.setCurrentItem(0);
            }
        });
        menuImageViewHeader2.setImageResource(R.drawable.back_btn);
        sendMoneyViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                sendMoneyPagerAdapter.callFragmentPage(position);


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.menuImageViewHeader2, R.id.appImageViewHeader2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
//                slideHolderSendMoney.toggle();
                finish();
                break;
            case R.id.appImageViewHeader2:
                mIntent = new Intent(SendMoneyActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);

                break;

        }
    }


}
