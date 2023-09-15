package com.fantasy.fantasyfootball.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.databinding.LeaderboardBinding
import com.fantasy.fantasyfootball.util.Utils.update
import android.text.SpannableString
import android.graphics.Color
import android.text.Spannable
import android.text.style.ForegroundColorSpan


class LeaderboardAdapter(private var users: List<User>) :
    RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {
    class LeaderboardViewHolder(val binding: LeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val binding = LeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaderboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val sortedUsers = users.sortedByDescending { it.team.points }
        val user = sortedUsers[position]
        val currentPosition = position + 1

        holder.binding.run {
//            tvTeamName.text = holder.itemView.context.getString(
//                R.string.user_card_text, user.team.name, user.email
//            )
//            tvEmail.text ="${ user.team.name} "
//            tvTeamName.text = user.name
            // Your team name and user name strings
            val teamName = user.team.name
            val userName = user.name

        // Create a SpannableString for the combined text
            val spannableText = SpannableString("$teamName  |  $userName")

        // Define the text color for team name and user name
            val teamNameColor = Color.RED // Set your desired color for team name
            val userNameColor = Color.BLACK // Set your desired color for user name

        // Apply the text color spans to the appropriate parts of the text
            if (teamName != null) {
                spannableText.setSpan(
                    ForegroundColorSpan(teamNameColor),
                    0, // Start index of teamName
                    teamName.length, // End index of teamName
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            if (teamName != null) {
                spannableText.setSpan(
                    ForegroundColorSpan(userNameColor),
                    teamName.length + 5, // Start index of userName (after " | ")
                    spannableText.length, // End index of userName
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

// Set the SpannableString to your TextView
            tvTeamName.text = spannableText

            tvTeamName.isSelected = true
            tvPoints.text = holder.itemView.context.getString(R.string.user_card_points, user.team.points)
            tvRanking.text = currentPosition.toString()

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