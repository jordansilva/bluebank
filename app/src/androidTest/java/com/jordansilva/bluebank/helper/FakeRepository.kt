package com.jordansilva.bluebank.helper

import com.jordansilva.bluebank.R
import com.jordansilva.bluebank.data.AccountRepository
import com.jordansilva.bluebank.data.BankAccount
import com.jordansilva.bluebank.ui.home.AccountServices
import com.jordansilva.bluebank.ui.home.PaymentOption

class FakeAccountRepository : AccountRepository {

    override fun getAccount(): BankAccount {
        return BankAccount(name = AccountName, balance = Balance, accountServices = AccountServices, paymentOptions = PaymentOptions)
    }

    companion object {
        const val AccountName = "Jordan"
        const val Balance = 1234567890.99
        val AccountServices = listOf(
            AccountServices("Empréstimo", "Começe a pagar em até 90 dias.\nValor disponível de até R$12.500,00", R.drawable.ic_baseline_credit_card_24),
            AccountServices("Investimentos", "O jeito Blue de investir: sem asteriscos, linguagem fácil e a partir de R$1. Saiba mais.", R.drawable.ic_baseline_show_chart_24),
            AccountServices("Seguro de vida", "Conheça o Blue Vida: um seguro simples e que cabe no seu bolso.", R.drawable.ic_baseline_credit_card_24)
        )
        val PaymentOptions = PaymentOption.values().toList()
    }
}
