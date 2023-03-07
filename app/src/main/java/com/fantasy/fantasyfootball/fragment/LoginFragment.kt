package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.MainActivity
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.databinding.FragmentLoginBinding
import com.fantasy.fantasyfootball.util.AuthService
import com.fantasy.fantasyfootball.viewModel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
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

//        val authService = AuthService.getInstance(requireContext())

        viewModel.success.asLiveData().observe(viewLifecycleOwner) {
            val msg = enumToString(it)
            binding?.run {
                etUsername.text?.clear()
                etPassword.text?.clear()
            }
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }

        viewModel.error.asLiveData().observe(viewLifecycleOwner) {
            val msg = enumToString(it)
            val snackBar = Snackbar.make(binding!!.root, "$msg", Snackbar.LENGTH_LONG)
            snackBar.setBackgroundTint(
                ContextCompat.getColor(requireContext(), R.color.red_500)
            )
            snackBar.setAction("Hide") {
                snackBar.dismiss()
            }
            snackBar.show()
        }

//        viewModel.user.asLiveData().observe(viewLifecycleOwner) {
//            binding?.run {
//                etUsername.text?.clear()
//                etPassword.text?.clear()
//            }
//            authService.authenticate(it!!)
//            (activity as MainActivity).identify(it)
//            val action = CredentialsFragmentDirections.actionCredentialsFragmentToHomeFragment()
//            NavHostFragment.findNavController(this).navigate(action)
//        }

        viewModel.login.asLiveData().observe(viewLifecycleOwner) {
            binding?.run {
                etUsername.text?.clear()
                etPassword.text?.clear()
            }
            (activity as MainActivity).identify()
            val action = CredentialsFragmentDirections.actionCredentialsFragmentToHomeFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding?.btnLogin?.setOnClickListener {
            lifecycleScope.launch {
                viewModel.login()
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