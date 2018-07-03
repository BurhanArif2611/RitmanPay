package com.fil.workerappz.pojo.ding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 22-Mar-18.
 * FIL AHM
 */

public class GetRegionsList implements Serializable {


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
    @SerializedName("CountryIso")
    private String CountryIso;
    @Expose
    @SerializedName("RegionName")
    private String RegionName;
    @Expose
    @SerializedName("RegionCode")
    private String RegionCode;

    public String getCountryIso() {
      return CountryIso;
    }

    public void setCountryIso(String CountryIso) {
      this.CountryIso = CountryIso;
    }

    public String getRegionName() {
      return RegionName;
    }

    public void setRegionName(String RegionName) {
      this.RegionName = RegionName;
    }

    public String getRegionCode() {
      return RegionCode;
    }

    public void setRegionCode(String RegionCode) {
      this.RegionCode = RegionCode;
    }
  }
}