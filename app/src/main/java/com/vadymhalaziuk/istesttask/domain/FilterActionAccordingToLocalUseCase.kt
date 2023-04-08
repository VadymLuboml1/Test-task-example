package com.vadymhalaziuk.istesttask.domain

import com.vadymhalaziuk.istesttask.data.AppPrefsRepository
import com.vadymhalaziuk.istesttask.data.android.AndroidSystemPrefRepository
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainModel
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainType
import com.vadymhalaziuk.istesttask.utils.currentTimeStamp
import javax.inject.Inject

class FilterActionAccordingToLocalUseCase @Inject constructor(
    private val appPrefsRepository: AppPrefsRepository,
) {

    operator fun invoke(actions: List<ActionDomainModel>): List<ActionDomainModel> {
        return actions.filter {
            filterAccodingToColdDown(it)
        }
    }

    private fun filterAccodingToColdDown(
        action: ActionDomainModel
    ): Boolean {
        val recordedAction =
            appPrefsRepository.cooledDownList.find { it.type == action.type } ?: return true

        return countCoolDownDiff(currentTimeStamp(), recordedAction.coolDown) > action.coolDown
    }

    private fun countCoolDownDiff(current: Long, recorded: Long): Long =
        current - recorded


}