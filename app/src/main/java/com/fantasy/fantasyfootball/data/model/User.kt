package com.fantasy.fantasyfootball.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int? = null,
    var name: String? = null,
    var username: String? = null,
    var password: String? = null,
//    val teamName: String? = null,
//    var points: Int = 0,
//    var remainingBudget: Float = 0f,
//    var lastUpdated: Date? = null,
)
