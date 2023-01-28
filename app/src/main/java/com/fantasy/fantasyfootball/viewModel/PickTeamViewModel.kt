package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.repository.PlayerRepository
import kotlinx.coroutines.launch

class PickTeamViewModel(private val repo: PlayerRepository): ViewModel() {

    fun getPlayersByArea(area: String) {
        viewModelScope.launch {
            repo.getPlayersByArea(area)
        }
    }

    class Provider(private val repo: PlayerRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T{
            return PickTeamViewModel(repo) as T
        }
    }
}