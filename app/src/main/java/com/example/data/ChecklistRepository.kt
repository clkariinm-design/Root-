package com.example.data

import kotlinx.coroutines.flow.Flow

class ChecklistRepository(private val checklistDao: ChecklistDao) {
    val allItems: Flow<List<ChecklistItem>> = checklistDao.getAllItems()

    fun getItemsByCategory(category: String): Flow<List<ChecklistItem>> = 
        checklistDao.getItemsByCategory(category)

    suspend fun updateCheckStatus(id: Long, isChecked: Boolean) {
        checklistDao.updateCheckStatus(id, isChecked)
    }

    suspend fun insertDefaultItems(items: List<ChecklistItem>) {
        checklistDao.insertItems(items)
    }
}
