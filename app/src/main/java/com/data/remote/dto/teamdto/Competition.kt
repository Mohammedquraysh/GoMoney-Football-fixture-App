package com.data.remote.dto.teamdto


data class Competition(
    val id: Int,
    val name: String,
    val code: String,
    val type: String,
    val emblem: String?,
    val plan: String? = null,
    val numberOfAvailableSeasons: Int? = null
)
