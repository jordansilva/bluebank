package com.jordansilva.bluebank.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jordansilva.bluebank.data.AccountRepositoryImpl
import com.jordansilva.bluebank.ui.theme.BlueBankTheme

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val state = viewModel.uiState.observeAsState().value!!
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
//        HomeHeader(
//            accountName = state.accountName,
//            isPrivateMode = state.isPrivateMode,
//            onPrivateMode = { viewModel.changeMode(!state.isPrivateMode) }
//        )
//
//    val activity = LocalContext.current as FragmentActivity
//        AndroidView(modifier = Modifier.fillMaxWidth(),
//            factory = { context ->
//                LayoutInflater.from(context)
//                    .inflate(R.layout.fragment_container, null, false)
//            },
//        update = {
//            activity.supportFragmentManager.commit {
//                add<HomeHeaderFragment>(R.id.fragment_container_view)
//            }
//        })
        HomeMyAccount(state.balance, state.isPrivateMode)
        HomePaymentList(state.paymentOptions)
        HomeSuggestions()
        HomeAccountOptions(state.accountServices)
    }
}


@Preview(showBackground = true, heightDp = 1024)
@Composable
private fun HomeScreenPreview() {
    val viewModel = HomeViewModel(AccountRepositoryImpl())
    BlueBankTheme {
        HomeScreen(viewModel)
    }
}