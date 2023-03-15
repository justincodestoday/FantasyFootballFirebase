package com.fantasy.fantasyfootball.data.model

import com.fantasy.fantasyfootball.constant.Enums

data class FantasyPlayer(
    val fanPlayerId: Int? = null,
    val teamOwnerId: Int,
    var firstName: String,
    var lastName: String,
    var team: String,
    var teamConst: Enums.Team,
    var price: Float = 0f,
    var color: Enums.ShirtColor,
    var position: String,
    var isSet: Boolean = false
)
