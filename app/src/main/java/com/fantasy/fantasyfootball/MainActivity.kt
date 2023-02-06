package com.fantasy.fantasyfootball

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
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
import com.fantasy.fantasyfootball.databinding.DrawerHeaderBinding
import com.fantasy.fantasyfootball.util.AuthService
import com.fantasy.fantasyfootball.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var headerView: View
    private lateinit var headerBinding: DrawerHeaderBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Provider((this.applicationContext as MainApplication).userRepo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        headerView = binding.navigationView.getHeaderView(0)
        headerBinding = DrawerHeaderBinding.bind(headerView)

        val authService = AuthService.getInstance(this)
        val user = authService.getAuthenticatedUser()

        if (user?.userId != null) {
            viewModel.getUserById(user.userId)
        }

        viewModel.user.observe(this) {
            Log.d("debug", "observe")
            if (it != null) {
                headerBinding.tvFullName.text = it.name
                headerBinding.tvUsername.text = it.name
                val bitmap =
                    it.image?.let { image -> BitmapFactory.decodeByteArray(image, 0, image.size) }
                headerBinding.ivImage.setImageBitmap(bitmap)
            }
        }

        headerBinding.ivClose.setOnClickListener {
            drawerLayout.closeDrawers()
        }

        val menuView = binding.navigationView.menu
        val menuProfile = menuView.findItem(R.id.profileFragment)
        val s = SpannableString(menuProfile.title)
        s.setSpan(AbsoluteSizeSpan(55), 0, s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        menuProfile.title = s

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
                R.id.teamManagementFragment
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

        authenticate(user, graph)
        navController.setGraph(graph, savedInstanceState)

        binding.btnLogoutDrawer.setOnClickListener {
            authService.unauthenticate()
            if (!authService.isAuthenticated()) {
                Toast.makeText(
                    this,
                    applicationContext.getString(R.string.logout_successful),
                    Toast.LENGTH_SHORT
                ).show()

                navController.popBackStack(R.id.main_nav_graph, true)
                navController.navigate(R.id.credentialsFragment)
                drawerLayout.close()
            }
        }
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