package com.vadymhalaziuk.istesttask.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vadymhalaziuk.istesttask.ui.elements.ActionButton
import com.vadymhalaziuk.istesttask.ui.elements.ActionDialog
import com.vadymhalaziuk.istesttask.ui.elements.ErrorRow
import com.vadymhalaziuk.istesttask.ui.model.ActionEffect
import com.vadymhalaziuk.istesttask.ui.model.ActionEvent
import com.vadymhalaziuk.istesttask.utils.sendNotification

@Composable
fun ActionsComposition(
    viewModel: ActionsViewModel = viewModel(),
) {
    Scaffold { paddingVal ->

        val screenState = viewModel.state.collectAsState()
        val effect = viewModel.effect.collectAsState(initial = null)

        val startAnimation = remember {
            mutableStateOf<Int>(startAnimationInitState)
        }

        effect.value?.let {
            HandleEffect(
                effect = it,
                startAnimation = { startAnimation.value++ },
                onEvent = viewModel::onEvent
            )
        }

        screenState.value.errorText?.let {
            ErrorRow(error = it, paddingValues = paddingVal)
        }

        ScreenContainer(paddingVal) {
            with(screenState.value) {
                if (isLoading) {
                    LinearProgressIndicator()
                }

                content?.let {
                    ActionButton(it, startAnimation.value) { event ->
                        viewModel.onEvent(event)
                    }
                }
            }
        }
    }

}

@Composable
private fun ScreenContainer(
    paddingVal: PaddingValues, content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(paddingVal)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
private fun HandleEffect(
    effect: ActionEffect,
    startAnimation: () -> Unit,
    onEvent: (ActionEvent) -> Unit,
) {
    when (effect) {
        is ActionEffect.Dialog -> {

            var showDialog by remember(effect.composeKey) {
                mutableStateOf<Boolean>(true)
            }

            if (showDialog) {
                ActionDialog(effect.title, effect.subtitle) { showDialog = false }
            }
        }
        is ActionEffect.Toast -> {
            val context = LocalContext.current

            LaunchedEffect(key1 = effect.composeKey) {
                Toast.makeText(context, effect.content, Toast.LENGTH_LONG).show()
            }
        }
        is ActionEffect.ShowAnimation -> {
            LaunchedEffect(key1 = effect.composeKey) {
                startAnimation()
            }
        }
        is ActionEffect.Notification -> SendNotification(effect, onEvent)
    }
}

@SuppressLint("MissingPermission")
@Composable
private fun SendNotification(effect: ActionEffect.Notification, onEvent: (ActionEvent) -> Unit) {
    val context = LocalContext.current

    val (title, subtitle) =
        stringResource(effect.title) to
                effect.subtitle?.let { stringResource(it) }

    val sendNotification = { context.sendNotification(title, subtitle) }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            val event = when {
                permissionGranted -> {
                    sendNotification()

                    ActionEvent.NotificationSent
                }
                else -> ActionEvent.NotificationAccessDenied
            }

            onEvent(event)
        }

    LaunchedEffect(key1 = effect.composeKey) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            sendNotification()
        }
    }
}

const val startAnimationInitState = 0
