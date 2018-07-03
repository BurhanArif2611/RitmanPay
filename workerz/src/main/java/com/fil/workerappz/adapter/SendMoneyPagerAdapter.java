package com.fil.workerappz.adapter;import android.os.Parcelable;import android.support.v4.app.Fragment;import android.support.v4.app.FragmentManager;import android.support.v4.app.FragmentStatePagerAdapter;import android.support.v4.app.FragmentTransaction;import android.support.v4.view.PagerAdapter;import com.fil.workerappz.AddBeneficiaryCustomerInfoFragment;import com.fil.workerappz.SendMoneyActivity;import com.fil.workerappz.fragments.BankTabFragment;import com.fil.workerappz.fragments.CashPickUpFragment;import com.fil.workerappz.fragments.DoorToDoorDeliveryFragment;import com.fil.workerappz.fragments.WalletMoneyTransferFragment;import com.fil.workerappz.pojo.QuickPayDataPojo;import com.fil.workerappz.utils.Constants;import java.util.ArrayList;/** * Created by HS on 27-Feb-18. * FIL AHM */public class SendMoneyPagerAdapter extends FragmentStatePagerAdapter {    private final ArrayList<String> stringArrayList;    private final int userId;    private WalletMoneyTransferFragment walletMoneyTransferFragment = null;    private BankTabFragment bankTabFragment = null;    private CashPickUpFragment cashPickUpFragment = null;    private DoorToDoorDeliveryFragment doorToDoorDeliveryFragment = null;    private QuickPayDataPojo quickPayData = new QuickPayDataPojo();    private AddBeneficiaryCustomerInfoFragment customerinfoFragment = null;    public SendMoneyPagerAdapter(FragmentManager fragmentManager, ArrayList<String> stringArrayList, int userId, QuickPayDataPojo quickPayData) {        super(fragmentManager);        this.stringArrayList = stringArrayList;        this.userId = userId;        this.quickPayData = quickPayData;    }    public void callFragmentPage(int position) {        switch (position) {            case 0:                if (walletMoneyTransferFragment!=null) {                    walletMoneyTransferFragment.onResume();                }                break;            case 1:                break;            case 2:                break;            case 3:                break;        }    }    @Override    public int getItemPosition(Object object) {        return PagerAdapter.POSITION_NONE;    }    // Returns total number of pages    @Override    public int getCount() {        return stringArrayList.size();    }    // Returns the fragment to display for that page    @Override    public Fragment getItem(int position) {        switch (position) {            case 0:                if (walletMoneyTransferFragment == null) {                    walletMoneyTransferFragment = new WalletMoneyTransferFragment().newInstance(userId, quickPayData);                }                return walletMoneyTransferFragment;            case 1:                if (Constants.bankBenificaryCount == 0 && Constants.beneficiarcount == 0) {                    return customerinfoFragment = new AddBeneficiaryCustomerInfoFragment().newInstance("bank", userId);                } else if (Constants.bankBenificaryCount != 0) {                    return bankTabFragment = new BankTabFragment().newInstance();                }            case 2:                if (Constants.bankBenificaryCount == 0 && Constants.beneficiarcount == 0) {                    return customerinfoFragment = new AddBeneficiaryCustomerInfoFragment().newInstance("cash", userId);                } else if (Constants.bankBenificaryCount != 0) {                    return cashPickUpFragment = new CashPickUpFragment().newInstance();                }            case 3:                return doorToDoorDeliveryFragment = new DoorToDoorDeliveryFragment().newInstance();        }        return null;    }    // Returns the page title for the top indicator    @Override    public CharSequence getPageTitle(int position) {        return stringArrayList.get(position);    }}