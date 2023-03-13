package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.databinding.FragmentRegisterBinding
import com.fantasy.fantasyfootball.viewModel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
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

//        viewModel.success.asLiveData().observe(viewLifecycleOwner) {
//            val msg = enumToString(it)
//            binding?.run {
//                etName.text?.clear()
////                etTeamName.text?.clear()
////                etUsername.text?.clear()
//                etEmail.text?.clear()
//                etPassword.text?.clear()
//                etPasswordConfirm.text?.clear()
//            }
//            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
//        }
//
//        viewModel.error.asLiveData().observe(viewLifecycleOwner) {
//            val msg = enumToString(it)
//            val snackBar = Snackbar.make(binding!!.root, "$msg", Snackbar.LENGTH_LONG)
//            snackBar.setBackgroundTint(
//                ContextCompat.getColor(requireContext(), R.color.red_500)
//            )
//            snackBar.setAction("Hide") {
//                snackBar.dismiss()
//            }
//            snackBar.show()
//        }

        binding?.btnRegister?.setOnClickListener {
            lifecycleScope.launch {
                viewModel.register()
            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        lifecycleScope.launch {
            viewModel.success.collect {
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