package com.vadymhalaziuk.istesttask.data.remote

import com.google.gson.annotations.SerializedName


data class ActionDtoItem(
    @SerializedName("type") val type: ActionRestType?,
    @SerializedName("enabled") val enabled: Boolean?,
    @SerializedName("priority") val priority: Int?,
    @SerializedName("valid_days") val validDays: List<Int?>?,
    @SerializedName("cool_down") val coolDown: Long?,
)

enum class ActionRestType {
    @SerializedName("animation")
    ANIMATION,

    @SerializedName("toast")
    TOAST,

    @SerializedName("call")
    CALL,

    @SerializedName("notification")
    NOTIFICATION
}
