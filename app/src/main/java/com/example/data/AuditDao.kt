package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AuditDao {
    @Query("SELECT * FROM device_audits ORDER BY timestamp DESC")
    fun getAllAudits(): Flow<List<DeviceAudit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAudit(audit: DeviceAudit)

    @Query("DELETE FROM device_audits")
    suspend fun deleteAll()
    
    @Query("SELECT * FROM device_audits ORDER BY timestamp DESC LIMIT 1")
    fun getLatestAudit(): Flow<DeviceAudit?>
}
