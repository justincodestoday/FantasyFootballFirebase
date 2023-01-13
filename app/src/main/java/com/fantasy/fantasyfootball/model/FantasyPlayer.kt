package com.fantasy.fantasyfootball.model

import androidx.room.PrimaryKey
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.constant.Enums.ShirtColor


data class FantasyPlayer(
    @PrimaryKey
    val id: Int? = null,
    var player: Player? = null,
    var position: Enums.Position? = null,
    var color: ShirtColor? = null,
    var isSet: Boolean = false
)
