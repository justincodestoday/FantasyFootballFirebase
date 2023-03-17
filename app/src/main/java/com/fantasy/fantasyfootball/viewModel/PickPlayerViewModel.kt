package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.*
import com.fantasy.fantasyfootball.repository.FireStoreFantasyRepository
import com.fantasy.fantasyfootball.repository.FireStorePlayerRepository
import com.fantasy.fantasyfootball.repository.FireStoreTeamRepository
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickPlayerViewModel @Inject constructor(
    private val playerRepo: FireStorePlayerRepository,
    private val teamRepo: FireStoreTeamRepository,
    private val userRepo: FireStoreUserRepository,
    private val fantasyRepo: FireStoreFantasyRepository

    ) : BaseViewModel() {
    val players: MutableLiveData<List<Player>> = MutableLiveData()

    fun addPlayer(fantasyPlayer: FantasyPlayer) {
        viewModelScope.launch {
            fantasyRepo.createPlayer(fantasyPlayer)
        }
    }

    fun addPlayerToTeam(fantasyPlayer: FantasyPlayer) {
        viewModelScope.launch {
            userRepo.addPlayerToTeam(fantasyPlayer)
        }
    }

    fun updateBudget(budget: Float) {
        viewModelScope.launch {
            userRepo.updateBudget(budget)
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

//    fun getPlayersByArea(area: String, existingPlayer: List<String>) {
//        viewModelScope.launch {
//            val res = playerRepo.getPlayersByArea(area)
//            val filtered = res.filterNot { player -> existingPlayer.any { it == player.lastName } }
//            players.value = filtered
//        }
//    }

    fun getPlayersByArea(area: String, existingPlayers: List<String>) {
        viewModelScope.launch {
            val playersInArea = playerRepo.getPlayersByArea(area, existingPlayers)
            players.value = playersInArea
        }
    }

    //    fun getPlayersBySearch(area: String, playerName: String, existingPlayer: List<String>) {
//        viewModelScope.launch {
//            val res = playerRepo.getPlayersBySearch(area, playerName)
//            val filtered = res.filterNot { player -> existingPlayer.any { it == player.lastName } }
//            players.value = filtered
//        }
//    }
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

    fun fetchCurrentUser() {
        viewModelScope.launch {
            try {
                val res = safeApiCall { userRepo.getCurrentUser() }
                user.value = res
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }
}