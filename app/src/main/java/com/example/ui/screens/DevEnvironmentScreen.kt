package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.RootMateViewModel
import com.example.ui.components.TerminalCard
import com.example.ui.theme.AcidGreen
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.WarningOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevEnvironmentScreen(
    viewModel: RootMateViewModel,
    onBack: () -> Unit
) {
    val audit by viewModel.currentAudit.collectAsState()
    val items by viewModel.checklistItems.collectAsState()
    val devItems = items.filter { it.category == "DEV_ENV" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DEV_ENV_VERIFICATION") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = NeonCyan)
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.refreshAudit() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = NeonCyan)
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
                .padding(16.dp)
        ) {
            audit?.let {
                TerminalCard(
                    title = "LIVE_STATUS",
                    borderColor = if (it.devOptionsEnabled && it.usbDebuggingEnabled) AcidGreen else WarningOrange
                ) {
                    StatusRow("DEV_OPTIONS", it.devOptionsEnabled)
                    StatusRow("USB_DEBUGGING", it.usbDebuggingEnabled)
                    StatusRow("OEM_UNLOCKING", it.oemUnlockStatus == "ENABLED")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("PREPARATION_CHECKLIST", style = MaterialTheme.typography.labelSmall, color = NeonCyan)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(devItems) { item ->
                    TerminalCard(
                        title = "DEV_${item.id}",
                        borderColor = if (item.isChecked) AcidGreen else NeonCyan.copy(alpha = 0.3f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = item.isChecked,
                                onCheckedChange = { viewModel.updateCheckStatus(item.id, it) },
                                colors = CheckboxDefaults.colors(checkedColor = AcidGreen)
                            )
                            Column {
                                Text(item.label, style = MaterialTheme.typography.bodyLarge)
                                Text(item.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusRow(label: String, status: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", style = MaterialTheme.typography.bodySmall)
        Text(
            text = if (status) "[PASS]" else "[FAIL]",
            style = MaterialTheme.typography.bodySmall.copy(
                color = if (status) AcidGreen else WarningOrange,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        )
    }
}
