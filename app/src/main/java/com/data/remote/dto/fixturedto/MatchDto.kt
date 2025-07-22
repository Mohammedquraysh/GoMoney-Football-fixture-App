package com.data.remote.dto.fixturedto

data class MatchDto(
    val id: Int,
    val utcDate: String,
    val status: String,
    val matchday: Int?,
    val stage: String?,
    val lastUpdated: String,
    val homeTeam: TeamDto,
    val awayTeam: TeamDto,
    val score: ScoreDto,
    val competition: CompetitionDto,
    val season: SeasonDto
)