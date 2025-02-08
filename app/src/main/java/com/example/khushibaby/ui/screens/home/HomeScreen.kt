package com.example.khushibaby.ui.screens.home

import Screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.khushibaby.R
import com.example.khushibaby.data.model.Patient
import com.example.khushibaby.data.model.Response
import com.example.khushibaby.ui.composable.PatientCard
import com.example.khushibaby.ui.theme.PrimaryColor
import com.example.khushibaby.ui.theme.backgroundColor
import com.example.khushibaby.ui.theme.buttonColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController) {

    val mViewModel: HomeViewModel = hiltViewModel()

    val response by mViewModel.resultState.observeAsState()

    LaunchedEffect(Unit) {
        mViewModel.getPatients()
    }

    Scaffold(floatingActionButton = {
        Button(colors = ButtonDefaults.buttonColors(containerColor = buttonColor), onClick = {
            navHostController.navigate(Screen.PatientRegistration.route)
        }, modifier = Modifier.wrapContentSize()) {
            Text(stringResource(R.string.register))
        }
    }, topBar = {
        CenterAlignedTopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
            modifier = Modifier.background(Color.White),
            title = {
                Image(
                    modifier = Modifier.height(40.dp),
                    painter = painterResource(R.drawable.header),
                    contentDescription = "logo"
                )
            })
    }, modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding)
        ) {

            if (response is Response.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryColor)
                }
            } else if (response is Response.Success) {
                val response = (response as Response.Success<List<Patient?>>).data

                if (response.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.White
                            )
                    )
                    {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                modifier = Modifier
                                    .height(200.dp),
                                painter = painterResource(R.drawable.no_data),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(
                                stringResource(R.string.patient_data_not_available),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp)
                            .background(backgroundColor)
                    ) {
                        items(response.size) { item ->
                            PatientCard(onClick = {
                                navHostController.navigate(
                                    Screen.VisitsScreen.setPatientId(
                                        response[item]!!.id
                                    )
                                )
                            }, response[item]!!)
                        }
                    }
                }
            } else if (response is Response.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .height(120.dp),
                        painter = painterResource(R.drawable.no_data),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.size(10.dp))

                    (response as Response.Error).message.localizedMessage?.let { Text(text = it) }
                }
            }
        }
    }
}

