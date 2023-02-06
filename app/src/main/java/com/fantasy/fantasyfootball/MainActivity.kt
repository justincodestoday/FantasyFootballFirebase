package com.fantasy.fantasyfootball

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.util.Log
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
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
import com.fantasy.fantasyfootball.viewModel.ProfileViewModel
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val headerView = binding.navigationView.getHeaderView(0)
        val headerBinding = DrawerHeaderBinding.bind(headerView)

        val authService = AuthService.getInstance(this)
        val user = authService.getAuthenticatedUser()

        val name = user?.name
        val username = user?.username
//        val image = user?.image
        val headerView = binding.navigationView.getHeaderView(0)
        val closeImageView = headerView.findViewById<ImageView>(R.id.ivClose)
        closeImageView.setOnClickListener {
            drawerLayout.closeDrawers()
        }
        headerBinding.ivClose.setOnClickListener {
            drawerLayout.closeDrawers()
        }

        val menuView = binding.navigationView.menu
        val menuProfile = menuView.findItem(R.id.profileFragment)
        val s = SpannableString(menuProfile.title)
        s.setSpan(AbsoluteSizeSpan(55), 0, s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        menuProfile.title = s

//        val drawerImage = headerView.findViewById<ShapeableImageView>(R.id.drawerImage)
//        if (image != null) {
//            Log.d("qwe", "user.image is not null")
//            val bitmap =
//                BitmapFactory.decodeByteArray(image, 0, image.size)
//            drawerImage.setImageBitmap(bitmap)
//        } else {
//            drawerImage.setImageResource(R.drawable.vector__3_)
//            Log.d("qwe", "user.image is null")
//        }

        val drawerName = headerView.findViewById<TextView>(R.id.tvFullName)
        val drawerUsername = headerView.findViewById<TextView>(R.id.tvUsername)
        drawerName.text = name
        drawerUsername.text = username


//        viewModel.userTeam.observe(this) {
//            binding.apply {
//                val headerName = headerView.findViewById<TextView>(R.id.tvFullName)
//                headerName.text = it.user.name
//            }
//        }


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

        if (user != null) {
            headerBinding.tvFullName.text = user.name
            headerBinding.tvUsername.text = user.username
        }

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