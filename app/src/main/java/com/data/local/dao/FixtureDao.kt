package com.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.data.local.entity.FixtureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FixtureDao {
    @Query("SELECT * FROM fixtures ORDER BY utcDate ASC")
    fun getAllFixtures(): Flow<List<FixtureEntity>>

    @Query("SELECT * FROM fixtures WHERE competitionId = :competitionId ORDER BY utcDate ASC")
    fun getFixturesByCompetition(competitionId: Int): Flow<List<FixtureEntity>>

    @Query("SELECT * FROM fixtures WHERE id = :id")
    suspend fun getFixtureById(id: Int): FixtureEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFixtures(fixtures: List<FixtureEntity>)

    @Query("DELETE FROM fixtures WHERE competitionId = :competitionId")
    suspend fun deleteFixturesByCompetition(competitionId: Int)

    @Query("DELETE FROM fixtures")
    suspend fun deleteAllFixtures()
}