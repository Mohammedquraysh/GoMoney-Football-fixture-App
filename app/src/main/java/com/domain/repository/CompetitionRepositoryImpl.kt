package com.domain.repository

import com.data.local.dao.CompetitionDao
import com.data.remote.api.FootballApiService
import com.domain.model.Competition
import com.util.Resource
import com.util.toDomain
import com.util.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompetitionRepositoryImpl @Inject constructor(
    private val apiService: FootballApiService,
    private val competitionDao: CompetitionDao
) : CompetitionRepository {

    override fun getCompetitions(): Flow<Resource<List<Competition>>> = flow {
        emit(Resource.Loading())

        val localCompetitions = competitionDao.getAllCompetitions().first()
        if (localCompetitions.isNotEmpty()) {
            emit(Resource.Success(localCompetitions.map { it.toDomain() }))
        }

        try {
            val response = apiService.getCompetitions()
            val competitions = response.competitions.map { it.toEntity() }

            competitionDao.insertCompetitions(competitions)
            emit(Resource.Success(competitions.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun refreshCompetitions() {
        try {
            val response = apiService.getCompetitions()
            val competitions = response.competitions.map { it.toEntity() }
            competitionDao.insertCompetitions(competitions)
        } catch (e: Exception) {
            throw e
        }
    }
}