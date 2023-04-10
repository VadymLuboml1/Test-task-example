package com.vadymhalaziuk.istesttask.ui.elements

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun ErrorRow(@StringRes error: Int, paddingValues: PaddingValues) {
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
