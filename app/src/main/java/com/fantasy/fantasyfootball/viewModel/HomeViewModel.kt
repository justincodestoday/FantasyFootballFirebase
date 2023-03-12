package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import com.fantasy.fantasyfootball.repository.FireStoreTeamRepository
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import com.fantasy.fantasyfootball.repository.TeamRepositoryImpl

import com.fantasy.fantasyfootball.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepo: FireStoreUserRepository) :
    BaseViewModel() {
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