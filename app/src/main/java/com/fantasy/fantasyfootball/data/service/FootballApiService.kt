package com.fantasy.fantasyfootball.data.service

import com.fantasy.fantasyfootball.data.model.Matches
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface FootballApiService {
    @GET("/v4/matches")
//    suspend fun getAllMatches(): List<Matches>
    suspend fun getAllMatches(): Response<Any>


}