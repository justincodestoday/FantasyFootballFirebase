package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fantasy.fantasyfootball.adapter.CredentialsAdapter
import com.fantasy.fantasyfootball.databinding.FragmentCredentialsBinding


class CredentialsFragment : Fragment() {
    private lateinit var binding: FragmentCredentialsBinding
    private val loginFragment = LoginFragment.getInstance()
    private val registerFragment = RegisterFragment.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCredentialsBinding.inflate(inflater, container, false)
//        val drawer = requireActivity().findViewById<View>(R.id.drawerLayout) as DrawerLayout
//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
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