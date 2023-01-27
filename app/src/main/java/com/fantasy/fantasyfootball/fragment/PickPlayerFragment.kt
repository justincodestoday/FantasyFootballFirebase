package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.adapter.PlayerAdapter
import com.fantasy.fantasyfootball.databinding.FragmentPickPlayerBinding
import com.fantasy.fantasyfootball.viewModel.PickPlayerViewModel

class PickPlayerFragment : Fragment() {
    private lateinit var adapter: PlayerAdapter
    private lateinit var binding: FragmentPickPlayerBinding
    private val viewModel: PickPlayerViewModel by viewModels {
        PickPlayerViewModel.Provider((requireContext().applicationContext as MainApplication).playerRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPickPlayerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()


        viewModel.players.observe(viewLifecycleOwner) { players ->
            adapter.setPlayer(players)
        }
    }

    fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = PlayerAdapter(emptyList()) {
        }
        binding.rvPlayers.adapter = adapter
        binding.rvPlayers.layoutManager = layoutManager
    }

    companion object {
        private var completedWordsFragmentInstance: PickPlayerFragment? = null
        fun getInstance(): PickPlayerFragment {
            if (completedWordsFragmentInstance == null) {
                completedWordsFragmentInstance = PickPlayerFragment()
            }

            return completedWordsFragmentInstance!!
        }
    }
}