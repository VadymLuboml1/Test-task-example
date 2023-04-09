package com.vadymhalaziuk.istesttask.data

import com.vadymhalaziuk.istesttask.data.local.AppSharedPrefStorage
import com.vadymhalaziuk.istesttask.data.mapper.ActionDataLocalMapper
import com.vadymhalaziuk.istesttask.domain.model.ActionCooledDownDomainModel
import com.vadymhalaziuk.istesttask.domain.repository.AppPrefsRepository
import javax.inject.Inject

class AppPrefsRepositoryImpl @Inject constructor(
    private val storage: AppSharedPrefStorage,
    private val mapper: ActionDataLocalMapper,
) : AppPrefsRepository {


    override suspend fun getCooledDownList(): List<ActionCooledDownDomainModel> =
        storage.cooledDownActions.let { mapper.toDomain(it) }


    override suspend fun saveCooledDown(domainModel: ActionCooledDownDomainModel): Unit {
        storage.cooledDownActions = mutableListOf(
            *storage.cooledDownActions.toTypedArray(),
            mapper.toLocal(domainModel)
        )
    }


}