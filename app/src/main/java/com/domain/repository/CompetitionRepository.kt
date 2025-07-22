package com.domain.repository

import com.domain.model.Competition
import com.util.Resource
import kotlinx.coroutines.flow.Flow

interface CompetitionRepository {
    fun getCompetitions(): Flow<Resource<List<Competition>>>
    suspend fun refreshCompetitions()
}