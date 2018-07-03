package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by FUSION on 23/03/2018.
 */

public class DingTransferPayJsonPojo implements Serializable {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("TransferRecord")
        @Expose
        private TransferRecord transferRecord;
        @SerializedName("ResultCode")
        @Expose
        private Integer resultCode;
        @SerializedName("ErrorCodes")
        @Expose
        private List<Object> errorCodes = null;

        public TransferRecord getTransferRecord() {
            return transferRecord;
        }

        public void setTransferRecord(TransferRecord transferRecord) {
            this.transferRecord = transferRecord;
        }

        public Integer getResultCode() {
            return resultCode;
        }

        public void setResultCode(Integer resultCode) {
            this.resultCode = resultCode;
        }

        public List<Object> getErrorCodes() {
            return errorCodes;
        }

        public void setErrorCodes(List<Object> errorCodes) {
            this.errorCodes = errorCodes;
        }

    }

    public class TransferRecord {

        @SerializedName("TransferId")
        @Expose
        private TransferId transferId;
        @SerializedName("SkuCode")
        @Expose
        private String skuCode;
        @SerializedName("Price")
        @Expose
        private Price price;
        @SerializedName("CommissionApplied")
        @Expose
        private Integer commissionApplied;
        @SerializedName("StartedUtc")
        @Expose
        private String startedUtc;
        @SerializedName("CompletedUtc")
        @Expose
        private String completedUtc;
        @SerializedName("ProcessingState")
        @Expose
        private String processingState;
        @SerializedName("ReceiptText")
        @Expose
        private Object receiptText;
        @SerializedName("ReceiptParams")
        @Expose
        private Object receiptParams;
        @SerializedName("AccountNumber")
        @Expose
        private String accountNumber;

        public TransferId getTransferId() {
            return transferId;
        }

        public void setTransferId(TransferId transferId) {
            this.transferId = transferId;
        }

        public String getSkuCode() {
            return skuCode;
        }

        public void setSkuCode(String skuCode) {
            this.skuCode = skuCode;
        }

        public Price getPrice() {
            return price;
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Integer getCommissionApplied() {
            return commissionApplied;
        }

        public void setCommissionApplied(Integer commissionApplied) {
            this.commissionApplied = commissionApplied;
        }

        public String getStartedUtc() {
            return startedUtc;
        }

        public void setStartedUtc(String startedUtc) {
            this.startedUtc = startedUtc;
        }

        public String getCompletedUtc() {
            return completedUtc;
        }

        public void setCompletedUtc(String completedUtc) {
            this.completedUtc = completedUtc;
        }

        public String getProcessingState() {
            return processingState;
        }

        public void setProcessingState(String processingState) {
            this.processingState = processingState;
        }

        public Object getReceiptText() {
            return receiptText;
        }

        public void setReceiptText(Object receiptText) {
            this.receiptText = receiptText;
        }

        public Object getReceiptParams() {
            return receiptParams;
        }

        public void setReceiptParams(Object receiptParams) {
            this.receiptParams = receiptParams;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

    }

    public class Price {

        @SerializedName("CustomerFee")
        @Expose
        private Integer customerFee;
        @SerializedName("DistributorFee")
        @Expose
        private Integer distributorFee;
        @SerializedName("ReceiveValue")
        @Expose
        private Integer receiveValue;
        @SerializedName("ReceiveCurrencyIso")
        @Expose
        private String receiveCurrencyIso;
        @SerializedName("ReceiveValueExcludingTax")
        @Expose
        private Integer receiveValueExcludingTax;
        @SerializedName("TaxRate")
        @Expose
        private Integer taxRate;
        @SerializedName("TaxName")
        @Expose
        private Object taxName;
        @SerializedName("TaxCalculation")
        @Expose
        private Object taxCalculation;
        @SerializedName("SendValue")
        @Expose
        private Double sendValue;
        @SerializedName("SendCurrencyIso")
        @Expose
        private String sendCurrencyIso;

        public Integer getCustomerFee() {
            return customerFee;
        }

        public void setCustomerFee(Integer customerFee) {
            this.customerFee = customerFee;
        }

        public Integer getDistributorFee() {
            return distributorFee;
        }

        public void setDistributorFee(Integer distributorFee) {
            this.distributorFee = distributorFee;
        }

        public Integer getReceiveValue() {
            return receiveValue;
        }

        public void setReceiveValue(Integer receiveValue) {
            this.receiveValue = receiveValue;
        }

        public String getReceiveCurrencyIso() {
            return receiveCurrencyIso;
        }

        public void setReceiveCurrencyIso(String receiveCurrencyIso) {
            this.receiveCurrencyIso = receiveCurrencyIso;
        }

        public Integer getReceiveValueExcludingTax() {
            return receiveValueExcludingTax;
        }

        public void setReceiveValueExcludingTax(Integer receiveValueExcludingTax) {
            this.receiveValueExcludingTax = receiveValueExcludingTax;
        }

        public Integer getTaxRate() {
            return taxRate;
        }

        public void setTaxRate(Integer taxRate) {
            this.taxRate = taxRate;
        }

        public Object getTaxName() {
            return taxName;
        }

        public void setTaxName(Object taxName) {
            this.taxName = taxName;
        }

        public Object getTaxCalculation() {
            return taxCalculation;
        }

        public void setTaxCalculation(Object taxCalculation) {
            this.taxCalculation = taxCalculation;
        }

        public Double getSendValue() {
            return sendValue;
        }

        public void setSendValue(Double sendValue) {
            this.sendValue = sendValue;
        }

        public String getSendCurrencyIso() {
            return sendCurrencyIso;
        }

        public void setSendCurrencyIso(String sendCurrencyIso) {
            this.sendCurrencyIso = sendCurrencyIso;
        }

    }
    public class TransferId {

        @SerializedName("TransferRef")
        @Expose
        private String transferRef;
        @SerializedName("DistributorRef")
        @Expose
        private String distributorRef;

        public String getTransferRef() {
            return transferRef;
        }

        public void setTransferRef(String transferRef) {
            this.transferRef = transferRef;
        }

        public String getDistributorRef() {
            return distributorRef;
        }

        public void setDistributorRef(String distributorRef) {
            this.distributorRef = distributorRef;
        }

    }

}
