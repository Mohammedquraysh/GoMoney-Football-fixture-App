package com.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competitions")
data class CompetitionEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val code: String,
    val type: String,
    val emblem: String?,
    val currentSeason: Int,
    val lastUpdated: String
)
