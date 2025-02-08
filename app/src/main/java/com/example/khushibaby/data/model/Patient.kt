package com.example.khushibaby.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val age: Int,
    val gender: String,
    val contactNumber: String?,
    val location: String,
    val healthId: String,
    val syncStatus: SyncStatus = SyncStatus.PENDING
) : Serializable

