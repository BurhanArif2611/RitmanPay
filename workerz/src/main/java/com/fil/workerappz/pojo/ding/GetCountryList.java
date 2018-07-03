package com.fil.workerappz.pojo.ding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 22-Mar-18.
 * FIL AHM
 */

public class GetCountryList implements Serializable {

    @Expose
    @SerializedName("info")
    private String info;
    @Expose
    @SerializedName("data")
    private List<Data> data;
    @Expose
    @SerializedName("status")
    private boolean status;

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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class Data {
        @Expose
        @SerializedName("RegionCodes")
        private List<String> RegionCodes;
        @Expose
        @SerializedName("InternationalDialingInformation")
        private List<InternationalDialingInformation> InternationalDialingInformation;
        @Expose
        @SerializedName("CountryName")
        private String CountryName;
        @Expose
        @SerializedName("CountryIso")
        private String CountryIso;

        public List<String> getRegionCodes() {
            return RegionCodes;
        }

        public void setRegionCodes(List<String> RegionCodes) {
            this.RegionCodes = RegionCodes;
        }

        public List<InternationalDialingInformation> getInternationalDialingInformation() {
            return InternationalDialingInformation;
        }

        public void setInternationalDialingInformation(List<InternationalDialingInformation> InternationalDialingInformation) {
            this.InternationalDialingInformation = InternationalDialingInformation;
        }

        public String getCountryName() {
            return CountryName;
        }

        public void setCountryName(String CountryName) {
            this.CountryName = CountryName;
        }

        public String getCountryIso() {
            return CountryIso;
        }

        public void setCountryIso(String CountryIso) {
            this.CountryIso = CountryIso;
        }
    }

    public static class InternationalDialingInformation {
        @Expose
        @SerializedName("MaximumLength")
        private int MaximumLength;
        @Expose
        @SerializedName("MinimumLength")
        private int MinimumLength;
        @Expose
        @SerializedName("Prefix")
        private String Prefix;

        public int getMaximumLength() {
            return MaximumLength;
        }

        public void setMaximumLength(int MaximumLength) {
            this.MaximumLength = MaximumLength;
        }

        public int getMinimumLength() {
            return MinimumLength;
        }

        public void setMinimumLength(int MinimumLength) {
            this.MinimumLength = MinimumLength;
        }

        public String getPrefix() {
            if(Prefix==null)
            {
               return "";
            }
            return Prefix;
        }

        public void setPrefix(String Prefix) {
            this.Prefix = Prefix;
        }
    }
}