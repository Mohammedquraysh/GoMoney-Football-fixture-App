package com.data.remote.dto.teamdto

data class Booking(
    val minute: Int,
    val team: Team,
    val player: Player,
    val card: String
)