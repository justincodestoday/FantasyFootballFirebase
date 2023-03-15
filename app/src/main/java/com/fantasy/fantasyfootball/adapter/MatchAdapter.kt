package com.fantasy.fantasyfootball.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Matches
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.databinding.MatchCardBinding
import com.fantasy.fantasyfootball.util.BadgeUtil.setTeamImage
import com.fantasy.fantasyfootball.util.Utils.update

class MatchAdapter(var matches: List<Matches>, val onClick: (match: Matches) -> Unit) :
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

            match.homeTeam?.name?.let { setTeamImage(it, home) }
            match.awayTeam?.name?.let { setTeamImage(it, away) }

//            when (match.homeTeam?.name) {
//                Enums.Team.Liverpool.name -> {
//                    home.setImageResource(R.drawable.liverpool)
//                }
//                Enums.Team.ManUnited.name -> {
//                    home.setImageResource(R.drawable.manutd)
//                }
//                Enums.Team.Arsenal.name -> {
//                    home.setImageResource(R.drawable.arsenal)
//                }
//                Enums.Team.Chelsea.name -> {
//                    home.setImageResource(R.drawable.chelsea)
//                }
//            }
//
//            when (match.awayTeam?.name) {
//                Enums.Team.Liverpool.name -> {
//                    away.setImageResource(R.drawable.liverpool)
//                }
//                Enums.Team.ManUnited.name -> {
//                    away.setImageResource(R.drawable.manutd)
//                }
//                Enums.Team.Arsenal.name -> {
//                    away.setImageResource(R.drawable.arsenal)
//                }
//                Enums.Team.Chelsea.name -> {
//                    away.setImageResource(R.drawable.chelsea)
//                }
//            }

            cvMatchCard.setOnClickListener {
                onClick(match)
            }
        }
    }

    override fun getItemCount() = matches.size

    fun setMatch(matches: List<Matches>) {
        this.matches = matches
        notifyDataSetChanged()
//        val oldItems = this.matches
//        this.matches = matches as MutableList<Matches>
//        if (oldItems.isEmpty()) {
//            update(emptyList(), matches) { user1, user2 ->
//                user1.id == user2.id
//            }
//        } else {
//            update(oldItems, matches) { user1, user2 ->
//                user1.id == user2.id
//            }
//        }
    }

    class MatchHolder(val binding: MatchCardBinding) : RecyclerView.ViewHolder(binding.root)
}
