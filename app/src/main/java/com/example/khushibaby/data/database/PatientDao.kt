package com.example.khushibaby.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.khushibaby.data.model.Patient

@Dao
interface PatientDao {
    @Insert
    suspend fun insertPatient(patient: Patient)

    @Query("SELECT * FROM patients WHERE id = :id LIMIT 1")
    suspend fun getPatient(id: Long): Patient?

    @Query("SELECT * FROM patients")
    suspend fun getPatients(): List<Patient?>
}