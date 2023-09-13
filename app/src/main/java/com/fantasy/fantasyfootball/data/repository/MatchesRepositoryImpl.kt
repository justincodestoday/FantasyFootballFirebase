package com.fantasy.fantasyfootball.data.repository

import android.util.Log
import com.fantasy.fantasyfootball.data.model.Matches
import com.fantasy.fantasyfootball.data.service.FootballApiService
import com.fantasy.fantasyfootball.domain.repository.MatchesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchesRepositoryImpl(private val api: FootballApiService) : MatchesRepository {
    override suspend fun getAllMatches(callback: (List<Matches>) -> Unit) {
        val response = api.getAllMatches()
        response.enqueue(object : Callback<List<Matches>> {
            override fun onResponse(call: Call<List<Matches>>, response: Response<List<Matches>>) {
                if (response.isSuccessful) {
                    val matches: List<Matches> = response.body() as List<Matches>
                    callback(matches)
                }
            }

            override fun onFailure(call: Call<List<Matches>>, t: Throwable) {
                val errorMessage = "API request failed: ${t.message}"
                Log.e("API Error", errorMessage)
            }

        })
    }
}