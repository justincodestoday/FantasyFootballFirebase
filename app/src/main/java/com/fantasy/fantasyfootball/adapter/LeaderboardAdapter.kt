package com.fantasy.fantasyfootball.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import com.fantasy.fantasyfootball.databinding.LeaderboardBinding

class LeaderboardAdapter(var users: List<UserWithTeam>) :
    RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {
    class LeaderboardViewHolder(val binding: LeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val binding = LeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaderboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val user = users[position]
        holder.binding.run {
            tvUsername.text = user.user.username
            tvTeamName.text = "@${user.team.name}"
            tvPoints.text = user.team.points.toString() + " pts"
            tvRanking.text = (position + 1).toString()
            if (position == 0) {
                trophy.visibility = View.VISIBLE
            } else {
                trophy.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setLeaderboard(users: List<UserWithTeam>) {
        this.users = users
        notifyDataSetChanged()
    }
}