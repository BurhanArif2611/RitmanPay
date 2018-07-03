package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.adapter.WalletSummaryPagerAdapter;
import com.fil.workerappz.fragments.FilterBottomSheetFragment;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.UserListPojo;
import com.fil.workerappz.utils.SessionManager;
import com.fil.workerappz.utils.SlideHolder;

import java.util.ArrayList;
import java.util.Currency;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HS on 27-Feb-18.
 * FIL AHM
 */

public class WalletSummaryActivity extends ActionBarActivity {

    @BindView(R.id.menuImageViewHeader2)
    ImageView menuImageViewHeader2;
    @BindView(R.id.titleTextViewViewHeader2)
    TextView titleTextViewViewHeader2;
    @BindView(R.id.filterImageViewHeader2)
    ImageView filterImageViewHeader2;
    @BindView(R.id.transactionTabLayout)
    TabLayout transactionTabLayout;
    @BindView(R.id.transactionViewPager)
    ViewPager transactionViewPager;
    @BindView(R.id.slideHolderTransaction)
    SlideHolder slideHolderTransaction;
    @BindView(R.id.accountInfoLinearLayoutTransaction)
    LinearLayout accountInfoLinearLayoutTransaction;
    @BindView(R.id.appImageViewHeader2)
    ImageView appImageViewHeader2;
    @BindView(R.id.appLeftImageViewHeader2)
    ImageView appLeftImageViewHeader2;
    @BindView(R.id.skipTextViewViewHeader2)
    TextView skipTextViewViewHeader2;
    @BindView(R.id.accountBalanceTransactionTextView)
    TextView accountBalanceTransactionTextView;
    @BindView(R.id.pointsTransactionTextView)
    TextView pointsTransactionTextView;
    @BindView(R.id.homeImageViewFooter)
    ImageView homeImageViewFooter;
    @BindView(R.id.homeTextViewFooter)
    TextView homeTextViewFooter;
    @BindView(R.id.homeLinearLayoutFooter)
    LinearLayout homeLinearLayoutFooter;
    @BindView(R.id.kycImageViewFooter)
    ImageView kycImageViewFooter;
    @BindView(R.id.kycTextViewFooter)
    TextView kycTextViewFooter;
    @BindView(R.id.kycLinearLayoutFooter)
    LinearLayout kycLinearLayoutFooter;
    @BindView(R.id.quickPayImageViewFooter)
    ImageView quickPayImageViewFooter;
    @BindView(R.id.quickPayTextViewFooter)
    TextView quickPayTextViewFooter;
    @BindView(R.id.quickPayLinearLayoutFooter)
    LinearLayout quickPayLinearLayoutFooter;
    @BindView(R.id.beneficiaryImageViewFooter)
    ImageView beneficiaryImageViewFooter;
    @BindView(R.id.beneficiaryTextViewFooter)
    TextView beneficiaryTextViewFooter;
    @BindView(R.id.beneficiaryLinearLayoutFooter)
    LinearLayout beneficiaryLinearLayoutFooter;
    @BindView(R.id.historyImageViewFooter)
    ImageView historyImageViewFooter;
    @BindView(R.id.historyTextViewFooter)
    TextView historyTextViewFooter;
    @BindView(R.id.historyLinearLayoutFooter)
    LinearLayout historyLinearLayoutFooter;

    private final ArrayList<String> arrayList = new ArrayList<>();
    @BindView(R.id.LinearFooter)
    LinearLayout LinearFooter;
    @BindView(R.id.textviewbalancewallet)
    TextView textviewbalancewallet;
    @BindView(R.id.textviewpointswallet)
    TextView textviewpointswallet;
    private WalletSummaryPagerAdapter walletSummaryPagerAdapter;
    private UserListPojo.Data userListPojo;
    private Currency currency;
    private Intent mIntent;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transaction);
        ButterKnife.bind(this);


        accountInfoLinearLayoutTransaction.setVisibility(View.VISIBLE);
        LinearFooter.setVisibility(View.GONE);
        userListPojo = getUserData();
        currency = Currency.getInstance(userListPojo.getCountryCurrencySymbol());
        accountBalanceTransactionTextView.setText(currency.getSymbol() + " " + getWalletBalance());
        pointsTransactionTextView.setText("0");


        try {
            sessionManager = new SessionManager(WalletSummaryActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();

            if (datumLable_languages != null) {

                arrayList.add(datumLable_languages.getAll());
                arrayList.add(datumLable_languages.getPaid());
                arrayList.add(datumLable_languages.getReceived());
                titleTextViewViewHeader2.setText(datumLable_languages.getWalletSummary());
                textviewbalancewallet.setText(datumLable_languages.getBalance() + ":"+" ");
                textviewpointswallet.setText(datumLable_languages.getPoints() + ":");
                homeTextViewFooter.setText(datumLable_languages.getHome());
                quickPayTextViewFooter.setText(datumLable_languages.getQuickPay());
                beneficiaryTextViewFooter.setText(datumLable_languages.getBeneficiary());
                historyTextViewFooter.setText(datumLable_languages.getHistory());


            } else {
                titleTextViewViewHeader2.setText(getResources().getString(R.string.wallet_summary));
                textviewbalancewallet.setText(getResources().getString(R.string.a_c_balance));
                textviewpointswallet.setText(getResources().getString(R.string.points));

                arrayList.add(getResources().getString(R.string.all));
                arrayList.add(getResources().getString(R.string.paid));
                arrayList.add(getResources().getString(R.string.received));
                homeTextViewFooter.setText(getResources().getString(R.string.home));
                quickPayTextViewFooter.setText(getResources().getString(R.string.quick_pay));
                beneficiaryTextViewFooter.setText(getResources().getString(R.string.beneficiary));
                historyTextViewFooter.setText(getResources().getString(R.string.history));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i = 0; i < arrayList.size(); i++) {
            transactionTabLayout.addTab(transactionTabLayout.newTab().setText(arrayList.get(i)));
        }

        transactionTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        transactionTabLayout.setTabMode(TabLayout.MODE_FIXED);

        walletSummaryPagerAdapter = new WalletSummaryPagerAdapter(getSupportFragmentManager(), arrayList, getMyUserId());
        transactionViewPager.setAdapter(walletSummaryPagerAdapter);
//        transactionTabLayout.post(new Runnable() {
//            @Override
//            public void run() {
        transactionTabLayout.setupWithViewPager(transactionViewPager);
        transactionViewPager.setCurrentItem(0);
        transactionViewPager.setOffscreenPageLimit(0);
//            }
//        });

        transactionViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                walletSummaryPagerAdapter.callFragmentPage(position, false);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.menuImageViewHeader2, R.id.appImageViewHeader2, R.id.filterImageViewHeader2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
                slideHolderTransaction.toggle();
                break;
            case R.id.appImageViewHeader2:
                mIntent = new Intent(WalletSummaryActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);

                break;
            case R.id.filterImageViewHeader2:
                FilterBottomSheetFragment filterBottomSheetFragment = new FilterBottomSheetFragment();
                filterBottomSheetFragment.setComeFrom("wallet", datumLable_languages,this);
                filterBottomSheetFragment.show(getSupportFragmentManager(), "BottomSheet Fragment");
                break;
        }
    }

    public void filterApplied() {
        walletSummaryPagerAdapter.callFragmentPage(transactionViewPager.getCurrentItem(), true);
    }
}
