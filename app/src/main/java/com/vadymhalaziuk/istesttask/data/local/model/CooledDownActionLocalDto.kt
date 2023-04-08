package com.vadymhalaziuk.istesttask.data.local.model

import com.google.gson.annotations.SerializedName

data class CooledDownActionLocalDto(
    @SerializedName("type") val type: String?,
    @SerializedName("lastActionTimestamp") val lastActionTimestamp: Long?,
)
