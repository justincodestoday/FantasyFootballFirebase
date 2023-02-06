package com.fantasy.fantasyfootball.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.adapter.LeaderboardAdapter
import com.fantasy.fantasyfootball.adapter.MatchAdapter
import com.fantasy.fantasyfootball.adapter.PlayerAdapter
import com.fantasy.fantasyfootball.databinding.FragmentLeaderboardBinding
import com.fantasy.fantasyfootball.databinding.FragmentMatchBinding
import com.fantasy.fantasyfootball.databinding.FragmentPickPlayerBinding
import com.fantasy.fantasyfootball.viewModel.MatchViewModel
import com.fantasy.fantasyfootball.viewModel.PickPlayerViewModel

class MatchFragment : Fragment() {
    private lateinit var adapter: MatchAdapter
    private lateinit var binding: FragmentMatchBinding
    private val viewModel: MatchViewModel by viewModels {
        MatchViewModel.Provider((requireContext().applicationContext as MainApplication).matchRepo)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        viewModel.getMatches()
        viewModel.matches.observe(viewLifecycleOwner) { matches ->
            adapter.setMatch(matches)
        }
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = MatchAdapter(emptyList())
        binding.rvMatches.adapter = adapter
        binding.rvMatches.layoutManager = layoutManager
    }
}