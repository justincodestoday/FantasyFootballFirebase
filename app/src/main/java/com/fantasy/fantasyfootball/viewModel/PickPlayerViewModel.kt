package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Player
import com.fantasy.fantasyfootball.repository.PlayerRepository
import kotlinx.coroutines.launch

class PickPlayerViewModel(val repo: PlayerRepository): ViewModel() {
    val players: MutableLiveData<List<Player>> = MutableLiveData()
//    val playerName = players.value?.name

    init {
//        mutableListOf(players)
        getPlayers("")
    }

    fun getPlayers(player: String) {
        viewModelScope.launch {
            val res = repo.getPlayers(player)
            players.value = res
        }
    }

    class Provider(val repo: PlayerRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PickPlayerViewModel(repo) as T
        }
    }
}