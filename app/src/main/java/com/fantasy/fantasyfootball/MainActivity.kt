package com.fantasy.fantasyfootball

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.databinding.ActivityMainBinding
import com.fantasy.fantasyfootball.util.AuthService

class MainActivity : AppCompatActivity() {
    private lateinit var filePickerLauncher: ActivityResultLauncher<String>
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val authService = AuthService.getInstance(this)

        setSupportActionBar(binding.toolbar)
        drawerLayout = binding.drawerLayout
        navController = findNavController(R.id.navHostFragment)
//        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.leaderboardFragment,
                R.id.pickTeamFragment
            ),
            drawerLayout
        )
        navController.addOnDestinationChangedListener { controller: NavController, destination: NavDestination, bundle: Bundle? ->
            binding.bottomNav.isVisible =
                appBarConfiguration.topLevelDestinations.contains(destination.id)
            binding.toolbar.isVisible =
                appBarConfiguration.topLevelDestinations.contains(destination.id)

        }

        binding.navigationView.setupWithNavController(navController)
        binding.bottomNav.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.btnLogoutDrawer.setOnClickListener {
            authService.unauthenticate()
            Toast.makeText(
                this,
                applicationContext.getString(R.string.logout_successful),
                Toast.LENGTH_SHORT
            ).show()

            recreate()
        }

        //        val user = authService.getAuthenticatedUser()
        authenticate(authService.getAuthenticatedUser())

//        binding.navigationView.getHeaderView(0).set
    }

    private fun authenticate(user: User?) {
        if (user != null) {
            navController.navigate(R.id.homeFragment)
        } else {
            navController.navigate(R.id.credentialsFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}