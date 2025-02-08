package com.example.khushibaby.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khushibaby.R
import com.example.khushibaby.data.model.Patient


@Composable
fun PatientCard(onClick: () -> Unit, patient: Patient) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
    ) {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White, // Card's background color is set to white
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },
            content = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Image(
                            modifier = Modifier
                                .width(60.dp)
                                .height(60.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.Gray, CircleShape),
                            painter = if (patient.gender.equals(
                                    "male",
                                    true
                                )
                            ) painterResource(R.drawable.male) else painterResource(
                                R.drawable.female
                            ),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    modifier = Modifier.weight(0.6f),
                                    text = patient.name,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                Text(
                                    modifier = Modifier.weight(0.4f),
                                    text = stringResource(R.string.age_years, patient.age),
                                    textAlign = TextAlign.End,
                                    style = TextStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                )
                            }

                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                style = TextStyle(fontSize = 13.sp),
                                text = stringResource(
                                    R.string.contact,
                                    patient.contactNumber ?: "N/A"
                                ),
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(R.string.health_id_, patient.healthId),
                                style = TextStyle(fontSize = 13.sp),
                            )
                        }

                    }
                }
            }
        )

    }

}