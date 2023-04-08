package com.vadymhalaziuk.istesttask.domain

import com.vadymhalaziuk.istesttask.domain.model.ActionDomainModel

class SortActionsUseCase {

    operator fun invoke(domainModels: List<ActionDomainModel>): List<ActionDomainModel> =
        //was little bi confused by priority
        domainModels.sortedByDescending {
            it.priority
        }
}