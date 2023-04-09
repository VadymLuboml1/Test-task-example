package com.vadymhalaziuk.istesttask.ui.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class ActionButtonContent(
    @StringRes val text: Int,
    val color: Color,
)