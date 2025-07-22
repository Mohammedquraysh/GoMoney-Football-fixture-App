package com.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.presentation.ui.teams_fixture.TeamsFixtureFragment
import com.presentation.ui.league.LeagueTableFragment
import com.presentation.ui.teams.TeamsFragment

class CompetitionPagerAdapter(
    fragment: Fragment,
    private val competitionId: String,
    private val competitionName: String
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LeagueTableFragment.newInstance(competitionId)
            1 -> TeamsFixtureFragment.newInstance(competitionId)
            2 -> TeamsFragment.newInstance(competitionId)
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}