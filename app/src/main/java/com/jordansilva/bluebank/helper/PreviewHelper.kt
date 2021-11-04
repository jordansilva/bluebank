package com.jordansilva.bluebank.helper

import com.jordansilva.bluebank.R
import com.jordansilva.bluebank.ui.home.AccountServices
import com.jordansilva.bluebank.ui.home.HomeUiState
import com.jordansilva.bluebank.ui.home.PaymentOption

object PreviewHelper {
    const val AccountName = "Jordan"
    const val AccountBalance = 9050.20

    val AccountServices = listOf(
        AccountServices("Empréstimo", "Começe a pagar em até 90 dias.\nValor disponível de até R$12.500,00", R.drawable.ic_baseline_credit_card_24),
        AccountServices("Investimentos", "O jeito Blue de investir: sem asteriscos, linguagem fácil e a partir de R$1. Saiba mais.", R.drawable.ic_baseline_show_chart_24),
        AccountServices("Seguro de vida", "Conheça o Blue Vida: um seguro simples e que cabe no seu bolso.", R.drawable.ic_baseline_credit_card_24)
    )

    val AccountPaymentOptions = PaymentOption.values().toList()

    val HomeState = HomeUiState(
        accountName = AccountName,
        balance = AccountBalance,
        isPrivateMode = false,
        accountServices = AccountServices,
        paymentOptions = AccountPaymentOptions
    )

}