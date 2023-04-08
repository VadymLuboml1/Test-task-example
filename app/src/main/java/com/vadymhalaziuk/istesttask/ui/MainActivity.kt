package com.vadymhalaziuk.istesttask.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.vadymhalaziuk.istesttask.di.GlobalFactory
import com.vadymhalaziuk.istesttask.domain.model.ActionDomainType
import com.vadymhalaziuk.istesttask.ui.theme.IsTestTaskTheme
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: ActionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = GlobalFactory.create(ActionsViewModel::class.java)



        setContent {
            MaterialTheme {
                // A surface container using the 'background' color from the theme


                LaunchedEffect(key1 = Unit) {
                    lifecycleScope.launchWhenStarted {
                        viewModel.action.collect {

                            //TODO implement rest of

                            when (it) {
                                ActionDomainType.TOAST -> showToast()
                                else -> Unit
                            }
                        }
                    }
                }

                Button(modifier = Modifier
                    .padding(200.dp)
                    .size(50.dp)
                    .background(Color.Green),
                    onClick = { viewModel.onClicked() }) {

                }


            }
        }
    }


    private fun showToast() {
        Toast.makeText(this, "Toast showed", Toast.LENGTH_LONG).show()
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IsTestTaskTheme {
        Greeting("Android")
    }
}