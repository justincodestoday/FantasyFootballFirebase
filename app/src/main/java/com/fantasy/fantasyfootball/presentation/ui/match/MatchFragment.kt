package com.fantasy.fantasyfootball.presentation.ui.match

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.presentation.adapter.MatchAdapter
import com.fantasy.fantasyfootball.core.Enums
import com.fantasy.fantasyfootball.data.model.FantasyPlayer
import com.fantasy.fantasyfootball.databinding.FragmentMatchBinding
import com.fantasy.fantasyfootball.presentation.ui.base.BaseFragment
import com.fantasy.fantasyfootball.presentation.ui.match.viewModel.MatchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchFragment : BaseFragment<FragmentMatchBinding>() {
    private lateinit var adapter: MatchAdapter
    override fun getLayoutResource(): Int = R.layout.fragment_match
    override val viewModel: MatchViewModel by viewModels()

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        viewModel.fetchCurrentUser()

        var teamPoints = 0
        val list: MutableList<FantasyPlayer> = mutableListOf()

        viewModel.matches.observe(viewLifecycleOwner) { matches ->
            if (matches != null) {
                adapter.setMatch(matches)
            }
        }

        viewModel.user.observe(viewLifecycleOwner) {
            teamPoints = it.team.points
            it.team.players.forEach { player ->
                list.add(player)
            }
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

        binding?.swiperefresh?.setOnRefreshListener {
            viewModel.fetchCurrentUser()
            binding?.swiperefresh?.isRefreshing = false
        }
    }
}