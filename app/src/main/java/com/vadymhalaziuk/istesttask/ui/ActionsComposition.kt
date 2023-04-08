package com.vadymhalaziuk.istesttask.ui

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vadymhalaziuk.istesttask.ui.model.ActionEffect

@Composable
fun ActionsComposition(viewModel: ActionsViewModel = viewModel()) {
    Scaffold { paddingVal ->

        val screenState = viewModel.state.collectAsState()
        val effect = viewModel.effect.collectAsState(initial = null)

        effect.value?.let {
            HandleEffect(effect = it)
        }

        screenState.value.errorText?.let {
            ErrorRow(error = it, paddingValues = paddingVal)
        }

        Box(modifier = Modifier
            .padding(paddingVal)
            .fillMaxSize()) {


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
private fun ActionButton(){

    Button(onClick = {}) {
        Text("Stub")
    }
}

@Composable
private fun HandleEffect(effect: ActionEffect) {
    when (effect) {
        is ActionEffect.Dialog -> {

            var showDialog by remember {
                mutableStateOf<Boolean>(true)
            }

            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    Box(Modifier.size(100.dp)) {
                        Column {
                            Text(text = stringResource(effect.title), color = Color.Black)

                            Text(text = stringResource(effect.subtitle), color = Color.Black)
                        }
                    }
                }
            }
        }
        is ActionEffect.Toast -> {
            Toast.makeText(LocalContext.current, effect.content, Toast.LENGTH_LONG).show()
        }
    }
}
