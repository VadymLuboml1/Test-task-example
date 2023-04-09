package com.vadymhalaziuk.istesttask.ui.model

import androidx.annotation.StringRes
import kotlin.random.Random

sealed class ActionEffect {

    data class Notification(
        @StringRes val title: Int,
        @StringRes val subtitle: Int? = null,
        val composeKey: Int = Random.nextInt(),
    ) : ActionEffect()

    data class Dialog(
        @StringRes val title: Int,
        @StringRes val subtitle: Int,
        val composeKey: Int = Random.nextInt()
    ) : ActionEffect()

    data class Toast(
        @StringRes val content: Int,
        val composeKey: Int = Random.nextInt()
    ) : ActionEffect()

    data class ShowAnimation(val composeKey: Int = Random.nextInt()) : ActionEffect()
}
