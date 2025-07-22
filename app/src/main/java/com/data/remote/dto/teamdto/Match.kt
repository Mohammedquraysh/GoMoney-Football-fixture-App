package com.data.remote.dto.teamdto

data class Match(
    val id: Int,
    val utcDate: String,
    val status: String,
    val matchday: Int?,
    val stage: String?,
    val group: String?,
    val lastUpdated: String,
    val homeTeam: Team,
    val awayTeam: Team,
    val score: Score,
    val goals: List<Goal>? = null,
    val bookings: List<Booking>? = null,
    val substitutions: List<Substitution>? = null,
    val statistics: List<TeamStatistics>? = null,
    val referees: List<Referee>? = null
)
