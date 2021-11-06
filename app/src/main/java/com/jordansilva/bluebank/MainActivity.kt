package com.jordansilva.bluebank

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.jordansilva.bluebank.ui.home.HomeScreen
import com.jordansilva.bluebank.ui.home.HomeViewModel
import com.jordansilva.bluebank.ui.home_fragment.HomeHeaderFragment
import com.jordansilva.bluebank.ui.theme.BlueBankTheme


class MainActivity : FragmentActivity() {

    private val viewModel: HomeViewModel by viewModels { HomeViewModel.provideFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        supportFragmentManager.commit { add<HomeHeaderFragment>(R.id.container) }
        val composeView = findViewById<ComposeView>(R.id.composeView)
        composeView.setContent {
            BlueBankTheme {
                Surface {
                    HomeScreen(viewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val viewModel = HomeViewModel.provideFactory().create(HomeViewModel::class.java)
    BlueBankTheme {
        HomeScreen(viewModel)
    }
}