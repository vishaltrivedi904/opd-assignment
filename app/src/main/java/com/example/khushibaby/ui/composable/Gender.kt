package com.example.khushibaby.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Gender(
    text: String,
    imagePainter: Painter,
    tintColor: Color,
    fontWeight: FontWeight,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .border(width = 1.dp, color = tintColor, shape = RoundedCornerShape(6.dp))
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
            .clickable(onClick = onClick) // Add click listener
    ) {
        Image(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape),
            painter = imagePainter,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = text,
            color = tintColor,
            style = TextStyle(
                fontSize = 14.sp, fontWeight = fontWeight  // Text styling
            )
        )

    }


}