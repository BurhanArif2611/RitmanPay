package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 09-Mar-18.
 * FIL AHM
 */

public class BankListPojo implements Serializable {


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

  static class Data {
    @Expose
    @SerializedName("userbankaccountCreatedDate")
    private String userbankaccountCreatedDate;
    @Expose
    @SerializedName("userbankaccountPhone")
    private String userbankaccountPhone;
    @Expose
    @SerializedName("userbankaccountStatus")
    private String userbankaccountStatus;
    @Expose
    @SerializedName("userbankaccountBranchName")
    private String userbankaccountBranchName;
    @Expose
    @SerializedName("userbankaccountIFSCCode")
    private String userbankaccountIFSCCode;
    @Expose
    @SerializedName("userbankaccountAccountNo")
    private String userbankaccountAccountNo;
    @Expose
    @SerializedName("userbankaccountBankName")
    private String userbankaccountBankName;
    @Expose
    @SerializedName("userbankaccountName")
    private String userbankaccountName;
    @Expose
    @SerializedName("languageID")
    private int languageID;
    @Expose
    @SerializedName("userID")
    private int userID;
    @Expose
    @SerializedName("userbankaccountID")
    private int userbankaccountID;

    public String getUserbankaccountCreatedDate() {
      return userbankaccountCreatedDate;
    }

    public void setUserbankaccountCreatedDate(String userbankaccountCreatedDate) {
      this.userbankaccountCreatedDate = userbankaccountCreatedDate;
    }

    public String getUserbankaccountPhone() {
      return userbankaccountPhone;
    }

    public void setUserbankaccountPhone(String userbankaccountPhone) {
      this.userbankaccountPhone = userbankaccountPhone;
    }

    public String getUserbankaccountStatus() {
      return userbankaccountStatus;
    }

    public void setUserbankaccountStatus(String userbankaccountStatus) {
      this.userbankaccountStatus = userbankaccountStatus;
    }

    public String getUserbankaccountBranchName() {
      return userbankaccountBranchName;
    }

    public void setUserbankaccountBranchName(String userbankaccountBranchName) {
      this.userbankaccountBranchName = userbankaccountBranchName;
    }

    public String getUserbankaccountIFSCCode() {
      return userbankaccountIFSCCode;
    }

    public void setUserbankaccountIFSCCode(String userbankaccountIFSCCode) {
      this.userbankaccountIFSCCode = userbankaccountIFSCCode;
    }

    public String getUserbankaccountAccountNo() {
      return userbankaccountAccountNo;
    }

    public void setUserbankaccountAccountNo(String userbankaccountAccountNo) {
      this.userbankaccountAccountNo = userbankaccountAccountNo;
    }

    public String getUserbankaccountBankName() {
      return userbankaccountBankName;
    }

    public void setUserbankaccountBankName(String userbankaccountBankName) {
      this.userbankaccountBankName = userbankaccountBankName;
    }

    public String getUserbankaccountName() {
      return userbankaccountName;
    }

    public void setUserbankaccountName(String userbankaccountName) {
      this.userbankaccountName = userbankaccountName;
    }

    public int getLanguageID() {
      return languageID;
    }

    public void setLanguageID(int languageID) {
      this.languageID = languageID;
    }

    public int getUserID() {
      return userID;
    }

    public void setUserID(int userID) {
      this.userID = userID;
    }

    public int getUserbankaccountID() {
      return userbankaccountID;
    }

    public void setUserbankaccountID(int userbankaccountID) {
      this.userbankaccountID = userbankaccountID;
    }
  }
}