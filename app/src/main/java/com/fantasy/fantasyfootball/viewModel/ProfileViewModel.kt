package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import com.fantasy.fantasyfootball.repository.TeamRepositoryImpl
import com.fantasy.fantasyfootball.repository.UserRepositoryImpl
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepo: UserRepositoryImpl, private val teamRepo: TeamRepositoryImpl): ViewModel() {
    val userTeam: MutableLiveData<UserWithTeam> = MutableLiveData()

    fun editUser(userId: Int, user: User) {
        viewModelScope.launch {
            userRepo.editUser(userId, user)
        }
    }

    fun editTeam(teamId: Int, team: Team) {
        viewModelScope.launch {
            teamRepo.editTeam(teamId, team)
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

    class Provider(private val userRepo: UserRepositoryImpl, private val teamRepo: TeamRepositoryImpl): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(userRepo, teamRepo) as T
        }
    }
}