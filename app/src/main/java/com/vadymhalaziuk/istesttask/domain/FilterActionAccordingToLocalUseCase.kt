package com.vadymhalaziuk.istesttask.domain

import com.vadymhalaziuk.istesttask.data.AppPrefsRepository
import com.vadymhalaziuk.istesttask.data.android.AndroidSystemPrefRepository
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainModel
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainType

class FilterActionAccordingToLocalUseCase(
    private val appPrefsRepository: AppPrefsRepository,
    private val androidSystemPrefRepository: AndroidSystemPrefRepository,
) {

    //TODO fix DRY principle
    private val currentTimeStamp: Long
        get() = System.currentTimeMillis()

    operator fun invoke(actions: List<ActionDomainModel>): List<ActionDomainModel> {
        return actions.filter {
            filterAccodingToColdDown(it)
        }.filter {
            filterAccordingToInternetConnection(it)
        }
    }

    private fun filterAccordingToInternetConnection(action: ActionDomainModel): Boolean =
        when {
            action.type == ActionDomainType.TOAST -> {
                androidSystemPrefRepository.isInternetAvailable()
            }
            else -> true
        }


    private fun filterAccodingToColdDown(
        action: ActionDomainModel
    ): Boolean {
        val recordedAction =
            appPrefsRepository.cooledDownList.find { it.type == action.type } ?: return true

        return countCoolDownDiff(currentTimeStamp, recordedAction.coolDown) > action.coolDown
    }

    private fun countCoolDownDiff(current: Long, recorded: Long): Long =
        current - recorded


}