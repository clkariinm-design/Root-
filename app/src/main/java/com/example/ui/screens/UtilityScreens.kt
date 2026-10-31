package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.components.TerminalCard
import com.example.ui.theme.NeonCyan
import com.example.ui.RootMateViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import com.example.ui.theme.AcidGreen
import com.example.ui.theme.WarningOrange
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Share

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(viewModel: RootMateViewModel, onBack: () -> Unit) {
    val history by viewModel.auditHistory.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SAVED_REPORTS") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = NeonCyan)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = NeonCyan
                )
            )
        }
    ) { padding ->
        if (history.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                TerminalCard(title = "ARCHIVE_EMPTY") {
                    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.FolderOpen, 
                            contentDescription = null, 
                            tint = NeonCyan.copy(alpha = 0.3f),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "NO_SAVED_REPORTS_FOUND", 
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "RUN_NEW_AUDIT_TO_GENERATE_LOG", 
                            style = MaterialTheme.typography.labelSmall,
                            color = NeonCyan.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        } else {
            androidx.compose.foundation.lazy.LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(history) { audit ->
                    val date = java.text.SimpleDateFormat("yyyy.MM.dd_HH:mm", java.util.Locale.getDefault()).format(java.util.Date(audit.timestamp))
                    TerminalCard(title = "LOG_$date") {
                        Column {
                            Text("${audit.manufacturer} ${audit.model}", style = MaterialTheme.typography.headlineSmall, color = NeonCyan)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("BUILD: ${audit.buildNumber}", style = MaterialTheme.typography.labelSmall)
                            Text("SCORE: ${audit.readinessScore}%", style = MaterialTheme.typography.labelSmall, color = if (audit.readinessScore > 70) AcidGreen else WarningOrange)
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { viewModel.shareAuditReport(audit) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                                shape = MaterialTheme.shapes.extraSmall
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("SHARE_REPORT")
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SYSTEM_SETTINGS") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = NeonCyan)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TerminalCard(title = "UI_PREFERENCES") {
                Text("Dark Theme: FORCE_ENABLED")
                Text("Unit System: METRIC")
            }
            
            TerminalCard(title = "ABOUT") {
                Text("ROOTMATE v1.0.0")
                Text("OFFLINE_FIRST")
                Text("CYBERPUNK_UTILITY")
            }
        }
    }
}
