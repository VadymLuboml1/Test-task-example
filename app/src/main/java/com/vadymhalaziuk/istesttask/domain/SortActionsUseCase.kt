package com.vadymhalaziuk.istesttask.domain

import com.vadymhalaziuk.istesttask.domain.model.ActionDomainModel
import javax.inject.Inject

class SortActionsUseCase  @Inject constructor (){

    operator fun invoke(domainModels: List<ActionDomainModel>): List<ActionDomainModel> =
        //was little bi confused by priority
        domainModels.sortedByDescending {
            it.priority
        }
}