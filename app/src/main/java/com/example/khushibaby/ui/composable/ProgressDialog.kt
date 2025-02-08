package com.example.khushibaby.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.khushibaby.ui.theme.buttonColor
import com.example.khushibaby.utils.DialogManager

@Composable
fun ProgressDialog() {
    if (DialogManager.showDialog.value) {
        Dialog(
            onDismissRequest = { DialogManager.hide() },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(20.dp)
                ) {
                    CircularProgressIndicator(color = buttonColor)
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "Loading...",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    )
                }
            }
        }
    }
}