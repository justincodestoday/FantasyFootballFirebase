package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.model.FantasyPlayer

interface FantasyRepository {
    suspend fun createPlayer(fantasyPlayer: FantasyPlayer)
}