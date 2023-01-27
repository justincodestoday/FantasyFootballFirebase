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
    val players: MutableLiveData<Player> = MutableLiveData(Player(1, "Xiang", "ze", "Xiang ze", "Chelsea", 7.0f, Enums.Area.Goalkeeper, Enums.Position.GK, Enums.ShirtColor.DARKBLUE, false))

    init {
        mutableListOf(players)
    }

    fun getPlayers(player: Player) {
        viewModelScope.launch {
            repo.getPlayers(player)
        }
    }

    class Provider(val repo: PlayerRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PickPlayerViewModel(repo) as T
        }
    }
}