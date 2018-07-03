package com.fil.workerappz.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fil.workerappz.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by HS on 27-Feb-18.
 * FIL AHM
 */

public class BillPaymentFragment extends BaseFragment {

  @BindView(R.id.selectCountryEditTextBillPay)
  MaterialSpinner selectCountryEditTextBillPay;
  @BindView(R.id.selectNetworkEditTextBillPay)
  MaterialSpinner selectNetworkEditTextBillPay;
  @BindView(R.id.rechargePhoneNoEditTextBillPay)
  MaterialEditText rechargePhoneNoEditTextBillPay;
  @BindView(R.id.amountEditTextBillPay)
  MaterialEditText amountEditTextBillPay;
  @BindView(R.id.saveNoCheckBoxBillPay)
  CheckBox saveNoCheckBoxBillPay;
  @BindView(R.id.proceedToPayTextViewBillPay)
  TextView proceedToPayTextViewBillPay;
  private Unbinder unbinder;

  public static BillPaymentFragment newInstance(String param1, int positionChild) {
    BillPaymentFragment fragment = new BillPaymentFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.bill_pay, container, false);

    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}