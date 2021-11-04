package com.jordansilva.bluebank.ui.home

import androidx.annotation.DrawableRes

data class HomeUiState(
    val accountName: String,
    val balance: Double,
    val accountServices: List<AccountServices>,
    val paymentOptions: List<PaymentOption>,
    var isPrivateMode: Boolean = false,
)

data class AccountServices(val title: String, val description: String, @DrawableRes val iconId: Int)
