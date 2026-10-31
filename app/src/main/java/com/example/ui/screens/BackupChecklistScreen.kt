package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupChecklistScreen(
    viewModel: RootMateViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.checklistItems.collectAsState()
    val backupItems = items.filter { it.category == "BACKUP" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BACKUP_PROTOCOL") },
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
                .padding(16.dp)
        ) {
            Text(
                "CRITICAL: ALL DATA WILL BE WIPED DURING BOOTLOADER UNLOCKING.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(backupItems) { item ->
                    TerminalCard(
                        title = "TASK_${item.id}",
                        borderColor = if (item.isChecked) AcidGreen else NeonCyan.copy(alpha = 0.3f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = item.isChecked,
                                onCheckedChange = { viewModel.updateCheckStatus(item.id, it) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = AcidGreen,
                                    uncheckedColor = NeonCyan.copy(alpha = 0.5f)
                                )
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
