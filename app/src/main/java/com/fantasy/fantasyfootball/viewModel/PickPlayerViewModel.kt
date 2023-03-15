package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.*
import com.fantasy.fantasyfootball.repository.FireStorePlayerRepository
import com.fantasy.fantasyfootball.repository.FireStoreTeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickPlayerViewModel @Inject constructor(
    private val playerRepo: FireStorePlayerRepository,
    private val teamRepo: FireStoreTeamRepository
) : ViewModel() {
    val players: MutableLiveData<List<Player>> = MutableLiveData()
    val teamPlayer: MutableLiveData<Team> = MutableLiveData()

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

//    fun getTeamWithPlayers(userId: Int) {
//        viewModelScope.launch {
//            val team = teamRepo.getTeamByOwnerId(userId)
//            val teamId = team?.teamId
//            val res = teamId?.let { teamRepo.getTeamWithPlayersByTeamId(it) }
//            res?.let {
//                teamPlayer.value = it
//            }
//        }
//    }

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
}