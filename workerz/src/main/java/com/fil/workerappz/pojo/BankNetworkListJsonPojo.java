package com.fil.workerappz.pojo;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by HS on 09-Mar-18.
 * FIL AHM
 */

public class BankNetworkListJsonPojo implements Serializable {


    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Object data;
    @SerializedName("info")
    @Expose
    private String info;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(BankDataPojo data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}


