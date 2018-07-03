package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 06-Mar-18.
 * FIL AHM
 */

public class BalanceListJsonPojo implements Serializable {


  @Expose
  @SerializedName("data")
  private List<Data> data;
  @Expose
  @SerializedName("info")
  private String info;
  @Expose
  @SerializedName("status")
  private boolean status;

  public List<Data> getData() {
    return data;
  }

  public void setData(List<Data> data) {
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
    @SerializedName("balance")
    private float balance;

    public float getBalance() {
      return balance;
    }

    public void setBalance(float balance) {
      this.balance = balance;
    }
  }
}