package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HS on 17-Mar-18.
 * FIL AHM
 */

public class NotificationListJsonPojo implements Serializable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("notificationID")
        @Expose
        private String notificationID;
        @SerializedName("notificationType")
        @Expose
        private String notificationType;
        @SerializedName("notificationReferenceKey")
        @Expose
        private String notificationReferenceKey;
        @SerializedName("notificationMessageText")
        @Expose
        private String notificationMessageText;
        @SerializedName("notificationReadStatus")
        @Expose
        private String notificationReadStatus;
        @SerializedName("MinutesAgo")
        @Expose
        private String minutesAgo;

        public String getNotificationID() {
            return notificationID;
        }

        public void setNotificationID(String notificationID) {
            this.notificationID = notificationID;
        }

        public String getNotificationType() {
            return notificationType;
        }

        public void setNotificationType(String notificationType) {
            this.notificationType = notificationType;
        }

        public String getNotificationReferenceKey() {
            return notificationReferenceKey;
        }

        public void setNotificationReferenceKey(String notificationReferenceKey) {
            this.notificationReferenceKey = notificationReferenceKey;
        }

        public String getNotificationMessageText() {
            return notificationMessageText;
        }

        public void setNotificationMessageText(String notificationMessageText) {
            this.notificationMessageText = notificationMessageText;
        }

        public String getNotificationReadStatus() {
            return notificationReadStatus;
        }

        public void setNotificationReadStatus(String notificationReadStatus) {
            this.notificationReadStatus = notificationReadStatus;
        }

        public String getMinutesAgo() {
            return minutesAgo;
        }

        public void setMinutesAgo(String minutesAgo) {
            this.minutesAgo = minutesAgo;
        }

    }
}