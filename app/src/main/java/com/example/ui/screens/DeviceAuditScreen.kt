package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.RootMateViewModel
import com.example.ui.components.TerminalCard
import com.example.ui.components.TerminalText
import com.example.ui.theme.NeonCyan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceAuditScreen(
    viewModel: RootMateViewModel,
    onBack: () -> Unit
) {
    val audit by viewModel.currentAudit.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SYSTEM_AUDIT_LOG") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = NeonCyan)
                    }
                },
                actions = {
                    audit?.let { data ->
                        IconButton(onClick = {
                            val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                            val clip = android.content.ClipData.newPlainText("RootMate Audit", "Device: ${data.manufacturer} ${data.model}\nAndroid: ${data.androidVersion}\nBuild: ${data.buildNumber}")
                            clipboard.setPrimaryClip(clip)
                            // Toast is fine for utility feedback
                            android.widget.Toast.makeText(context, "COPIED_TO_CLIPBOARD", android.widget.Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = NeonCyan)
                        }
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            audit?.let {
                TerminalCard(title = "HARDWARE_IDENTITY") {
                    TerminalText("MANUFACTURER", it.manufacturer)
                    TerminalText("MODEL", it.model)
                    TerminalText("HARDWARE", it.hardware)
                    TerminalText("BOARD", it.board)
                    TerminalText("SOC", it.hardware)
                }

                TerminalCard(title = "FIRMWARE_SPEC") {
                    TerminalText("ANDROID_VER", it.androidVersion)
                    TerminalText("API_LEVEL", it.apiLevel.toString())
                    TerminalText("BUILD_ID", it.buildNumber)
                    TerminalText("SECURITY_PATCH", it.securityPatch)
                    TerminalText("BOOTLOADER", it.bootloader)
                }

                TerminalCard(title = "KERNEL_INFO") {
                    TerminalText("OS_VERSION", it.kernelVersion)
                    TerminalText("RADIO_VER", it.radioVersion ?: "[UNKNOWN]")
                }
                
                Text(
                    text = "NOTE: Information marked [UNKNOWN] could not be reliably retrieved via standard APIs.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } ?: Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(androidx.compose.ui.Alignment.Center), color = NeonCyan)
            }
        }
    }
}
