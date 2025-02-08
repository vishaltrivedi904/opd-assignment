package com.example.khushibaby.data.worker

import android.content.Context
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.khushibaby.data.database.AppDatabase
import com.example.khushibaby.data.model.SyncStatus
import com.example.khushibaby.data.repository.VisitRepository
import com.example.khushibaby.utils.DeviceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncManager @Inject constructor(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        val visitDao= Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration() // For debugging, not recommended in production
            .build().visitDao()

        val visitRepository=VisitRepository(visitDao)

        val patientId = inputData.getLong("patient_id", -1L)

        if (patientId == -1L) {
            return Result.failure()  // Invalid Patient ID
        }

        return try {
            mySuspendFunction(visitRepository,patientId)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private suspend fun mySuspendFunction(visitRepository:VisitRepository,patientId: Long) {
        // Simulating a suspend function, e.g., a network call or database operation
        withContext(Dispatchers.IO) {
            val visits = visitRepository.getVisitsForPatient(patientId)
            val filteredVisits = visits.filter { it.syncStatus == SyncStatus.PENDING }
            val response = visitRepository.syncVisits(filteredVisits)
            if (response.isSuccess) {
                for (visit in filteredVisits) {
                    visitRepository.markVisitAsSync(visit)
                }
                Result.success() // Sync successful
            } else {
                Result.retry()
            }
        }
    }
}

