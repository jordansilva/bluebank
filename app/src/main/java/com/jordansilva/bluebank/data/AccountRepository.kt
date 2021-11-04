package com.jordansilva.bluebank.data

import com.jordansilva.bluebank.helper.PreviewHelper
import com.jordansilva.bluebank.ui.home.AccountServices
import com.jordansilva.bluebank.ui.home.PaymentOption

data class BankAccount(
    val name: String,
    val balance: Double,
    val accountServices: List<AccountServices>,
    val paymentOptions: List<PaymentOption>,
)

interface AccountRepository {
    fun getAccount(): BankAccount
}

class AccountRepositoryImpl : AccountRepository {
    override fun getAccount(): BankAccount {
        return BankAccount(
            name = PreviewHelper.AccountName,
            balance = PreviewHelper.AccountBalance,
            accountServices = PreviewHelper.AccountServices,
            paymentOptions = PreviewHelper.AccountPaymentOptions
        )
    }
}