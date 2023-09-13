package com.fantasy.fantasyfootball.domain.repository

import android.content.Context
import com.fantasy.fantasyfootball.data.model.Matches
import retrofit2.Callback
import retrofit2.Response

interface MatchesRepository {
//    suspend fun getAllMatches() :List<Matches>
    suspend fun getAllMatches(): Response<Any>
}