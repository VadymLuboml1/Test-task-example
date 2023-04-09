package com.vadymhalaziuk.istesttask.domain

import com.vadymhalaziuk.istesttask.domain.model.ActionCooledDownDomainModel
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainType
import com.vadymhalaziuk.istesttask.domain.repository.AppPrefsRepository
import javax.inject.Inject

class TrackActionUseCase @Inject constructor(
    private val appPrefsRepository: AppPrefsRepository,
) {

    private val currentTimeStamp
        get() = System.currentTimeMillis()

    suspend operator fun invoke(actionDomainType: ActionDomainType) {
        appPrefsRepository.saveCooledDown(
            ActionCooledDownDomainModel(
                actionDomainType,
                currentTimeStamp,
            )
        )
    }
}