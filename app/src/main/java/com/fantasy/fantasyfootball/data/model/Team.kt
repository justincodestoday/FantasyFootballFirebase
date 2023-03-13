package com.fantasy.fantasyfootball.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity
    (
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["ownerId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class Team(
    @PrimaryKey(autoGenerate = true)
    val teamId: Int? = null,
    var name: String? = null,
    var points: Int = 0,
    var budget: Float = 100.0f,
    var lastUpdated: String? = null,
    val ownerId: Int? = null,
)