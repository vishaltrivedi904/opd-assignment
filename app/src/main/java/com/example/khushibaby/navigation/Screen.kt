sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")

    data object PatientRegistration : Screen("patient_registration")


    data object VisitsScreen : Screen("visits/{patientId}") {
        fun setPatientId(patientId: Long) = "visits/$patientId"
    }

    data object PatientVisitDetails : Screen("patient_visit_details/{patientId}") {
        fun setPatientId(patientId: Long) = "patient_visit_details/$patientId"
    }

    data object PrescriptionSummary : Screen("prescription_summary/{patientId}/{visitId}") {
        fun setPatientId(patientId: Long, visitId: Long) = "prescription_summary/$patientId/$visitId"
    }
}
