package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by FUSION on 08/08/2018.
 */

public class GetLanguageList {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("data")
    @Expose
    private List<Listdatum> data = null;

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

    public List<Listdatum> getData() {
        return data;
    }

    public void setData(List<Listdatum> data) {
        this.data = data;
    }
    public class Listdatum {

        @SerializedName("languageName")
        @Expose
        private String languageName;
        @SerializedName("languageID")
        @Expose
        private Integer languageID;

        public String getLanguageName() {
            return languageName;
        }

        public void setLanguageName(String languageName) {
            this.languageName = languageName;
        }

        public Integer getLanguageID() {
            return languageID;
        }

        public void setLanguageID(Integer languageID) {
            this.languageID = languageID;
        }

    }


}
