package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.RootMateViewModel
import com.example.ui.navigation.Screen
import com.example.ui.screens.*
import com.example.ui.theme.RootMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RootMateTheme {
                RootMateApp()
            }
        }
    }
}

@Composable
fun RootMateApp() {
    val navController = rememberNavController()
    val viewModel: RootMateViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash
    ) {
        composable<Screen.Splash> {
            SplashScreen(onAnimationComplete = {
                navController.navigate(Screen.Dashboard) {
                    popUpTo(Screen.Splash) { inclusive = true }
                }
            })
        }
        composable<Screen.Dashboard> {
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToAudit = { navController.navigate(Screen.DeviceAudit) },
                onNavigateToDevEnv = { navController.navigate(Screen.DevEnvironment) },
                onNavigateToBackup = { navController.navigate(Screen.BackupChecklist) },
                onNavigateToTutorials = { navController.navigate(Screen.Tutorials) },
                onNavigateToReports = { navController.navigate(Screen.Reports) },
                onNavigateToSettings = { navController.navigate(Screen.Settings) }
            )
        }
        composable<Screen.DeviceAudit> {
            DeviceAuditScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable<Screen.DevEnvironment> {
            DevEnvironmentScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable<Screen.BackupChecklist> {
            BackupChecklistScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable<Screen.Tutorials> {
            TutorialsScreen(
                onTutorialClick = { id -> navController.navigate(Screen.TutorialDetail(id)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable<Screen.TutorialDetail> { backStackEntry ->
            val id = backStackEntry.arguments?.getString("tutorialId") ?: ""
            TutorialDetailScreen(tutorialId = id, onBack = { navController.popBackStack() })
        }
        composable<Screen.Reports> {
            ReportsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable<Screen.Settings> {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}
