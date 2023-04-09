package com.vadymhalaziuk.istesttask.domain.mapper

import com.vadymhalaziuk.istesttask.domain.model.ActionDomainError
import javax.inject.Inject

class ActionDomainMapper @Inject constructor() {

    fun mapError(throwable: Throwable): ActionDomainError =
        when (throwable) {
            is NoSuchElementException -> ActionDomainError.NoAvailable
            else -> ActionDomainError.Unknown
        }

}