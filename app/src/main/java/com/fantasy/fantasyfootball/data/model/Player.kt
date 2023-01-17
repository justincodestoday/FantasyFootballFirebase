package com.fantasy.fantasyfootball.data.model

import androidx.room.PrimaryKey
import com.fantasy.fantasyfootball.constant.Enums

data class Player(
    @PrimaryKey
    val id: Int? = null,
    var firstName: String? = null,
    var surname: String? = null,
    var price: Float = 0f,
    var area: Enums.Area? = null
) {
    val name = firstName + surname
}
