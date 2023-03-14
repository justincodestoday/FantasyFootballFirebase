package com.fantasy.fantasyfootball.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fantasy.fantasyfootball.constant.Enums

data class Player(
    val playerId: Int? = null,
    var firstName: String,
    var lastName: String,
    var team: String,
    var teamConst: Enums.Team,
    var price: Float = 0f,
    var area: Enums.Area,
    var color: Enums.ShirtColor,
) {
    val name: String
        get() = "$firstName $lastName"
}
