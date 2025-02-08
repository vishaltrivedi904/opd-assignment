import com.example.khushibaby.data.model.BaseResponse
import com.example.khushibaby.data.model.Visit
import com.example.khushibaby.data.repository.VisitRepository
import com.example.khushibaby.data.database.VisitDao
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.junit.Assert.*

class VisitRepositoryTest {

    @Mock
    private lateinit var mockVisitDao: VisitDao

    private lateinit var visitRepository: VisitRepository

    @Before
    fun setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this)
        // Initialize repository with the mocked VisitDao
        visitRepository = VisitRepository(mockVisitDao)
    }

    @Test
    fun testInsertVisit() = runBlocking {
        // Create a test Visit object
        val visit = Visit(
            patientId = 1L,
            visitDate = "2025-02-08",
            symptoms = "Fever",
            diagnosis = "Cold",
            medicines = "Paracetamol",
            dosage = "500mg",
            frequency = "Twice a day",
            duration = "5 days"
        )

        // Call insertVisit method
        visitRepository.insertVisit(visit)

        // Verify that the insertVisit method in VisitDao was called with the same visit object
        Mockito.verify(mockVisitDao).insertVisit(visit)
    }

    @Test
    fun testGetVisitsForPatient() = runBlocking {
        // Test case to fetch visits for a patient
        val patientId = 1L
        val visits = listOf(
            Visit(
                patientId = patientId,
                visitDate = "2025-02-08",
                symptoms = "Fever",
                diagnosis = "Cold",
                medicines = "Paracetamol",
                dosage = "500mg",
                frequency = "Twice a day",
                duration = "5 days"
            ),
            Visit(
                patientId = patientId,
                visitDate = "2025-02-09",
                symptoms = "Cough",
                diagnosis = "Flu",
                medicines = "Cough Syrup",
                dosage = "10ml",
                frequency = "Once a day",
                duration = "3 days"
            )
        )

        // Mock the getVisitsForPatient method of VisitDao to return the list of visits
        Mockito.`when`(mockVisitDao.getVisitsForPatient(patientId)).thenReturn(visits)

        // Call getVisitsForPatient method in VisitRepository
        val result = visitRepository.getVisitsForPatient(patientId)

        // Verify that the result matches the expected visits list
        assertEquals(visits, result)
    }

    @Test
    fun testMarkVisitAsCompleted() = runBlocking {
        // Test case to mark a visit as completed
        val visit = Visit(
            id = 1L,
            patientId = 123L,
            visitDate = "2025-02-08",
            symptoms = "Headache",
            diagnosis = "Migraine",
            medicines = "Ibuprofen",
            dosage = "200mg",
            frequency = "Once a day",
            duration = "2 days"
        )

        // Call markVisitAsCompleted method in VisitRepository
        val response = visitRepository.markVisitAsCompleted(visit)

        // Verify the BaseResponse status and message
        assertEquals(200, response.code)
        assertEquals("OK", response.status)
        assertEquals("Visit Complete SuccessFully", response.message)

        // Verify that VisitDao's markVisitAsCompleted method was called with the correct visit ID
        Mockito.verify(mockVisitDao).markVisitAsCompleted(visit.id)
    }

    @Test
    fun testSyncVisits() = runBlocking {
        // Test case to sync a list of visits
        val visits = listOf(
            Visit(
                patientId = 123L,
                visitDate = "2025-02-08",
                symptoms = "Fever",
                diagnosis = "Cold",
                medicines = "Paracetamol",
                dosage = "500mg",
                frequency = "Twice a day",
                duration = "5 days"
            )
        )

        // Call syncVisits method in VisitRepository
        val response = visitRepository.syncVisits(visits)

        // Verify the BaseResponse status and message
        assertEquals(200, response.code)
        assertEquals("OK", response.status)
        assertEquals("Sync SuccessFully", response.message)
    }
}
