import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.khushibaby.data.model.Response
import com.example.khushibaby.data.model.Visit
import com.example.khushibaby.data.repository.VisitRepository
import com.example.khushibaby.ui.screens.visit.PrescriptionViewModel
import com.example.khushibaby.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PrescriptionViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher= StandardTestDispatcher()

    private lateinit var visitRepository: VisitRepository
    private lateinit var prescriptionViewModel: PrescriptionViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        visitRepository = mock(VisitRepository::class.java)
        prescriptionViewModel = PrescriptionViewModel(visitRepository)
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
    fun testSaveVisitDetails() = runBlocking {

        // Create the expected Visit object
        val visitDetail = Visit(
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
        Mockito.`when`(visitRepository.insertVisit(visitDetail)).thenReturn(Unit)

        // Create the ViewModel
        val sut = PrescriptionViewModel(visitRepository)

        // Call the method to save visit details
        sut.saveVisitDetails(visitDetail.id, visitDetail.symptoms, visitDetail.diagnosis, visitDetail.medicines, visitDetail.dosage, visitDetail.frequency, visitDetail.duration)

        // Advance the coroutine until all coroutines are completed
        testDispatcher.scheduler.advanceUntilIdle()

        // Get the LiveData value
        val result = sut.visit.getOrAwaitValue()

        // Verify repository method call
        Mockito.verify(visitRepository).insertVisit(visitDetail)

        // Assert that the LiveData is updated correctly
        assertEquals(Response.Success(visitDetail), result)
    }

    @Test
    fun testGetVisits() = runBlockingTest {
        val patientId = 1L
        val visitsList = listOf(
            Visit(patientId = patientId, visitDate = "01-Jan-2023", symptoms = "Cough", diagnosis = "Flu", medicines = "Paracetamol", dosage = "500mg", frequency = "Twice a day", duration = "5 days")
        )

        // Mocking the repository call
        Mockito. `when`(visitRepository.getVisitsForPatient(patientId)).thenReturn(visitsList)

        val sut=PrescriptionViewModel(visitRepository)

        sut.getVisits(patientId)


        testDispatcher.scheduler.advanceUntilIdle()
        val result=sut.visits.getOrAwaitValue()


        // Assert that the LiveData is updated correctly
        assertEquals(Response.Success(visitsList), result)
    }

    @Test
    fun testGetVisitsEmpty() = runBlockingTest {
        val patientId = 1L
        val visitsList = emptyList<Visit>()

        // Mocking the repository call
        Mockito. `when`(visitRepository.getVisitsForPatient(patientId)).thenReturn(visitsList)

        val sut=PrescriptionViewModel(visitRepository)

        sut.getVisits(patientId)

        testDispatcher.scheduler.advanceUntilIdle()
        val result=sut.visits.getOrAwaitValue()

        // Assert that the LiveData is updated correctly
        assertEquals(Response.Success(visitsList), result)
    }
}