package com.domain.repository

import android.util.Log
import com.data.remote.api.FootballApiService
import com.data.remote.dto.teamdto.Match
import com.data.remote.dto.teamdto.Team
import com.data.remote.dto.teamdto.TeamDetails
import com.data.remote.dto.teamdto.TeamStanding
import com.google.gson.Gson
import com.util.ApiExceptionBody
import com.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FootballRepositoryImpl @Inject constructor(
    private val apiService: FootballApiService
) : FootballRepository {

    override suspend fun getStandings(competitionCode: String): Flow<Resource<List<TeamStanding>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apiService.getStandings(competitionCode)
                if (response.isSuccessful) {
                    response.body()?.let { standingsResponse ->
                        val table = standingsResponse.standings.firstOrNull()?.table
                        if (table != null) {
                            emit(Resource.Success(table))
                        } else {
                            emit(Resource.Error("No standings data available"))
                        }
                    } ?: emit(Resource.Error("No data received"))
                } else {
                    emit(Resource.Error("Error: ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error occurred"))
            }
        }
    }

    override suspend fun getTeams(competitionCode: String): Flow<Resource<List<Team>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apiService.getTeams(competitionCode)
                if (response.isSuccessful) {
                    response.body()?.let { teamsResponse ->
                        emit(Resource.Success(teamsResponse.teams))
                    } ?: emit(Resource.Error("No teams data available"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ApiExceptionBody::class.java)
                    emit(Resource.Error("Error: ${errorResponse.message}"))                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error occurred"))
            }
        }
    }

    override suspend fun getTeamDetails(teamId: Int): Flow<Resource<TeamDetails>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apiService.getTeamDetails(teamId)
                if (response.isSuccessful) {
                    response.body()?.let { teamDetails ->
                        emit(Resource.Success(teamDetails))
                    } ?: emit(Resource.Error("No team details available"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ApiExceptionBody::class.java)
                    emit(Resource.Error("Error: ${errorResponse.message}"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error occurred"))
            }
        }
    }

    override suspend fun getMatches(competitionCode: String): Flow<Resource<List<Match>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apiService.getMatches(competitionCode)
                if (response.isSuccessful) {
                    response.body()?.let { matchesResponse ->
                        emit(Resource.Success(matchesResponse.matches))
                    } ?: emit(Resource.Error("No matches data available"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ApiExceptionBody::class.java)
                    emit(Resource.Error("Error: ${errorResponse.message}"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error occurred"))
            }
        }
    }
}
