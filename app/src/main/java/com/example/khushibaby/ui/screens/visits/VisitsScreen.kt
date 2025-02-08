package com.example.khushibaby.ui.screens.visit

import Screen
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.khushibaby.R
import com.example.khushibaby.data.model.Response
import com.example.khushibaby.data.model.Visit
import com.example.khushibaby.ui.composable.TextFieldColor
import com.example.khushibaby.ui.theme.buttonColor
import com.example.khushibaby.utils.DialogManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitsScreen(patientId: Long, navHostController: NavHostController) {
    var date by remember { mutableStateOf("") }
    var symptoms by remember { mutableStateOf("") }
    var diagnosis by remember { mutableStateOf("") }
    var medicines by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    var mViewModel: PrescriptionViewModel = hiltViewModel()

    val onBackPress: () -> Unit = {
        navHostController.navigateUp()
    }



    val observer: Observer<Response<Visit?>> = Observer { response ->
        when (response) {
            is Response.Loading -> {
                DialogManager.show()
            }

            is Response.Success -> {
                DialogManager.hide()
                navHostController.navigateUp()
            }

            is Response.Error -> {
                DialogManager.hide()
                val error = response.message.localizedMessage
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    DisposableEffect(Unit) {
        date = mViewModel.getCurrentDate()
        mViewModel.visit.observe(lifecycleOwner.value, observer)

        onDispose {
            print("onDispose")
        }
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            navigationIcon = {
            IconButton(onClick = { onBackPress() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        },colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
            modifier = Modifier.background(
                Color.White
            ),
            title = {
                Text(stringResource(R.string.add_prescription_))
            })
    },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable { keyboardController!!.hide() }) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.White)
            .clickable { keyboardController!!.hide() }) {
            Column(modifier = Modifier
                .fillMaxSize()
                .clickable { keyboardController!!.hide() }) {


                Column(modifier = Modifier
                    .clickable { keyboardController!!.hide() }
                    .padding(16.dp)
                    .background(Color.White)
                    .verticalScroll(
                        rememberScrollState()
                    )) {
                    OutlinedTextField(
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        value = date,
                        enabled = false,
                        onValueChange = { date = it },
                        label = { Text(stringResource(R.string.date)) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.None
                        ),
                        colors = TextFieldColor()
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = symptoms,
                        minLines = 3,
                        maxLines = 3,
                        onValueChange = { symptoms = it },
                        label = { Text(stringResource(R.string.symptoms_)) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        colors = TextFieldColor()
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = diagnosis,
                        minLines = 3,
                        maxLines = 3,
                        onValueChange = { diagnosis = it },
                        label = { Text(stringResource(R.string.diagnosis)) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        colors = TextFieldColor()
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                    OutlinedTextField(
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        value = medicines,
                        onValueChange = { medicines = it },
                        label = { Text(stringResource(R.string.medicine_name)) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        colors = TextFieldColor()
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    OutlinedTextField(
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        value = dosage,
                        onValueChange = { dosage = it },
                        label = { Text(stringResource(R.string.dosage)) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        colors = TextFieldColor()
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                    OutlinedTextField(
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        value = frequency,
                        onValueChange = { frequency = it },
                        label = { Text(stringResource(R.string.frequency)) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        colors = TextFieldColor()
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                    OutlinedTextField(
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        value = duration,
                        onValueChange = { duration = it },
                        label = { Text(stringResource(R.string.duration)) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                        ),
                        colors = TextFieldColor()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                        onClick = {
                            if (TextUtils.isEmpty(date)) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.please_select_visit_date),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (TextUtils.isEmpty(symptoms.trim())) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.please_enter_patient_symptoms),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (TextUtils.isEmpty(diagnosis.trim())) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.please_enter_the_diagnosis_report),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (TextUtils.isEmpty(medicines.trim())) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.please_enter_medicine_names),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (TextUtils.isEmpty(dosage.trim())) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.please_enter_dosage),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (TextUtils.isEmpty(dosage.trim())) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.please_enter_dosage),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (TextUtils.isEmpty(frequency.trim())) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.please_enter_frequency),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (TextUtils.isEmpty(duration.trim())) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.please_enter_duration),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                mViewModel.saveVisitDetails(
                                    patientId,
                                    symptoms,
                                    diagnosis,
                                    medicines,
                                    dosage,
                                    frequency,
                                    duration
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { keyboardController!!.hide() }) {
                        Text(stringResource(R.string.save))
                    }

                }
            }

        }
    }

}


