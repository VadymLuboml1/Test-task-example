package com.vadymhalaziuk.istesttask.data.local

import com.vadymhalaziuk.istesttask.data.local.source.SavedActionsDao
import com.vadymhalaziuk.istesttask.data.local.source.SavedActionsDatabase
import com.vadymhalaziuk.istesttask.domain.model.ActionCooledDownDomainModel
import com.vadymhalaziuk.istesttask.domain.repository.LocalActionsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalActionsRepositoryImpl @Inject constructor(
    private val mapper: ActionDataLocalMapper,
    database: SavedActionsDatabase,
) : LocalActionsRepository {

    private val dao: SavedActionsDao = database.dao

    override suspend fun getCooledDownList(): List<ActionCooledDownDomainModel> =
        dao.getAll().let { mapper.toDomain(it) }


    override suspend fun saveCooledDown(domainModel: ActionCooledDownDomainModel) {
        dao.insert(mapper.toLocal(domainModel))
    }
}