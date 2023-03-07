package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel : ViewModel() {
    val success: MutableSharedFlow<String> = MutableSharedFlow()
    val error: MutableSharedFlow<String> = MutableSharedFlow()
    val logout: MutableSharedFlow<Unit> = MutableSharedFlow()

    open fun onViewCreated() {}

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): T? {
        return try {
            apiCall.invoke()
        } catch (e: Exception) {
            error.emit(e.message.toString())
            null
        }
    }
}