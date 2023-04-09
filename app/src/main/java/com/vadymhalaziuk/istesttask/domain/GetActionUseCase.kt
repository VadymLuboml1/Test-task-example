package com.vadymhalaziuk.istesttask.domain

import android.util.Log
import com.vadymhalaziuk.istesttask.data.remote.RemoteActionsRepository
import com.vadymhalaziuk.istesttask.domain.mapper.ActionDomainMapper
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainError
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainModel
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainType
import com.vadymhalaziuk.istesttask.utils.Result
import com.vadymhalaziuk.istesttask.utils.Result.Companion.safeResult
import javax.inject.Inject
import kotlin.random.Random

class GetActionUseCase @Inject constructor(
    private val mapper: ActionDomainMapper,
    private val sortActionsUseCase: SortActionsUseCase,
    private val remoteActionsRepository: RemoteActionsRepository,
    private val filterActionAccordingToLocal: FilterActionAccordingToLocalConditionsUseCase,
    private val filterActionAccordingToRemoteUseCase: FilterActionAccordingToRemoteConditionsUseCase,
) {

    suspend operator fun invoke(): Result<ActionDomainType, ActionDomainError> =
        remoteActionsRepository.getActions().also {
            Log.d("vadymLog", "received domain $it")

        }.map {
            filterActionAccordingToRemoteUseCase(it)
        }.map {
            filterActionAccordingToLocal(it)
        }.map {
            sortActionsUseCase(it)
        }.let {
            safeResult(mapper::mapError) {
                val list = it.getOrThrow()
                when {
                    list.checkIfTopAreWithEquivalentPriority() -> list[oneOrTwoRandom()]
                    else -> list.first()
                }.type
            }
        }

    private fun List<ActionDomainModel>.checkIfTopAreWithEquivalentPriority(): Boolean {
        if (size < 2) return false

        val (first, second) = this[0] to this[1]

        return first.priority == second.priority
    }

    private fun oneOrTwoRandom(): Int =
        Random.nextInt(2)

}
