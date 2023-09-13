package com.fantasy.fantasyfootball.data.model

import com.fantasy.fantasyfootball.core.Enums

data class Matches(
    var matchId: String = "",
    var homeTeam: Enums.Team? = null,
    var awayTeam: Enums.Team? = null,
    var homeScore: Int? = null,
    var awayScore: Int? = null,
    var date: String? = null
)
