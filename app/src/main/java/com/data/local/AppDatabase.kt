package com.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.data.local.dao.CompetitionDao
import com.data.local.dao.FixtureDao
import com.data.local.entity.CompetitionEntity
import com.data.local.entity.FixtureEntity

@Database(
    entities = [FixtureEntity::class, CompetitionEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fixtureDao(): FixtureDao
    abstract fun competitionDao(): CompetitionDao
}
