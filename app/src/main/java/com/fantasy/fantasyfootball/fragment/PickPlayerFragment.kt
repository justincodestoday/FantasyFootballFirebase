package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.adapter.PlayerAdapter
import com.fantasy.fantasyfootball.data.model.Player
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

        binding.svSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    refresh(it)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let {
                    refresh(it)
                }
                return false
            }
        })
    }

    fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = PlayerAdapter(emptyList()) {
        }
        binding.rvPlayers.adapter = adapter
        binding.rvPlayers.layoutManager = layoutManager
    }

    fun refresh(player: String) {
        viewModel.getPlayers(player)
    }

    companion object {
        private var pickPlayerFragmentInstance: PickPlayerFragment? = null
        fun getInstance(): PickPlayerFragment {
            if (pickPlayerFragmentInstance == null) {
                pickPlayerFragmentInstance = PickPlayerFragment()
            }

            return pickPlayerFragmentInstance!!
        }
    }
}