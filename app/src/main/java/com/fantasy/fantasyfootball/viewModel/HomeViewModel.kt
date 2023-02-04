package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import com.fantasy.fantasyfootball.repository.TeamRepository
import com.fantasy.fantasyfootball.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepo: UserRepository, private val teamRepo: TeamRepository) : ViewModel() {
    val userTeam: MutableLiveData<UserWithTeam> = MutableLiveData()
//    val user: MutableSharedFlow<User?> = MutableSharedFlow()

    val teamManagement: MutableSharedFlow<Unit> = MutableSharedFlow()
    val profile: MutableSharedFlow<Unit> = MutableSharedFlow()
    val logout: MutableSharedFlow<String> = MutableSharedFlow()
    val error: MutableSharedFlow<String> = MutableSharedFlow()

    val refreshPage: MutableLiveData<Boolean> = MutableLiveData(false)

    fun refreshPage(refresh: Boolean) {
        refreshPage.value = refresh
    }

    fun navigateToTeam() {
        viewModelScope.launch {
            teamManagement.emit(Unit)
        }
    }

    fun navigateToProfile() {
        viewModelScope.launch {
            profile.emit(Unit)
        }
    }

    fun getUserWithTeam(userId: Int) {
        viewModelScope.launch {
            val res = userRepo.getUserWithTeam(userId)
            res?.let {
                userTeam.value = it
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logout.emit(Enums.FormSuccess.LOGOUT_SUCCESSFUL.name)
        }
    }

    class Provider(private val userRepo: UserRepository, private val teamRepo: TeamRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(userRepo, teamRepo) as T
        }
    }
}