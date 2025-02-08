package com.example.khushibaby.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "visits"
)
data class Visit(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val patientId: Long,
    val visitDate: String,
    val symptoms: String,
    val diagnosis: String,
    val medicines: String,
    val dosage: String,
    val frequency: String,
    val duration: String,
    val isCompleted: Boolean = false,
    val syncStatus: SyncStatus = SyncStatus.PENDING
)