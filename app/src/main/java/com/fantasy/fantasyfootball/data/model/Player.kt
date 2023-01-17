package com.fantasy.fantasyfootball.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fantasy.fantasyfootball.constant.Enums

@Entity
data class Player(
    @PrimaryKey
    val playerId: Int? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var name: String = firstName + lastName,
    var price: Float = 0f,
    var area: Enums.Area? = null,
    var position: Enums.Position? = null,
    var color: Enums.ShirtColor? = null,
    var isSet: Boolean = false,

    val ownerId: Int? = null
)
