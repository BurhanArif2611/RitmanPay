package com.fil.workerappz.pojo.ding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 22-Mar-18.
 * FIL AHM
 */

public class GetBalanceList implements Serializable {


  @Expose
  @SerializedName("info")
  private String info;
  @Expose
  @SerializedName("data")
  private Data data;
  @Expose
  @SerializedName("status")
  private boolean status;

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
    @SerializedName("CurrencyIso")
    private String CurrencyIso;
    @Expose
    @SerializedName("Balance")
    private int Balance;

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

    public String getCurrencyIso() {
      return CurrencyIso;
    }

    public void setCurrencyIso(String CurrencyIso) {
      this.CurrencyIso = CurrencyIso;
    }

    public int getBalance() {
      return Balance;
    }

    public void setBalance(int Balance) {
      this.Balance = Balance;
    }
  }
}