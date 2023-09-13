package com.fantasy.fantasyfootball.presentation.ui.home.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.core.Enums
import com.fantasy.fantasyfootball.data.repository.UserRepositoryImpl
import com.fantasy.fantasyfootball.presentation.ui.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val auth: UserRepositoryImpl) :
    BaseViewModel() {
    val fixtures: MutableSharedFlow<Unit> = MutableSharedFlow()
    val leaderboard: MutableSharedFlow<Unit> = MutableSharedFlow()
    val teamManagement: MutableSharedFlow<Unit> = MutableSharedFlow()
    val profile: MutableSharedFlow<Unit> = MutableSharedFlow()
    val logout: MutableSharedFlow<Unit> = MutableSharedFlow()
    val refreshPage: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun onViewCreated() {
        super.onViewCreated()
        getCurrentUser()
    }

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

    fun navigateToLogin() {
        viewModelScope.launch {
            try {
                logout()
                logout.emit(Unit)
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            try {
                val res = safeApiCall { auth.getCurrentUser() }
                user.value = res
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            try {
                safeApiCall { auth.deAuthenticate() }
                success.emit(Enums.FormSuccess.LOGOUT_SUCCESSFUL.name)
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }
}