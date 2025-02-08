package com.example.khushibaby.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SummaryDetail(title: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.sp,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Text(
            text = value, fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        )
    }
}