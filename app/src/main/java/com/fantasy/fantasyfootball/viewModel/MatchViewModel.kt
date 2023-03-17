package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Matches
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.repository.FireStoreMatchRepository
import com.fantasy.fantasyfootball.repository.FireStoreTeamRepository
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val matchRepo: FireStoreMatchRepository,
    private val teamRepo: FireStoreTeamRepository,
    private val userRepo: FireStoreUserRepository
) :
    BaseViewModel() {

    val matches: MutableLiveData<List<Matches>?> = MutableLiveData()
    val teamPlayer: MutableLiveData<Team> = MutableLiveData()

//    init {
//        viewModelScope.launch {
//            getMatches() // Always get the latest matches
//        }
//    }

    init {
        getMatches()
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

//    init {
//        viewModelScope.launch {
//            game.forEach {
//                matchRepo.insert(it)
//            }
//        }
//    }

    fun getMatches() {
        viewModelScope.launch {
            matches.value = matchRepo.getMatches()
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
            userRepo.updatePoints(points)
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
}