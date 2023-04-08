package com.vadymhalaziuk.istesttask.domain.model

enum class ActionDomainType {
    ANIMATION,

    TOAST,

    CALL,

    NOTIFICATION;

    companion object {
        fun from(source: String): ActionDomainType? {
            return ActionDomainType.values().find { it.name == source }
        }
    }

}