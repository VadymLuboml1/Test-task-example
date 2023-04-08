package com.vadymhalaziuk.istesttask.data.mapper

import com.vadymhalaziuk.istesttask.data.local.model.CooledDownActionLocalDto
import com.vadymhalaziuk.istesttask.domain.model.ActionCooledDownDomainModel
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainType
import javax.inject.Inject

class ActionDataLocalMapper @Inject constructor() {

    fun toDomain(localDtos: List<CooledDownActionLocalDto>): List<ActionCooledDownDomainModel> =
        localDtos.mapNotNull {
            ActionCooledDownDomainModel(
                it.type?.let { ActionDomainType.from(it) } ?: return@mapNotNull null,
                coolDown = it.lastActionTimestamp ?: return@mapNotNull null,
            )
        }

    fun toLocal(
        domainModels: List<ActionCooledDownDomainModel>,
    ): List<CooledDownActionLocalDto> =
        domainModels.map {
            CooledDownActionLocalDto(
                it.type.name,
                lastActionTimestamp = it.coolDown,
            )
        }

    fun toLocal(
        domainModels: ActionCooledDownDomainModel,
    ): CooledDownActionLocalDto =
        CooledDownActionLocalDto(
            domainModels.type.name,
            domainModels.coolDown,
        )
}