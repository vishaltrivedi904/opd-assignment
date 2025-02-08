package com.example.khushibaby.ui.screens.registration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.khushibaby.data.model.Patient
import com.example.khushibaby.data.model.Response
import com.example.khushibaby.data.model.SyncStatus
import com.example.khushibaby.data.repository.PatientRepository
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

class RegistrationViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var patientRepository: PatientRepository
    private lateinit var registrationViewModel: RegistrationViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        patientRepository = mock(PatientRepository::class.java)
        registrationViewModel = RegistrationViewModel(patientRepository)
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
    fun registerPatient() = runBlocking {
        // Create the expected Visit object
        val patient = Patient(
            name = "John Doe",
            age = 30,
            gender = "Male",
            contactNumber = "1234567890",
            location = "New York, USA",
            healthId = "HID12345",
            syncStatus = SyncStatus.PENDING
        )

        // Mocking the repository call
        Mockito.`when`(patientRepository.insertPatient(patient)).thenReturn(Unit)

        // Create the ViewModel
        val sut = RegistrationViewModel(patientRepository)

        // Call the method to register patient
        sut.registerPatient(
            patient.name,
            patient.age,
            patient.gender,
            patient.contactNumber,
            patient.location,
            patient.healthId
        )

        // Advance the coroutine until all coroutines are completed
        testDispatcher.scheduler.advanceUntilIdle()

        // Get the LiveData value
        val result = sut.patient.getOrAwaitValue()

        // Assert that the LiveData is updated correctly
        assertEquals(Response.Success(patient), result)
    }
}