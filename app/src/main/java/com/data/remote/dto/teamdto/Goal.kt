package com.data.remote.dto.teamdto


data class Goal(
    val minute: Int,
    val injuryTime: Int?,
    val type: String,
    val team: Team,
    val scorer: Player,
    val assist: Player?
)
