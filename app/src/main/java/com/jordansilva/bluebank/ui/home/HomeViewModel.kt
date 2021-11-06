package com.jordansilva.bluebank.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jordansilva.bluebank.data.AccountRepository
import com.jordansilva.bluebank.data.AccountRepositoryImpl

class HomeViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    private val _uiState = MutableLiveData(loadMyAccount())
    val uiState: LiveData<HomeUiState> = _uiState

//    var uiState = mutableStateOf(loadMyAccount())
//        private set
//
//    private val _uiStateLD = MutableLiveData(uiState.value)
//    val uiStateLiveData: LiveData<HomeUiState> = _uiStateLD

    private fun loadMyAccount(): HomeUiState {

        // Some nice call to our UseCase, Repository or another layer
        val myAccount = accountRepository.getAccount()

        // Convert myAccount to UI State
        return HomeUiState(
            accountName = myAccount.name,
            balance = myAccount.balance,
            accountServices = myAccount.accountServices,
            paymentOptions = myAccount.paymentOptions,
            isPrivateMode = false,
        )
    }

    /**
     * It changes the application to hide or display sensitive information
     * such as balance, loans, credit card number.
     * @param isPrivate It indicates whether sensitive data should be visible or hidden
     */
    fun changeMode(isPrivate: Boolean) {
        _uiState.value = _uiState.value?.copy(isPrivateMode = isPrivate)
    }

    /**
     * Factory for HomeViewModel
     */
    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(AccountRepositoryImpl()) as T
            }
        }
    }
}