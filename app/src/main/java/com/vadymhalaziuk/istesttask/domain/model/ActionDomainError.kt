package com.vadymhalaziuk.istesttask.domain.model

sealed class ActionDomainError {
    object NoAvailable : ActionDomainError()
    object Unknown : ActionDomainError()
}