package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import com.fantasy.fantasyfootball.repository.UserRepository
import kotlinx.coroutines.launch

class LeaderboardViewModel(val repo: UserRepository) : ViewModel() {
    val users: MutableLiveData<List<UserWithTeam>> = MutableLiveData()

    fun getUsersWithTeams() {
        viewModelScope.launch {
            users.value = repo.getUsersWithTeams().sortedWith(compareBy() {
                it.team.points
            }).reversed()
        }
    }

    class Provider(val repo: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LeaderboardViewModel(repo) as T
        }
    }
}