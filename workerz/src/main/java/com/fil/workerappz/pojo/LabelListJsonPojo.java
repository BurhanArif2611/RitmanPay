package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.List;

/**
 * Created by FUSION on 08/05/2018.
 */

public class LabelListJsonPojo  implements Serializable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("data")
    @Expose
    private List<LabelListData> data = null;

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

    public List<LabelListData> getData() {
        return data;
    }

    public void setData(List<LabelListData> data) {
        this.data = data;
    }
}




