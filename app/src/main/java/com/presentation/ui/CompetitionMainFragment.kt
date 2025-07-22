package com.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gomoneyfootballfixtureassessment.MainActivity
import com.example.gomoneyfootballfixtureassessment.R
import com.example.gomoneyfootballfixtureassessment.databinding.FragmentCompetitionMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.presentation.adapter.CompetitionPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompetitionMainFragment : Fragment() {
    private var _binding: FragmentCompetitionMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPagerAdapter: CompetitionPagerAdapter
    private var competitionId: String? = null
    private var competitionName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCompetitionMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            competitionId = it.getString("competition_id")
            competitionName = it.getString("competition_name")
        }
        setupToolbar()
        setupViewPager()
        setupTabs()

    }

    private fun setupToolbar() {
        with(binding) {
            textCompetitionTitle.text = competitionName
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupViewPager() {
        viewPagerAdapter = CompetitionPagerAdapter(
            this,
            competitionId.toString(),
            competitionName.toString()
        )
        binding.viewPager.adapter = viewPagerAdapter
    }


    private fun setupTabs() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Table"
                1 -> "Fixtures"
                2 -> "Teams"
                else -> "Tab $position"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}