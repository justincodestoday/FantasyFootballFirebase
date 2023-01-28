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
import androidx.fragment.app.findFragment
import androidx.fragment.app.setFragmentResultListener
import com.fantasy.fantasyfootball.constant.Enums


class PickTeamFragment : Fragment() {
    private lateinit var binding: FragmentPickTeamBinding
    private val pickPlayerFragment = PickPlayerFragment.getInstance()
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

//        setFragmentResultListener("from_pick_player") { _, result ->
//            val refresh = result.getBoolean("refresh")
//            if (refresh) {
//                pickPlayerFragment.refresh("", "")
//            }
//        }

        binding.gk.setOnClickListener {
            val area = Enums.Area.Goalkeeper
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment(area.toString())
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.lb.setOnClickListener {
             val area = Enums.Area.Defender
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment(area.toString())
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.lcb.setOnClickListener {
             val area = Enums.Area.Defender
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment(area.toString())
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rcb.setOnClickListener {
             val area = Enums.Area.Defender
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment(area.toString())
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.lm.setOnClickListener {
             val area = Enums.Area.Midfielder
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment(area.toString())
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.lcm.setOnClickListener {
             val area = Enums.Area.Midfielder
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment(area.toString())
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rcm.setOnClickListener {
             val area = Enums.Area.Midfielder
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment(area.toString())
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rm.setOnClickListener {
             val area = Enums.Area.Midfielder
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment(area.toString())
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.ls.setOnClickListener {
             val area = Enums.Area.Striker
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment(area.toString())
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rs.setOnClickListener {
             val area = Enums.Area.Striker
            val action = PickTeamFragmentDirections.actionPickTeamFragmentToPickPlayerFragment(area.toString())
            NavHostFragment.findNavController(this).navigate(action)
        }
    }
}