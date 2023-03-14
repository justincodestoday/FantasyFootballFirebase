package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.adapter.LeaderboardAdapter
import com.fantasy.fantasyfootball.databinding.FragmentLeaderboardBinding
import com.fantasy.fantasyfootball.viewModel.LeaderboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderboardFragment : Fragment() {
    private lateinit var adapter: LeaderboardAdapter
    private lateinit var binding: FragmentLeaderboardBinding
    private val viewModel: LeaderboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

//        viewModel.getUsersWithTeams()
//        viewModel.users.observe(viewLifecycleOwner) {users ->
//            adapter.setLeaderboard(users)
//        }
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = LeaderboardAdapter(emptyList())
        binding.rvLeaderboard.adapter = adapter
        binding.rvLeaderboard.layoutManager = layoutManager
    }
}