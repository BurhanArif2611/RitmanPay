package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by FUSION on 09/04/2018.
 */

public class CustomerCardJsonPojo implements Serializable {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("id_customer")
        @Expose
        private String idCustomer;
        @SerializedName("customer_name")
        @Expose
        private String customerName;
        @SerializedName("ID_number")
        @Expose
        private String iDNumber;
        @SerializedName("ID_type_id")
        @Expose
        private String iDTypeId;
        @SerializedName("ID_number_country_code")
        @Expose
        private String iDNumberCountryCode;
        @SerializedName("entities_relationships_id")
        @Expose
        private String entitiesRelationshipsId;
        @SerializedName("relationships_status_id")
        @Expose
        private String relationshipsStatusId;
        @SerializedName("relationship_type_id")
        @Expose
        private String relationshipTypeId;
        @SerializedName("relationship_date_modified")
        @Expose
        private String relationshipDateModified;
        @SerializedName("id_card")
        @Expose
        private String idCard;
        @SerializedName("currency_code")
        @Expose
        private String currencyCode;
        @SerializedName("currency_sign")
        @Expose
        private String currencySign;
        @SerializedName("card_type_id")
        @Expose
        private String cardTypeId;
        @SerializedName("exp_date")
        @Expose
        private String expDate;
        @SerializedName("cvv")
        @Expose
        private String cvv;
        @SerializedName("card_number")
        @Expose
        private String cardNumber;
        @SerializedName("public_token")
        @Expose
        private String publicToken;
        @SerializedName("numerator")
        @Expose
        private String numerator;
        @SerializedName("close_date")
        @Expose
        private String closeDate;
        @SerializedName("card_status_id")
        @Expose
        private String cardStatusId;
        @SerializedName("entity_type_id")
        @Expose
        private String entityTypeId;
        @SerializedName("open_date")
        @Expose
        private String openDate;
        @SerializedName("balance")
        @Expose
        private String balance;
        @SerializedName("balance_date")
        @Expose
        private String balanceDate;
        @SerializedName("establishment_date")
        @Expose
        private String establishmentDate;
        @SerializedName("initiator_id")
        @Expose
        private String initiatorId;
        @SerializedName("login_user_name")
        @Expose
        private String loginUserName;
        @SerializedName("login_ivr_password")
        @Expose
        private String loginIvrPassword;
        @SerializedName("default_program_id")
        @Expose
        private String defaultProgramId;

        public String getIdCustomer() {
            return idCustomer;
        }

        public void setIdCustomer(String idCustomer) {
            this.idCustomer = idCustomer;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getIDNumber() {
            return iDNumber;
        }

        public void setIDNumber(String iDNumber) {
            this.iDNumber = iDNumber;
        }

        public String getIDTypeId() {
            return iDTypeId;
        }

        public void setIDTypeId(String iDTypeId) {
            this.iDTypeId = iDTypeId;
        }

        public String getIDNumberCountryCode() {
            return iDNumberCountryCode;
        }

        public void setIDNumberCountryCode(String iDNumberCountryCode) {
            this.iDNumberCountryCode = iDNumberCountryCode;
        }

        public String getEntitiesRelationshipsId() {
            return entitiesRelationshipsId;
        }

        public void setEntitiesRelationshipsId(String entitiesRelationshipsId) {
            this.entitiesRelationshipsId = entitiesRelationshipsId;
        }

        public String getRelationshipsStatusId() {
            return relationshipsStatusId;
        }

        public void setRelationshipsStatusId(String relationshipsStatusId) {
            this.relationshipsStatusId = relationshipsStatusId;
        }

        public String getRelationshipTypeId() {
            return relationshipTypeId;
        }

        public void setRelationshipTypeId(String relationshipTypeId) {
            this.relationshipTypeId = relationshipTypeId;
        }

        public String getRelationshipDateModified() {
            return relationshipDateModified;
        }

        public void setRelationshipDateModified(String relationshipDateModified) {
            this.relationshipDateModified = relationshipDateModified;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public String getCurrencySign() {
            return currencySign;
        }

        public void setCurrencySign(String currencySign) {
            this.currencySign = currencySign;
        }

        public String getCardTypeId() {
            return cardTypeId;
        }

        public void setCardTypeId(String cardTypeId) {
            this.cardTypeId = cardTypeId;
        }

        public String getExpDate() {
            return expDate;
        }

        public void setExpDate(String expDate) {
            this.expDate = expDate;
        }

        public String getCvv() {
            return cvv;
        }

        public void setCvv(String cvv) {
            this.cvv = cvv;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getPublicToken() {
            return publicToken;
        }

        public void setPublicToken(String publicToken) {
            this.publicToken = publicToken;
        }

        public String getNumerator() {
            return numerator;
        }

        public void setNumerator(String numerator) {
            this.numerator = numerator;
        }

        public String getCloseDate() {
            return closeDate;
        }

        public void setCloseDate(String closeDate) {
            this.closeDate = closeDate;
        }

        public String getCardStatusId() {
            return cardStatusId;
        }

        public void setCardStatusId(String cardStatusId) {
            this.cardStatusId = cardStatusId;
        }

        public String getEntityTypeId() {
            return entityTypeId;
        }

        public void setEntityTypeId(String entityTypeId) {
            this.entityTypeId = entityTypeId;
        }

        public String getOpenDate() {
            return openDate;
        }

        public void setOpenDate(String openDate) {
            this.openDate = openDate;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getBalanceDate() {
            return balanceDate;
        }

        public void setBalanceDate(String balanceDate) {
            this.balanceDate = balanceDate;
        }

        public String getEstablishmentDate() {
            return establishmentDate;
        }

        public void setEstablishmentDate(String establishmentDate) {
            this.establishmentDate = establishmentDate;
        }

        public String getInitiatorId() {
            return initiatorId;
        }

        public void setInitiatorId(String initiatorId) {
            this.initiatorId = initiatorId;
        }

        public String getLoginUserName() {
            return loginUserName;
        }

        public void setLoginUserName(String loginUserName) {
            this.loginUserName = loginUserName;
        }

        public String getLoginIvrPassword() {
            return loginIvrPassword;
        }

        public void setLoginIvrPassword(String loginIvrPassword) {
            this.loginIvrPassword = loginIvrPassword;
        }

        public String getDefaultProgramId() {
            return defaultProgramId;
        }

        public void setDefaultProgramId(String defaultProgramId) {
            this.defaultProgramId = defaultProgramId;
        }

    }
}
