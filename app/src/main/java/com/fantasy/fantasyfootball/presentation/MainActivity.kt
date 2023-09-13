package com.fantasy.fantasyfootball.presentation

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.bumptech.glide.Glide
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.core.Enums
import com.fantasy.fantasyfootball.databinding.ActivityMainBinding
import com.fantasy.fantasyfootball.databinding.DrawerHeaderBinding
import com.fantasy.fantasyfootball.data.service.ImageStorageService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var headerView: View
    private lateinit var headerBinding: DrawerHeaderBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getCurrentUser()

        viewModel.nameLiveData.observe(this) { name ->
            headerBinding.tvFullName.text = name
        }

        viewModel.emailLiveData.observe(this) { email ->
            headerBinding.tvEmail.text = email
        }

        viewModel.imageLiveData.observe(this) { imageName ->
            if (imageName != null) {
                ImageStorageService.getImageUri(imageName) { uri ->
                    Glide.with(this.applicationContext)
                        .load(uri)
                        .placeholder(R.drawable.vector__3_)
                        .into(headerBinding.ivImage)
                }
            } else {
                Glide.with(this.applicationContext)
                    .load(R.drawable.vector__3_)
                    .into(headerBinding.ivImage)
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val binding =
//            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        headerView = binding.navigationView.getHeaderView(0)
        headerBinding = DrawerHeaderBinding.bind(headerView)

        headerBinding.ivClose.setOnClickListener {
            drawerLayout.closeDrawers()
        }

        val menuView = binding.navigationView.menu
        val menuProfile = menuView.findItem(R.id.profileFragment)
        val menuFixture = menuView.findItem(R.id.matchFragment)
        val s = SpannableString(menuProfile.title)
        val ss = SpannableString(menuFixture.title)
        s.setSpan(AbsoluteSizeSpan(55), 0, s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        menuProfile.title = s
        ss.setSpan(AbsoluteSizeSpan(55), 0, ss.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        menuFixture.title = ss

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

//        viewModel.user.observe(this) {
////            authenticate(it, graph)
//            if (it == null) {
//                navController.popBackStack(R.id.main_nav_graph, true)
//                navController.navigate(R.id.credentialsFragment)
//            } else {
////                identify()
//                navController.popBackStack(R.id.main_nav_graph, true)
//                navController.navigate(R.id.homeFragment)
//                headerBinding.tvFullName.text = it.name
//                headerBinding.tvEmail.text = it.email
//
//                if (it.image != null) {
//                    it.image.let { imageName ->
//                        ImageStorageService.getImageUri(imageName) { uri ->
//                            Glide.with(this.applicationContext)
//                                .load(uri)
//                                .placeholder(R.drawable.vector__3_)
//                                .into(headerBinding.ivImage)
//                        }
//                    }
//                } else {
//                    Glide.with(this.applicationContext)
//                        .load(R.drawable.vector__3_)
//                        .into(headerBinding.ivImage)
//                }
//            }
//        }

        viewModel.user.observe(this) { user ->
            if (user == null) {
                navController.popBackStack(R.id.main_nav_graph, true)
                navController.navigate(R.id.credentialsFragment)
            } else {
                navController.popBackStack(R.id.main_nav_graph, true)
                navController.navigate(R.id.homeFragment)
                getData()
            }
        }

        binding.btnLogoutDrawer.setOnClickListener {
            viewModel.logout()
            Toast.makeText(
                this,
                applicationContext.getString(R.string.logout_successful),
                Toast.LENGTH_SHORT
            ).show()

            navController.popBackStack(R.id.main_nav_graph, true)
            navController.navigate(R.id.credentialsFragment)
            drawerLayout.close()
        }

        navController.setGraph(graph, savedInstanceState)
    }

//    private fun authenticate(user: User?, graph: NavGraph) {
//        if (user != null) {
//            graph.setStartDestination(R.id.homeFragment)
//        } else {
//            graph.setStartDestination(R.id.credentialsFragment)
//        }
//    }

    fun identify() {
        viewModel.getCurrentUser()
    }

    fun getData() {
        viewModel.updateNameData()
        viewModel.updateEmailData()
        viewModel.updateImageData()
    }

    fun navigate(destination: String) {
        when (destination) {
            Enums.Fragment.Team.name -> {
                val item = binding.bottomNav.menu.findItem(R.id.teamManagementFragment)
                NavigationUI.onNavDestinationSelected(item, navController)
            }
            Enums.Fragment.Leaderboard.name -> {
                val item = binding.bottomNav.menu.findItem(R.id.leaderboardFragment)
                NavigationUI.onNavDestinationSelected(item, navController)
            }
            Enums.Fragment.Profile.name -> {
                val item = binding.navigationView.menu.findItem(R.id.profileFragment)
                NavigationUI.onNavDestinationSelected(item, navController)
            }
            Enums.Fragment.Match.name -> {
                val item = binding.navigationView.menu.findItem(R.id.matchFragment)
                NavigationUI.onNavDestinationSelected(item, navController)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}