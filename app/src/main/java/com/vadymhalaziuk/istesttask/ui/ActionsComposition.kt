package com.vadymhalaziuk.istesttask.ui

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vadymhalaziuk.istesttask.ui.model.ActionEffect
import com.vadymhalaziuk.istesttask.ui.theme.ActionDialog

@Composable
fun ActionsComposition(viewModel: ActionsViewModel = viewModel()) {
    Scaffold { paddingVal ->

        val screenState = viewModel.state.collectAsState()
        val effect = viewModel.effect.collectAsState(initial = null)

        val startAnimation = remember {
            mutableStateOf<Int>(startAnimationInitState)
        }

        effect.value?.let {
            HandleEffect(effect = it) { startAnimation.value++ }
        }

        screenState.value.errorText?.let {
            ErrorRow(error = it, paddingValues = paddingVal)
        }

        ActionButtonContainer(paddingVal) {
            screenState.value.content?.let {
                ActionButton(it, startAnimation.value) { event ->
                    viewModel.onEvent(event)
                }
            }
        }
    }

}

@Composable
private fun ErrorRow(@StringRes error: Int, paddingValues: PaddingValues) {
    Row(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .background(Color.Red),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(id = error),
            color = Color.Black,
        )
    }
}

@Composable
private fun ActionButtonContainer(
    paddingVal: PaddingValues, content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .padding(paddingVal)
            .fillMaxSize()
    ) {
        content()
    }
}

@Composable
private fun HandleEffect(effect: ActionEffect, startAnimation: () -> Unit) {
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

            LaunchedEffect(key1 = Unit) {
                Toast.makeText(context, effect.content, Toast.LENGTH_LONG).show()
            }
        }
        is ActionEffect.ShowAnimation -> {
            LaunchedEffect(key1 = effect.composeKey) {
                startAnimation()
            }
        }
    }
}

const val startAnimationInitState = 0
