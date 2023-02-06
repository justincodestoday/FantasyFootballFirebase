package com.fantasy.fantasyfootball.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fantasy.fantasyfootball.constant.Enums

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true)
    val playerId: Int? = null,
    var firstName: String,
    var lastName: String,
    var team: Enums.Team,
    var price: Float = 0f,
    var area: Enums.Area,
    var position: Enums.Position,
    var color: Enums.ShirtColor,
    var isSet: Boolean = false,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray? = null,
) {
    val name: String
        get() = "$firstName $lastName"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        if (playerId != other.playerId) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (name != other.name) return false
        if (team != other.team) return false
        if (price != other.price) return false
        if (area != other.area) return false
        if (position != other.position) return false
        if (color != other.color) return false
        if (isSet != other.isSet) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = playerId ?: 0
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + name.hashCode()
        result = 31 * result + team.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + (area?.hashCode() ?: 0)
        result = 31 * result + (position?.hashCode() ?: 0)
        result = 31 * result + (color?.hashCode() ?: 0)
        result = 31 * result + isSet.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}
