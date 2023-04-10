package com.vadymhalaziuk.istesttask.ui.elements

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.vadymhalaziuk.istesttask.ui.theme.dialogHeight
import com.vadymhalaziuk.istesttask.ui.theme.dialogWidth

@Composable
fun ActionDialog(@StringRes title: Int, @StringRes subtitle: Int, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            Modifier
                .size(
                    width = dialogWidth,
                    height = dialogHeight,
                )
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(title), color = Color.Black)

                Text(text = stringResource(subtitle), color = Color.Black)
            }
        }
    }

}