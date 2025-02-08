package com.example.khushibaby.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.khushibaby.data.model.SyncStatus
import com.example.khushibaby.data.model.Visit

@Dao
interface VisitDao {
    @Insert
    suspend fun insertVisit(visitDetail: Visit)


    @Query("SELECT * FROM visits WHERE id = :visitId")
    suspend fun getVisitById(visitId: Long): Visit?

    @Query("SELECT * FROM visits WHERE patientId = :patientId")
    suspend fun getVisitsForPatient(patientId: Long): List<Visit>


    @Query("UPDATE visits SET isCompleted = 1 WHERE id = :visitId")
    suspend fun markVisitAsCompleted(visitId: Long)

    @Query("UPDATE visits SET syncStatus = :status WHERE id = :visitId")
    suspend fun markSynced(visitId: Long, status: SyncStatus)
}