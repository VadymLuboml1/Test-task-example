package com.vadymhalaziuk.istesttask.ui.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable

@Stable
data class ScreenState(
    val content: ActionButtonContentState? = null,
    val isLoading: Boolean = false,
    @StringRes val errorText: Int? = null,
) {

    /**
     * Next methods used for manipulation with current screen state
     * */

    fun content(contentChanging: ActionButtonContentState.() -> ActionButtonContentState): ScreenState =
        copy(
            content = content?.contentChanging(),
            isLoading = false,
            errorText = null,
        )

    fun loading(show: Boolean = true): ScreenState =
        copy(
            isLoading = show,
            errorText = null,
        )

    fun error(@StringRes errorText: Int?): ScreenState =
        copy(
            isLoading = false,
            errorText = errorText,
        )

}



