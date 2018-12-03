package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransactionReceiptListJsonPojo implements Serializable {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("status")
    @Expose
    private Boolean status;

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public class Data {

        @SerializedName("ResponseCode")
        @Expose
        private Integer responseCode;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("TransactionNumber")
        @Expose
        private String transactionNumber;
        @SerializedName("TransactionDateTime")
        @Expose
        private String transactionDateTime;
        @SerializedName("SendingAmount")
        @Expose
        private String sendingAmount;
        @SerializedName("PayInCurrency")
        @Expose
        private String payInCurrency;
        @SerializedName("ReceivingAmount")
        @Expose
        private String receivingAmount;
        @SerializedName("PayOutCurrency")
        @Expose
        private String payOutCurrency;
        @SerializedName("CommissionCharge")
        @Expose
        private String commissionCharge;
        @SerializedName("OtherCharges")
        @Expose
        private String otherCharges;
        @SerializedName("VATCharges")
        @Expose
        private String vATCharges;
        @SerializedName("VATPercentage")
        @Expose
        private Integer vATPercentage;
        @SerializedName("TotalPayable")
        @Expose
        private String totalPayable;
        @SerializedName("ExchangeRate")
        @Expose
        private String exchangeRate;
        @SerializedName("PurposeOfTransfer")
        @Expose
        private String purposeOfTransfer;
        @SerializedName("RemitterNo")
        @Expose
        private String remitterNo;
        @SerializedName("RemitterName")
        @Expose
        private String remitterName;
        @SerializedName("RemitterContactNo")
        @Expose
        private String remitterContactNo;
        @SerializedName("RemitterAddress")
        @Expose
        private String remitterAddress;
        @SerializedName("Remitter_DOB")
        @Expose
        private String remitterDOB;
        @SerializedName("ID_Type")
        @Expose
        private String iDType;
        @SerializedName("SenderIDNumber")
        @Expose
        private String senderIDNumber;
        @SerializedName("SenderIDExpiry")
        @Expose
        private String senderIDExpiry;
        @SerializedName("RelationWithBeneficiary")
        @Expose
        private String relationWithBeneficiary;
        @SerializedName("BeneficiaryNo")
        @Expose
        private String beneficiaryNo;
        @SerializedName("BeneficiaryName")
        @Expose
        private String beneficiaryName;
        @SerializedName("BeneficiaryAddress")
        @Expose
        private String beneficiaryAddress;
        @SerializedName("BankName")
        @Expose
        private String bankName;
        @SerializedName("BankAccountNo")
        @Expose
        private String bankAccountNo;
        @SerializedName("BankBranch")
        @Expose
        private String bankBranch;
        @SerializedName("BankAddress")
        @Expose
        private String bankAddress;
        @SerializedName("BankCode")
        @Expose
        private String bankCode;
        @SerializedName("CustomerEmail")
        @Expose
        private String customerEmail;
        @SerializedName("PayInCountry")
        @Expose
        private String payInCountry;
        @SerializedName("PayOutCountry")
        @Expose
        private String payOutCountry;
        @SerializedName("earnPoint")
        @Expose
        private String earnPoint;
        @SerializedName("AvailPoints")
        @Expose
        private String availPoints;
        @SerializedName("PaymentMode")
        @Expose
        private String paymentMode;
        @SerializedName("PayoutAgent_Name")
        @Expose
        private String payoutAgentName;
        @SerializedName("EnteredBy")
        @Expose
        private String enteredBy;

        public Integer getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(Integer responseCode) {
            this.responseCode = responseCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTransactionNumber() {
            return transactionNumber;
        }

        public void setTransactionNumber(String transactionNumber) {
            this.transactionNumber = transactionNumber;
        }

        public String getTransactionDateTime() {
            return transactionDateTime;
        }

        public void setTransactionDateTime(String transactionDateTime) {
            this.transactionDateTime = transactionDateTime;
        }

        public String getSendingAmount() {
            return sendingAmount;
        }

        public void setSendingAmount(String sendingAmount) {
            this.sendingAmount = sendingAmount;
        }

        public String getPayInCurrency() {
            return payInCurrency;
        }

        public void setPayInCurrency(String payInCurrency) {
            this.payInCurrency = payInCurrency;
        }

        public String getReceivingAmount() {
            return receivingAmount;
        }

        public void setReceivingAmount(String receivingAmount) {
            this.receivingAmount = receivingAmount;
        }

        public String getPayOutCurrency() {
            return payOutCurrency;
        }

        public void setPayOutCurrency(String payOutCurrency) {
            this.payOutCurrency = payOutCurrency;
        }

        public String getCommissionCharge() {
            return commissionCharge;
        }

        public void setCommissionCharge(String commissionCharge) {
            this.commissionCharge = commissionCharge;
        }

        public String getOtherCharges() {
            return otherCharges;
        }

        public void setOtherCharges(String otherCharges) {
            this.otherCharges = otherCharges;
        }

        public String getVATCharges() {
            return vATCharges;
        }

        public void setVATCharges(String vATCharges) {
            this.vATCharges = vATCharges;
        }

        public Integer getVATPercentage() {
            return vATPercentage;
        }

        public void setVATPercentage(Integer vATPercentage) {
            this.vATPercentage = vATPercentage;
        }

        public String getTotalPayable() {
            return totalPayable;
        }

        public void setTotalPayable(String totalPayable) {
            this.totalPayable = totalPayable;
        }

        public String getExchangeRate() {
            return exchangeRate;
        }

        public void setExchangeRate(String exchangeRate) {
            this.exchangeRate = exchangeRate;
        }

        public String getPurposeOfTransfer() {
            return purposeOfTransfer;
        }

        public void setPurposeOfTransfer(String purposeOfTransfer) {
            this.purposeOfTransfer = purposeOfTransfer;
        }

        public String getRemitterNo() {
            return remitterNo;
        }

        public void setRemitterNo(String remitterNo) {
            this.remitterNo = remitterNo;
        }

        public String getRemitterName() {
            return remitterName;
        }

        public void setRemitterName(String remitterName) {
            this.remitterName = remitterName;
        }

        public String getRemitterContactNo() {
            return remitterContactNo;
        }

        public void setRemitterContactNo(String remitterContactNo) {
            this.remitterContactNo = remitterContactNo;
        }

        public String getRemitterAddress() {
            return remitterAddress;
        }

        public void setRemitterAddress(String remitterAddress) {
            this.remitterAddress = remitterAddress;
        }

        public String getRemitterDOB() {
            return remitterDOB;
        }

        public void setRemitterDOB(String remitterDOB) {
            this.remitterDOB = remitterDOB;
        }

        public String getIDType() {
            return iDType;
        }

        public void setIDType(String iDType) {
            this.iDType = iDType;
        }

        public String getSenderIDNumber() {
            return senderIDNumber;
        }

        public void setSenderIDNumber(String senderIDNumber) {
            this.senderIDNumber = senderIDNumber;
        }

        public String getSenderIDExpiry() {
            return senderIDExpiry;
        }

        public void setSenderIDExpiry(String senderIDExpiry) {
            this.senderIDExpiry = senderIDExpiry;
        }

        public String getRelationWithBeneficiary() {
            return relationWithBeneficiary;
        }

        public void setRelationWithBeneficiary(String relationWithBeneficiary) {
            this.relationWithBeneficiary = relationWithBeneficiary;
        }

        public String getBeneficiaryNo() {
            return beneficiaryNo;
        }

        public void setBeneficiaryNo(String beneficiaryNo) {
            this.beneficiaryNo = beneficiaryNo;
        }

        public String getBeneficiaryName() {
            return beneficiaryName;
        }

        public void setBeneficiaryName(String beneficiaryName) {
            this.beneficiaryName = beneficiaryName;
        }

        public String getBeneficiaryAddress() {
            return beneficiaryAddress;
        }

        public void setBeneficiaryAddress(String beneficiaryAddress) {
            this.beneficiaryAddress = beneficiaryAddress;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankAccountNo() {
            return bankAccountNo;
        }

        public void setBankAccountNo(String bankAccountNo) {
            this.bankAccountNo = bankAccountNo;
        }

        public String getBankBranch() {
            return bankBranch;
        }

        public void setBankBranch(String bankBranch) {
            this.bankBranch = bankBranch;
        }

        public String getBankAddress() {
            return bankAddress;
        }

        public void setBankAddress(String bankAddress) {
            this.bankAddress = bankAddress;
        }

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public String getCustomerEmail() {
            return customerEmail;
        }

        public void setCustomerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
        }

        public String getPayInCountry() {
            return payInCountry;
        }

        public void setPayInCountry(String payInCountry) {
            this.payInCountry = payInCountry;
        }

        public String getPayOutCountry() {
            return payOutCountry;
        }

        public void setPayOutCountry(String payOutCountry) {
            this.payOutCountry = payOutCountry;
        }

        public String getEarnPoint() {
            return earnPoint;
        }

        public void setEarnPoint(String earnPoint) {
            this.earnPoint = earnPoint;
        }

        public String getAvailPoints() {
            return availPoints;
        }

        public void setAvailPoints(String availPoints) {
            this.availPoints = availPoints;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public String getPayoutAgentName() {
            return payoutAgentName;
        }

        public void setPayoutAgentName(String payoutAgentName) {
            this.payoutAgentName = payoutAgentName;
        }

        public String getEnteredBy() {
            return enteredBy;
        }

        public void setEnteredBy(String enteredBy) {
            this.enteredBy = enteredBy;
        }


    }
}
