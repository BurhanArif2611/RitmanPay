package com.fil.workerappz.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fil.workerappz.fragments.TransactionHistoryFragment;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.utils.Constants;

import java.util.ArrayList;

/**
 * Created by HS on 27-Feb-18.
 * FIL AHM
 */

public class TransactionPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<String> stringArrayList;
    private final int userId;
    private final Activity activity;
    private LabelListData datumLable_languages;
    private TransactionHistoryFragment transactionHistoryFragment = null;
    private TransactionHistoryFragment transactionHistoryFragment1 = null;
    private TransactionHistoryFragment transactionHistoryFragment2 = null;

    public TransactionPagerAdapter(FragmentManager fragmentManager, ArrayList<String> stringArrayList, int userId, Activity activity, LabelListData datumLable_languages) {
        super(fragmentManager);
        this.stringArrayList = stringArrayList;
        this.userId = userId;
        this.activity = activity;
    }

    public void callFragmentPage(int position, boolean flag) {
        switch (position) {
            case 0:
                if (transactionHistoryFragment == null) {
                    transactionHistoryFragment = new TransactionHistoryFragment();
                    transactionHistoryFragment.newInstance();
                }
                if (flag == false) {
//                    Constants.filter = stringArrayList.get(0);
                    Constants.filter = "";
                }
                transactionHistoryFragment.getTransactionHistory(position, userId, activity);
                break;
            case 1:
                if (transactionHistoryFragment1 == null) {
                    transactionHistoryFragment1 = new TransactionHistoryFragment();
                    transactionHistoryFragment1.newInstance();
                }
                if (flag == false) {
//                    Constants.filter = stringArrayList.get(1);
                    Constants.filter ="paid";
                }
                transactionHistoryFragment1.getTransactionHistory(position, userId, activity);
                break;
            case 2:
                if (transactionHistoryFragment2 == null) {
                    transactionHistoryFragment2 = new TransactionHistoryFragment();
                    transactionHistoryFragment2.newInstance();
                }
                if (flag == false) {
//                    Constants.filter = stringArrayList.get(2);
                    Constants.filter = "received";
                }
                transactionHistoryFragment2.getTransactionHistory(position, userId, activity);
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
                if (transactionHistoryFragment == null) {
                    transactionHistoryFragment = new TransactionHistoryFragment();
                    transactionHistoryFragment.newInstance();
                }
                return transactionHistoryFragment;
            case 1:
                if (transactionHistoryFragment1 == null) {
                    transactionHistoryFragment1 = new TransactionHistoryFragment();
                    transactionHistoryFragment1.newInstance();
                }
                return transactionHistoryFragment1;
            case 2:
                if (transactionHistoryFragment2 == null) {
                    transactionHistoryFragment2 = new TransactionHistoryFragment();
                    transactionHistoryFragment2.newInstance();
                }
                return transactionHistoryFragment2;
        }
        return null;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return stringArrayList.get(position);
    }

}