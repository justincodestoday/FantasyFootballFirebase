package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel : ViewModel() {
    val name: MutableLiveData<String?> = MutableLiveData()
    val username: MutableLiveData<String?> = MutableLiveData()
    val password: MutableLiveData<String?> = MutableLiveData()
    val passwordConfirm: MutableLiveData<String?> = MutableLiveData()

    val refreshPage: MutableLiveData<Boolean> = MutableLiveData(false)
    val loggedIn: MutableLiveData<Boolean?> = MutableLiveData()
    val user: MutableLiveData<User?> = MutableLiveData()
    val userTeam: MutableLiveData<UserWithTeam> = MutableLiveData()

    val fixtures: MutableSharedFlow<Unit> = MutableSharedFlow()
    val leaderboard: MutableSharedFlow<Unit> = MutableSharedFlow()
    val teamManagement: MutableSharedFlow<Unit> = MutableSharedFlow()
    val profile: MutableSharedFlow<Unit> = MutableSharedFlow()
    val login: MutableSharedFlow<Unit> = MutableSharedFlow()
    val logout: MutableSharedFlow<Unit> = MutableSharedFlow()

    val success: MutableSharedFlow<String> = MutableSharedFlow()
    val error: MutableSharedFlow<String> = MutableSharedFlow()

    open fun onViewCreated() {}

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): T? {
        return try {
            apiCall.invoke()
        } catch (e: Exception) {
            error.emit(e.message.toString())
            e.printStackTrace() // ask Khayrul about this along with add image function
            null
        }
    }
}