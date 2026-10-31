package com.example.ui

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RootMateViewModel(application: Application) : AndroidViewModel(application) {
    private val database = RootMateDatabase.getDatabase(application)
    private val checklistRepository = ChecklistRepository(database.checklistDao())
    private val systemInfoProvider = SystemInfoProvider(application)
    private val auditDao = database.auditDao()

    val checklistItems = checklistRepository.allItems.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val auditHistory = auditDao.getAllAudits().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _currentAudit = MutableStateFlow<DeviceAudit?>(null)
    val currentAudit: StateFlow<DeviceAudit?> = _currentAudit.asStateFlow()

    init {
        refreshAudit(saveToHistory = false)
        seedDatabase()
    }

    fun refreshAudit(saveToHistory: Boolean = true) {
        viewModelScope.launch {
            val audit = systemInfoProvider.getDeviceAudit()
            _currentAudit.value = audit
            if (saveToHistory) {
                auditDao.insertAudit(audit)
            }
        }
    }

    fun updateCheckStatus(id: Long, isChecked: Boolean) {
        viewModelScope.launch {
            checklistRepository.updateCheckStatus(id, isChecked)
        }
    }

    fun shareAuditReport(audit: DeviceAudit) {
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(audit.timestamp))
        val report = """
            ROOTMATE PREPARATION REPORT
            Generated: $timestamp
            ---------------------------
            DEVICE: ${audit.manufacturer} ${audit.model}
            OS: Android ${audit.androidVersion} (API ${audit.apiLevel})
            BUILD: ${audit.buildNumber}
            SECURITY_PATCH: ${audit.securityPatch}
            
            DEVELOPER_SETTINGS:
            - Options: ${if (audit.devOptionsEnabled) "ENABLED" else "DISABLED"}
            - USB Debugging: ${if (audit.usbDebuggingEnabled) "ENABLED" else "DISABLED"}
            - OEM Unlocking: ${audit.oemUnlockStatus}
            
            READINESS_SCORE: ${audit.readinessScore}%
            ---------------------------
            SAFE PREPARATION FIRST. NEVER ROOT WITHOUT BACKUPS.
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "RootMate Readiness Report")
            putExtra(Intent.EXTRA_TEXT, report)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        
        getApplication<Application>().startActivity(Intent.createChooser(intent, "Share Report").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    private fun seedDatabase() {
        viewModelScope.launch {
            checklistItems.filter { it.isNotEmpty() }.firstOrNull() ?: run {
                val defaultItems = listOf(
                    ChecklistItem(category = "BACKUP", label = "Backup Photos", description = "Ensure all your photos are synced or copied to an external drive."),
                    ChecklistItem(category = "BACKUP", label = "Backup Contacts", description = "Export contacts to a VCF file or sync with an offline tool."),
                    ChecklistItem(category = "BACKUP", label = "Save Important Files", description = "Check Downloads, Documents, and app-specific folders."),
                    ChecklistItem(category = "BACKUP", label = "Recovery Preparation", description = "Know your recovery key or account passwords."),
                    
                    ChecklistItem(category = "DEV_ENV", label = "Enable Developer Options", description = "Tap 'Build Number' 7 times in About Phone."),
                    ChecklistItem(category = "DEV_ENV", label = "Enable USB Debugging", description = "Allows ADB communication from your PC."),
                    ChecklistItem(category = "DEV_ENV", label = "Check OEM Unlocking", description = "Required for unlocking the bootloader.")
                )
                checklistRepository.insertDefaultItems(defaultItems)
            }
        }
    }
}
