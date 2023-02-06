package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.data.model.Matches
import com.fantasy.fantasyfootball.repository.MatchRepository
import kotlinx.coroutines.launch

class MatchViewModel(private val repo: MatchRepository) : ViewModel() {
    val matches: MutableLiveData<List<Matches>> = MutableLiveData()

    fun getMatches() {
        viewModelScope.launch {
            matches.value = repo.getMatches()
        }
    }

    class Provider(val repo: MatchRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MatchViewModel(repo) as T
        }
    }
}