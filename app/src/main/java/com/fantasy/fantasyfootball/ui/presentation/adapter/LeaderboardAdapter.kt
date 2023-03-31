package com.fantasy.fantasyfootball.ui.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.databinding.LeaderboardBinding
import com.fantasy.fantasyfootball.util.Utils.update

class LeaderboardAdapter(private var users: List<User>) :
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
            tvEmail.text ="${ user.team.name} |"
            tvTeamName.text = user.email
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

    fun setLeaderboard(users: List<User>) {
        val oldItems = this.users
        this.users = users as MutableList<User>
        update(oldItems, users) { user1, user2 ->
            user1.id == user2.id
        }
    }
}