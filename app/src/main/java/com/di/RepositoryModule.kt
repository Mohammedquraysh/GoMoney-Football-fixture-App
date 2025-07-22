package com.di

import com.domain.repository.CompetitionRepository
import com.domain.repository.CompetitionRepositoryImpl
import com.domain.repository.FixtureRepository
import com.domain.repository.FixtureRepositoryImpl
import com.domain.repository.FootballRepository
import com.domain.repository.FootballRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindFixtureRepository(
        fixtureRepositoryImpl: FixtureRepositoryImpl
    ): FixtureRepository

    @Binds
    abstract fun bindCompetitionRepository(
        competitionRepositoryImpl: CompetitionRepositoryImpl
    ): CompetitionRepository

    @Binds
    abstract fun bindFootBallRepository(
        footballRepositoryImpl: FootballRepositoryImpl
    ): FootballRepository
}