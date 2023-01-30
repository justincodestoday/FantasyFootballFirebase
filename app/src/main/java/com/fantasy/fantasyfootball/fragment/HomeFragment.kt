package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPickTeam.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToPickTeamFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.profile.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }
    }
}