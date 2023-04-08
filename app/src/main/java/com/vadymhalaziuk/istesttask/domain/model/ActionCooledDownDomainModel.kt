package com.vadymhalaziuk.istesttask.domain.model

data class ActionCooledDownDomainModel(
    val type: ActionDomainType,
    val coolDown: Long,
)
