package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.repository.PlayerRepository
import com.fantasy.fantasyfootball.repository.TeamRepository
import com.fantasy.fantasyfootball.repository.UserRepository

class LeaderboardViewModel(val repo: UserRepository, val repo2: TeamRepository): ViewModel() {
    val users: MutableLiveData<List<User>> = MutableLiveData()
    val teams: MutableLiveData<List<Team>> = MutableLiveData()

    class Provider(val repo: UserRepository, val repo2: TeamRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LeaderboardViewModel(repo, repo2) as T
        }
    }
}