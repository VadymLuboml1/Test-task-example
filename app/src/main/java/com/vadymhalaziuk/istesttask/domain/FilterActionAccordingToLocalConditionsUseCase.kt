package com.vadymhalaziuk.istesttask.domain

import com.vadymhalaziuk.istesttask.domain.model.ActionDomainModel
import com.vadymhalaziuk.istesttask.domain.repository.LocalActionsRepository
import com.vadymhalaziuk.istesttask.utils.currentTimeStamp
import javax.inject.Inject

class FilterActionAccordingToLocalConditionsUseCase @Inject constructor(
    private val localActionsRepository: LocalActionsRepository,
) {

    suspend operator fun invoke(actions: List<ActionDomainModel>): List<ActionDomainModel> {
        return actions.filter {
            filterAccordingToColdDown(it)
        }
    }

    private suspend fun filterAccordingToColdDown(
        action: ActionDomainModel
    ): Boolean {
        val recordedAction =
            localActionsRepository.getCooledDownList().find { it.type == action.type } ?: return true

        return countCoolDownDiff(currentTimeStamp(), recordedAction.coolDown) > action.coolDown
    }

    private fun countCoolDownDiff(current: Long, recorded: Long): Long =
        current - recorded
}
