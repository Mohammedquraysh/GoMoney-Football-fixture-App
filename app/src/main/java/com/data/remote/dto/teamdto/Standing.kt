package com.data.remote.dto.teamdto

data class Standing(
    val stage: String,
    val type: String,
    val group: String?,
    val table: List<TeamStanding>
)

