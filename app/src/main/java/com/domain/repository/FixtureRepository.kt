package com.domain.repository

import com.data.remote.dto.fixturedto.MatchDto
import com.domain.model.Fixture
import com.util.Resource
import kotlinx.coroutines.flow.Flow

interface FixtureRepository {
    fun getFixtures(): Flow<Resource<List<Fixture>>>
    suspend fun getMatchById(id: Int): Flow<Resource<MatchDto>>
    fun getFixturesByCompetition(competitionId: Int): Flow<Resource<List<Fixture>>>
    suspend fun refreshFixtures()
    suspend fun getFixtureById(id: Int): Fixture?
}