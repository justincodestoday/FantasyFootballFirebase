package com.fantasy.fantasyfootball.presentation.ui.pickPlayer.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.*
import com.fantasy.fantasyfootball.data.repository.PlayerRepositoryImpl
import com.fantasy.fantasyfootball.data.repository.UserRepositoryImpl
import com.fantasy.fantasyfootball.presentation.ui.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickPlayerViewModel @Inject constructor(
    private val playerRepo: PlayerRepositoryImpl,
    private val auth: UserRepositoryImpl
) : BaseViewModel() {
    val players: MutableLiveData<List<Player>> = MutableLiveData()

    fun addPlayerToTeam(fantasyPlayer: FantasyPlayer) {
        viewModelScope.launch {
            try {
                safeApiCall { auth.addPlayerToTeam(fantasyPlayer) }
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun updateBudget(budget: Float) {
        viewModelScope.launch {
            try {
                safeApiCall { auth.updateBudget(budget) }
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun getPlayersByArea(area: String, existingPlayers: List<String>) {
        viewModelScope.launch {
            try {
                val playersInArea = playerRepo.getPlayersByArea(area, existingPlayers)
                players.value = playersInArea
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun getPlayersBySearch(area: String, playerName: String, existingPlayer: List<String>) {
        viewModelScope.launch {
            try {
                val res = playerRepo.getPlayersBySearch(area, playerName)
                val filtered =
                    res.filterNot { player -> existingPlayer.any { it == player.lastName } }
                players.value = filtered
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun sortPlayers(order: String, by: String, area: String, existingPlayer: List<String>) {
        viewModelScope.launch {
            try {
                val res = playerRepo.sortPlayer(order, by, area)
                val filtered =
                    res.filterNot { player -> existingPlayer.any { it == player.lastName } }
                players.value = filtered
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }

    fun fetchCurrentUser() {
        viewModelScope.launch {
            try {
                val res = safeApiCall { auth.getCurrentUser() }
                user.value = res
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }
}