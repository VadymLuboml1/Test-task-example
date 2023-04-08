package com.vadymhalaziuk.istesttask.domain

import com.vadymhalaziuk.istesttask.domain.model.ActionDomainModel
import com.vadymhalaziuk.istesttask.utils.currentDayOfWeek
import java.time.DayOfWeek
import java.util.*
import javax.inject.Inject

class FilterActionAccordingToRemoteUseCase @Inject constructor() {

    operator fun invoke(domainModels: List<ActionDomainModel>): List<ActionDomainModel> =
        domainModels.filter {
            it.enabled
        }.filter {
            filterByCurrentDay(it)
        }

    private fun filterByCurrentDay(domainModel: ActionDomainModel): Boolean =
        currentDayOfWeek() in domainModel.days
}