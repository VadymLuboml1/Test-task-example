package com.vadymhalaziuk.istesttask.ui.model

import androidx.annotation.StringRes

data class ActionButtonContent(
    @StringRes val text: Int,
    val contentType: ActionContentType,
)

enum class ActionContentType {
    ANIMATION, DISABLED
}