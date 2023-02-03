package com.fantasy.fantasyfootball.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.fantasyfootball.data.model.Player
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.databinding.LeaderboardBinding

class LeaderboardAdapter(private var users: List<User>, private var teams: List<Team>):
    RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val binding = LeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaderboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val user = users[position]
        val team = teams[position]
        holder.binding.run {
            tvUsername.text = user.username
            tvTeamName.text = user.name
            tvPoints.text = team.points.toString() + " pts"
            tvRanking.text = "#" + (position + 1).toString()
        }
    }

    override fun getItemCount(): Int = users.size

    fun setUser(user: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    fun setTeam(team: List<Team>) {
        this.teams = teams
        notifyDataSetChanged()
    }

    class LeaderboardViewHolder(val binding: LeaderboardBinding) : RecyclerView.ViewHolder(binding.root)
}