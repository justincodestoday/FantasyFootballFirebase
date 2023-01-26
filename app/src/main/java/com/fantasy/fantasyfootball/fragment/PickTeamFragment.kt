package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.databinding.FragmentPickTeamBinding
import com.fantasy.fantasyfootball.viewModel.PickTeamViewModel
import android.R

import android.widget.TextView




class PickTeamFragment : Fragment() {
    private lateinit var binding: FragmentPickTeamBinding
    private val viewModel: PickTeamViewModel by viewModels {
        PickTeamViewModel.Provider((requireContext().applicationContext as MainApplication).playerRepo)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPickTeamBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gk.setOnClickListener {
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.lb.setOnClickListener {
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.lcb.setOnClickListener {
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rcb.setOnClickListener {
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.lm.setOnClickListener {
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.lcm.setOnClickListener {
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rcm.setOnClickListener {
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rm.setOnClickListener {
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.ls.setOnClickListener {
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rs.setOnClickListener {
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }
    }
}