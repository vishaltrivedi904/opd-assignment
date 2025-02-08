package com.example.khushibaby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.khushibaby.navigation.AppNavigation
import com.example.khushibaby.ui.theme.KhushiBabyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KhushiBabyTheme {
                /* Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                     *//*Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )*//*
                }*/
                AppNavigation()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = false)
@Composable
fun GreetingPreview() {
    KhushiBabyTheme {
        Greeting("Android")
    }
}