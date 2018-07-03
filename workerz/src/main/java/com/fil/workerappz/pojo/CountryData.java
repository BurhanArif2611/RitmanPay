package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by HS on 22-Mar-18.
 * FIL AHM
 */

public class CountryData extends SugarRecord implements Serializable {

    public CountryData() {}

    @Expose
    @SerializedName("countryDialCode")
    private String countryDialCode;
    @Expose
    @SerializedName("countryFlagImage")
    private String countryFlagImage;
    @Expose
    @SerializedName("countryCurrencySymbol")
    private String countryCurrencySymbol;
    @Expose
    @SerializedName("countryShortCode")
    private String countryShortCode;
    @Expose
    @SerializedName("countryName")
    private String countryName;
    @Expose
    @SerializedName("countryID")
    private int countryID;

    public String getCountryDialCode() {
        return countryDialCode;
    }

    public void setCountryDialCode(String countryDialCode) {
        this.countryDialCode = countryDialCode;
    }

    public String getCountryFlagImage() {
        return countryFlagImage;
    }

    public void setCountryFlagImage(String countryFlagImage) {
        this.countryFlagImage = countryFlagImage;
    }

    public String getCountryCurrencySymbol() {
        return countryCurrencySymbol;
    }

    public void setCountryCurrencySymbol(String countryCurrencySymbol) {
        this.countryCurrencySymbol = countryCurrencySymbol;
    }

    public String getCountryShortCode() {
        return countryShortCode;
    }

    public void setCountryShortCode(String countryShortCode) {
        this.countryShortCode = countryShortCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}