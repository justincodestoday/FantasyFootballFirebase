package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import com.fantasy.fantasyfootball.repository.PlayerRepository
import com.fantasy.fantasyfootball.repository.UserRepository
import kotlinx.coroutines.launch

class TeamManagementViewModel(private val playerRepo: PlayerRepository, private val userRepo: UserRepository): ViewModel() {
    val userTeam: MutableLiveData<UserWithTeam> = MutableLiveData()


    fun getPlayersByArea(area: String) {
        viewModelScope.launch {
            playerRepo.getPlayersByArea(area)
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

    class Provider(private val playerRepo: PlayerRepository, private val userRepo: UserRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T{
            return TeamManagementViewModel(playerRepo, userRepo) as T
        }
    }
}