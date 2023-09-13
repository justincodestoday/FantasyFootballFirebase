package com.fantasy.fantasyfootball.data.model

import com.fantasy.fantasyfootball.core.Enums

data class Player(
    val playerId: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var team: String? = null,
    var teamConst: Enums.Team? = null,
    var price: Float = 0f,
    var area: Enums.Area? = null,
    var color: Enums.ShirtColor? = null,
) {
    val name: String
        get() = "$firstName $lastName"
}
