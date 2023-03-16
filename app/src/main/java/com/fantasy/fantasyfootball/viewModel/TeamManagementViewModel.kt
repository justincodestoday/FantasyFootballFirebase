package com.fantasy.fantasyfootball.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Player
import com.fantasy.fantasyfootball.repository.FireStorePlayerRepository
import com.fantasy.fantasyfootball.repository.FireStoreTeamRepository
import com.fantasy.fantasyfootball.repository.FireStoreUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamManagementViewModel @Inject constructor(
    private val playerRepo: FireStorePlayerRepository,
    private val teamRepo: FireStoreTeamRepository,
    private val userRepo: FireStoreUserRepository
) : BaseViewModel() {
    val player: MutableLiveData<Player> = MutableLiveData()
    val players: MutableLiveData<List<Player>> = MutableLiveData()

    init {
//        createPlayer()
    }

    val players2 = listOf(
        Player(
            firstName = "Kylian",
            lastName = "Mbappe",
            team = "Paris Saint-Germain",
            teamConst = Enums.Team.ParisSG,
            price = 45.0f,
            area = Enums.Area.Striker,
            color = Enums.ShirtColor.DARKBLUE,
        ),
        Player(
            firstName = "Erling",
            lastName = "Haaland",
            team = "Manchester City",
            teamConst = Enums.Team.ManCity,
            price = 42.5f,
            area = Enums.Area.Striker,
            color = Enums.ShirtColor.LIGHTBLUE
        ),
        Player(
            firstName = "Mohamed",
            lastName = "Salah",
            team = "Liverpool FC",
            teamConst = Enums.Team.Liverpool,
            price = 20.0f,
            area = Enums.Area.Midfielder,
            color = Enums.ShirtColor.DARKRED
        ),
        Player(
            firstName = "Jude",
            lastName = "Bellingham",
            team = "Borussia Dortmund",
            teamConst = Enums.Team.BorussiaDort,
            price = 27.5f,
            area = Enums.Area.Midfielder,
            color = Enums.ShirtColor.YELLOW
        ),
        Player(
            firstName = "Phil",
            lastName = "Foden",
            team = "Manchester City",
            teamConst = Enums.Team.ManCity,
            price = 27.5f,
            area = Enums.Area.Midfielder,
            color = Enums.ShirtColor.LIGHTBLUE
        ),
        Player(
            firstName = "",
            lastName = "Pedri",
            team = "FC Barcelona",
            teamConst = Enums.Team.Barcelona,
            price = 25.0f,
            area = Enums.Area.Midfielder,
            color = Enums.ShirtColor.DARKBLUE
        ),
        Player(
            firstName = "Josko",
            lastName = "Gvardiol",
            team = "RB Leipzig",
            teamConst = Enums.Team.Leipzig,
            price = 19.0f,
            area = Enums.Area.Defender,
            color = Enums.ShirtColor.WHITE
        ),
        Player(
            firstName = "Ruben",
            lastName = "Dias",
            team = "Manchester City",
            teamConst = Enums.Team.ManCity,
            price = 19.0f,
            area = Enums.Area.Defender,
            color = Enums.ShirtColor.LIGHTBLUE
        ),
        Player(
            firstName = "Reece",
            lastName = "James",
            team = "Chelsea FC",
            teamConst = Enums.Team.Chelsea,
            price = 17.5f,
            area = Enums.Area.Defender,
            color = Enums.ShirtColor.DARKBLUE
        ),
        Player(
            firstName = "Alphonso",
            lastName = "Davies",
            team = "Bayern Munich",
            teamConst = Enums.Team.BayernMunich,
            price = 17.5f,
            area = Enums.Area.Defender,
            color = Enums.ShirtColor.LIGHTRED
        ),
        Player(
            firstName = "Thibaut",
            lastName = "Courtois",
            team = "Real Madrid",
            teamConst = Enums.Team.RealMadrid,
            price = 15.0f,
            area = Enums.Area.Goalkeeper,
            color = Enums.ShirtColor.WHITE
        ),
        Player(
            firstName = "Aaron",
            lastName = "Ramsdale",
            team = "Arsenal",
            teamConst = Enums.Team.Arsenal,
            price = 7.5f,
            area = Enums.Area.Goalkeeper,
            color = Enums.ShirtColor.LIGHTRED
        ),
        Player(
            firstName = "Nick",
            lastName = "Pope",
            team = "Newcastle United",
            teamConst = Enums.Team.Newcastle,
            price = 4.5f,
            area = Enums.Area.Goalkeeper,
            color = Enums.ShirtColor.BLACK
        ),
        Player(
            firstName = "Romelu",
            lastName = "Lukaku",
            team = "Inter Milan",
            teamConst = Enums.Team.InterMilan,
            price = 14.0f,
            area = Enums.Area.Striker,
            color = Enums.ShirtColor.DARKBLUE
        ),
        Player(
            firstName = "Julian",
            lastName = "Alvarez",
            team = "Manchester City",
            teamConst = Enums.Team.ManCity,
            price = 12.5f,
            area = Enums.Area.Striker,
            color = Enums.ShirtColor.LIGHTBLUE
        ),
        Player(
            firstName = "Allan",
            lastName = "Saint-Maximin",
            team = "Newcastle United",
            teamConst = Enums.Team.Newcastle,
            price = 10.0f,
            area = Enums.Area.Striker,
            color = Enums.ShirtColor.BLACK
        ),
        Player(
            firstName = "James",
            lastName = "Madison",
            team = "Leicester City",
            teamConst = Enums.Team.LeicesterCity,
            price = 14.0f,
            area = Enums.Area.Midfielder,
            color = Enums.ShirtColor.LIGHTBLUE
        ),
        Player(
            firstName = "Sandro",
            lastName = "Tonali",
            team = "AC Milan",
            teamConst = Enums.Team.ACMilan,
            price = 12.5f,
            area = Enums.Area.Midfielder,
            color = Enums.ShirtColor.DARKRED
        ),
        Player(
            firstName = "",
            lastName = "Fabinho",
            team = "Liverpool FC",
            teamConst = Enums.Team.Liverpool,
            price = 14.0f,
            area = Enums.Area.Midfielder,
            color = Enums.ShirtColor.DARKRED
        ),
        Player(
            firstName = "Eduardo",
            lastName = "Camavinga",
            team = "Real Madrid",
            teamConst = Enums.Team.RealMadrid,
            price = 12.5f,
            area = Enums.Area.Midfielder,
            color = Enums.ShirtColor.WHITE
        ),
        Player(
            firstName = "Carlos",
            lastName = "Soler",
            team = "Paris Saint-Germain",
            teamConst = Enums.Team.ParisSG,
            price = 9.0f,
            area = Enums.Area.Midfielder,
            color = Enums.ShirtColor.DARKBLUE
        ),
        Player(
            firstName = "David",
            lastName = "Alaba",
            team = "Real Madrid",
            teamConst = Enums.Team.RealMadrid,
            price = 14.0f,
            area = Enums.Area.Defender,
            color = Enums.ShirtColor.WHITE
        ),
        Player(
            firstName = "Andrew",
            lastName = "Robertson",
            team = "Liverpool FC",
            teamConst = Enums.Team.Liverpool,
            price = 14.0f,
            area = Enums.Area.Defender,
            color = Enums.ShirtColor.DARKRED
        ),
        Player(
            firstName = "Marc",
            lastName = "Cucurella",
            team = "Chelsea FC",
            teamConst = Enums.Team.Chelsea,
            price = 14.0f,
            area = Enums.Area.Defender,
            color = Enums.ShirtColor.DARKBLUE
        ),
        Player(
            firstName = "",
            lastName = "Bremer",
            team = "Juventus FC",
            teamConst = Enums.Team.Juventus,
            price = 10.0f,
            area = Enums.Area.Defender,
            color = Enums.ShirtColor.WHITE
        ),
        Player(
            firstName = "Benoit",
            lastName = "Badiashile",
            team = "Chelsea FC",
            teamConst = Enums.Team.Chelsea,
            price = 10.0f,
            area = Enums.Area.Defender,
            color = Enums.ShirtColor.DARKBLUE
        ),
        Player(
            firstName = "Raheem",
            lastName = "Sterling",
            team = "Chelsea FC",
            teamConst = Enums.Team.Chelsea,
            price = 17.5f,
            area = Enums.Area.Striker,
            color = Enums.ShirtColor.DARKBLUE
        ),
        Player(
            firstName = "Gabriel",
            lastName = "Martinelli",
            team = "Arsenal",
            teamConst = Enums.Team.Arsenal,
            price = 15.0f,
            area = Enums.Area.Striker,
            color = Enums.ShirtColor.LIGHTRED
        ),
        Player(
            firstName = "Giovanni",
            lastName = "Reyna",
            team = "Borussia Dortmund",
            teamConst = Enums.Team.BorussiaDort,
            price = 9.0f,
            area = Enums.Area.Midfielder,
            color = Enums.ShirtColor.YELLOW
        ),
        Player(
            firstName = "Hakan",
            lastName = "Calhanoglu",
            team = "Inter Milan",
            teamConst = Enums.Team.InterMilan,
            price = 9.0f,
            area = Enums.Area.Midfielder,
            color = Enums.ShirtColor.DARKBLUE
        ),
        Player(
            firstName = "Nathan",
            lastName = "Aké",
            team = "Manchester City",
            teamConst = Enums.Team.ManCity,
            price = 7.5f,
            area = Enums.Area.Defender,
            color = Enums.ShirtColor.LIGHTBLUE
        ),
        Player(
            firstName = "Harry",
            lastName = "Maguire",
            team = "Manchester United",
            teamConst = Enums.Team.ManUnited,
            price = 7.5f,
            area = Enums.Area.Defender,
            color = Enums.ShirtColor.DARKRED
        ),
        Player(
            firstName = "Yann",
            lastName = "Sommer",
            team = "Bayern Munich",
            teamConst = Enums.Team.BayernMunich,
            price = 1.5f,
            area = Enums.Area.Goalkeeper,
            color = Enums.ShirtColor.LIGHTRED
        ),
        Player(
            firstName = "Caaron",
            lastName = "Ooi",
            team = "Arsenal",
            teamConst = Enums.Team.Arsenal,
            price = 5.0f,
            area = Enums.Area.Midfielder,
            color = Enums.ShirtColor.LIGHTRED
        ),
        Player(
            firstName = "Matt",
            lastName = "Turner",
            team = "Arsenal",
            teamConst = Enums.Team.Arsenal,
            price = 1.5f,
            area = Enums.Area.Goalkeeper,
            color = Enums.ShirtColor.LIGHTRED
        )
    )

    fun removePlayer(fanPlayerId: Int) {
        viewModelScope.launch {
            teamRepo.deletePlayer(fanPlayerId)
        }
    }

    fun createPlayer() {
        viewModelScope.launch {
            players2.forEach {
                playerRepo.createPlayer(it)
            }
        }
    }

    fun updateBudget(teamId: Int, budget: Float) {
        viewModelScope.launch {
            teamRepo.updateBudget(teamId, budget)
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

//    fun getUserWithTeam(userId: Int) {
//        viewModelScope.launch {
//            val res = userRepo.getUserWithTeam(userId)
//            res?.let {
//                userTeam.value = it
//            }
//        }
//    }
}