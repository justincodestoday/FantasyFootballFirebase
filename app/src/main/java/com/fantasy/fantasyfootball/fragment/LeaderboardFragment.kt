package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.adapter.LeaderboardAdapter
import com.fantasy.fantasyfootball.data.model.Player
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.databinding.FragmentLeaderboardBinding
import com.fantasy.fantasyfootball.viewModel.LeaderboardViewModel
import com.fantasy.fantasyfootball.viewModel.PickPlayerViewModel

class LeaderboardFragment : Fragment() {
    private lateinit var adapter: LeaderboardAdapter
    private lateinit var binding: FragmentLeaderboardBinding
    private val viewModel: LeaderboardViewModel by viewModels {
        LeaderboardViewModel.Provider(
            (requireContext().applicationContext as MainApplication).userRepo,
            (requireContext().applicationContext as MainApplication).teamRepo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setupAdapter()

        viewModel.users.observe(viewLifecycleOwner) { users ->
            adapter.setUser(users)
        }

        viewModel.teams.observe(viewLifecycleOwner) { teams ->
            adapter.setTeam(teams)
        }

    }

//    fun setupAdapter() {
//        val layoutManager = LinearLayoutManager(requireContext())
//        adapter = LeaderboardAdapter(emptyList())
//        binding.rvLeaderboard.adapter = adapter
//        binding.rvLeaderboard.layoutManager = layoutManager
//    }

    companion object {
        private var leaderboardFragmentInstance: LeaderboardFragment? = null
        fun getInstance(): LeaderboardFragment {
            if (leaderboardFragmentInstance == null) {
                leaderboardFragmentInstance = LeaderboardFragment()
            }
            return leaderboardFragmentInstance!!
        }
    }
}

