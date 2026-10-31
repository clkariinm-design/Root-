package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.ui.RootMateViewModel
import com.example.ui.components.TerminalCard
import com.example.ui.components.TerminalText
import com.example.ui.theme.AcidGreen
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.WarningOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: RootMateViewModel,
    onNavigateToAudit: () -> Unit,
    onNavigateToDevEnv: () -> Unit,
    onNavigateToBackup: () -> Unit,
    onNavigateToTutorials: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val audit by viewModel.currentAudit.collectAsState()
    val checklist by viewModel.checklistItems.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("ROOTMATE_DASHBOARD") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = NeonCyan
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // System Status Card
            TerminalCard(title = "SYSTEM_STATUS") {
                audit?.let {
                    TerminalText("DEVICE", "${it.manufacturer} ${it.model}")
                    TerminalText("ANDROID", it.androidVersion)
                    TerminalText("DEV_OPTS", if (it.devOptionsEnabled) "ENABLED" else "DISABLED", valueColor = if (it.devOptionsEnabled) AcidGreen else WarningOrange)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.refreshAudit(saveToHistory = true) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.extraSmall,
                        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black)
                    ) {
                        Icon(Icons.Default.Analytics, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("GENERATE_NEW_REPORT")
                    }
                } ?: Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = NeonCyan, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("POLLING_SYSTEM_DATA...", style = MaterialTheme.typography.labelSmall, color = NeonCyan.copy(alpha = 0.5f))
                    }
                }
            }

            // Readiness Score (Compatibility Engine)
            audit?.let {
                val readinessScore = it.readinessScore
                TerminalCard(title = "PREPARATION_LEVEL", borderColor = if (readinessScore > 70) AcidGreen else WarningOrange) {
                    LinearProgressIndicator(
                        progress = { readinessScore / 100f },
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        color = if (readinessScore > 70) AcidGreen else WarningOrange,
                        trackColor = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("LEVEL: $readinessScore%", style = MaterialTheme.typography.bodySmall)
                        Text(if (readinessScore > 70) "READY_FOR_PREP" else "INCOMPLETE", style = MaterialTheme.typography.labelSmall, color = if (readinessScore > 70) AcidGreen else WarningOrange)
                    }
                }
            }

            // Navigation Grid
            Text("COMMAND_PANEL", style = MaterialTheme.typography.labelSmall, color = NeonCyan, modifier = Modifier.padding(top = 8.dp))
            
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                DashboardButton(Icons.Default.Dns, "DEVICE_AUDIT", onNavigateToAudit)
                DashboardButton(Icons.Default.Terminal, "DEV_ENVIRONMENT", onNavigateToDevEnv)
                DashboardButton(Icons.Default.Backup, "BACKUP_CHECKLIST", onNavigateToBackup)
                DashboardButton(Icons.Default.MenuBook, "TUTORIALS", onNavigateToTutorials)
                DashboardButton(Icons.Default.Assessment, "REPORTS", onNavigateToReports)
                DashboardButton(Icons.Default.Settings, "SETTINGS", onNavigateToSettings)
            }
        }
    }
}

@Composable
fun DashboardButton(icon: ImageVector, label: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraSmall,
        border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.3f)),
        contentPadding = PaddingValues(12.dp)
    ) {
        Icon(icon, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(label, style = MaterialTheme.typography.labelLarge, color = NeonCyan)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = NeonCyan)
    }
}
