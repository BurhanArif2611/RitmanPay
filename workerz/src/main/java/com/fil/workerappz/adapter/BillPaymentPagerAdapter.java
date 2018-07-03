package com.fil.workerappz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fil.workerappz.fragments.BillPaymentFragment;
import com.fil.workerappz.pojo.HomeDataBean;

import java.util.ArrayList;

/**
 * Created by HS on 27-Feb-18.
 * FIL AHM
 */

public class BillPaymentPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<HomeDataBean> stringArrayList;

    public BillPaymentPagerAdapter(FragmentManager fragmentManager, ArrayList<HomeDataBean> stringArrayList) {
        super(fragmentManager);
        this.stringArrayList = stringArrayList;
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
        return BillPaymentFragment.newInstance(stringArrayList.get(position).getDataName(), position);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return stringArrayList.get(position).getDataName();
    }

}