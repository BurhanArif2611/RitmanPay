package com.fil.workerappz.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fil.workerappz.AddBeneficiaryActivity;
import com.fil.workerappz.R;
import com.fil.workerappz.SelectBeneficiaryActivity;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by HS on 08-Mar-18.
 * FIL AHM
 */

public class BankTabFragment extends BaseFragment {

    @BindView(R.id.addBeneficiaryTextView)
    TextView addBeneficiaryTextView;
    @BindView(R.id.selectBeneficiaryTextView)
    TextView selectBeneficiaryTextView;
    private Unbinder unbinder;

    private Intent mIntent;
    private LabelListData datumLable_languages = new LabelListData();
    private SessionManager sessionManager;

    public BankTabFragment() {

    }

    public static BankTabFragment newInstance() {
        BankTabFragment fragment = new BankTabFragment();
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bank_tab, container, false);

        unbinder = ButterKnife.bind(this, view);

        try {
sessionManager=new SessionManager(getActivity());
            datumLable_languages = sessionManager.getAppLanguageLabel();

            if (datumLable_languages != null) {

                addBeneficiaryTextView.setHint(datumLable_languages.getAddBeneficiary());
                selectBeneficiaryTextView.setHint(datumLable_languages.getSelectBeneficiary());

            }
            else
            {
                addBeneficiaryTextView.setHint(getResources().getString(R.string.add_beneficiary));
                selectBeneficiaryTextView.setHint(getResources().getString(R.string.select_beneficiary));

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (Constants.bankNextBenificaryCount == 0) {
            selectBeneficiaryTextView.setVisibility(View.GONE);
        } else {
            selectBeneficiaryTextView.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.addBeneficiaryTextView, R.id.selectBeneficiaryTextView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addBeneficiaryTextView:
                mIntent = new Intent(getActivity(), AddBeneficiaryActivity.class);
                mIntent.putExtra("come_from", "bank");
                mIntent.putExtra("activitytype", "bankadd");
                mIntent.putExtra("customernumber", String.valueOf(Constants.bankBenificaryCount));
                startActivity(mIntent);
                break;
            case R.id.selectBeneficiaryTextView:
                mIntent = new Intent(getActivity(), SelectBeneficiaryActivity.class);
                mIntent.putExtra("come_from", "bank");
                startActivity(mIntent);
                break;
        }
    }
}