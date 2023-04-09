package com.vadymhalaziuk.istesttask.domain

import com.vadymhalaziuk.istesttask.domain.model.ActionCooledDownDomainModel
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainType
import com.vadymhalaziuk.istesttask.domain.repository.LocalActionsRepository
import javax.inject.Inject

class TrackActionUseCase @Inject constructor(
    private val localActionsRepository: LocalActionsRepository,
) {

    private val currentTimeStamp
        get() = System.currentTimeMillis()

    suspend operator fun invoke(actionDomainType: ActionDomainType) {
        localActionsRepository.saveCooledDown(
            ActionCooledDownDomainModel(
                actionDomainType,
                currentTimeStamp,
            )
        )
    }
}