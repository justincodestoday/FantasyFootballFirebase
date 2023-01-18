package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
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