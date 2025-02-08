package com.example.khushibaby.navigation

import Screen
import SummaryScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.khushibaby.ui.screens.home.HomeScreen
import com.example.khushibaby.ui.screens.registration.PatientRegistrationScreen
import com.example.khushibaby.ui.screens.visit.PrescriptionScreen
import com.example.khushibaby.ui.screens.visit.VisitsScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    // Define the navigation graph
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {

        // Patient Registration Screen
        composable(Screen.HomeScreen.route) {
            HomeScreen(navHostController = navController)
        }


        // Patient Registration Screen
        composable(Screen.PatientRegistration.route) {
            PatientRegistrationScreen(navHostController = navController)
        }


        // Patient Visit Details Screen
        composable(
            route = Screen.VisitsScreen.route,
            arguments = listOf(navArgument("patientId") { type = NavType.LongType })
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getLong("patientId") ?: return@composable
            PrescriptionScreen(patientId = patientId, navController)
        }


        // Patient Visit Details Screen
        composable(
            route = Screen.PatientVisitDetails.route,
            arguments = listOf(navArgument("patientId") { type = NavType.LongType })
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getLong("patientId") ?: return@composable
            VisitsScreen(patientId = patientId, navController)
        }

        // Prescription Summary Screen
        composable(
            route = Screen.PrescriptionSummary.route,
            arguments = listOf(
                navArgument("patientId") { type = NavType.LongType },
                navArgument("visitId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getLong("patientId") ?: return@composable
            val visitId = backStackEntry.arguments?.getLong("visitId") ?: return@composable
            SummaryScreen(patientId = patientId, visitId = visitId,navController)
        }
    }
}