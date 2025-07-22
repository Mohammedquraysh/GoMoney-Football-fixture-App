package com.data.remote.dto.fixturedto

data class FixturesResponse(
    val filters: FiltersDto,
    val resultSet: ResultSetDto,
    val matches: List<MatchDto>
)

