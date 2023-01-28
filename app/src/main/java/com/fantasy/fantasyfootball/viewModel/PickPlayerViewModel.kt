package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.Player
import com.fantasy.fantasyfootball.repository.PlayerRepository
import kotlinx.coroutines.launch

class PickPlayerViewModel(val repo: PlayerRepository): ViewModel() {
    val players: MutableLiveData<List<Player>> = MutableLiveData()
//    val playerName = players.value?.name

    init {
//        mutableListOf(players)
//        getPlayers("")
//        getPlayersByPosition(Enums.Position.GK)
    }

//    fun getPlayers(area: String, playername: String) {
//        viewModelScope.launch {
//            val res = repo.getPlayers(area, playername)
//            players.value = res
//        }
//    }

    fun getPlayersByArea(area: String) {
        viewModelScope.launch {
            val res = repo.getPlayersByArea(area)
            players.value = res
        }
    }

    fun getPlayersBySearch(area: String, playername: String) {
        viewModelScope.launch {
            val res = repo.getPlayersBySearch(area, playername)
            players.value = res
        }
    }

    fun sortPlayers(order:String,by:String){
        viewModelScope.launch {
            val res = repo.sortPlayer(order, by)
            players.value = res
        }
    }

    class Provider(val repo: PlayerRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PickPlayerViewModel(repo) as T
        }
    }
}