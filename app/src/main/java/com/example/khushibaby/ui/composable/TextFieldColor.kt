package com.example.khushibaby.ui.composable

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldColor(): TextFieldColors {
    return TextFieldDefaults.outlinedTextFieldColors(
        focusedLabelColor = Color.Black, // Focused label color
        focusedBorderColor = Color.Black, // Focused border color
        unfocusedLabelColor = Color.Black, // Unfocused label color
        unfocusedBorderColor = Color.Black, // Unfocused border color
        cursorColor = Color.Black, // Cursor color
        disabledTextColor = Color.Black, // Disabled text color
        disabledBorderColor = Color.Black, // Disabled border color
    )
}