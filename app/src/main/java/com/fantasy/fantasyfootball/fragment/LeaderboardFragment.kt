package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.adapter.LeaderboardAdapter
import com.fantasy.fantasyfootball.databinding.FragmentLeaderboardBinding
import com.fantasy.fantasyfootball.viewModel.LeaderboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LeaderboardFragment : BaseFragment<FragmentLeaderboardBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_leaderboard
    override val viewModel: LeaderboardViewModel by viewModels()
    private lateinit var adapter: LeaderboardAdapter

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        setupAdapter()

//        binding?.swiperefresh?.setOnRefreshListener {
//            viewModel.getUsers()
//            binding?.swiperefresh?.isRefreshing = false
//        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        viewModel.users.observe(viewLifecycleOwner) { users ->
            adapter.setLeaderboard(users)
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect {
                if (it) {
                    binding?.progress?.visibility = View.VISIBLE
                } else {
                    binding?.progress?.visibility = View.GONE
                }
            }
        }
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = LeaderboardAdapter(emptyList())
        binding?.rvLeaderboard?.adapter = adapter
        binding?.rvLeaderboard?.layoutManager = layoutManager
    }
}