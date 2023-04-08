package com.vadymhalaziuk.istesttask.domain.model

import java.time.DayOfWeek

data class ActionDomainModel(
    val type: ActionDomainType,
    val enabled: Boolean,
    val priority: Int,
    val days: List<DayOfWeek>,
    val coolDown: Long,
)
