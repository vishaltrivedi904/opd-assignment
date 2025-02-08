package com.example.khushibaby.ui.screens.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.khushibaby.data.model.Patient
import com.example.khushibaby.data.model.Response
import com.example.khushibaby.data.model.SyncStatus
import com.example.khushibaby.data.model.Visit
import com.example.khushibaby.data.repository.PatientRepository
import com.example.khushibaby.data.repository.VisitRepository
import com.example.khushibaby.ui.screens.visit.PrescriptionViewModel
import com.example.khushibaby.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations


class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher= StandardTestDispatcher()
    private lateinit var patientRepository: PatientRepository
    private lateinit var homeViewModel: HomeViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        patientRepository = mock(PatientRepository::class.java)
        homeViewModel = HomeViewModel(patientRepository)
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getPatients()= runBlocking{

        val patientList = listOf(
            Patient(
                name = "John Doe",
                age = 30,
                gender = "Male",
                contactNumber = "1234567890",
                location = "New York, USA",
                healthId = "HID12345",
                syncStatus = SyncStatus.PENDING
            ),
            Patient(
                name = "Jane Smith",
                age = 25,
                gender = "Female",
                contactNumber = "9876543210",
                location = "Los Angeles, USA",
                healthId = "HID54321",
                syncStatus = SyncStatus.SYNCED
            )
        )

        // Mocking the repository call
        Mockito. `when`(patientRepository.getPatients()).thenReturn(patientList)

        val sut=HomeViewModel(patientRepository)

        sut.getPatients()


        testDispatcher.scheduler.advanceUntilIdle()
        val result=sut.resultState.getOrAwaitValue()

        // Assert that the LiveData is updated correctly
        assertEquals(Response.Success(patientList), result)
    }

    @Test
    fun getPatientsEmpty()= runBlocking{

        val patientList = emptyList<Patient>()

        // Mocking the repository call
        Mockito. `when`(patientRepository.getPatients()).thenReturn(patientList)

        val sut=HomeViewModel(patientRepository)

        sut.getPatients()


        testDispatcher.scheduler.advanceUntilIdle()
        val result=sut.resultState.getOrAwaitValue()

        // Assert that the LiveData is updated correctly
        assertEquals(Response.Success(patientList), result)
    }
}