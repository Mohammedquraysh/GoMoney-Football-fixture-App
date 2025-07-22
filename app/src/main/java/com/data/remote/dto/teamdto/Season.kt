package com.data.remote.dto.teamdto

data class Season(
    val id: Int,
    val startDate: String,
    val endDate: String,
    val currentMatchday: Int?,
    val winner: Team?
)

