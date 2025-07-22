package com.data.remote.dto.teamdto


data class Player(
    val id: Int,
    val name: String,
    val position: String?,
    val dateOfBirth: String?,
    val nationality: String?,
    val jerseyNumber: Int? = null,
    val section: String? = null
)