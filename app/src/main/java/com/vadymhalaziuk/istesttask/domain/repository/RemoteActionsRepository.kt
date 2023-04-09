package com.vadymhalaziuk.istesttask.domain.repository

import com.vadymhalaziuk.istesttask.domain.model.ActionDomainModel

interface RemoteActionsRepository {
    suspend fun getActions(): Result<List<ActionDomainModel>>
}