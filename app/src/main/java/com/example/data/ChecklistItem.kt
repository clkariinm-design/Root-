package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checklist_items")
data class ChecklistItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val category: String, // "BACKUP", "DEV_ENV", "GENERAL"
    val label: String,
    val description: String,
    val isChecked: Boolean = false,
    val orderIndex: Int = 0
)
