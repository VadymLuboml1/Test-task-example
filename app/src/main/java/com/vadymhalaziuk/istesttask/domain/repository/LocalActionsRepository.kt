package com.vadymhalaziuk.istesttask.domain.repository

import com.vadymhalaziuk.istesttask.domain.model.ActionCooledDownDomainModel

interface LocalActionsRepository {
    suspend fun saveCooledDown(domainModel: ActionCooledDownDomainModel)
    suspend fun getCooledDownList(): List<ActionCooledDownDomainModel>
}