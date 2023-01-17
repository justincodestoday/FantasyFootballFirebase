package com.fantasy.fantasyfootball.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fantasy.fantasyfootball.constant.Enums

@Entity
data class Player(
    @PrimaryKey
    val id: Int? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var price: Float = 0f,
    var area: Enums.Area? = null
) {
    val name = firstName + lastName
}
