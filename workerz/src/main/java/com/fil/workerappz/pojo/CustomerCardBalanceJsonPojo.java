package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by FUSION on 09/04/2018.
 */

public class CustomerCardBalanceJsonPojo implements Serializable {

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

        @SerializedName("ActionCode")
        @Expose
        private String actionCode;
        @SerializedName("AvlBal")
        @Expose
        private Double avlBal;
        @SerializedName("AccountBaseCurrency")
        @Expose
        private String accountBaseCurrency;

        public String getActionCode() {
            return actionCode;
        }

        public void setActionCode(String actionCode) {
            this.actionCode = actionCode;
        }

        public Double getAvlBal() {
            return avlBal;
        }

        public void setAvlBal(Double avlBal) {
            this.avlBal = avlBal;
        }

        public String getAccountBaseCurrency() {
            return accountBaseCurrency;
        }

        public void setAccountBaseCurrency(String accountBaseCurrency) {
            this.accountBaseCurrency = accountBaseCurrency;
        }
    }
}

