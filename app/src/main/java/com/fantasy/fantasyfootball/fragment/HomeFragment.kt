package com.fantasy.fantasyfootball.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.*
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.MainActivity
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.databinding.FragmentHomeBinding
import com.fantasy.fantasyfootball.util.AuthService
import com.fantasy.fantasyfootball.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_home

//    private lateinit var authService: AuthService

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)


        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        setFragmentResults()

        // either one works
        //        authService = AuthService.getInstance(requireActivity().applicationContext)
        //        authService = AuthService.getInstance(requireContext())

//        val user = authService.getAuthenticatedUser()
//        if (user != null) {
//            viewModel.getUserWithTeam(user.userId!!)
//        }

        viewModel.getCurrentUser()

//        viewModel.userTeam.observe(viewLifecycleOwner) {
//            binding.apply {
//                points.text = it.team.points.toString() + " Points"
//                tvProfile.text = it.user.username
//                if (it.user.image != null) {
//                    val bitmap =
//                        BitmapFactory.decodeByteArray(it.user.image, 0, it.user.image.size)
//                    ivProfile.setImageBitmap(bitmap)
//                }
//            }
//        }

        viewModel.teamManagement.asLiveData().observe(viewLifecycleOwner) {
            (activity as MainActivity).navigate(Enums.Fragment.Team.name)
        }

        viewModel.profile.asLiveData().observe(viewLifecycleOwner) {
            (activity as MainActivity).navigate(Enums.Fragment.Profile.name)
        }

        viewModel.leaderboard.asLiveData().observe(viewLifecycleOwner) {
            (activity as MainActivity).navigate(Enums.Fragment.Leaderboard.name)
        }

        viewModel.fixtures.asLiveData().observe(viewLifecycleOwner) {
            (activity as MainActivity).navigate(Enums.Fragment.Match.name)
        }

        viewModel.logout.asLiveData().observe(viewLifecycleOwner) {
//            authService.unauthenticate()

            NavHostFragment.findNavController(this).popBackStack(R.id.main_nav_graph, true)
            NavHostFragment.findNavController(this).navigate(R.id.credentialsFragment)
        }

        viewModel.success.asLiveData().observe(viewLifecycleOwner) {
            val msg = enumToString(it)
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }

    }

    private fun setFragmentResults() {
        setFragmentResultListener(Enums.Result.EDIT_PROFILE_RESULT.name) { _, result ->
            val refresh = result.getBoolean(Enums.Result.REFRESH.name)
            viewModel.refreshPage(refresh)
        }

        setFragmentResultListener(Enums.Result.ADD_PLAYER_RESULT.name) { _, result ->
            val refresh = result.getBoolean(Enums.Result.REFRESH.name)
            viewModel.refreshPage(refresh)
        }

        setFragmentResultListener(Enums.Result.REMOVE_PLAYER_RESULT.name) { _, result ->
            val refresh = result.getBoolean(Enums.Result.REFRESH.name)
            viewModel.refreshPage(refresh)
        }

        setFragmentResultListener(Enums.Result.COLLECTED_POINTS.name) { _, result ->
            val refresh = result.getBoolean(Enums.Result.REFRESH.name)
            viewModel.refreshPage(refresh)
        }
    }
}