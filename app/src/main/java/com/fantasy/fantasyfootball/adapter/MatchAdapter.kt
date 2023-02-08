package com.fantasy.fantasyfootball.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.fantasyfootball.data.model.Matches
import com.fantasy.fantasyfootball.databinding.MatchCardBinding

class MatchAdapter(var matches: List<Matches>):
    RecyclerView.Adapter<MatchAdapter.MatchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchHolder {
        val binding = MatchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchHolder, position: Int) {
        val match = matches[position]
        holder.binding.run {
            tvHomeTeam.text = match.homeTeam.toString()
            tvAwayTeam.text = match.awayTeam.toString()
            tvHomeScore.text = match.homeScore.toString()
            tvAwayScore.text = match.awayScore.toString()
            tvDate.text = match.date
            when (match.homeTeam.name) {
                "Arsenal" -> {
                    arsenalHome.isVisible = true
                }
                "Chelsea" -> {
                    chelseaHome.isVisible = true
                }
                "Liverpool" -> {
                    liverpoolHome.isVisible = true
                }
                "ManUnited" -> {
                    manutdHome.isVisible = true
                }
            }

            when (match.awayTeam.name) {
                "Arsenal" -> {
                    arsenalAway.isVisible = true
                }
                "Chelsea" -> {
                    chelseaAway.isVisible = true
                }
                "Liverpool" -> {
                    liverpoolAway.isVisible = true
                }
                "ManUnited" -> {
                    manutdAway.isVisible = true
                }
            }
            cvMatchCard.setOnClickListener {
            }
        }
    }

    override fun getItemCount() = matches.size

    fun setMatch(matches: List<Matches>) {
        this.matches = matches
        notifyDataSetChanged()
    }

    class MatchHolder(val binding: MatchCardBinding) : RecyclerView.ViewHolder(binding.root)
}
