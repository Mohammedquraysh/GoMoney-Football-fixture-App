package com.util

import android.os.Build
import com.data.local.entity.CompetitionEntity
import com.data.local.entity.FixtureEntity
import com.data.remote.dto.fixturedto.CompetitionDto
import com.data.remote.dto.fixturedto.MatchDto
import com.domain.model.Competition
import com.domain.model.Fixture
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun MatchDto.toEntity(): FixtureEntity {
    return FixtureEntity(
        id = id,
        homeTeam = homeTeam.name,
        awayTeam = awayTeam.name,
        homeTeamCrest = homeTeam.crest,
        awayTeamCrest = awayTeam.crest,
        utcDate = utcDate,
        status = status,
        matchday = matchday,
        stage = stage,
        lastUpdated = lastUpdated,
        competition = competition.name,
        competitionId = competition.id,
        season = season.id,
        homeScore = score.fullTime.home,
        awayScore = score.fullTime.away
    )
}


fun FixtureEntity.toDomain(): Fixture {
    return Fixture(
        id = id,
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        homeTeamCrest = homeTeamCrest,
        awayTeamCrest = awayTeamCrest,
        utcDate = utcDate,
        status = status,
        matchDay = matchday,
        stage = stage,
        competition = competition,
        competitionId = competitionId,
        homeScore = homeScore,
        awayScore = awayScore
    )
}

fun CompetitionDto.toEntity(): CompetitionEntity {
    return CompetitionEntity(
        id = id,
        name = name,
        code = code,
        type = type,
        emblem = emblem,
        currentSeason = 2025,
        lastUpdated = System.currentTimeMillis().toString()
    )
}

fun CompetitionEntity.toDomain(): Competition {
    return Competition(
        id = id,
        name = name,
        code = code,
        type = type,
        emblem = emblem
    )
}
fun String.toFormattedDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        val date = inputFormat.parse(this)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        this
    }
}

fun getCurrentYear(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now().year
    } else {
        Calendar.getInstance().get(Calendar.YEAR)
    }
}
