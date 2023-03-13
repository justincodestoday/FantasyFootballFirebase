package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel() : ViewModel() {
    val name: MutableLiveData<String?> = MutableLiveData()
    val username: MutableLiveData<String?> = MutableLiveData()
    val email: MutableLiveData<String?> = MutableLiveData()
    val password: MutableLiveData<String?> = MutableLiveData()
    val passwordConfirm: MutableLiveData<String?> = MutableLiveData()

    val user: MutableLiveData<User> = MutableLiveData()
    val team: MutableLiveData<Team> = MutableLiveData()
    val teamName: MutableLiveData<String> = MutableLiveData()
    val userTeam: MutableLiveData<UserWithTeam> = MutableLiveData()

    val logout: MutableSharedFlow<Unit> = MutableSharedFlow()

    val success: MutableSharedFlow<String> = MutableSharedFlow()
    val error: MutableSharedFlow<String> = MutableSharedFlow()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

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