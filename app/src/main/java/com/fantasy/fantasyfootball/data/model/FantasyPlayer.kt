package com.fantasy.fantasyfootball.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.fantasy.fantasyfootball.constant.Enums

@Entity(
    foreignKeys = [ForeignKey(
        entity = Team::class,
        parentColumns = ["teamId"],
        childColumns = ["teamOwnerId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class FantasyPlayer(
    @PrimaryKey(autoGenerate = true)
    val fanPlayerId: Int? = null,
    val teamOwnerId: Int,
    var firstName: String,
    var lastName: String,
    var team: String,
    var teamConst: Enums.Team,
    var price: Float = 0f,
    var color: Enums.ShirtColor,
    var position: String,
    var isSet: Boolean = false
)
