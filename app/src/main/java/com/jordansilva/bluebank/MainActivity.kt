package com.jordansilva.bluebank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jordansilva.bluebank.ui.home.HomeScreen
import com.jordansilva.bluebank.ui.theme.BlueBankTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlueBankTheme {
                HomeScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BlueBankTheme {
        HomeScreen()
    }
}