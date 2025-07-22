package com.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.domain.repository.CompetitionRepository
import com.domain.repository.FixtureRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val fixtureRepository: FixtureRepository,
    private val competitionRepository: CompetitionRepository
) : CoroutineWorker(context, workerParams) {

    @AssistedFactory
    interface Factory {
        fun create(context: Context, params: WorkerParameters): SyncWorker
    }

    override suspend fun doWork(): Result {
        return try {
            fixtureRepository.refreshFixtures()
            competitionRepository.refreshCompetitions()
            Result.success()
        } catch (exception: Exception) {
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }
}
