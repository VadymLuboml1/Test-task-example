package com.vadymhalaziuk.istesttask.domain

import com.vadymhalaziuk.istesttask.domain.model.ActionDomainModel
import com.vadymhalaziuk.istesttask.domain.repository.AppPrefsRepository
import com.vadymhalaziuk.istesttask.utils.currentTimeStamp
import javax.inject.Inject

class FilterActionAccordingToLocalConditionsUseCase @Inject constructor(
    private val appPrefsRepository: AppPrefsRepository,
) {

    suspend operator fun invoke(actions: List<ActionDomainModel>): List<ActionDomainModel> {
        return actions.filter {
            filterAccodingToColdDown(it)
        }
    }

    private suspend fun filterAccodingToColdDown(
        action: ActionDomainModel
    ): Boolean {
        val recordedAction =
            appPrefsRepository.getCooledDownList().find { it.type == action.type } ?: return true

        return countCoolDownDiff(currentTimeStamp(), recordedAction.coolDown) > action.coolDown
    }

    private fun countCoolDownDiff(current: Long, recorded: Long): Long =
        current - recorded
}
