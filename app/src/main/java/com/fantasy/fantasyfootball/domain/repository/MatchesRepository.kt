package com.fantasy.fantasyfootball.domain.repository

import android.content.Context
import com.fantasy.fantasyfootball.data.model.Matches
import retrofit2.Callback

interface MatchesRepository {
    suspend fun getAllMatches(callback: (List<Matches>) -> Unit)
}