package com.example.khushibaby.ui.screens.registration

import android.text.TextUtils
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
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.khushibaby.R
import com.example.khushibaby.data.model.Patient
import com.example.khushibaby.data.model.Response
import com.example.khushibaby.ui.composable.Gender
import com.example.khushibaby.ui.composable.ProgressDialog
import com.example.khushibaby.ui.composable.TextFieldColor
import com.example.khushibaby.ui.theme.buttonColor
import com.example.khushibaby.utils.DialogManager


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientRegistrationScreen(navHostController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }

    var contactNumber by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var healthId by remember { mutableStateOf("") }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    val mViewModel: RegistrationViewModel = hiltViewModel()

    val maleColor = if (gender.equals("male", true)) buttonColor else Black
    val maleFont = if (gender.equals("male", true)) FontWeight.SemiBold else FontWeight.Normal
    val femaleColor = if (gender.equals("female", true)) buttonColor else Black
    val femaleFont = if (gender.equals("female", true)) FontWeight.SemiBold else FontWeight.Normal

    val onBackPress: () -> Unit = {
        navHostController.navigateUp()
    }



    val observer: Observer<Response<Patient?>> = Observer { response ->
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

    if (DialogManager.isShowing()) {
        ProgressDialog()
    }

    DisposableEffect(Unit) {
        mViewModel.patient.observe(lifecycleOwner.value, observer)
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
                modifier = Modifier.background(
                    White
                ),
                title = {
                    Image(
                        modifier = Modifier.height(40.dp),
                        painter = painterResource(R.drawable.header),
                        contentDescription = "logo"
                    )
                })
        }, modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.name)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                    ), colors = TextFieldColor()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        singleLine = true,
                        value = age,
                        onValueChange = { newValue ->
                            if (newValue.length <= 3 && newValue.all { it.isDigit() }) {
                                age = newValue
                            }
                        },
                        label = { Text(stringResource(R.string.age)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                        ), colors = TextFieldColor()
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth())
                {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Gender(
                            text = context.getString(R.string.male),
                            imagePainter = painterResource(R.drawable.male),
                            maleColor,
                            maleFont
                        ) {
                            gender = "Male"
                        }
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Gender(
                            text = stringResource(R.string.female),
                            imagePainter = painterResource(R.drawable.female),
                            femaleColor,
                            femaleFont,
                        ) {
                            gender = "Female"
                        }
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = contactNumber,
                    onValueChange = { newValue ->
                        if (newValue.length <= 10 && newValue.all { it.isDigit() }) {
                            contactNumber = newValue
                        }
                    },
                    label = { Text(stringResource(R.string.contact_number)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next
                    ),
                    colors = TextFieldColor(),
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = location,
                    onValueChange = { location = it },
                    label = { Text(stringResource(R.string.location)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                    ), colors = TextFieldColor()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = healthId,
                    onValueChange = { healthId = it },
                    label = { Text(stringResource(R.string.health_id)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                    ), colors = TextFieldColor()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    onClick = {
                        keyboardController?.hide()

                        if (TextUtils.isEmpty(name.trim())) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.please_enter_name), Toast.LENGTH_SHORT
                            ).show()
                        } else if (TextUtils.isEmpty(age.trim())) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.please_enter_age), Toast.LENGTH_SHORT
                            ).show()
                        } else if (TextUtils.isEmpty(gender.trim())) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.please_select_gender), Toast.LENGTH_SHORT
                            )
                                .show()
                        } else if (TextUtils.isEmpty(contactNumber.trim())) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.please_enter_phone_number),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else if (TextUtils.isEmpty(location.trim())) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.please_enter_your_location),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (TextUtils.isEmpty(healthId.trim())) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.please_enter_your_health_id),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            mViewModel.registerPatient(
                                name,
                                age.toInt(),
                                gender,
                                contactNumber,
                                location,
                                healthId
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.register))
                }

            }

        }

    }

}




