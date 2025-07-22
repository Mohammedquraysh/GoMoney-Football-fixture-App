package com.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fixtures")
data class FixtureEntity(
    @PrimaryKey val id: Int,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamCrest: String?,
    val awayTeamCrest: String?,
    val utcDate: String,
    val status: String,
    val matchday: Int?,
    val stage: String?,
    val lastUpdated: String,
    val competition: String,
    val competitionId: Int,
    val season: Int,
    val homeScore: Int?,
    val awayScore: Int?
)
