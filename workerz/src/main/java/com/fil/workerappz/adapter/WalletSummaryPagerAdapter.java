package com.fil.workerappz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fil.workerappz.WalletSummaryActivity;
import com.fil.workerappz.fragments.WalletHistoryFragment;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.SessionManager;

import java.util.ArrayList;

/**
 * Created by HS on 27-Feb-18.
 * FIL AHM
 */

public class WalletSummaryPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<String> stringArrayList;
    private final int userId;
    private WalletHistoryFragment walletHistoryFragment = null;
    private WalletHistoryFragment walletHistoryFragment1 = null;
    private WalletHistoryFragment walletHistoryFragment2 = null;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();

    public WalletSummaryPagerAdapter(FragmentManager fragmentManager, ArrayList<String> stringArrayList, int userId) {
        super(fragmentManager);
        this.stringArrayList = stringArrayList;
        this.userId = userId;
    }

    public void callFragmentPage(int position, boolean flag) {
        switch (position) {
            case 0:
                if (walletHistoryFragment == null) {
                    walletHistoryFragment = new WalletHistoryFragment();
                    walletHistoryFragment.newInstance();
                }
                if (flag == false) {
//                    Constants.filter = "all";
                    Constants.filter = stringArrayList.get(0);
                }
                walletHistoryFragment.getTransactionHistory(position, userId);
                break;
            case 1:
                if (walletHistoryFragment1 == null) {
                    walletHistoryFragment1 = new WalletHistoryFragment();
                    walletHistoryFragment1.newInstance();
                }
                if (flag == false) {
//                    Constants.filter = "paid";
                    Constants.filter = stringArrayList.get(1);
                }
                walletHistoryFragment1.getTransactionHistory(position, userId);
                break;
            case 2:
                if (walletHistoryFragment2 == null) {
                    walletHistoryFragment2 = new WalletHistoryFragment();
                    walletHistoryFragment2.newInstance();
                }
                if (flag == false) {
//                    Constants.filter = "received";
                    Constants.filter = stringArrayList.get(2);
                }
                walletHistoryFragment2.getTransactionHistory(position, userId);
                break;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return stringArrayList.size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (walletHistoryFragment == null) {
                    walletHistoryFragment = new WalletHistoryFragment();
                    walletHistoryFragment.newInstance();
                }
                return walletHistoryFragment;
            case 1:
                if (walletHistoryFragment1 == null) {
                    walletHistoryFragment1 = new WalletHistoryFragment();
                    walletHistoryFragment1.newInstance();
                }
                return walletHistoryFragment1;
            case 2:
                if (walletHistoryFragment2 == null) {
                    walletHistoryFragment2 = new WalletHistoryFragment();
                    walletHistoryFragment2.newInstance();
                }
                return walletHistoryFragment2;
        }
        return null;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return stringArrayList.get(position);
    }

}