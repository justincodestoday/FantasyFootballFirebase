package com.fantasy.fantasyfootball.data.model

import com.fantasy.fantasyfootball.constant.Enums

data class FantasyPlayer(
    var player: Player? = null,
    var position: Enums.Position? = null,
    var color: Enums.ShirtColor? = null,
    var isSet: Boolean = false
)
