package com.fantasy.fantasyfootball.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int? = null,
    val name: String? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val image: String? = null
//    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
//    val image: ByteArray? = null,
)

