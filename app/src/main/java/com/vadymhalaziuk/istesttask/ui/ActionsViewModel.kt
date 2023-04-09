package com.vadymhalaziuk.istesttask.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadymhalaziuk.istesttask.R
import com.vadymhalaziuk.istesttask.di.qualifiers.IoDispatcher
import com.vadymhalaziuk.istesttask.domain.GetActionUseCase
import com.vadymhalaziuk.istesttask.domain.TrackActionUseCase
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainError
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainType
import com.vadymhalaziuk.istesttask.domain.repository.AndroidSystemPrefRepository
import com.vadymhalaziuk.istesttask.ui.model.ActionButtonContentState
import com.vadymhalaziuk.istesttask.ui.model.ActionEffect
import com.vadymhalaziuk.istesttask.ui.model.ActionEvent
import com.vadymhalaziuk.istesttask.ui.model.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private val initialState = ScreenState(
    content = ActionButtonContentState.ENABLED
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
        checkInternet { return }

        when (event) {
            is ActionEvent.ClickWhileAnimation -> {
                emitDialog(
                    R.string.wait_animation_end_title,
                    R.string.wait_animation_end_subtitle,
                )
            }
            is ActionEvent.Click -> executeAction()
            is ActionEvent.NotificationAccessDenied -> {
                emitDialog(
                    R.string.unable_to_send_push_title,
                    R.string.unable_to_send_push_subtitle,
                )
            }
            is ActionEvent.ClickWhileDisabled -> {
                emitDialog(
                    R.string.unable_to_make_click_title, R.string.unable_to_make_click_subtitle
                )
            }
            is ActionEvent.NotificationSent -> {
                viewModelScope.launch(ioDispatcher) {
                    trackAction(ActionDomainType.NOTIFICATION)
                }
            }
        }
    }

    private fun executeAction() {
        viewModelScope.launch(ioDispatcher) {

            _state.update { it.loading() }

            val actionTypeResult = getActionUseCase()

            _effect.emit(ActionEffect.Notification(R.string.push_title))

            actionTypeResult.ifSuccess { actionType ->

                if (actionType !in TrackActionUseCase.actionsWithAssurance) {
                    viewModelScope.launch(ioDispatcher) {
                        trackAction(actionType)
                    }
                }

                _effect.emit(
                    when (actionType) {
                        ActionDomainType.ANIMATION -> ActionEffect.ShowAnimation()
                        ActionDomainType.TOAST -> ActionEffect.Toast(R.string.success_action_title)
                        ActionDomainType.NOTIFICATION -> ActionEffect.Notification(R.string.success_action_title)
                        else -> {
                            _state.update { it.loading(false) }
                            return@launch
                        }
                    }
                )
            }.ifError { errorType ->
                when (errorType) {
                    is ActionDomainError.NoAvailable -> {
                        _state.update { it.content { ActionButtonContentState.DISABLED } }

                        emitDialog(
                            R.string.unable_to_make_click_title,
                            R.string.unable_to_make_click_subtitle
                        )
                    }
                    else -> {
                        emitDialog(
                            title = R.string.unknown_error_title,
                            subtitle = R.string.unknown_error_subtitle
                        )
                    }
                }
            }

            _state.update { it.loading(false) }
        }
    }

    private inline fun checkInternet(invokeOnDisabled: () -> Unit) {
        if (!systemPrefRepository.isInternetAvailable()) {
            emitDialog(
                title = R.string.internet_error_title,
                subtitle = R.string.internet_error_subtitle
            )

            invokeOnDisabled()
        }
    }

    private fun emitDialog(@StringRes title: Int, @StringRes subtitle: Int) =
        viewModelScope.launch {
            _effect.emit(
                ActionEffect.Dialog(
                    title = title,
                    subtitle = subtitle,
                )
            )
        }
}