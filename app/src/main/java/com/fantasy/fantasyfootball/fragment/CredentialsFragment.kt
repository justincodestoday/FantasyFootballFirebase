package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fantasy.fantasyfootball.adapter.CredentialsAdapter
import com.fantasy.fantasyfootball.databinding.FragmentCredentialsBinding
import com.fantasy.fantasyfootball.util.AuthService.Companion.getInstance

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