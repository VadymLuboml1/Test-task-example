package com.vadymhalaziuk.istesttask.ui.model

sealed class ActionEvent {

    object Click : ActionEvent()

    object ClickWhileAnimation : ActionEvent()

    object NotificationSent : ActionEvent()
    object NotificationAccessDenied : ActionEvent()

}
