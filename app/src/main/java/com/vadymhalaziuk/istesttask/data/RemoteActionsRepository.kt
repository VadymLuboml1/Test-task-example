package com.vadymhalaziuk.istesttask.data

import android.util.Log
import com.vadymhalaziuk.istesttask.data.mapper.ActionsDataMapper
import com.vadymhalaziuk.istesttask.data.remote.ActionsRestApi
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainModel
import com.vadymhalaziuk.istesttask.domain.repository.RemoteActionsRepository
import javax.inject.Inject

class RemoteActionsRepository @Inject constructor(
    private val api: ActionsRestApi,
    private val mapper: ActionsDataMapper,
) : RemoteActionsRepository {

    override suspend fun getActions(): Result<List<ActionDomainModel>> =
        api.getActions().also {
            Log.d("vadymLog", "received action from BE $it")

        }.runCatching {
            Log.d("vadymLog", "received action from BE ${this.body().toString()}")

            mapper(this.body()!!)
        }
}