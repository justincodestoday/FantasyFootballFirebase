package com.fantasy.fantasyfootball.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

data class Team(
    var name: String? = null,
    var points: Int = 0,
    var budget: Float = 100.0f,
    var lastUpdated: String? = null,
    val players: List<FantasyPlayer> = listOf()
)