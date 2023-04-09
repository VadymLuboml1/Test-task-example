package com.vadymhalaziuk.istesttask.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import com.vadymhalaziuk.istesttask.ui.model.ActionButtonContent
import com.vadymhalaziuk.istesttask.ui.model.ActionEvent

@Composable
fun BoxScope.ActionButton(
    model: ActionButtonContent,
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


    Button(
        modifier = Modifier
            .align(Alignment.Center)
            .rotate(currentRotation),
        onClick = {
            onEvent(
                when {
                    rotation.isRunning -> ActionEvent.ClickWhileAnimation
                    else -> ActionEvent.Click
                }
            )
        }
    ) {
        Text(
            text = stringResource(model.text),
            color = model.color
        )
    }
}

private const val rotationAngle = 180f
private const val rotationDuration = 3000