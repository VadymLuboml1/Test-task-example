package com.vadymhalaziuk.istesttask.domain

import android.util.Log
import com.vadymhalaziuk.istesttask.data.RemoteActionsRepository
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainModel
import kotlin.random.Random

class GetActionUseCase(
    private val sortActionsUseCase: SortActionsUseCase,
    private val remoteActionsRepository: RemoteActionsRepository,
    private val filterActionAccordingToLocal: FilterActionAccordingToLocalUseCase,
    private val filterActionAccordingToRemoteUseCase: FilterActionAccordingToRemoteUseCase,
) {


    suspend operator fun invoke(): Result<ActionDomainModel> =
        remoteActionsRepository.getActions().also {
            Log.d("vadymLog", "received domain $it")

        }.map {
            filterActionAccordingToRemoteUseCase(it)
        }.map {
            filterActionAccordingToLocal(it)
        }.map {
            sortActionsUseCase(it)
        }.runCatching {
            val list = getOrThrow()
            when {
                list.checkIfTopAreWithEqualentPrioprity() -> list[oneOrTwoRandom()]
                else -> list.first()
            }
        }//TODO

    private fun List<ActionDomainModel>.checkIfTopAreWithEqualentPrioprity(): Boolean {
        if (size < 2) return false

        val (first, second) = this[0] to this[1]

        return first.priority == second.priority
    }

    private fun oneOrTwoRandom(): Int =
        Random.nextInt(2)

}