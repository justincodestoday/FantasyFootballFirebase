package com.fantasy.fantasyfootball

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
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
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.main_nav_graph)

//        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.leaderboardFragment,
                R.id.pickTeamFragment
            ),
            drawerLayout
        )

        binding.navigationView.setupWithNavController(navController)
        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller: NavController, destination: NavDestination, bundle: Bundle? ->
            binding.bottomNav.isVisible =
                appBarConfiguration.topLevelDestinations.contains(destination.id)
            binding.toolbar.isVisible =
                appBarConfiguration.topLevelDestinations.contains(destination.id)
        }

        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.btnLogoutDrawer.setOnClickListener {
            authService.unauthenticate()
            Toast.makeText(
                this,
                applicationContext.getString(R.string.logout_successful),
                Toast.LENGTH_SHORT
            ).show()

            finish()
            startActivity(intent)
        }

        authenticate(authService.getAuthenticatedUser(), graph)
        navController.setGraph(graph, savedInstanceState)

//        binding.navigationView.getHeaderView(0).set
    }

    private fun authenticate(user: User?, graph: NavGraph) {
        if (user != null) {
            graph.setStartDestination(R.id.homeFragment)
        } else {
            graph.setStartDestination(R.id.credentialsFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}