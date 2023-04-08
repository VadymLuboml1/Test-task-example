package com.vadymhalaziuk.istesttask.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadymhalaziuk.istesttask.R
import com.vadymhalaziuk.istesttask.data.android.AndroidSystemPrefRepository
import com.vadymhalaziuk.istesttask.di.qualifiers.IoDispatcher
import com.vadymhalaziuk.istesttask.domain.GetActionUseCase
import com.vadymhalaziuk.istesttask.domain.TrackActionUseCase
import com.vadymhalaziuk.istesttask.ui.model.ActionEffect
import com.vadymhalaziuk.istesttask.ui.model.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActionsViewModel @Inject constructor(
    private val getActionUseCase: GetActionUseCase,
    private val trackAction: TrackActionUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    systemPrefRepository: AndroidSystemPrefRepository,
) : ViewModel() {

    private val _effect: MutableSharedFlow<ActionEffect> = MutableSharedFlow<ActionEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        systemPrefRepository.isInternetAvailableFlow()
            .filterNot { it }
            .onEach {
                _state.update {
                    it.error(R.string.internet_error)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onClicked() {
        Log.d("vadymLog", "onClick")

        viewModelScope.launch(ioDispatcher) {

            _state.update { it.loading() }

            val value = getActionUseCase()

            when {
                value.isSuccess -> {
                    val type = value.getOrNull()?.type

                    Log.d("vadymLog", "received action $type")



                }
                else -> Unit//TODO show error

            }
        }
    }

    private fun handleActionState(){

    }

}