package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.databinding.FragmentOptionalBinding
import com.fantasy.fantasyfootball.viewModel.OptionalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OptionalFragment : BaseFragment<FragmentOptionalBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_optional
    override val viewModel: OptionalViewModel by viewModels()

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        binding?.btnSave?.setOnClickListener {
            lifecycleScope.launch {
                viewModel.registerTeam()
            }
        }

        binding?.btnSkip?.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        lifecycleScope.launch {
            viewModel.navigate.collect {
                binding?.run {
                    etTeamName.text?.clear()
                }
                navController.popBackStack()
            }
        }
    }
}