package com.data.remote.dto.teamdto


data class TeamDetails(
    val id: Int,
    val name: String,
    val shortName: String?,
    val tla: String?,
    val crest: String?,
    val address: String?,
    val website: String?,
    val founded: Int?,
    val clubColors: String?,
    val venue: String?,
    val squad: List<Player>,
    val coach: Coach?
)
