package com.domain.repository

import com.data.remote.dto.teamdto.Match
import com.data.remote.dto.teamdto.Team
import com.data.remote.dto.teamdto.TeamDetails
import com.data.remote.dto.teamdto.TeamStanding
import com.util.Resource
import kotlinx.coroutines.flow.Flow

interface FootballRepository {
    suspend fun getStandings(competitionCode: String): Flow<Resource<List<TeamStanding>>>
    suspend fun getTeams(competitionCode: String): Flow<Resource<List<Team>>>
    suspend fun getTeamDetails(teamId: Int): Flow<Resource<TeamDetails>>
    suspend fun getMatches(competitionCode: String): Flow<Resource<List<Match>>>
}