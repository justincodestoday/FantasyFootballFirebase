package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.databinding.FragmentLoginBinding
import com.fantasy.fantasyfootball.util.AuthService
import com.fantasy.fantasyfootball.viewModel.LoginViewModel
import com.fantasy.fantasyfootball.viewModel.RegisterViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModel.Provider(
            (requireContext().applicationContext as MainApplication).userRepo
        )
    }
    private lateinit var savedStateHandle: SavedStateHandle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
//        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
//        bottomNav.visibility = View.GONE
//        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
//        toolbar.visibility = View.GONE
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authService = AuthService.getInstance(requireContext())

        viewModel.success.asLiveData().observe(viewLifecycleOwner) {
            val msg = enumToString(it)
            binding.run {
                etUsername.text?.clear()
                etPassword.text?.clear()
            }
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }

        viewModel.error.asLiveData().observe(viewLifecycleOwner) {
            val msg = enumToString(it)
            val snackBar = Snackbar.make(binding.root, "$msg", Snackbar.LENGTH_LONG)
            snackBar.setBackgroundTint(
                ContextCompat.getColor(requireContext(), R.color.red_500)
            )
            snackBar.setAction("Hide") {
                snackBar.dismiss()
            }
            snackBar.show()
        }

        viewModel.userLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                authService.authenticate(it)
                val action = CredentialsFragmentDirections.actionCredentialsFragmentToHomeFragment()
                NavHostFragment.findNavController(this).navigate(action)
                Toast.makeText(
                    requireContext(),
                    context?.getString(R.string.login_successful),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val snackBar = Snackbar.make(
                    binding.root,
                    requireContext().getString(R.string.wrong_credentials),
                    Snackbar.LENGTH_LONG
                )
                snackBar.setBackgroundTint(
                    ContextCompat.getColor(requireContext(), R.color.red_500)
                )
                snackBar.setAction("Hide") {
                    snackBar.dismiss()
                }
                snackBar.show()
            }
        }
    }

//    private fun setFragment(fragment: Fragment) {
//        requireActivity().supportFragmentManager.beginTransaction().apply {
//            replace(R.id.navHostFragment, fragment)
//            commit()
//        }
//        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
//        savedStateHandle.set(LOGIN_SUCCESSFUL, false)
//    }

    private fun enumToString(type: String?): String? {
        return when (type) {
            Enums.FormErrors.EMPTY_FIELD.name -> context?.getString(R.string.empty_field)
            Enums.FormErrors.INVALID_USERNAME.name -> context?.getString(R.string.no_user)
            Enums.FormErrors.INVALID_PASSWORD.name -> context?.getString(R.string.incorrect_password)
            Enums.FormErrors.WRONG_CREDENTIALS.name -> context?.getString(R.string.wrong_credentials)
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