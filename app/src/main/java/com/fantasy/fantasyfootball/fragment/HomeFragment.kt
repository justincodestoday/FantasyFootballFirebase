package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.databinding.FragmentHomeBinding
import com.fantasy.fantasyfootball.util.AuthService
import com.fantasy.fantasyfootball.viewModel.RegisterViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var authService: AuthService

    //    private val userViewModel: UserViewModel by activityViewModels()
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
//        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
//        bottomNav.visibility = View.VISIBLE
//        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
//        toolbar.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("edit_profile") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                authService.getAuthenticatedUser()
            }
        }

        binding.btnPickTeam.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToPickTeamFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.profile.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.cvLeaderBoard.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToLeaderboardFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }
    }
}