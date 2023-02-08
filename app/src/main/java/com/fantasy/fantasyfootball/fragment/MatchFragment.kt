package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
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
import com.fantasy.fantasyfootball.databinding.FragmentMatchBinding
import com.fantasy.fantasyfootball.util.AuthService
import com.fantasy.fantasyfootball.viewModel.MatchViewModel

class MatchFragment : Fragment() {
    private lateinit var adapter: MatchAdapter
    private lateinit var binding: FragmentMatchBinding
    private lateinit var authService: AuthService
    private val viewModel: MatchViewModel by viewModels {
        MatchViewModel.Provider(
            (requireContext().applicationContext as MainApplication).matchRepo,
            (requireContext().applicationContext as MainApplication).teamRepo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authService = AuthService.getInstance(requireActivity().applicationContext)
        val user = authService.getAuthenticatedUser()

        if (user != null) {
            viewModel.getTeamWithPlayers(user.userId!!)
        }

        var teamId = 0
        var teamPoints = 0
        var list: MutableList<FantasyPlayer> = mutableListOf()
        viewModel.teamPlayer.observe(viewLifecycleOwner) {
            teamId = it.team.teamId!!
            teamPoints = it.team.points
            it.players.forEach { player ->
                list.add(player)
            }

            viewModel.getMatches()
            viewModel.matches.observe(viewLifecycleOwner) { matches ->
                adapter.setMatch(matches)
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        adapter = MatchAdapter(emptyList()) { match ->
            if (match.homeScore > match.awayScore) {
                val bundle = Bundle()
                bundle.putBoolean(Enums.Result.REFRESH.name, true)
                setFragmentResult(Enums.Result.COLLECTED_POINTS.name, bundle)
                val matchingPlayers =
                    list.filter { player -> player.teamConst == match.homeTeam }.size
                val points = matchingPlayers * match.homeScore * 2
                val totalPoints = teamPoints + points
                viewModel.updatePoints(teamId, totalPoints)
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
                val points = matchingPlayers * match.awayScore * 2
                val totalPoints = teamPoints + points
                viewModel.updatePoints(teamId, totalPoints)
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
        binding.rvMatches.adapter = adapter
        binding.rvMatches.layoutManager = layoutManager
    }
}