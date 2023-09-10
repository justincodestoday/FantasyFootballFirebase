package com.fantasy.fantasyfootball.presentation.ui.match.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Matches
import com.fantasy.fantasyfootball.data.repository.MatchRepositoryImpl
import com.fantasy.fantasyfootball.data.repository.UserRepositoryImpl
import com.fantasy.fantasyfootball.presentation.ui.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val matchRepo: MatchRepositoryImpl,
    private val auth: UserRepositoryImpl
) :
    BaseViewModel() {
    val matches: MutableLiveData<List<Matches>?> = MutableLiveData()

    init {
//        getMatches()
        addMatches()
    }

    val game = listOf(
        Matches(
            homeTeam = Enums.Team.Liverpool,
            awayTeam = Enums.Team.ManUnited,
            homeScore = 2,
            awayScore = 1,
            date = "8/2/2023"
        ),
        Matches(
            homeTeam = Enums.Team.Arsenal,
            awayTeam = Enums.Team.Chelsea,
            homeScore = 2,
            awayScore = 3,
            date = "15/2/2023"
        )
    )


    fun getMatches() {
        viewModelScope.launch {
            try {
                safeApiCall { matches.value = matchRepo.getMatches() }
            } catch (e: Exception) {
                error.emit(e.message.toString())
            }
        }
    }


    private fun addMatches() {
        viewModelScope.launch {
            val existingMatches = matches.value
            if (existingMatches == null || existingMatches.isEmpty()) {
                game.forEach { match ->
                    val matchExists = matchRepo.getMatches()
                        .any { it.homeTeam == match.homeTeam && it.awayTeam == match.awayTeam && it.date == match.date }
                    if (!matchExists) {
                        matchRepo.addMatches(match)
                    }
                }
                getMatches()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        matches.value = null
    }

    fun updatePoints(points: Int) {
        viewModelScope.launch {
            try {
                auth.updatePoints(points)
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