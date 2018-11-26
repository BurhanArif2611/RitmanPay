package com.fil.workerappz.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.ActionBarActivity;
import com.fil.workerappz.AddMoneyToWalletActivity;
import com.fil.workerappz.R;
import com.fil.workerappz.WalletActivity;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.QuickPayDataPojo;
import com.fil.workerappz.pojo.WalletSuggestionListPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HS on 08-Mar-18.
 * FIL AHM
 */

public class WalletMoneyTransferFragment extends BaseFragment {

    @BindView(R.id.amountSendMoneyEditText)
    MaterialEditText amountSendMoneyEditText;
    @BindView(R.id.mobileNoSendMoneyEditText)
    AutoCompleteTextView mobileNoSendMoneyEditText;
    @BindView(R.id.walletNameSendMoneyEditText)
    MaterialEditText walletNameSendMoneyEditText;
    @BindView(R.id.descriptionSendMoneyEditText)
    MaterialEditText descriptionSendMoneyEditText;
    @BindView(R.id.saveWalletDetailsCheckBox)
    CheckBox saveWalletDetailsCheckBox;
    private Unbinder unbinder;
    @BindView(R.id.mainWalletSummaryTransferLinearLayout)
    LinearLayout mainWalletSummaryTransferLinearLayout;
    @BindView(R.id.sendNowWalletMoneyTransfer)
    TextView sendNowWalletMoneyTransfer;

    private Context mContext;
    private ActionBarActivity activity;
    private int userId;
    private QuickPayDataPojo quickPayData = new QuickPayDataPojo();
    private int receiverId;
    private final ArrayList<WalletSuggestionListPojo> walletSuggestionListPojos = new ArrayList<>();
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private Intent mIntent;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<String> stringArrayListname = new ArrayList<>();
    private SessionManager sessionManager;
    private String amountsendmsg, vaildamountmsg, largeramountmsg, mobilenumber, mobilenumbernotregisteredmsg;

    public WalletMoneyTransferFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        activity.onUserInteraction();
        amountSendMoneyEditText.getText().clear();
        mobileNoSendMoneyEditText.getText().clear();
        walletNameSendMoneyEditText.getText().clear();
        descriptionSendMoneyEditText.getText().clear();
    }

    public static WalletMoneyTransferFragment newInstance(int userId, QuickPayDataPojo quickPayData) {
        WalletMoneyTransferFragment fragment = new WalletMoneyTransferFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", userId);
        bundle.putSerializable("quick_pay_object", quickPayData);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ActionBarActivity) context;
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = getArguments().getInt("user_id");
        quickPayData = (QuickPayDataPojo) getArguments().getSerializable("quick_pay_object");
        sessionManager = new SessionManager(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_money_transfer, container, false);
        unbinder = ButterKnife.bind(this, view);


        try {

            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {
                amountSendMoneyEditText.setHint(datumLable_languages.getAmount());
                amountSendMoneyEditText.setFloatingLabelText(datumLable_languages.getAmount());
                mobileNoSendMoneyEditText.setHint(datumLable_languages.getMobileNumber());
                walletNameSendMoneyEditText.setHint(datumLable_languages.getWalletName());
                walletNameSendMoneyEditText.setFloatingLabelText(datumLable_languages.getWalletName());
                descriptionSendMoneyEditText.setHint(datumLable_languages.getDescription());
                descriptionSendMoneyEditText.setFloatingLabelText(datumLable_languages.getDescription());
                sendNowWalletMoneyTransfer.setText(datumLable_languages.getSendNow());

            } else {
                amountSendMoneyEditText.setHint(getResources().getString(R.string.amount));
                mobileNoSendMoneyEditText.setHint(getResources().getString(R.string.mobile_number));
                walletNameSendMoneyEditText.setHint(getResources().getString(R.string.wallet_name));
                descriptionSendMoneyEditText.setHint(getResources().getString(R.string.description));
                sendNowWalletMoneyTransfer.setText(getResources().getString(R.string.send_now));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (datumLable_languages_msg != null) {
            amountsendmsg = datumLable_languages_msg.getEnterAmount();
            vaildamountmsg = datumLable_languages_msg.getEnterValidAmount();
            largeramountmsg = datumLable_languages_msg.getAmountIsLargerThanYourBalance();
            mobilenumber = datumLable_languages_msg.getEnterMobileNumber();
            mobilenumbernotregisteredmsg = datumLable_languages_msg.getMobileNumberIsNotRegistered();
        } else {

            amountsendmsg = getResources().getString(R.string.Please_Enter_amount);
            vaildamountmsg = getResources().getString(R.string.Please_Enter_valid_amount);
            largeramountmsg = getResources().getString(R.string.Amount_larger_your_balance);
            mobilenumber = getResources().getString(R.string.Please_Enter_Mobile_number);
            mobilenumbernotregisteredmsg = getResources().getString(R.string.Mobile_not_registerd_us);


        }
        if (quickPayData != null) {

            Gson gson = new Gson();
            String str = gson.toJson(quickPayData);
            CustomLog.d("System out", "quick pay object " + str);
//            quickPayData = gson.fromJson(str, QuickPayDataPojo.class);

            if (quickPayData.getBenificaryFirstName().length() != 0) {
                walletNameSendMoneyEditText.setText(quickPayData.getBenificaryFirstName());
                mobileNoSendMoneyEditText.setText(quickPayData.getBenificaryTelephone());
                receiverId = Integer.parseInt(quickPayData.getBenificaryID());
                stringArrayList.add(quickPayData.getBenificaryTelephone());
                stringArrayListname.add(quickPayData.getBenificaryFirstName());
            } else {
                walletNameSendMoneyEditText.setText(quickPayData.getUserFirstName());
                mobileNoSendMoneyEditText.setText(quickPayData.getUserMobile());
                receiverId = Integer.parseInt(quickPayData.getReceiverWalletUserID());
                stringArrayList.add(quickPayData.getUserMobile());
                stringArrayListname.add(quickPayData.getUserFirstName());
                amountSendMoneyEditText.setText(quickPayData.getTransactionAmount());
            }
            CustomLog.d("System out", "receiverId " + receiverId);
        }

        mobileNoSendMoneyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = mobileNoSendMoneyEditText.getText().toString().trim();
                if (IsNetworkConnection.checkNetworkConnection(getActivity())) {
                    if (str.length() > 1) {
                        getWalletSuggestionJsonCall(str);
                    } else if (str.length() == 0) {
                        walletNameSendMoneyEditText.setText("");
                        receiverId = 0;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });

        mobileNoSendMoneyEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                walletNameSendMoneyEditText.setText(walletSuggestionListPojos.get(0).getData().get(0).getUserFirstName());
                receiverId = walletSuggestionListPojos.get(0).getData().get(0).getUserID();
            }
        });

        return view;
    }

    private boolean checkValidation() {
        boolean checkFlag = true;
        if (amountSendMoneyEditText.getText().toString().trim().length() == 0) {
            Constants.showMessage(mainWalletSummaryTransferLinearLayout, mContext, amountsendmsg);
            checkFlag = false;
        } else if (Constants.findNumericValue(amountSendMoneyEditText.getText().toString().trim()) <= 0 || Constants.findNumericValue(amountSendMoneyEditText.getText().toString().trim()) <= 0 || amountSendMoneyEditText.getText().toString().trim().equalsIgnoreCase(".") || amountSendMoneyEditText.getText().toString().trim().equalsIgnoreCase("0") || amountSendMoneyEditText.getText().toString().startsWith(".")) {
            Constants.showMessage(mainWalletSummaryTransferLinearLayout, mContext, vaildamountmsg);
            return false;
        } else if (Constants.findNumericValue(amountSendMoneyEditText.getText().toString().trim()) > sessionManager.getWalletBalance()) {
            Constants.showMessage(mainWalletSummaryTransferLinearLayout, mContext, largeramountmsg);
            return false;
        } else if (mobileNoSendMoneyEditText.getText().toString().trim().length() == 0) {
            Constants.showMessage(mainWalletSummaryTransferLinearLayout, mContext, mobilenumber);
            checkFlag = false;
        }
//        else if (walletNameSendMoneyEditText.getText().toString().trim().length() == 0) {
//            Constants.showMessage(mainWalletSummaryTransferLinearLayout, mContext, "Please enter wallet name.");
//            checkFlag = false;
//        }


        return checkFlag;
    }

    private void getWalletSuggestionJsonCall(String search) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("searchCond", search);
            jsonObject.put("userID", String.valueOf(userId));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "profile json " + json);

        Call<List<WalletSuggestionListPojo>> call = RestClient.get().getWalletSuggestionJsonCall(json);

        call.enqueue(new Callback<List<WalletSuggestionListPojo>>() {

            @Override
            public void onResponse(Call<List<WalletSuggestionListPojo>> call, Response<List<WalletSuggestionListPojo>> response) {

                if (response.body() != null && response.body() instanceof ArrayList) {
                    walletSuggestionListPojos.clear();
                    walletSuggestionListPojos.addAll(response.body());
                    if (walletSuggestionListPojos.get(0).getStatus() == true) {
                        if (walletSuggestionListPojos.get(0).getData() != null) {
                            stringArrayList.clear();
                            for (int i = 0; i < walletSuggestionListPojos.get(0).getData().size(); i++) {
                                stringArrayList.add(walletSuggestionListPojos.get(0).getData().get(i).getUserMobile());
                                stringArrayListname.add(walletSuggestionListPojos.get(0).getData().get(i).getUserFirstName());
                            }
                            ArrayAdapter cityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stringArrayList);
                            mobileNoSendMoneyEditText.setAdapter(cityAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<WalletSuggestionListPojo>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.sendNowWalletMoneyTransfer)
    public void onViewClicked() {
        Constants.hideKeyboard((Activity) mContext);
        Boolean flag = false;
        Boolean flagname = false;
        if (checkValidation() == true) {
            for (int i = 0; i < stringArrayList.size(); i++) {
                if (mobileNoSendMoneyEditText.getText().toString().trim().equalsIgnoreCase(stringArrayList.get(i))) {
                    flag = true;

                }
                if (walletNameSendMoneyEditText.getText().toString().equalsIgnoreCase(stringArrayListname.get(i))) {
                    flagname = true;

                }
            }
            if (flag == true && flagname == true) {
                mIntent = new Intent(getActivity(), WalletActivity.class);
                mIntent.putExtra("amount", amountSendMoneyEditText.getText().toString().trim());
                mIntent.putExtra("receiverWalletUserID", receiverId);
                mIntent.putExtra("receiverWalletname", walletNameSendMoneyEditText.getText().toString().trim());
                startActivity(mIntent);
            } else if (!flag) {
                Constants.showMessage(mainWalletSummaryTransferLinearLayout, mContext, mobilenumbernotregisteredmsg);
            } else if (!flagname) {
                Constants.showMessage(mainWalletSummaryTransferLinearLayout, mContext, "Wallet Name not Registered With Us.");
            }
//
//            mIntent = new Intent(getActivity(), WalletActivity.class);
//                mIntent.putExtra("amount", amountSendMoneyEditText.getText().toString().trim());
//                mIntent.putExtra("receiverWalletUserID", receiverId);
//                mIntent.putExtra("receiverWalletname",walletNameSendMoneyEditText.getText().toString().trim());
//                startActivity(mIntent);
        }
    }
}