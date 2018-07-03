package com.fil.workerappz.pojo.ding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 22-Mar-18.
 * FIL AHM
 */

public class GetProvidersList implements Serializable {


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

  public static class Data implements Serializable {
    @Expose
    @SerializedName("RegionCodes")
    private List<String> RegionCodes;
    @Expose
    @SerializedName("CustomerCareNumber")
    private String CustomerCareNumber;
    @Expose
    @SerializedName("ValidationRegex")
    private String ValidationRegex;
    @Expose
    @SerializedName("ShortName")
    private String ShortName;
    @Expose
    @SerializedName("Name")
    private String Name;
    @Expose
    @SerializedName("CountryIso")
    private String CountryIso;
    @Expose
    @SerializedName("ProviderCode")
    private String ProviderCode;

    public List<String> getRegionCodes() {
      return RegionCodes;
    }

    public void setRegionCodes(List<String> RegionCodes) {
      this.RegionCodes = RegionCodes;
    }

    public String getCustomerCareNumber() {
      return CustomerCareNumber;
    }

    public void setCustomerCareNumber(String CustomerCareNumber) {
      this.CustomerCareNumber = CustomerCareNumber;
    }

    public String getValidationRegex() {
      return ValidationRegex;
    }

    public void setValidationRegex(String ValidationRegex) {
      this.ValidationRegex = ValidationRegex;
    }

    public String getShortName() {
      return ShortName;
    }

    public void setShortName(String ShortName) {
      this.ShortName = ShortName;
    }

    public String getName() {
      return Name;
    }

    public void setName(String Name) {
      this.Name = Name;
    }

    public String getCountryIso() {
      return CountryIso;
    }

    public void setCountryIso(String CountryIso) {
      this.CountryIso = CountryIso;
    }

    public String getProviderCode() {
      return ProviderCode;
    }

    public void setProviderCode(String ProviderCode) {
      this.ProviderCode = ProviderCode;
    }
  }
}