package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel : ViewModel() {
    val name: MutableLiveData<String?> = MutableLiveData()
    val email: MutableLiveData<String?> = MutableLiveData()
    val password: MutableLiveData<String?> = MutableLiveData()
    val passwordConfirm: MutableLiveData<String?> = MutableLiveData()
    val user: MutableLiveData<User> = MutableLiveData()
    val success: MutableSharedFlow<String> = MutableSharedFlow()
    val error: MutableSharedFlow<String> = MutableSharedFlow()
    val dialog: MutableSharedFlow<String> = MutableSharedFlow()

    open fun onViewCreated() {}

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): T? {
        return try {
            apiCall.invoke()
        } catch (e: Exception) {
            error.emit(e.message.toString())
            e.printStackTrace()
            null
        }
    }
}