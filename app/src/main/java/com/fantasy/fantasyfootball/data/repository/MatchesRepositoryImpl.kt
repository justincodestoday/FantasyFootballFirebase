package com.fantasy.fantasyfootball.data.repository

import android.util.Log
import com.fantasy.fantasyfootball.data.model.Matches
import com.fantasy.fantasyfootball.data.service.FootballApiService
import com.fantasy.fantasyfootball.domain.repository.MatchesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchesRepositoryImpl(private val api: FootballApiService) : MatchesRepository {
//    override suspend fun getAllMatches(): List<Matches> {
    override suspend fun getAllMatches(): Response<Any> {
        return api.getAllMatches()
    }
}