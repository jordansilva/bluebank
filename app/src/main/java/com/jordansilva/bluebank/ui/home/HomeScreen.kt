package com.jordansilva.bluebank.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jordansilva.bluebank.ui.components.VerticalScroll
import com.jordansilva.bluebank.ui.theme.BlueBankTheme

private val staticViewModel = HomeViewModel.provideFactory().create(HomeViewModel::class.java)

@Composable
fun HomeScreen(viewModel: HomeViewModel = staticViewModel) {
    val state = viewModel.uiState
    VerticalScroll {
        HomeHeader(
            accountName = viewModel.uiState.accountName,
            isPrivateMode = viewModel.uiState.isPrivateMode,
            onPrivateMode = { viewModel.changeMode(!state.isPrivateMode) }
        )

        HomeMyAccount(state.balance, viewModel.uiState.isPrivateMode)
        HomePaymentList(state.paymentOptions)
        HomeSuggestions()
        HomeAccountOptions(state.accountServices)
    }
}


@Preview(showBackground = true, heightDp = 1024)
@Composable
private fun HomeScreenPreview() {
    BlueBankTheme {
        HomeScreen()
    }
}