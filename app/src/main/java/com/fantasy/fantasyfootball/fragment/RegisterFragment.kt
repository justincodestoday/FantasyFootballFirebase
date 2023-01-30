package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.databinding.FragmentRegisterBinding
import com.fantasy.fantasyfootball.viewModel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModel.Provider(
            (requireContext().applicationContext as MainApplication).userRepo,
            (requireContext().applicationContext as MainApplication).teamRepo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.success.asLiveData().observe(viewLifecycleOwner) {
            val msg = enumToString(it)
            binding.run {
                etName.text?.clear()
                etTeamName.text?.clear()
                etUsername.text?.clear()
                etPassword.text?.clear()
                etPasswordConfirm.text?.clear()
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
    }

    private fun enumToString(type: String?): String? {
        return when (type) {
            Enums.FormErrors.MISSING_NAME.name -> context?.getString(R.string.missing_name)
            Enums.FormErrors.MISSING_TEAM_NAME.name -> context?.getString(R.string.missing_team_name)
            Enums.FormErrors.INVALID_USERNAME.name -> context?.getString(R.string.invalid_username)
            Enums.FormErrors.INVALID_PASSWORD.name -> context?.getString(R.string.invalid_password)
            Enums.FormErrors.PASSWORDS_NOT_MATCHING.name -> context?.getString(R.string.passwords_not_matching)
            Enums.FormSuccess.REGISTER_SUCCESSFUL.name -> context?.getString(R.string.register_successful)
            else -> context?.getString(R.string.nothing)
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