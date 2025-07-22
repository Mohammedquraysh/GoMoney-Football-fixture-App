package com.di

import android.content.Context
import androidx.room.Room
import com.data.local.AppDatabase
import com.data.local.dao.CompetitionDao
import com.data.local.dao.FixtureDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "football_fixtures_db"
        ).build()
    }

    @Provides
    fun provideFixtureDao(database: AppDatabase): FixtureDao {
        return database.fixtureDao()
    }

    @Provides
    fun provideCompetitionDao(database: AppDatabase): CompetitionDao {
        return database.competitionDao()
    }
}
