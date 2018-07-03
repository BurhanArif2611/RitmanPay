package com.fil.workerappz.pojo.ding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 22-Mar-18.
 * FIL AHM
 */

public class SendTransferList implements Serializable {


    @Expose
    @SerializedName("data")
    private Data data;
    @Expose
    @SerializedName("info")
    private String info;
    @Expose
    @SerializedName("status")
    private boolean status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class Data {
        @Expose
        @SerializedName("ErrorCodes")
        private List<String> ErrorCodes;
        @Expose
        @SerializedName("ResultCode")
        private int ResultCode;
        @Expose
        @SerializedName("TransferRecord")
        private TransferRecord TransferRecord;

        public List<String> getErrorCodes() {
            return ErrorCodes;
        }

        public void setErrorCodes(List<String> ErrorCodes) {
            this.ErrorCodes = ErrorCodes;
        }

        public int getResultCode() {
            return ResultCode;
        }

        public void setResultCode(int ResultCode) {
            this.ResultCode = ResultCode;
        }

        public TransferRecord getTransferRecord() {
            return TransferRecord;
        }

        public void setTransferRecord(TransferRecord TransferRecord) {
            this.TransferRecord = TransferRecord;
        }
    }

    public static class TransferRecord {
        @Expose
        @SerializedName("AccountNumber")
        private String AccountNumber;
        @Expose
        @SerializedName("ReceiptParams")
        private String ReceiptParams;
        @Expose
        @SerializedName("ReceiptText")
        private String ReceiptText;
        @Expose
        @SerializedName("ProcessingState")
        private String ProcessingState;
        @Expose
        @SerializedName("CompletedUtc")
        private String CompletedUtc;
        @Expose
        @SerializedName("StartedUtc")
        private String StartedUtc;
        @Expose
        @SerializedName("CommissionApplied")
        private int CommissionApplied;
        @Expose
        @SerializedName("Price")
        private Price Price;
        @Expose
        @SerializedName("SkuCode")
        private String SkuCode;
        @Expose
        @SerializedName("TransferId")
        private TransferId TransferId;

        public String getAccountNumber() {
            return AccountNumber;
        }

        public void setAccountNumber(String AccountNumber) {
            this.AccountNumber = AccountNumber;
        }

        public String getReceiptParams() {
            return ReceiptParams;
        }

        public void setReceiptParams(String ReceiptParams) {
            this.ReceiptParams = ReceiptParams;
        }

        public String getReceiptText() {
            return ReceiptText;
        }

        public void setReceiptText(String ReceiptText) {
            this.ReceiptText = ReceiptText;
        }

        public String getProcessingState() {
            return ProcessingState;
        }

        public void setProcessingState(String ProcessingState) {
            this.ProcessingState = ProcessingState;
        }

        public String getCompletedUtc() {
            return CompletedUtc;
        }

        public void setCompletedUtc(String CompletedUtc) {
            this.CompletedUtc = CompletedUtc;
        }

        public String getStartedUtc() {
            return StartedUtc;
        }

        public void setStartedUtc(String StartedUtc) {
            this.StartedUtc = StartedUtc;
        }

        public int getCommissionApplied() {
            return CommissionApplied;
        }

        public void setCommissionApplied(int CommissionApplied) {
            this.CommissionApplied = CommissionApplied;
        }

        public Price getPrice() {
            return Price;
        }

        public void setPrice(Price Price) {
            this.Price = Price;
        }

        public String getSkuCode() {
            return SkuCode;
        }

        public void setSkuCode(String SkuCode) {
            this.SkuCode = SkuCode;
        }

        public TransferId getTransferId() {
            return TransferId;
        }

        public void setTransferId(TransferId TransferId) {
            this.TransferId = TransferId;
        }
    }

    public static class Price {
        @Expose
        @SerializedName("SendCurrencyIso")
        private String SendCurrencyIso;
        @Expose
        @SerializedName("SendValue")
        private double SendValue;
        @Expose
        @SerializedName("TaxCalculation")
        private String TaxCalculation;
        @Expose
        @SerializedName("TaxName")
        private String TaxName;
        @Expose
        @SerializedName("TaxRate")
        private int TaxRate;
        @Expose
        @SerializedName("ReceiveValueExcludingTax")
        private int ReceiveValueExcludingTax;
        @Expose
        @SerializedName("ReceiveCurrencyIso")
        private String ReceiveCurrencyIso;
        @Expose
        @SerializedName("ReceiveValue")
        private int ReceiveValue;
        @Expose
        @SerializedName("DistributorFee")
        private int DistributorFee;
        @Expose
        @SerializedName("CustomerFee")
        private int CustomerFee;

        public String getSendCurrencyIso() {
            return SendCurrencyIso;
        }

        public void setSendCurrencyIso(String SendCurrencyIso) {
            this.SendCurrencyIso = SendCurrencyIso;
        }

        public double getSendValue() {
            return SendValue;
        }

        public void setSendValue(double SendValue) {
            this.SendValue = SendValue;
        }

        public String getTaxCalculation() {
            return TaxCalculation;
        }

        public void setTaxCalculation(String TaxCalculation) {
            this.TaxCalculation = TaxCalculation;
        }

        public String getTaxName() {
            return TaxName;
        }

        public void setTaxName(String TaxName) {
            this.TaxName = TaxName;
        }

        public int getTaxRate() {
            return TaxRate;
        }

        public void setTaxRate(int TaxRate) {
            this.TaxRate = TaxRate;
        }

        public int getReceiveValueExcludingTax() {
            return ReceiveValueExcludingTax;
        }

        public void setReceiveValueExcludingTax(int ReceiveValueExcludingTax) {
            this.ReceiveValueExcludingTax = ReceiveValueExcludingTax;
        }

        public String getReceiveCurrencyIso() {
            return ReceiveCurrencyIso;
        }

        public void setReceiveCurrencyIso(String ReceiveCurrencyIso) {
            this.ReceiveCurrencyIso = ReceiveCurrencyIso;
        }

        public int getReceiveValue() {
            return ReceiveValue;
        }

        public void setReceiveValue(int ReceiveValue) {
            this.ReceiveValue = ReceiveValue;
        }

        public int getDistributorFee() {
            return DistributorFee;
        }

        public void setDistributorFee(int DistributorFee) {
            this.DistributorFee = DistributorFee;
        }

        public int getCustomerFee() {
            return CustomerFee;
        }

        public void setCustomerFee(int CustomerFee) {
            this.CustomerFee = CustomerFee;
        }
    }

    public static class TransferId {
        @Expose
        @SerializedName("DistributorRef")
        private String DistributorRef;
        @Expose
        @SerializedName("TransferRef")
        private String TransferRef;

        public String getDistributorRef() {
            return DistributorRef;
        }

        public void setDistributorRef(String DistributorRef) {
            this.DistributorRef = DistributorRef;
        }

        public String getTransferRef() {
            return TransferRef;
        }

        public void setTransferRef(String TransferRef) {
            this.TransferRef = TransferRef;
        }
    }
}