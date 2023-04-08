package com.vadymhalaziuk.istesttask.domain

import com.vadymhalaziuk.istesttask.data.AppPrefsRepository
import com.vadymhalaziuk.istesttask.domain.model.ActionCooledDownDomainModel
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainType

class TrackActionUseCase(
    private val appPrefsRepository: AppPrefsRepository,
) {

    private val currentTimeStamp
        get() = System.currentTimeMillis()

    operator fun invoke(actionDomainType: ActionDomainType) {
        appPrefsRepository.saveCooledDown(
            ActionCooledDownDomainModel(
                actionDomainType,
                currentTimeStamp,
            )
        )
    }
}