package com.example.khushibaby.data.repository

import com.example.animeapplication.data.api.ApiInterface
import com.example.khushibaby.data.database.PatientDao
import com.example.khushibaby.data.model.Patient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientRepository @Inject constructor(private val apiService: ApiInterface,
    private val patientDao: PatientDao
) {
    suspend fun insertPatient(patient: Patient) {
        patientDao.insertPatient(patient)
    }

    suspend fun getPatients(): List<Patient?> {
        return patientDao.getPatients()
    }

    suspend fun getPatient(id: Long): Patient? {
        return patientDao.getPatient(id)
    }
}
