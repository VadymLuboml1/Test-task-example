package com.vadymhalaziuk.istesttask.data

import com.vadymhalaziuk.istesttask.data.local.AppSharedPrefStorage
import com.vadymhalaziuk.istesttask.data.mapper.ActionDataLocalMapper
import com.vadymhalaziuk.istesttask.domain.model.ActionCooledDownDomainModel

class AppPrefsRepository(
    private val storage: AppSharedPrefStorage,
    private val mapper: ActionDataLocalMapper,
) {


    val cooledDownList: List<ActionCooledDownDomainModel>
        get() = storage.cooledDownActions.let { mapper.toDomain(it) }

    fun saveCooledDownList(domainModels: List<ActionCooledDownDomainModel>) =
        mapper.toLocal(domainModels).let {
            storage.cooledDownActions = it
        }

    fun saveCooledDown(domainModel: ActionCooledDownDomainModel) =
        mutableListOf(
            *storage.cooledDownActions.toTypedArray(),
            mapper.toLocal(domainModel)
        )

}