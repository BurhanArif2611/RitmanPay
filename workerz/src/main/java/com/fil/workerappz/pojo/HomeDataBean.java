package com.fil.workerappz.pojo;

import java.io.Serializable;

/**
 * Created by HS on 26-Feb-18.
 * FIL AHM
 */

public class HomeDataBean implements Serializable {

    private int imageId;
    private String dataName;

    public HomeDataBean(int imageId, String dataName) {
        this.imageId = imageId;
        this.dataName = dataName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }
}
