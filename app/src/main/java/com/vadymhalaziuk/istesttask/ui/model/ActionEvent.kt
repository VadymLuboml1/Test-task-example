package com.vadymhalaziuk.istesttask.ui.model

sealed class ActionEvent {

    object Click : ActionEvent()

    object ClickWhileAnimation : ActionEvent()

}
