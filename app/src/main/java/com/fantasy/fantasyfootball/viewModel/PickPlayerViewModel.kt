package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.*
import com.fantasy.fantasyfootball.repository.PlayerRepository
import com.fantasy.fantasyfootball.repository.TeamRepository
import kotlinx.coroutines.launch

class PickPlayerViewModel(
    private val playerRepo: PlayerRepository,
    private val teamRepo: TeamRepository
) : ViewModel() {
    val players: MutableLiveData<List<Player>> = MutableLiveData()
    val teamPlayer: MutableLiveData<TeamsWithPlayers> = MutableLiveData()
    val team: MutableLiveData<Team> = MutableLiveData()

//    fun createTeamId(team: Team): Team {
//        viewModelScope.launch {
//            teamRepo.createTeamId(team)
//        }
//        return team
//    }

    fun createPlayer(fantasyPlayer: FantasyPlayer) {
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
        private val teamRepo: TeamRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PickPlayerViewModel(playerRepo, teamRepo) as T
        }
    }
}