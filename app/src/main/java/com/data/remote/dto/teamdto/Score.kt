package com.data.remote.dto.teamdto


data class Score(
    val winner: String?,
    val duration: String,
    val fullTime: ScoreDetail,
    val halfTime: ScoreDetail,
    val regularTime: ScoreDetail? = null,
    val extraTime: ScoreDetail? = null,
    val penalties: ScoreDetail? = null
)

