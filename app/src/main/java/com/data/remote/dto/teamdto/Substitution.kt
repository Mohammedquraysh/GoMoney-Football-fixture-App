package com.data.remote.dto.teamdto


data class Substitution(
    val minute: Int,
    val team: Team,
    val playerOut: Player,
    val playerIn: Player
)
