package com.vadymhalaziuk.istesttask.ui.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
data class ActionButtonContent(
    @StringRes val text: Int,
    val color: Color,
)