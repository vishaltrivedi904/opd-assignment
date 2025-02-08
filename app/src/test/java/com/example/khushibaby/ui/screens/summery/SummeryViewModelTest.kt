package com.example.khushibaby.ui.screens.summery

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.khushibaby.data.model.BaseResponse
import com.example.khushibaby.data.model.Patient
import com.example.khushibaby.data.model.Response
import com.example.khushibaby.data.model.SyncStatus
import com.example.khushibaby.data.model.Visit
import com.example.khushibaby.data.repository.PatientRepository
import com.example.khushibaby.data.repository.VisitRepository
import com.example.khushibaby.ui.screens.registration.RegistrationViewModel
import com.example.khushibaby.ui.screens.visit.PrescriptionViewModel
import com.example.khushibaby.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class SummeryViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var patientRepository: PatientRepository
    private lateinit var visitRepository: VisitRepository
    private lateinit var summeryViewModel: SummeryViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        visitRepository = mock(VisitRepository::class.java)
        patientRepository = mock(PatientRepository::class.java)
        summeryViewModel = SummeryViewModel(patientRepository,visitRepository)
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
    fun testGetPatient() = runBlocking {
        val patientId = 0L
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
        Mockito. `when`(patientRepository.getPatient(patientId)).thenReturn(patient)

        val sut= SummeryViewModel(patientRepository,visitRepository)

        sut.getPatient(patientId)

        testDispatcher.scheduler.advanceUntilIdle()
        val result=sut.patient.getOrAwaitValue()

        // Assert that the LiveData is updated correctly
        assertEquals(Response.Success(patient), result)
    }



    @Test
    fun testGetVisit() = runBlocking{

        val visitId=0L

        val visit = Visit(
            patientId = 0L,
            visitDate = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(Date()), // You can use the method to get the current date if needed
            symptoms = "Cough",
            diagnosis = "Flu",
            medicines = "Paracetamol",
            dosage = "500mg",
            frequency = "Twice a day",
            duration = "5 days"
        )

        // Mocking the repository call
        Mockito. `when`(visitRepository.getVisitForPatient(visitId)).thenReturn(visit)

        val sut= SummeryViewModel(patientRepository,visitRepository)

        sut.getVisit(visitId)

        testDispatcher.scheduler.advanceUntilIdle()
        val result=sut.visit.getOrAwaitValue()

        // Assert that the LiveData is updated correctly
        assertEquals(Response.Success(visit), result)
    }


    @Test
    fun testMarkVisitAsCompleted() = runBlocking {
        val visit = Visit(
            patientId = 0L,
            visitDate = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(Date()), // You can use the method to get the current date if needed
            symptoms = "Cough",
            diagnosis = "Flu",
            medicines = "Paracetamol",
            dosage = "500mg",
            frequency = "Twice a day",
            duration = "5 days"
        )

        val response =BaseResponse(200,"Visit Complete SuccessFully","OK")
        Mockito. `when` (visitRepository.markVisitAsCompleted(visit)).thenReturn(response)
        val sut=SummeryViewModel(patientRepository,visitRepository)
        sut.markVisitAsCompleted(visit)
        testDispatcher.scheduler.advanceUntilIdle()
        val result=sut.visitComplete.getOrAwaitValue()
        assertEquals(Response.Success(response),result)
    }
}