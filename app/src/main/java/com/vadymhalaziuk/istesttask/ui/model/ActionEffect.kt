package com.vadymhalaziuk.istesttask.ui.model

import androidx.annotation.StringRes

sealed class ActionEffect {

    data class Dialog(
        @StringRes val title: Int,
        @StringRes val subtitle: Int
    ) : ActionEffect()

    data class Toast(@StringRes val content: Int) : ActionEffect()
}
