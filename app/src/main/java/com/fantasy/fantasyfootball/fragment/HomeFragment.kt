package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.databinding.ActivityMainBinding
import com.fantasy.fantasyfootball.databinding.FragmentHomeBinding
import com.fantasy.fantasyfootball.databinding.FragmentLoginBinding
import com.fantasy.fantasyfootball.viewModel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
//    private val userViewModel: UserViewModel by activityViewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.visibility = View.VISIBLE
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

<<<<<<< HEAD
        binding.btnPickTeam.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToPickTeamFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    private fun setFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.navHostFragment, fragment)
            commit()
=======
        val navController = findNavController()
//        userViewModel.user.observe(viewLifecycleOwner, Observer {user->
//            if (user != null) {
//                showWelcomeMessage()
//            } else {
//                navController.navigate(R.id.credentialsFragment)
//            }
//        })
        binding.btnPickTeam.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToPickTeamFragment()
            NavHostFragment.findNavController(this).navigate(action)
>>>>>>> f2b08e679c9ffd4548156a083531967740ce5de1
        }
    }

    private fun showWelcomeMessage() {
    }
}