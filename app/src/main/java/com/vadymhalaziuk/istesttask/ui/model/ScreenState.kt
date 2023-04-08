package com.vadymhalaziuk.istesttask.ui.model

import androidx.annotation.StringRes

data class ScreenState(
    val content: ActionContentType? = null,
    val isLoading: Boolean = false,
    @StringRes val errorText: Int? = null,
) {

    /**
     * Next methods used for manipulation with current screen state
     * */

    fun content(content: ActionContentType): ScreenState =
        copy(
            content = content,
            isLoading = false,
            errorText = null,
        )

    fun loading(show: Boolean = true): ScreenState =
        copy(
            isLoading = show,
            errorText = null,
        )

    fun error(@StringRes errorText: Int): ScreenState =
        copy(
            isLoading = false,
            errorText = errorText,
        )

    companion object Initial {
        val Loading = ScreenState(isLoading = true)
    }

}



