package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.pojo.TransactionReceiptListJsonPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionReceiptActivity extends ActionBarActivity {

    @BindView(R.id.menuImageViewHeader2)
    ImageView menuImageViewHeader2;
    @BindView(R.id.appImageViewHeader2)
    ImageView appImageViewHeader2;
    @BindView(R.id.titleTextViewViewHeader2)
    TextView titleTextViewViewHeader2;
    @BindView(R.id.appLeftImageViewHeader2)
    ImageView appLeftImageViewHeader2;
    @BindView(R.id.skipTextViewViewHeader2)
    TextView skipTextViewViewHeader2;
    @BindView(R.id.filterImageViewHeader2)
    ImageView filterImageViewHeader2;
    @BindView(R.id.LinearBeneficiarReceiptSendLayout)
    LinearLayout LinearBeneficiarReceiptSendLayout;
    @BindView(R.id.transactionNumberTextView)
    TextView transactionNumberTextView;
    @BindView(R.id.dateandtimeTextView)
    TextView dateandtimeTextView;
    @BindView(R.id.sendingCurrencyTextView)
    TextView sendingCurrencyTextView;
    @BindView(R.id.serviceFeeTextView)
    TextView serviceFeeTextView;
    @BindView(R.id.otherChargeTextView)
    TextView otherChargeTextView;
    @BindView(R.id.vatTextView)
    TextView vatTextView;
    @BindView(R.id.totalPayableTextView)
    TextView totalPayableTextView;
    @BindView(R.id.exchangeRateTextView)
    TextView exchangeRateTextView;
    @BindView(R.id.receivingAmountTextView)
    TextView receivingAmountTextView;
    @BindView(R.id.purposeOfTransferTextView)
    TextView purposeOfTransferTextView;
    @BindView(R.id.accountNumberTextView)
    TextView accountNumberTextView;
    @BindView(R.id.bankNameTextView)
    TextView bankNameTextView;
    @BindView(R.id.bankCodeTextView)
    TextView bankCodeTextView;
    @BindView(R.id.branchTextView)
    TextView branchTextView;
    @BindView(R.id.bankAddressTextView)
    TextView bankAddressTextView;
    @BindView(R.id.customerNameTextView)
    TextView customerNameTextView;
    @BindView(R.id.customerMobileNumberTextView)
    TextView customerMobileNumberTextView;
    @BindView(R.id.customerAddressTextView)
    TextView customerAddressTextView;
    @BindView(R.id.idTypeTextView)
    TextView idTypeTextView;
    @BindView(R.id.idNumberTextView)
    TextView idNumberTextView;
    @BindView(R.id.idExpireyDateTextView)
    TextView idExpireyDateTextView;
    @BindView(R.id.relationTextView)
    TextView relationTextView;
    @BindView(R.id.beneficiaryNameTextView)
    TextView beneficiaryNameTextView;
    @BindView(R.id.beneficiaryMobileNumber)
    TextView beneficiaryMobileNumber;
    @BindView(R.id.beneficiaryAddressTextView)
    TextView beneficiaryAddressTextView;
    @BindView(R.id.cardNumberTextView)
    TextView cardNumberTextView;
    @BindView(R.id.availPointTextView)
    TextView availPointTextView;
    @BindView(R.id.balancePointsTextView)
    TextView balancePointsTextView;
    @BindView(R.id.linearAccountNumber)
    LinearLayout linearAccountNumber;
    @BindView(R.id.customerIdTextView)
    TextView customerIdTextView;
    @BindView(R.id.vatpercentageTextView)
    TextView vatpercentageTextView;
    private Intent mIntent;
    private String transactionNumber;
    private final ArrayList<TransactionReceiptListJsonPojo> transactionReceiptListJsonPojos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_receipt);
        ButterKnife.bind(this);
        menuImageViewHeader2.setImageResource(R.drawable.back_btn);
        titleTextViewViewHeader2.setText("Receipt");
        filterImageViewHeader2.setVisibility(View.INVISIBLE);

        mIntent = getIntent();
        if (mIntent != null) {
            transactionNumber = mIntent.getStringExtra("transactionnumber");
        }
        if (IsNetworkConnection.checkNetworkConnection(TransactionReceiptActivity.this)) {
            getTransactionReceipt();
        } else {
            Constants.showMessage(LinearBeneficiarReceiptSendLayout, TransactionReceiptActivity.this, getResources().getString(R.string.no_internet));
        }
    }

    @OnClick({R.id.menuImageViewHeader2, R.id.appImageViewHeader2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
                finish();
                break;

            case R.id.appImageViewHeader2:
                Intent mIntent = new Intent(TransactionReceiptActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
        }
    }

    private void getTransactionReceipt() {
        Constants.showProgress(TransactionReceiptActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("transactionNumber", transactionNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "transaction receipt json " + json);
        Call<List<TransactionReceiptListJsonPojo>> call = RestClient.get().transactionReceipyJsonCall(json);

        call.enqueue(new Callback<List<TransactionReceiptListJsonPojo>>() {
            @Override
            public void onResponse(Call<List<TransactionReceiptListJsonPojo>> call, Response<List<TransactionReceiptListJsonPojo>> response) {
                Constants.closeProgress();
                transactionReceiptListJsonPojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    if (response.body().get(0).getStatus() == true) {
                        transactionReceiptListJsonPojos.addAll(response.body());
                        if (transactionReceiptListJsonPojos.get(0).getData() != null) {
                            transactionNumberTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getTransactionNumber());
                            dateandtimeTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getTransactionDateTime());
                            sendingCurrencyTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getSendingAmount() + " " + "(" + transactionReceiptListJsonPojos.get(0).getData().getPayInCurrency() + ")");
                            serviceFeeTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getCommissionCharge() + " " + "(" + transactionReceiptListJsonPojos.get(0).getData().getPayInCurrency() + ")");
                            otherChargeTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getOtherCharges());
                            vatTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getVATCharges() + " " + "(" + transactionReceiptListJsonPojos.get(0).getData().getPayInCurrency() + ")");
                            totalPayableTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getTotalPayable() + " " + "(" + transactionReceiptListJsonPojos.get(0).getData().getPayInCurrency() + ")");
                            exchangeRateTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getExchangeRate());
                            receivingAmountTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getReceivingAmount() + " " + "(" + transactionReceiptListJsonPojos.get(0).getData().getPayOutCurrency() + ")");
                            vatpercentageTextView.setText(getResources().getString(R.string.vat_10_00) + " " + "(" + transactionReceiptListJsonPojos.get(0).getData().getVATPercentage() +"%"+ ")");
                            purposeOfTransferTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getPurposeOfTransfer());
                            if (transactionReceiptListJsonPojos.get(0).getData().getPaymentMode().equalsIgnoreCase("Cash Transfer")) {
                                linearAccountNumber.setVisibility(View.GONE);

                            } else {
                                linearAccountNumber.setVisibility(View.VISIBLE);
                                accountNumberTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getBankAccountNo());
                            }
                            bankNameTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getBankName());
                            bankCodeTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getBankCode());
                            branchTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getBankBranch());
                            bankAddressTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getPayoutAgentName());
                            customerNameTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getRemitterName());
                            customerMobileNumberTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getRemitterContactNo());
                            customerAddressTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getRemitterAddress());
                            idTypeTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getIDType());
                            idNumberTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getSenderIDNumber());
                            idExpireyDateTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getSenderIDExpiry());
                            relationTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getRelationWithBeneficiary());
                            beneficiaryNameTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getBeneficiaryName());
                            beneficiaryMobileNumber.setText(transactionReceiptListJsonPojos.get(0).getData().getBeneficiaryNo());
                            beneficiaryAddressTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getBeneficiaryAddress());
                            cardNumberTextView.setText("");
                            availPointTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getAvailPoints());
                            balancePointsTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getEarnPoint());
                            customerIdTextView.setText(transactionReceiptListJsonPojos.get(0).getData().getRemitterNo());
                        } else {
                            Constants.showMessage(LinearBeneficiarReceiptSendLayout, TransactionReceiptActivity.this, response.body().get(0).getInfo());
                            transactionNumberTextView.setText("");
                            dateandtimeTextView.setText("");
                            sendingCurrencyTextView.setText("");
                            otherChargeTextView.setText("");
                            totalPayableTextView.setText("");
                            exchangeRateTextView.setText("");
                            receivingAmountTextView.setText("");
                            purposeOfTransferTextView.setText("");
                            accountNumberTextView.setText("");
                            bankNameTextView.setText("");
                            bankCodeTextView.setText("");
                            branchTextView.setText("");
                            bankAddressTextView.setText("");
                            customerNameTextView.setText("");
                            customerMobileNumberTextView.setText("");
                            customerAddressTextView.setText("");
                            idTypeTextView.setText("");
                            idNumberTextView.setText("");
                            idExpireyDateTextView.setText("");
                            relationTextView.setText("");
                            beneficiaryNameTextView.setText("");
                            beneficiaryMobileNumber.setText("");
                            beneficiaryAddressTextView.setText("");
                            cardNumberTextView.setText("");
                            availPointTextView.setText("");
                            balancePointsTextView.setText("");

                        }

                    } else {
                        Constants.showMessage(LinearBeneficiarReceiptSendLayout, TransactionReceiptActivity.this, response.body().get(0).getInfo());
                        transactionNumberTextView.setText("");
                        dateandtimeTextView.setText("");
                        sendingCurrencyTextView.setText("");
                        otherChargeTextView.setText("");
                        totalPayableTextView.setText("");
                        exchangeRateTextView.setText("");
                        receivingAmountTextView.setText("");
                        purposeOfTransferTextView.setText("");
                        accountNumberTextView.setText("");
                        bankNameTextView.setText("");
                        bankCodeTextView.setText("");
                        branchTextView.setText("");
                        bankAddressTextView.setText("");
                        customerNameTextView.setText("");
                        customerMobileNumberTextView.setText("");
                        customerAddressTextView.setText("");
                        idTypeTextView.setText("");
                        idNumberTextView.setText("");
                        idExpireyDateTextView.setText("");
                        relationTextView.setText("");
                        beneficiaryNameTextView.setText("");
                        beneficiaryMobileNumber.setText("");
                        beneficiaryAddressTextView.setText("");
                        cardNumberTextView.setText("");
                        availPointTextView.setText("");
                        balancePointsTextView.setText("");
                    }
                }

            }


            @Override
            public void onFailure(Call<List<TransactionReceiptListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }
}
