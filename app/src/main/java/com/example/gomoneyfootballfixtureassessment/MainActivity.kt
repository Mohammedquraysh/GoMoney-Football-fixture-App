package com.example.gomoneyfootballfixtureassessment

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.domain.model.Competition
import com.example.gomoneyfootballfixtureassessment.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.presentation.ui.bottomsheet.CompetitionsBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomAppBar = binding.nav
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        /**Bottom Navigation Implementation Visibility and all*/
         bottomNav = findViewById(R.id.nav_view)
        bottomNav.itemBackgroundResource = R.color.transparent
        NavigationUI.setupWithNavController(bottomNav, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fixturesFragment -> {
                    bottomNav.visibility = View.VISIBLE
                    bottomAppBar.visibility = View.VISIBLE
                }
                R.id.competitionMainFragment -> {
                    bottomNav.visibility = View.VISIBLE
                    bottomAppBar.visibility = View.VISIBLE
                }
                else -> {
                    bottomNav.visibility = View.GONE
                    bottomAppBar.visibility = View.GONE
                }
            }
        }

        /** to set listener to bottom Navigation */
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.fixturesFragment)
                    true
                }
                R.id.navigation_table -> {
                    showCompetitionsBottomSheet()
                    false /** Return false to prevent automatic navigation **/
                }
                else -> {
                    NavigationUI.onNavDestinationSelected(item, navController)
                }
            }
        }
    }

    private fun showCompetitionsBottomSheet() {
        val bottomSheet = CompetitionsBottomSheetFragment.newInstance(
            object : CompetitionsBottomSheetFragment.OnCompetitionSelectedListener {
                override fun onCompetitionSelected(competition: Competition?) {

                    /** Navigate to table fragment with selected competition ID **/
                    navigateToTableFragment(competition?.id.toString(), competition?.name.toString(), competition?.name)
                }
            }
        )
        bottomSheet.show(supportFragmentManager, "CompetitionsBottomSheet")
    }

    private fun navigateToTableFragment(competitionId: String?, competitionName: String?, season: String?) {
        val bundle = Bundle().apply {
            putString("competition_id", competitionId)
            putString("competition_name", getCompetitionName(competitionName))
            putString("season", getCompetitionSeason(season))

        }
        try {
            navController.navigate(R.id.competitionMainFragment, bundle)

        } catch (e: IllegalArgumentException) {
            /** Handle navigation error when destination does not exist **/
            navController.navigate(R.id.competitionMainFragment, bundle)
        }
    }

    private fun getCompetitionName(competitionId: String?): String {
        return competitionId ?: "All Competitions"
    }
    private fun getCompetitionSeason(season: String?): String {
        return season ?: "All Competitions"
    }

}