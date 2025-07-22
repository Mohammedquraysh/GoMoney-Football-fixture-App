package com.data.remote.dto.teamdto


data class TeamsResponse(
    val count: Int,
    val competition: Competition,
    val season: Season,
    val teams: List<Team>
)
