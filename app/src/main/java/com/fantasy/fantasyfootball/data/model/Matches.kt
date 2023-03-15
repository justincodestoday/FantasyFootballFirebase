package com.fantasy.fantasyfootball.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fantasy.fantasyfootball.constant.Enums

//data class Matches(
//    val matchId: String? = null,
//    val homeTeam: Enums.Team,
//    val awayTeam: Enums.Team,
//    val homeScore: Int,
//    val awayScore: Int,
//    val date: String,
//)

data class Matches(
    var matchId: String = "",
    var homeTeam: Enums.Team? = null,
    var awayTeam: Enums.Team? = null,
    var homeScore: Int? = null,
    var awayScore: Int? = null,
    var date: String? = null
)
// {
//    constructor() : this("", null, null, null, null, null)
//}