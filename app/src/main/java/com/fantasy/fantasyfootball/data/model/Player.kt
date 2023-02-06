package com.fantasy.fantasyfootball.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fantasy.fantasyfootball.constant.Enums

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true) val playerId: Int? = null,
    val teamOwnerId: Int? = null,
    var firstName: String,
    var lastName: String,
    var team: String,
    var teamConst: Enums.Team,
    var price: Float = 0f,
    var area: Enums.Area,
//    var position: Enums.Position,
    var color: Enums.ShirtColor,
//    var isSet: Boolean = false,
) {
    val name: String
        get() = "$firstName $lastName"
}
