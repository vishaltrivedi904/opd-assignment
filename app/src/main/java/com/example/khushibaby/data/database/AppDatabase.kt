package com.example.khushibaby.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.khushibaby.data.model.Patient
import com.example.khushibaby.data.model.Visit

@Database(entities = [Patient::class, Visit::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun visitDao(): VisitDao
}