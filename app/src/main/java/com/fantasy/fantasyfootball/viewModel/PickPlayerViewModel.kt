package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.Player
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import com.fantasy.fantasyfootball.repository.PlayerRepository
import com.fantasy.fantasyfootball.repository.TeamRepository
import com.fantasy.fantasyfootball.repository.UserRepository
import kotlinx.coroutines.launch

class PickPlayerViewModel(
    private val playerRepo: PlayerRepository,
    private val teamRepo: TeamRepository,
    private val userRepo: UserRepository
) : ViewModel() {
    val players: MutableLiveData<List<Player>> = MutableLiveData()

    val userTeam: MutableLiveData<UserWithTeam> = MutableLiveData()

//    init {
////        mutableListOf(players)
////        getPlayers("")
////        getPlayersByPosition(Enums.Position.GK)
//    }

//    fun getPlayers(area: String, playername: String) {
//        viewModelScope.launch {
//            val res = playerRepo.getPlayers(area, playername)
//            players.value = res
//        }
//    }

    fun updateBudget(teamId: Int, budget: Float, cost: Float) {
        viewModelScope.launch {
            val team = teamRepo.getTeamById(teamId)
            val currentValue = team!!.budget.minus(cost)
            val updatedValue = currentValue - 10 // subtract the desired value
//            yourDao.updateValue(updatedValue) // upd
//            teamRepo.updateBudget()
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

    fun getPlayersByArea(area: String) {
        viewModelScope.launch {
            val res = playerRepo.getPlayersByArea(area)
            players.value = res
        }
    }

    fun getPlayersBySearch(area: String, playername: String) {
        viewModelScope.launch {
            val res = playerRepo.getPlayersBySearch(area, playername)
            players.value = res
        }
    }

    fun sortPlayers(order: String, by: String, area: String) {
        viewModelScope.launch {
            val res = playerRepo.sortPlayer(order, by, area)
            players.value = res
        }
    }

    class Provider(
        private val playerRepo: PlayerRepository,
        private val teamRepo: TeamRepository,
        private val userRepo: UserRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PickPlayerViewModel(playerRepo, teamRepo, userRepo) as T
        }
    }
}