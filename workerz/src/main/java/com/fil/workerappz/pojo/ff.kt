package com.fil.workerappz.pojo
import com.google.gson.annotations.SerializedName


data class ff(
    @SerializedName("message")
    var message: String?,
    @SerializedName("result")
    var result: Result?,
    @SerializedName("status")
    var status: String?
) {
    data class Result(
        @SerializedName("agentlist")
        var agentlist: List<Agentlist?>?,
        @SerializedName("subscriptionDetails")
        var subscriptionDetails: List<SubscriptionDetail?>?
    ) {
        data class SubscriptionDetail(
            @SerializedName("subscriptionID")
            var subscriptionID: String?,
            @SerializedName("subscriptionImage")
            var subscriptionImage: String?,
            @SerializedName("subscriptionItem")
            var subscriptionItem: String?,
            @SerializedName("subscriptionName")
            var subscriptionName: String?
        )

        data class Agentlist(
            @SerializedName("agentsubscriptionID")
            var agentsubscriptionID: String?,
            @SerializedName("userEmail")
            var userEmail: String?,
            @SerializedName("userFullName")
            var userFullName: String?,
            @SerializedName("userID")
            var userID: String?
        )
    }
}