package com.fantasy.fantasyfootball.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.fantasyfootball.core.Enums
import com.fantasy.fantasyfootball.data.model.Matches
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

            when (match.homeTeam) {
                Enums.Team.ManUnited -> tvHomeTeam.text = "Man United"
                Enums.Team.Liverpool -> tvHomeTeam.text = "Liverpool"
                Enums.Team.Arsenal -> tvHomeTeam.text = "Arsenal"
                Enums.Team.Chelsea -> tvHomeTeam.text = "Chelsea"
                Enums.Team.ManCity -> tvHomeTeam.text = "Man City"
                Enums.Team.Newcastle -> tvHomeTeam.text = "Newcastle"
                Enums.Team.Barcelona -> tvHomeTeam.text = "Barcelona"
                Enums.Team.RealMadrid -> tvHomeTeam.text = "Real Madrid"
                Enums.Team.BorussiaDort -> tvHomeTeam.text = "Dortmund"
                Enums.Team.BayernMunich -> tvHomeTeam.text = "Bayern"
                Enums.Team.ACMilan -> tvHomeTeam.text = "AC Milan"
                Enums.Team.InterMilan -> tvHomeTeam.text = "Inter Milan"
                // Add more cases for other teams as needed
                else -> tvHomeTeam.text = match.homeTeam.toString()
            }

            when (match.awayTeam) {
                Enums.Team.ManUnited -> tvAwayTeam.text = "Man United"
                Enums.Team.Liverpool -> tvAwayTeam.text = "Liverpool"
                Enums.Team.Chelsea -> tvAwayTeam.text = "Chelsea"
                Enums.Team.Arsenal -> tvAwayTeam.text = "Arsenal"
                Enums.Team.ManCity -> tvAwayTeam.text = "Man City"
                Enums.Team.Newcastle -> tvAwayTeam.text = "Newcastle"
                Enums.Team.Barcelona -> tvAwayTeam.text = "Barcelona"
                Enums.Team.RealMadrid -> tvAwayTeam.text = "Real Madrid"
                Enums.Team.BorussiaDort -> tvAwayTeam.text = "Dortmund"
                Enums.Team.BayernMunich -> tvAwayTeam.text = "Bayern"
                Enums.Team.ACMilan -> tvAwayTeam.text = "AC Milan"
                Enums.Team.InterMilan -> tvAwayTeam.text = "Inter Milan"
                // Add more cases for other teams as needed
                else -> tvAwayTeam.text = match.awayTeam.toString()
            }

            tvHomeScore.text = match.homeScore.toString()
            tvAwayScore.text = match.awayScore.toString()
            tvDate.text = match.date

            match.homeTeam?.name?.let { setTeamImage(it, home) }
            match.awayTeam?.name?.let { setTeamImage(it, away) }


            cvMatchCard.setOnClickListener {
                onClick(match)
            }
        }
    }

    override fun getItemCount() = matches.size

    fun setMatch(matches: List<Matches>) {
        val oldItems = this.matches
        this.matches = matches as MutableList<Matches>
        update(oldItems, matches) { match1, match2 ->
            match1.matchId == match2.matchId
        }
    }

    class MatchHolder(val binding: MatchCardBinding) : RecyclerView.ViewHolder(binding.root)
}
