package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import com.fantasy.fantasyfootball.repository.TeamRepository
import com.fantasy.fantasyfootball.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepo: UserRepository, private val teamRepo: TeamRepository): ViewModel() {

    val team: MutableLiveData<Team> = MutableLiveData()
    val user: MutableLiveData<UserWithTeam> = MutableLiveData()

    fun createUser(user: User) {
        viewModelScope.launch {
            userRepo.createUser(user)
        }
    }

    fun editUser(id: Int, user: User) {
        viewModelScope.launch {
            userRepo.editUser(id, user)
        }
    }

     fun getTeamName(ownerId: Int) {
        viewModelScope.launch {
            val res = teamRepo.getTeamByOwnerId(ownerId)
            res?.let {
                team.value = it
            }
//            teamRepo.getTeamByOwnerId(ownerId)
        }
    }

    fun getUserWithTeamByUserId(id: Int) {
        viewModelScope.launch {
            val res = userRepo.getUserWithTeamByUserId(id)
            res?.let {
                user.value = it
            }
        }
    }

    class Provider(private val userRepo: UserRepository, private val teamRepo: TeamRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(userRepo, teamRepo) as T
        }
    }
}