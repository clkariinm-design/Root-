package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.components.TerminalCard
import com.example.ui.theme.NeonCyan

data class Tutorial(
    val id: String, 
    val title: String, 
    val description: String,
    val content: String
)

val tutorials = listOf(
    Tutorial(
        id = "adb_setup", 
        title = "ADB & Fastboot Setup", 
        description = "How to install and verify ADB on your PC.",
        content = """
            > STEP_1: DOWNLOAD_TOOLS
            Download the Android SDK Platform-Tools for your OS (Windows/Mac/Linux).
            
            > STEP_2: INSTALLATION
            Extract the ZIP to a known folder, e.g., C:\platform-tools.
            
            > STEP_3: ENVIRONMENT_VARS
            Add the folder to your System PATH to run 'adb' from any command prompt.
            
            > STEP_4: VERIFICATION
            Connect phone and run:
            $ adb devices
            If you see a serial number, you are connected.
        """.trimIndent()
    ),
    Tutorial(
        id = "oem_unlock", 
        title = "Understanding OEM Unlocking", 
        description = "What it is and why it's the first step.",
        content = """
            > CONCEPT: BOOTLOADER
            The bootloader is the first piece of software that runs when you turn on your device. It decides what OS to load.
            
            > OEM_UNLOCKING
            Manufacturers lock the bootloader to ensure only official software is loaded. 'OEM Unlocking' is the master switch that allows you to request an unlock.
            
            > WARNING
            Enabling this switch does NOT unlock the bootloader. It only permits the 'fastboot oem unlock' command to work. Unlocking ALWAYS triggers a factory reset.
        """.trimIndent()
    ),
    Tutorial(
        id = "drivers", 
        title = "Universal Driver Installation", 
        description = "Solving connection issues on Windows/Linux.",
        content = """
            > THE_PROBLEM
            Windows often fails to recognize devices in 'Fastboot Mode' even if ADB works in normal mode.
            
            > SOLUTION: GOOGLE_USB_DRIVERS
            Download 'Google USB Driver' via SDK Manager or direct download.
            
            > DEVICE_MANAGER_FIX
            1. Open Device Manager.
            2. Right-click 'Android' with yellow triangle.
            3. Update Driver -> Browse my computer -> Let me pick from a list.
            4. Select 'Google, Inc.' -> 'Android Bootloader Interface'.
        """.trimIndent()
    ),
    Tutorial(
        id = "safetynet", 
        title = "SafetyNet & Play Integrity", 
        description = "Life after root and how to pass integrity checks.",
        content = """
            > THE_CONSEQUENCE
            Rooting triggers 'Play Integrity' (formerly SafetyNet) to fail. Banking apps and Google Pay may stop working.
            
            > REMEDIATION
            Use tools like 'Play Integrity Fix' or 'MagiskHide' (Legacy) to spoof device fingerprint.
            
            > ADVICE
            Always keep an unrooted backup device if you rely on banking apps daily.
        """.trimIndent()
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialsScreen(
    onTutorialClick: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EDUCATIONAL_GUIDES") },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tutorials) { tutorial ->
                TerminalCard(title = "DOC_${tutorial.id.uppercase()}") {
                    Column {
                        Text(tutorial.title, style = MaterialTheme.typography.headlineLarge, color = NeonCyan)
                        Text(tutorial.description, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = { onTutorialClick(tutorial.id) }) {
                            Text("READ_FULL_GUIDE >", color = NeonCyan)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialDetailScreen(tutorialId: String, onBack: () -> Unit) {
    val tutorial = tutorials.find { it.id == tutorialId } ?: tutorials.first()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GUIDE_DETAIL") },
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
                .verticalScroll(rememberScrollState())
        ) {
            Text(tutorial.title, style = MaterialTheme.typography.displaySmall, color = NeonCyan, modifier = Modifier.padding(bottom = 16.dp))
            
            TerminalCard(title = "INSTRUCTIONS") {
                Text(
                    text = tutorial.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = NeonCyan.copy(alpha = 0.9f)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            TerminalCard(title = "SAFETY_PROTOCOL") {
                Text(
                    "This guide is for educational purposes only. Rooting carries risks of data loss and hardware damage. Proceed with caution.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
