package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.*
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.databinding.FragmentHomeBinding
import com.fantasy.fantasyfootball.util.AuthService
import com.fantasy.fantasyfootball.viewModel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Provider(
            (requireContext().applicationContext as MainApplication).userRepo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authService = AuthService.getInstance(requireContext())
        val user = authService.getAuthenticatedUser()

        viewModel.teamManagement.asLiveData().observe(viewLifecycleOwner) {
            val action = HomeFragmentDirections.actionHomeFragmentToPickTeamFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        viewModel.profile.asLiveData().observe(viewLifecycleOwner) {
            val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        viewModel.logout.asLiveData().observe(viewLifecycleOwner) {
            authService.unauthenticate()
            NavHostFragment.findNavController(this).popBackStack(R.id.main_nav_graph, true)
            NavHostFragment.findNavController(this).navigate(R.id.credentialsFragment)
            val msg = enumToString(it)
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }

        setFragmentResults()
    }

    private fun setFragmentResults() {
        setFragmentResultListener(Enums.Result.EDIT_PROFILE_RESULT.name) { _, result ->
            val refresh = result.getBoolean(Enums.Result.REFRESH.name)
            viewModel.refreshPage(refresh)
        }
    }

        private fun enumToString(type: String?): String? {
        return when (type) {
            Enums.FormSuccess.LOGOUT_SUCCESSFUL.name -> context?.getString(R.string.logout_successful)
            else -> context?.getString(R.string.nothing)
        }
    }
}