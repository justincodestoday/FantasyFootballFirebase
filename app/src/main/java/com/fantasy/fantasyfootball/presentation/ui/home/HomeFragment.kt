package com.fantasy.fantasyfootball.presentation.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.fantasy.fantasyfootball.presentation.MainActivity
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.core.Enums
import com.fantasy.fantasyfootball.databinding.FragmentHomeBinding
import com.fantasy.fantasyfootball.databinding.HowToPlayDialogBinding
import com.fantasy.fantasyfootball.data.service.ImageStorageService
import com.fantasy.fantasyfootball.presentation.ui.base.BaseFragment
import com.fantasy.fantasyfootball.presentation.ui.home.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_home
    private lateinit var howToPlayDialogBinding: HowToPlayDialogBinding

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        setFragmentResults()

        binding?.swiperefresh?.setOnRefreshListener {
            viewModel.getCurrentUser()
            binding?.swiperefresh?.isRefreshing = false
        }

        howToPlayDialogBinding = HowToPlayDialogBinding.inflate(layoutInflater)
        val howToPlayDialog = Dialog(requireContext(), R.style.Custom_AlertDialog)
        binding?.btnHowToPlay?.setOnClickListener {
            howToPlayDialog.setContentView(howToPlayDialogBinding.root)
            howToPlayDialog.setCancelable(true)
            howToPlayDialog.show()
        }
        howToPlayDialogBinding.ivClose.setOnClickListener {
            howToPlayDialog.dismiss()
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding?.apply {
                if (user != null) {
                    points.text = "${user.team.points} points"
                    tvProfile.text = user.team.name
                    user.image?.let { imageName ->
                        ImageStorageService.getImageUri(imageName) { uri ->
                            Glide.with(root).load(uri).placeholder(R.drawable.vector__3_)
                                .into(ivProfile)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.teamManagement.collect {
                navigateToTeamManagement()
            }
        }

        lifecycleScope.launch {
            viewModel.leaderboard.collect {
                navigateToLeaderboard()
            }
        }

        lifecycleScope.launch {
            viewModel.profile.collect {
                navigateToProfile()
            }
        }

        lifecycleScope.launch {
            viewModel.fixtures.collect {
                navigateToFixtures()
            }
        }

        lifecycleScope.launch {
            viewModel.logout.collect {
                navigateToLogin()
            }
        }
    }

    private fun navigateToTeamManagement() {
        (activity as MainActivity).navigate(Enums.Fragment.Team.name)
    }

    private fun navigateToLeaderboard() {
        (activity as MainActivity).navigate(Enums.Fragment.Leaderboard.name)
    }

    private fun navigateToProfile() {
        (activity as MainActivity).navigate(Enums.Fragment.Profile.name)
    }

    private fun navigateToFixtures() {
        (activity as MainActivity).navigate(Enums.Fragment.Match.name)
    }

    private fun navigateToLogin() {
        navController.popBackStack(R.id.main_nav_graph, true)
        navController.navigate(R.id.credentialsFragment)
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