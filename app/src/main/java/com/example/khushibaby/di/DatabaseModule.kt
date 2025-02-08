package com.example.khushibaby.di

import android.content.Context
import androidx.room.Room
import com.example.khushibaby.data.database.AppDatabase
import com.example.khushibaby.data.database.PatientDao
import com.example.khushibaby.data.database.VisitDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration() // For debugging, not recommended in production
            .build()
    }

    @Provides
    @Singleton
    fun providePatientDao(appDatabase: AppDatabase): PatientDao {
        return appDatabase.patientDao()
    }

    @Provides
    @Singleton
    fun provideVisitDao(appDatabase: AppDatabase): VisitDao {
        return appDatabase.visitDao()
    }
}
