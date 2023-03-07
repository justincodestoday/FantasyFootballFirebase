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
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.databinding.FragmentLoginBinding
import com.fantasy.fantasyfootball.util.AuthService
import com.fantasy.fantasyfootball.viewModel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
//    private lateinit var binding: FragmentLoginBinding
    override val viewModel: LoginViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_login

//    private val viewModel: LoginViewModel by viewModels {
//        LoginViewModel.Provider(
//            (requireContext().applicationContext as MainApplication).userRepo
//        )
//    }

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

        viewModel.user.asLiveData().observe(viewLifecycleOwner) {
            binding?.run {
                etUsername.text?.clear()
                etPassword.text?.clear()
            }
//            authService.authenticate(it!!)
            (activity as MainActivity).identify(it)
            val action = CredentialsFragmentDirections.actionCredentialsFragmentToHomeFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding?.btnLogin?.setOnClickListener {
            lifecycleScope.launch {
                viewModel.login()
            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentLoginBinding.inflate(layoutInflater)
//        binding.viewModel = viewModel
//        binding.lifecycleOwner = viewLifecycleOwner
//        return binding.root
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val authService = AuthService.getInstance(requireContext())
//
//        viewModel.success.asLiveData().observe(viewLifecycleOwner) {
//            val msg = enumToString(it)
//            binding.run {
//                etUsername.text?.clear()
//                etPassword.text?.clear()
//            }
//            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
//        }
//
//        viewModel.error.asLiveData().observe(viewLifecycleOwner) {
//            val msg = enumToString(it)
//            val snackBar = Snackbar.make(binding.root, "$msg", Snackbar.LENGTH_LONG)
//            snackBar.setBackgroundTint(
//                ContextCompat.getColor(requireContext(), R.color.red_500)
//            )
//            snackBar.setAction("Hide") {
//                snackBar.dismiss()
//            }
//            snackBar.show()
//        }
//
//        viewModel.user.asLiveData().observe(viewLifecycleOwner) {
//            binding.run {
//                etUsername.text?.clear()
//                etPassword.text?.clear()
//            }
//            authService.authenticate(it!!)
//            (activity as MainActivity).identify(it)
//            val action = CredentialsFragmentDirections.actionCredentialsFragmentToHomeFragment()
//            NavHostFragment.findNavController(this).navigate(action)
//        }
//
//        binding.btnLogin.setOnClickListener {
//            lifecycleScope.launch {
//                viewModel.login()
//            }
//        }
//    }

    private fun enumToString(type: String?): String? {
        return when (type) {
            Enums.FormError.EMPTY_FIELD.name -> context?.getString(R.string.empty_field)
            Enums.FormError.INVALID_USERNAME.name -> context?.getString(R.string.no_user)
            Enums.FormError.INVALID_PASSWORD.name -> context?.getString(R.string.incorrect_password)
            Enums.FormError.WRONG_CREDENTIALS.name -> context?.getString(R.string.wrong_credentials)
            Enums.FormSuccess.LOGIN_SUCCESSFUL.name -> context?.getString(R.string.login_successful)
            else -> context?.getString(R.string.nothing)
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