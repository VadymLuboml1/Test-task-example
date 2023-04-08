package com.vadymhalaziuk.istesttask.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadymhalaziuk.istesttask.domain.GetActionUseCase
import com.vadymhalaziuk.istesttask.domain.TrackActionUseCase
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ActionsViewModel(
    private val getActionUseCase: GetActionUseCase,
    private val trackAction: TrackActionUseCase,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _action: MutableSharedFlow<ActionDomainType> = MutableSharedFlow<ActionDomainType>()
    val action = _action.asSharedFlow()


    fun onClicked() {
        Log.d("vadymLog", "onClick")

        viewModelScope.launch(ioDispatcher) {
            val value = getActionUseCase()

            when {
                value.isSuccess -> {
                    val type = value.getOrThrow().type

                    Log.d("vadymLog", "received action $type")


                    _action.emit(type)

                    trackAction(type)

                }
                else -> Unit//TODO show error

            }
        }
    }

}