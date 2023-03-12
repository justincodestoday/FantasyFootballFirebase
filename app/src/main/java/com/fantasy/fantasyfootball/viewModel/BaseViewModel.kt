package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fantasy.fantasyfootball.data.model.User
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel: ViewModel() {
    val user: MutableLiveData<User?> = MutableLiveData()
    val login: MutableSharedFlow<Unit> = MutableSharedFlow()
    val error: MutableSharedFlow<String> = MutableSharedFlow()
    val success: MutableSharedFlow<String> = MutableSharedFlow()

    open fun onViewCreated() {}

    suspend fun <T> safeApiCall(callback: suspend() -> T): T? {
        return try {
            callback.invoke()
        } catch(e: Exception) {
            error.emit(e.message.toString())
            null
        }
    }
}