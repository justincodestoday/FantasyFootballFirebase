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
import com.fantasy.fantasyfootball.databinding.FragmentRegisterBinding
import com.fantasy.fantasyfootball.viewModel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModel.Provider((requireContext().applicationContext as MainApplication).userRepo)
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
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.error.asLiveData().observe(viewLifecycleOwner) {
            val snackBar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackBar.setBackgroundTint(
                ContextCompat.getColor(requireContext(), R.color.red_500)
            )
            snackBar.setAction("Hide") {
                snackBar.dismiss()
            }
            snackBar.show()
        }

//        binding.btnRegister.setOnClickListener {
//            if (validate(viewModel.name.value!!)) {
//                viewModel.register()
//                Toast.makeText(requireContext(), "Successfully register", Toast.LENGTH_SHORT).show()
//            } else {
//                val snackBar =
//                    Snackbar.make(it, "Please enter all the values", Snackbar.LENGTH_LONG)
//                snackBar.setBackgroundTint(
//                    ContextCompat.getColor(requireContext(), R.color.red_500)
//                )
//                snackBar.setAction("Hide") {
//                    snackBar.dismiss()
//                }
//                snackBar.show()
//            }
//        }
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