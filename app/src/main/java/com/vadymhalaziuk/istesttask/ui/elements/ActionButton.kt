package com.vadymhalaziuk.istesttask.ui.elements

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.vadymhalaziuk.istesttask.R
import com.vadymhalaziuk.istesttask.ui.model.ActionButtonContentState
import com.vadymhalaziuk.istesttask.ui.model.ActionEvent
import com.vadymhalaziuk.istesttask.ui.startAnimationInitState
import com.vadymhalaziuk.istesttask.utils.toTriple

@Composable
fun ActionButton(
    model: ActionButtonContentState,
    animationKey: Int,
    onEvent: (ActionEvent) -> Unit
) {
    var currentRotation by remember { mutableStateOf(0f) }

    val rotation = remember { Animatable(currentRotation) }

    if (animationKey != startAnimationInitState) {
        LaunchedEffect(key1 = animationKey) {
            rotation.animateTo(
                targetValue = currentRotation + rotationAngle,
                animationSpec = tween(rotationDuration)
            ) {
                currentRotation = value
            }
        }
    }

    val (text, color, clickEvent) = when (model) {
        ActionButtonContentState.ENABLED -> {
            R.string.active_button to Color.Green toTriple ActionEvent.Click
        }
        ActionButtonContentState.DISABLED -> {
            R.string.disabled_button to Color.Gray toTriple ActionEvent.ClickWhileDisabled
        }
    }

    Button(
        modifier = Modifier
            .rotate(currentRotation),
        onClick = {
            onEvent(
                when {
                    rotation.isRunning -> ActionEvent.ClickWhileAnimation
                    else -> clickEvent
                }
            )
        }
    ) {
        Text(
            text = stringResource(text),
            color = color
        )
    }
}

private const val rotationAngle = 360f
private const val rotationDuration = 3000