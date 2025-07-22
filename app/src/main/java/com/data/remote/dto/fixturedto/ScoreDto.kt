package com.data.remote.dto.fixturedto

data class ScoreDto(
    val winner: String?,
    val duration: String,
    val fullTime: TimeScoreDto,
    val halfTime: TimeScoreDto
)
