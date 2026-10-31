package com.example.data

import android.os.Build
import android.content.Context
import android.provider.Settings

class SystemInfoProvider(private val context: Context) {

    fun getDeviceAudit(): DeviceAudit {
        val devOptionsEnabled = isDeveloperOptionsEnabled()
        val usbDebuggingEnabled = isUsbDebuggingEnabled()
        val oemUnlockStatus = getOemUnlockStatus()
        
        return DeviceAudit(
            manufacturer = Build.MANUFACTURER,
            model = Build.MODEL,
            androidVersion = Build.VERSION.RELEASE,
            apiLevel = Build.VERSION.SDK_INT,
            securityPatch = getSecurityPatchDate(),
            kernelVersion = System.getProperty("os.version") ?: "[UNKNOWN]",
            buildNumber = Build.DISPLAY,
            hardware = Build.HARDWARE,
            board = Build.BOARD,
            bootloader = Build.BOOTLOADER,
            radioVersion = Build.getRadioVersion(),
            devOptionsEnabled = devOptionsEnabled,
            usbDebuggingEnabled = usbDebuggingEnabled,
            oemUnlockStatus = oemUnlockStatus,
            readinessScore = calculateReadinessScore(devOptionsEnabled, usbDebuggingEnabled, oemUnlockStatus)
        )
    }

    /**
     * Compatibility Engine: Rule-based offline scoring system.
     * Calculates preparation readiness, not root success.
     */
    private fun calculateReadinessScore(
        devOptions: Boolean,
        usbDebugging: Boolean,
        oemUnlock: String
    ): Int {
        var score = 0
        
        // Critical Developer Settings (Base Preparation)
        if (devOptions) score += 30
        if (usbDebugging) score += 30
        if (oemUnlock == "ENABLED") score += 20 // Note: OEM status often unknown, but if it is enabled, that's high readiness.
        
        // Device Context (Education Readiness)
        // Some devices are historically easier to prepare (Pixel, OnePlus)
        val lowerManufacturer = Build.MANUFACTURER.lowercase()
        if (lowerManufacturer.contains("google") || lowerManufacturer.contains("oneplus")) {
            score += 20
        } else if (lowerManufacturer.contains("samsung")) {
            // Samsung often needs Knox considerations/Odin, score is lower for general readiness
            score += 10
        } else {
            score += 15
        }
        
        return score.coerceAtMost(100)
    }

    private fun getSecurityPatchDate(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Build.VERSION.SECURITY_PATCH
        } else {
            "[UNKNOWN]"
        }
    }

    private fun isDeveloperOptionsEnabled(): Boolean {
        return try {
            Settings.Global.getInt(context.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0
        } catch (e: Exception) {
            false
        }
    }

    private fun isUsbDebuggingEnabled(): Boolean {
        return try {
            Settings.Global.getInt(context.contentResolver, Settings.Global.ADB_ENABLED, 0) != 0
        } catch (e: Exception) {
            false
        }
    }

    private fun getOemUnlockStatus(): String {
        return "[UNKNOWN]"
    }
}
