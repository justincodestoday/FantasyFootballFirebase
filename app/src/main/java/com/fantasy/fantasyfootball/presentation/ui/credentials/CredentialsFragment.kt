package com.fantasy.fantasyfootball.presentation.ui.credentials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fantasy.fantasyfootball.presentation.adapter.CredentialsAdapter
import com.fantasy.fantasyfootball.databinding.FragmentCredentialsBinding
import com.fantasy.fantasyfootball.presentation.ui.credentials.login.LoginFragment
import com.fantasy.fantasyfootball.presentation.ui.credentials.register.RegisterFragment

class CredentialsFragment : Fragment() {
    private lateinit var binding: FragmentCredentialsBinding
    private val loginFragment = LoginFragment.getInstance()
    private val registerFragment = RegisterFragment.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCredentialsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CredentialsAdapter(
            listOf(loginFragment, registerFragment),
            childFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
    }
}