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
import com.example.khushibaby.data.model.Visit
import com.example.khushibaby.ui.theme.buttonColor
import com.example.khushibaby.ui.theme.pendingColor


@Composable
fun VisitsCard(onClick: () -> Unit, visit: Visit) {
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
                            painter =  painterResource(R.drawable.prescription),
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
                                    text = visit.visitDate,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                )
                                Spacer(modifier = Modifier.size(5.dp))

                                if (visit.isCompleted){
                                    ChipView("Completed", Color.White,buttonColor)
                                }
                                else{
                                    ChipView("Pending", Color.White,pendingColor)
                                }



                                /*Text(
                                    modifier = Modifier.weight(0.4f),
                                    text = visit.dosage,
                                    textAlign = TextAlign.End,
                                    style = TextStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                )*/
                            }

                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                style = TextStyle(fontSize = 13.sp),
                                text = visit.frequency,
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = stringResource(R.string.health_id_, visit.id),
                                style = TextStyle(fontSize = 13.sp),
                            )
                        }

                    }
                }
            }
        )

    }

}