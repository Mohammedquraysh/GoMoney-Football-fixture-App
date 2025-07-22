package com.data.remote.dto.teamdto

data class StandingsResponse(
    val competition: Competition,
    val season: Season,
    val standings: List<Standing>
)
