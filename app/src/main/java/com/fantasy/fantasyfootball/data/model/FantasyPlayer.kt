package com.fantasy.fantasyfootball.data.model

import com.fantasy.fantasyfootball.constant.Enums

data class FantasyPlayer(
    val fanPlayerId: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var team: String? = null,
    var teamConst: Enums.Team? = null,
    var price: Float = 0f,
    var color: Enums.ShirtColor? = null,
    var position: String? = null,
    var isSet: Boolean = false
)
