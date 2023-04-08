package com.vadymhalaziuk.istesttask.data.mapper

import com.vadymhalaziuk.istesttask.data.remote.ActionDtoItem
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainModel
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainType
import java.time.DayOfWeek

class ActionsDataMapper {

    operator fun invoke(dtos: List<ActionDtoItem?>): List<ActionDomainModel> =
        dtos.mapNotNull {
            it ?: return@mapNotNull null

            ActionDomainModel(
                type = it.type?.let { ActionDomainType.from(it.name) } ?: return@mapNotNull null,
                enabled = it.enabled ?: return@mapNotNull null,
                days = it.validDays?.mapNotNull { day ->
                    day ?: return@mapNotNull null

                    DayOfWeek.values().find { dayOfWeek -> dayOfWeek.ordinal == day }
                        ?: return@mapNotNull null
                } ?: return@mapNotNull null,
                priority = it.priority ?: return@mapNotNull null,
                coolDown = it.coolDown ?: return@mapNotNull null,
            )
        }


}