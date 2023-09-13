package com.fantasy.fantasyfootball.data.service

import com.fantasy.fantasyfootball.data.model.Matches
import retrofit2.Call
import retrofit2.http.GET

interface FootballApiService {
    @GET("matches")
    fun getAllMatches(): Call<List<Matches>>
}