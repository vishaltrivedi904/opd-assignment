import com.example.khushibaby.data.model.Patient
import com.example.khushibaby.data.repository.PatientRepository
import com.example.khushibaby.data.database.PatientDao
import com.example.animeapplication.data.api.ApiInterface
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.junit.Assert.*

class PatientRepositoryTest {

    @Mock
    private lateinit var mockApiService: ApiInterface

    @Mock
    private lateinit var mockPatientDao: PatientDao

    private lateinit var patientRepository: PatientRepository

    @Before
    fun setup() {
        // Mockito annotations ko initialize karte hain
        MockitoAnnotations.openMocks(this)
        // PatientRepository object banate hain
        patientRepository = PatientRepository(mockApiService, mockPatientDao)
    }

    @Test
    fun testInsertPatient() = runBlocking {
        // Test case for insertPatient method
        val patient = Patient(
            name = "John Doe",
            age = 30,
            gender = "M",
            contactNumber = "1234567890",
            location = "Location A",
            healthId = "H123"
        )

        // Call insertPatient method
        patientRepository.insertPatient(patient)

        // Verify that the insertPatient method in PatientDao was called
        Mockito.verify(mockPatientDao).insertPatient(patient)
    }

    @Test
    fun testGetPatients() = runBlocking {
        // Test case for getPatients method
        val patients = listOf(
            Patient(
                id = 1,
                name = "John Doe",
                age = 30,
                gender = "M",
                contactNumber = "1234567890",
                location = "Location A",
                healthId = "H123"
            ),
            Patient(
                id = 2,
                name = "Jane Doe",
                age = 28,
                gender = "F",
                contactNumber = "9876543210",
                location = "Location B",
                healthId = "H124"
            )
        )

        // Mock the getPatients method of PatientDao
        Mockito.`when`(mockPatientDao.getPatients()).thenReturn(patients)

        // Call getPatients method
        val result = patientRepository.getPatients()

        // Verify the result
        assertEquals(patients, result)
    }

    @Test
    fun testGetPatient() = runBlocking {
        // Test case for getPatient method
        val patient = Patient(
            id = 1,
            name = "John Doe",
            age = 30,
            gender = "M",
            contactNumber = "1234567890",
            location = "Location A",
            healthId = "H123"
        )

        // Mock the getPatient method of PatientDao
        Mockito.`when`(mockPatientDao.getPatient(1)).thenReturn(patient)

        // Call getPatient method
        val result = patientRepository.getPatient(1)

        // Verify the result
        assertEquals(patient, result)
    }
}
