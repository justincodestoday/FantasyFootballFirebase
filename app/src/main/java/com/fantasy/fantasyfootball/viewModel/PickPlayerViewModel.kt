package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.*
import com.fantasy.fantasyfootball.repository.PlayerRepositoryImpl
import com.fantasy.fantasyfootball.repository.TeamRepositoryImpl
import kotlinx.coroutines.launch

class PickPlayerViewModel(
    private val playerRepo: PlayerRepositoryImpl,
    private val teamRepo: TeamRepositoryImpl
) : ViewModel() {
    val players: MutableLiveData<List<Player>> = MutableLiveData()
    val teamPlayer: MutableLiveData<TeamsWithPlayers> = MutableLiveData()

    fun addPlayer(fantasyPlayer: FantasyPlayer) {
        viewModelScope.launch {
            teamRepo.createPlayer(fantasyPlayer)
        }
    }

    fun updateBudget(teamId: Int, budget: Float) {
        viewModelScope.launch {
            teamRepo.updateBudget(teamId, budget)
        }
    }

    fun getTeamWithPlayers(userId: Int) {
        viewModelScope.launch {
            val team = teamRepo.getTeamByOwnerId(userId)
            val teamId = team?.teamId
            val res = teamId?.let { teamRepo.getTeamWithPlayersByTeamId(it) }
            res?.let {
                teamPlayer.value = it
            }
        }
    }

    fun getPlayersByArea(area: String, existingPlayer: List<String>) {
        viewModelScope.launch {
            val res = playerRepo.getPlayersByArea(area)
            val filtered = res.filterNot { player -> existingPlayer.any { it == player.lastName } }
            players.value = filtered
        }
    }

    fun getPlayersBySearch(area: String, playerName: String, existingPlayer: List<String>) {
        viewModelScope.launch {
            val res = playerRepo.getPlayersBySearch(area, playerName)
            val filtered = res.filterNot { player -> existingPlayer.any { it == player.lastName } }
            players.value = filtered
        }
    }

    fun sortPlayers(order: String, by: String, area: String, existingPlayer: List<String>) {
        viewModelScope.launch {
            val res = playerRepo.sortPlayer(order, by, area)
            val filtered = res.filterNot { player -> existingPlayer.any { it == player.lastName } }
            players.value = filtered
        }
    }

    class Provider(
        private val playerRepo: PlayerRepositoryImpl,
        private val teamRepo: TeamRepositoryImpl
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PickPlayerViewModel(playerRepo, teamRepo) as T
        }
    }
}