package com.domain.model

data class Fixture(
    val id: Int,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamCrest: String?,
    val awayTeamCrest: String?,
    val utcDate: String,
    val status: String,
    val matchDay: Int?,
    val stage: String?,
    val competition: String,
    val competitionId: Int,
    val homeScore: Int?,
    val awayScore: Int?
)
