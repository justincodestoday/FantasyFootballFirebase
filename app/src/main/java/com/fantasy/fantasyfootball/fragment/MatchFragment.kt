package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.adapter.MatchAdapter
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.FantasyPlayer
import com.fantasy.fantasyfootball.data.model.Player
import com.fantasy.fantasyfootball.databinding.FragmentMatchBinding
import com.fantasy.fantasyfootball.service.AuthService
import com.fantasy.fantasyfootball.viewModel.MatchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MatchFragment : BaseFragment<FragmentMatchBinding>() {
    private lateinit var adapter: MatchAdapter
    override fun getLayoutResource(): Int = R.layout.fragment_match
    override val viewModel: MatchViewModel by viewModels()

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        var teamId = 0
        var teamPoints = 0
        var list: MutableList<FantasyPlayer> = mutableListOf()

        viewModel.matches.observe(viewLifecycleOwner) { matches ->
            if (matches != null) {
                adapter.setMatch(matches)
            }
        }

        viewModel.user.observe(viewLifecycleOwner) {
//            teamId = it.teamId!!
            teamPoints = it.team.points
            it.team.players.forEach { player ->
                list.add(player)
            }

            viewModel.getMatches()

        }

        val layoutManager = LinearLayoutManager(requireContext())
        adapter = MatchAdapter(emptyList()) { match ->
            if (match.homeScore!! > match.awayScore!!) {
                val bundle = Bundle()
                bundle.putBoolean(Enums.Result.REFRESH.name, true)
                setFragmentResult(Enums.Result.COLLECTED_POINTS.name, bundle)
                val matchingPlayers =
                    list.filter { player -> player.teamConst == match.homeTeam }.size
                val points = matchingPlayers * match.homeScore!! * 2
            Log.d("debugging", list.toString())
                val totalPoints = teamPoints + points
                viewModel.updatePoints(totalPoints)
                NavHostFragment.findNavController(this).popBackStack()
                context?.let { context ->
                    Toast.makeText(
                        requireContext(),
                        String.format(context.getString(R.string.collected_points), points),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                val bundle = Bundle()
                bundle.putBoolean(Enums.Result.REFRESH.name, true)
                setFragmentResult(Enums.Result.COLLECTED_POINTS.name, bundle)
                val matchingPlayers =
                    list.filter { player -> player.teamConst == match.awayTeam }.size
                val points = matchingPlayers * match.awayScore!! * 2
                val totalPoints = teamPoints + points
                viewModel.updatePoints(totalPoints)
                NavHostFragment.findNavController(this).popBackStack()
                context?.let { context ->
                    Toast.makeText(
                        requireContext(),
                        String.format(context.getString(R.string.collected_points), points),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
        binding?.rvMatches?.adapter = adapter
        binding?.rvMatches?.layoutManager = layoutManager
    }
}