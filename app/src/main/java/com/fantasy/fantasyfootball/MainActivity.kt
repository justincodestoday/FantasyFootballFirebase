package com.fantasy.fantasyfootball

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
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

        val authService = AuthService.getInstance(this)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        drawerLayout = binding.drawerLayout
        navController = findNavController(R.id.navHostFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
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


//        binding.bottomNav.visibility = View.VISIBLE
//        binding.toolbar.visibility = View.VISIBLE

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