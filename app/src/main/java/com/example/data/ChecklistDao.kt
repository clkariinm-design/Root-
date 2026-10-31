package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecklistDao {
    @Query("SELECT * FROM checklist_items ORDER BY category, orderIndex ASC")
    fun getAllItems(): Flow<List<ChecklistItem>>

    @Query("SELECT * FROM checklist_items WHERE category = :category ORDER BY orderIndex ASC")
    fun getItemsByCategory(category: String): Flow<List<ChecklistItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ChecklistItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ChecklistItem>)

    @Update
    suspend fun updateItem(item: ChecklistItem)

    @Query("UPDATE checklist_items SET isChecked = :isChecked WHERE id = :id")
    suspend fun updateCheckStatus(id: Long, isChecked: Boolean)

    @Query("DELETE FROM checklist_items")
    suspend fun deleteAll()
}
