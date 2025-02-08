import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.example.khushibaby.R
import com.example.khushibaby.data.model.BaseResponse
import com.example.khushibaby.data.model.Patient
import com.example.khushibaby.data.model.Response
import com.example.khushibaby.data.model.Visit
import com.example.khushibaby.ui.composable.CustomDialogView
import com.example.khushibaby.ui.composable.ProgressDialog
import com.example.khushibaby.ui.screens.summery.SummeryViewModel
import com.example.khushibaby.ui.theme.buttonColor
import com.example.khushibaby.utils.DialogManager
import com.example.khushibaby.utils.ShareUtil
import com.example.khushibaby.utils.SummaryPrintAdapter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(patientId: Long, visitId: Long, navigationController: NavHostController) {
    val mViewModel: SummeryViewModel = hiltViewModel()
    val context = LocalContext.current
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    var isCustomDialog by remember { mutableStateOf(false) }

    val patientResponse by mViewModel.patient.observeAsState()
    val visitResponse by mViewModel.visit.observeAsState()
    
    var patient:Patient?=null
    var visit:Visit?=null

    val onBackPress: () -> Unit = {
        navigationController.navigateUp()
    }

    val observer: Observer<Response<BaseResponse?>> = Observer { response ->
        when (response) {
            is Response.Loading -> {
                DialogManager.show()
            }

            is Response.Success -> {
                DialogManager.hide()
                Toast.makeText(context,response.data!!.message,Toast.LENGTH_SHORT).show()
                navigationController.navigateUp()
            }

            is Response.Error -> {
                DialogManager.hide()
                Toast.makeText(context,response.message.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (DialogManager.isShowing()) {
        ProgressDialog()
    }

    if (isCustomDialog) {
        CustomDialogView({
            isCustomDialog = false
        }, {
            mViewModel.markVisitAsCompleted(visit!!)
        }, stringResource(R.string.khushi_baby),
            stringResource(R.string.are_you_sure_you_want_to_mark_this_visit_as_completed),
            stringResource(R.string.complete)
        )
    }


    LaunchedEffect(true) {
        mViewModel.getPatient(patientId)
        mViewModel.getVisit(visitId)
    }

    DisposableEffect(Unit) {
        mViewModel.visitComplete.observe(lifecycleOwner.value, observer)
        onDispose {
            print("onDispose")
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBackPress() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = White),
                title = {
                    Image(
                        modifier = Modifier.height(40.dp),
                        painter = painterResource(R.drawable.header),
                        contentDescription = "logo"
                    )
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(White)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {


                // Handle Patient Response
                patientResponse?.let {
                    if (it is Response.Success) {
                        patient = it.data
                        PatientSection(patient)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Handle Visit Response
                visitResponse?.let {
                    if (it is Response.Success) {
                        visit=it.data
                        Column(modifier = Modifier.fillMaxSize()) {
                            VisitSection(visit)
                            Spacer(modifier = Modifier.height(20.dp))

                            if (visit!!.isCompleted) {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp, 0.dp)) {
                                    Button(
                                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                                        onClick = {
                                            val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
                                            val printAdapter = SummaryPrintAdapter(context, buildSummaryContent(patient, visit))
                                            printManager.print("Summary Print", printAdapter, PrintAttributes.Builder().build())
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                    ) {
                                        Text(stringResource(R.string.print))
                                    }
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Button(
                                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                                        onClick = {
                                            ShareUtil.shareSummary(context,buildSummaryContent(patient,visit))
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                    ) {
                                        Text(stringResource(R.string.share))
                                    }
                                }
                            } else {
                                Button(
                                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                                    onClick = {
                                        isCustomDialog = true
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(stringResource(R.string.mark_as_complete))
                                }
                            }


                        }

                    }
                }
            }
        }
    }
}

@Composable
fun PatientSection(patientDetail: Patient?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SectionHeader("Patient")

        SummaryDetailRow(
            "Name" to (patientDetail?.name ?: stringResource(R.string.not_provided)),
            "Age" to (patientDetail?.age?.toString() ?: stringResource(R.string.not_provided))
        )

        SummaryDetailRow(
            "Gender" to (patientDetail?.gender ?: stringResource(R.string.not_provided)),
            "Contact" to (patientDetail?.contactNumber ?: stringResource(R.string.not_provided))
        )

        SummaryDetailRow(
            "Location" to (patientDetail?.location ?: stringResource(R.string.not_provided)),
            "Health Id" to (patientDetail?.healthId ?: stringResource(R.string.not_provided))
        )
    }
}

@Composable
fun VisitSection(visitDetails: Visit?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SectionHeader(stringResource(R.string.visit))

        SummaryDetailRow(
            "Visit Date" to (visitDetails?.visitDate ?: stringResource(R.string.not_provided)),
            "Frequency" to (visitDetails?.frequency ?: stringResource(R.string.not_provided))
        )

        SummaryDetailRow(
            "Duration" to (visitDetails?.duration ?: stringResource(R.string.not_provided)),
            "Dosages" to (visitDetails?.dosage ?: stringResource(R.string.not_provided))
        )

        SummaryDetailRow(
            "Duration" to (visitDetails?.duration ?: stringResource(R.string.not_provided)),
            "Dosages" to (visitDetails?.dosage ?: stringResource(R.string.not_provided))
        )

        SummaryDetail("Medicines", visitDetails?.medicines ?: stringResource(R.string.not_provided))
        SummaryDetail("Symptoms", visitDetails?.symptoms ?: stringResource(R.string.not_provided))
        SummaryDetail("Diagnosis", visitDetails?.diagnosis ?: stringResource(R.string.not_provided))
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun SummaryDetailRow(vararg details: Pair<String, String>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        details.forEach { (title, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                SummaryDetail(title, value)
            }
        }
    }
}

@Composable
fun SummaryDetail(title: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = buttonColor
            ),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Spacer(modifier = Modifier.size(3.dp))
        Text(
            text = value,
            style = TextStyle(fontSize = 16.sp),
            color = Color.Black
        )
    }
}


fun buildSummaryContent(patientDetail: Patient?,visitDetails: Visit?): String {
    // Construct the summary string from your data
    return """
        Patient Details:
        Name: ${patientDetail?.name ?: "Not Provided"}
        Age: ${patientDetail?.age ?: "Not Provided"}
        Gender: ${patientDetail?.gender ?: "Not Provided"}
        Contact: ${patientDetail?.contactNumber ?: "Not Provided"}
        Location: ${patientDetail?.location ?: "Not Provided"}
        Health ID: ${patientDetail?.healthId ?: "Not Provided"}
        
        Visit Details:
        Visit Date: ${visitDetails?.visitDate ?: "Not Provided"}
        Frequency: ${visitDetails?.frequency ?: "Not Provided"}
        Duration: ${visitDetails?.duration ?: "Not Provided"}
        Dosages: ${visitDetails?.dosage ?: "Not Provided"}
        Symptoms: ${visitDetails?.symptoms ?: "Not Provided"}
        Diagnosis: ${visitDetails?.diagnosis ?: "Not Provided"}
    """.trimIndent()
}