package com.example.khushibaby.data.repository

import com.example.animeapplication.data.api.ApiInterface
import com.example.khushibaby.data.database.VisitDao
import com.example.khushibaby.data.model.BaseResponse
import com.example.khushibaby.data.model.SyncStatus
import com.example.khushibaby.data.model.Visit
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VisitRepository @Inject constructor(private val visitDao: VisitDao) {
    suspend fun insertVisit(visitDetail: Visit) {
        visitDao.insertVisit(visitDetail)
    }

    suspend fun getVisitsForPatient(patientId: Long): List<Visit> {
        return visitDao.getVisitsForPatient(patientId)
    }

    suspend fun getVisitForPatient(visitId: Long): Visit? {
        return visitDao.getVisitById(visitId)
    }

    suspend fun markVisitAsCompleted(visit: Visit):BaseResponse {
        delay(2000)
        visitDao.markVisitAsCompleted(visit.id)
        return BaseResponse(200,"Visit Complete SuccessFully","OK")
    }

    suspend fun markVisitAsSync(visit: Visit) {
        delay(2000)
        visitDao.markSynced(visit.id,SyncStatus.SYNCED)
    }


    suspend fun syncVisits(visits: List<Visit>): BaseResponse {
        delay(200)
        return BaseResponse(200,"Sync SuccessFully","OK")
    }
}
