package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Matches
import com.fantasy.fantasyfootball.data.model.TeamsWithPlayers
import com.fantasy.fantasyfootball.repository.MatchRepository
import com.fantasy.fantasyfootball.repository.TeamRepository
import kotlinx.coroutines.launch

class MatchViewModel(private val matchRepo: MatchRepository, private val teamRepo: TeamRepository) :
    ViewModel() {
    val matches: MutableLiveData<List<Matches>> = MutableLiveData()
    val teamPlayer: MutableLiveData<TeamsWithPlayers> = MutableLiveData()

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
            date = "8/2/2023"
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

    fun updatePoints(teamId: Int, points: Int) {
        viewModelScope.launch {
            teamRepo.updatePoints(teamId, points)
        }
    }

    fun getTeamWithPlayers(userId: Int) {
        viewModelScope.launch {
            val team = teamRepo.getTeamByOwnerId(userId)
            val teamId = team?.teamId
            val res = teamId?.let { teamRepo.getTeamWithPlayersByTeamId(it) }
            res?.let {
                teamPlayer.value = it
            }
        }
    }

    class Provider(private val matchRepo: MatchRepository, private val teamRepo: TeamRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MatchViewModel(matchRepo, teamRepo) as T
        }
    }
}