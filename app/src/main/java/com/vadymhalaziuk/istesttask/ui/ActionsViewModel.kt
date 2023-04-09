package com.vadymhalaziuk.istesttask.ui

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadymhalaziuk.istesttask.R
import com.vadymhalaziuk.istesttask.data.android.AndroidSystemPrefRepository
import com.vadymhalaziuk.istesttask.di.qualifiers.IoDispatcher
import com.vadymhalaziuk.istesttask.domain.GetActionUseCase
import com.vadymhalaziuk.istesttask.domain.TrackActionUseCase
import com.vadymhalaziuk.istesttask.ui.model.ActionButtonContent
import com.vadymhalaziuk.istesttask.ui.model.ActionEffect
import com.vadymhalaziuk.istesttask.ui.model.ActionEvent
import com.vadymhalaziuk.istesttask.ui.model.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private val initialState = ScreenState(
    content = ActionButtonContent(
        text = R.string.start_button,
        color = Color.Green
    )
)

@HiltViewModel
class ActionsViewModel @Inject constructor(
    private val getActionUseCase: GetActionUseCase,
    private val trackAction: TrackActionUseCase,
    private val systemPrefRepository: AndroidSystemPrefRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _effect: MutableSharedFlow<ActionEffect> = MutableSharedFlow<ActionEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow<ScreenState>(initialState)
    val state = _state.asStateFlow()

    init {
        systemPrefRepository.isInternetAvailableFlow()
            .onEach { connectionEnabled ->
                val errorText = takeIf { !connectionEnabled }?.let { R.string.internet_error_title }

                _state.update {
                    it.error(errorText)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: ActionEvent) {
        Log.d("vadymLog", "onClick")

        checkInternet { return }

        viewModelScope.launch(ioDispatcher) {

            _state.update { it.loading() }

            val value = getActionUseCase()

            _effect.emit(ActionEffect.ShowAnimation())

            when {
                value.isSuccess -> {
                    val type = value.getOrNull()?.type

                    Log.d("vadymLog", "received action $type")


                }
                else -> Unit//TODO show error

            }
        }
    }

    private inline fun checkInternet(finish: () -> Unit) {
        if (!systemPrefRepository.isInternetAvailable()) {
            viewModelScope.launch {
                _effect.emit(
                    ActionEffect.Dialog(
                        title = R.string.internet_error_title,
                        subtitle = R.string.internet_error_subtitle,
                    )
                )
            }

            finish()
        }
    }


    private fun handleActionState() {

    }

}