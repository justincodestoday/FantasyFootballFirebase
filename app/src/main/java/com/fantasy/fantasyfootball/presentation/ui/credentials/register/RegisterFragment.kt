package com.fantasy.fantasyfootball.presentation.ui.credentials.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.databinding.FragmentRegisterBinding
import com.fantasy.fantasyfootball.presentation.ui.base.BaseFragment
import com.fantasy.fantasyfootball.presentation.ui.credentials.CredentialsFragmentDirections
import com.fantasy.fantasyfootball.presentation.ui.credentials.register.viewModel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override val viewModel: RegisterViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_register

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        binding?.btnRegister?.setOnClickListener {
            lifecycleScope.launch {
                viewModel.register()
            }
        }
    }

    // after register, the app thinks it is logged in after closing app
    override fun onBindData(view: View) {
        super.onBindData(view)

        lifecycleScope.launch {
            viewModel.register.collect {
                val action =
                    CredentialsFragmentDirections.actionCredentialsFragmentToOptionalFragment(
                        binding?.etEmail?.text.toString()
                    )
                navController.navigate(action)

                binding?.run {
                    etName.text?.clear()
                    etEmail.text?.clear()
                    etPassword.text?.clear()
                    etPasswordConfirm.text?.clear()
                }
            }
        }
    }

    companion object {
        private var registerFragmentInstance: RegisterFragment? = null

        fun getInstance(): RegisterFragment {
            if (registerFragmentInstance == null) {
                registerFragmentInstance = RegisterFragment()
            }
            return registerFragmentInstance!!
        }
    }
}