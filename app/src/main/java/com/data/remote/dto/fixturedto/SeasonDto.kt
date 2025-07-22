package com.data.remote.dto.fixturedto

data class SeasonDto(
    val id: Int,
    val startDate: String,
    val endDate: String,
    val currentMatchday: Int?
)