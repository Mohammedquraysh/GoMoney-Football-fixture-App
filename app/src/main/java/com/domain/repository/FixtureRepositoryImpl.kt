package com.domain.repository

import com.data.local.dao.CompetitionDao
import com.data.local.dao.FixtureDao
import com.data.remote.api.FootballApiService
import com.data.remote.dto.fixturedto.MatchDto
import com.domain.model.Fixture
import com.util.Resource
import com.util.toDomain
import com.util.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FixtureRepositoryImpl @Inject constructor(
    private val apiService: FootballApiService,
    private val fixtureDao: FixtureDao,
) : FixtureRepository {

    override fun getFixtures(): Flow<Resource<List<Fixture>>> = flow {
        emit(Resource.Loading())

        val localFixtures = fixtureDao.getAllFixtures().first()
        if (localFixtures.isNotEmpty()) {
            emit(Resource.Success(localFixtures.map { it.toDomain() }))
        }

        try {

            val response = apiService.getFixtures(
                dateFrom = getCurrentDate(),
                dateTo = getDateAfterDays(3)
            )
            val fixtures = response.matches.map { it.toEntity() }
            fixtureDao.insertFixtures(fixtures)

            emit(Resource.Success(fixtures.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun getMatchById(id: Int): Flow<Resource<MatchDto>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apiService.getMatchById(id)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error occurred"))
            }
        }
    }


    override fun getFixturesByCompetition(competitionId: Int): Flow<Resource<List<Fixture>>> = flow {
        emit(Resource.Loading())

        try {
            val localFixtures = fixtureDao.getFixturesByCompetition(competitionId).first()
            if (localFixtures.isNotEmpty()) {
                emit(Resource.Success(localFixtures.map { it.toDomain() }))
            }

            val response = apiService.getFixtures(competitions = competitionId.toString())
            val fixtures = response.matches.map { it.toEntity() }

            fixtureDao.deleteFixturesByCompetition(competitionId)
            fixtureDao.insertFixtures(fixtures)

            emit(Resource.Success(fixtures.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun refreshFixtures() {
        try {
            val response = apiService.getFixtures(
                dateFrom = getCurrentDate(),
                dateTo = getDateAfterDays(3)
            )

            val fixtures = response.matches.map { it.toEntity() }
            fixtureDao.insertFixtures(fixtures)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getFixtureById(id: Int): Fixture? {
        return fixtureDao.getFixtureById(id)?.toDomain()
    }

    private fun getCurrentDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    private fun getDateAfterDays(days: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, days)
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
    }
}