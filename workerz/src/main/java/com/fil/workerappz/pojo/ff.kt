package com.fil.workerappz.pojo
import com.google.gson.annotations.SerializedName


data class ff(
        @SerializedName("result")
        var result: List<Result?>?
) {
    data class Result(
            @SerializedName("address_id")
            var addressId: String?,
            @SerializedName("area")
            var area: List<Area?>?,
            @SerializedName("cityname")
            var cityname: String?
    ) {
        data class Area(
                @SerializedName("areaName")
                var areaName: String?,
                @SerializedName("areaZipcode")
                var areaZipcode: String?
        )
    }
}