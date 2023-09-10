package com.fantasy.fantasyfootball.presentation.ui.credentials.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fantasy.fantasyfootball.presentation.MainActivity
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.databinding.FragmentLoginBinding
import com.fantasy.fantasyfootball.presentation.ui.base.BaseFragment
import com.fantasy.fantasyfootball.presentation.ui.credentials.CredentialsFragmentDirections
import com.fantasy.fantasyfootball.presentation.ui.credentials.login.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel: LoginViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_login

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        binding?.btnLogin?.setOnClickListener {
            lifecycleScope.launch {
                viewModel.login()
            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        lifecycleScope.launch {
            viewModel.login.collect {
                binding?.run {
                    etEmail.text?.clear()
                    etPassword.text?.clear()
                }

                (activity as MainActivity).identify()
                val action =
                    CredentialsFragmentDirections.actionCredentialsFragmentToHomeFragment()
                navController.navigate(action)
            }
        }
    }

    companion object {
        private var loginFragmentInstance: LoginFragment? = null

        fun getInstance(): LoginFragment {
            if (loginFragmentInstance == null) {
                loginFragmentInstance = LoginFragment()
            }
            return loginFragmentInstance!!
        }
    }
}