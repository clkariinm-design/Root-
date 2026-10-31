package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device_audits")
data class DeviceAudit(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val manufacturer: String,
    val model: String,
    val androidVersion: String,
    val apiLevel: Int,
    val securityPatch: String,
    val kernelVersion: String,
    val buildNumber: String,
    val hardware: String,
    val board: String,
    val bootloader: String,
    val radioVersion: String?,
    val devOptionsEnabled: Boolean,
    val usbDebuggingEnabled: Boolean,
    val oemUnlockStatus: String, // "ENABLED", "DISABLED", "UNKNOWN"
    val readinessScore: Int
)
