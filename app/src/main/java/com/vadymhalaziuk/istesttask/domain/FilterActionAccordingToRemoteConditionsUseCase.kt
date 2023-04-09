package com.vadymhalaziuk.istesttask.domain

import com.vadymhalaziuk.istesttask.domain.model.ActionDomainModel
import com.vadymhalaziuk.istesttask.utils.currentDayOfWeek
import javax.inject.Inject

class FilterActionAccordingToRemoteConditionsUseCase @Inject constructor() {

    operator fun invoke(domainModels: List<ActionDomainModel>): List<ActionDomainModel> =
        domainModels.filter {
            it.enabled
        }.filter {
            filterByCurrentDay(it)
        }

    private fun filterByCurrentDay(domainModel: ActionDomainModel): Boolean =
        currentDayOfWeek() in domainModel.days
}