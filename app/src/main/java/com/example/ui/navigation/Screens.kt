package com.example.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Splash : Screen
    
    @Serializable
    data object Dashboard : Screen
    
    @Serializable
    data object DeviceAudit : Screen
    
    @Serializable
    data object DevEnvironment : Screen
    
    @Serializable
    data object BackupChecklist : Screen
    
    @Serializable
    data object Tutorials : Screen
    
    @Serializable
    data class TutorialDetail(val tutorialId: String) : Screen
    
    @Serializable
    data object Reports : Screen
    
    @Serializable
    data object Settings : Screen
}
