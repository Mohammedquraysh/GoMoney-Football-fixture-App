package com.data.remote.api

import com.data.remote.dto.competitions_response.CompetitionsResponse
import com.data.remote.dto.fixturedto.FixturesResponse
import com.data.remote.dto.fixturedto.MatchDto
import com.data.remote.dto.teamdto.MatchesResponse
import com.data.remote.dto.teamdto.StandingsResponse
import com.data.remote.dto.teamdto.TeamDetails
import com.data.remote.dto.teamdto.TeamsResponse
import com.util.getCurrentYear
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FootballApiService {
    @GET("matches")
    suspend fun getFixtures(
        @Query("competitions") competitions: String? = null,
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("status") status: String? = null,
        @Query("season") season: String? = getCurrentYear().toString()
    ): FixturesResponse

    @GET("competitions")
    suspend fun getCompetitions(): CompetitionsResponse

    @GET("matches/{id}")
    suspend fun getMatchById(@Path("id") id: Int): MatchDto


    @GET("competitions/{code}/standings")
    suspend fun getStandings(
        @Path("code") competitionCode: String
    ): Response<StandingsResponse>

    @GET("competitions/{code}/teams")
    suspend fun getTeams(
        @Path("code") competitionCode: String
    ): Response<TeamsResponse>

    @GET("teams/{id}")
    suspend fun getTeamDetails(
        @Path("id") teamId: Int
    ): Response<TeamDetails>

    @GET("competitions/{code}/matches")
    suspend fun getMatches(
        @Path("code") competitionCode: String,
        @Query("status") status: String? = null,
        @Query("matchday") matchday: Int? = null,
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null
    ): Response<MatchesResponse>

}