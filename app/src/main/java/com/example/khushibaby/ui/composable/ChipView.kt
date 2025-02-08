package com.example.khushibaby.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChipView(text:String,textColor: Color,color: Color) {
    Box(
        modifier = Modifier
            .background(color, shape = CircleShape)  // Round background
            .padding(horizontal = 5.dp, vertical = 3.dp)  // Padding for text
    ) {
        Text(
            text = text,
            color = textColor,  // Text color
            style = TextStyle(fontSize = 12.sp)  // Text styling
        )
    }
}