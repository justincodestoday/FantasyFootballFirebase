package com.fantasy.fantasyfootball.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class User(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val image: String? = null,
    val team: Team = Team()
    //    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
//    val image: ByteArray? = null,
)

