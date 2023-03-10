package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepo: FireStoreUserRepository) :
    BaseViewModel() {
    val fixtures: MutableSharedFlow<Unit> = MutableSharedFlow()
    val leaderboard: MutableSharedFlow<Unit> = MutableSharedFlow()
    val teamManagement: MutableSharedFlow<Unit> = MutableSharedFlow()
    val profile: MutableSharedFlow<Unit> = MutableSharedFlow()
    val refreshPage: MutableLiveData<Boolean> = MutableLiveData(false)

    fun refreshPage(refresh: Boolean) {
        refreshPage.value = refresh
    }

    fun navigateToMatches() {
        viewModelScope.launch {
            try {
                fixtures.emit(Unit)
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun navigateToLeaderboard() {
        viewModelScope.launch {
            try {
                leaderboard.emit(Unit)
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun navigateToTeam() {
        viewModelScope.launch {
            try {
                teamManagement.emit(Unit)
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun navigateToProfile() {
        viewModelScope.launch {
            try {
                profile.emit(Unit)
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

//    fun getUserWithTeam(userId: Int) {
//        viewModelScope.launch {
//            val res = userRepo.getUserWithTeam(userId)
//            res?.let {
//                userTeam.value = it
//            }
//        }
//    }

    fun getCurrentUser() {
        viewModelScope.launch {
            try {
                val res = safeApiCall { userRepo.getCurrentUser() }
                user.value = res
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                safeApiCall { userRepo.deAuthenticate() }
                success.emit(Enums.FormSuccess.LOGOUT_SUCCESSFUL.name)
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

//    fun logout() {
//        viewModelScope.launch {
//            logout.emit(Enums.FormSuccess.LOGOUT_SUCCESSFUL.name)
//        }
//    }
}