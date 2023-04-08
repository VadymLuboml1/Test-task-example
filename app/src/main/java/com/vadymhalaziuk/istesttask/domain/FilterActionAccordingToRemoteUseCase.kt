package com.vadymhalaziuk.istesttask.domain

import com.vadymhalaziuk.istesttask.domain.model.ActionDomainModel
import java.time.DayOfWeek
import java.util.*

class FilterActionAccordingToRemoteUseCase {

    private val currentDay: DayOfWeek
        //TODO fix DRY principle
        get() = requireNotNull(
            DayOfWeek.values()
                .find {
                    it.ordinal == Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1
                }
        )

    operator fun invoke(domainModels: List<ActionDomainModel>): List<ActionDomainModel> =
        domainModels.filter {
            it.enabled
        }.filter {
            filterByCurrentDay(it)
        }

    private fun filterByCurrentDay(domainModel: ActionDomainModel): Boolean =
        currentDay in domainModel.days
}