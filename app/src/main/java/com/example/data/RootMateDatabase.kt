package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ChecklistItem::class, DeviceAudit::class], version = 2, exportSchema = false)
abstract class RootMateDatabase : RoomDatabase() {
    abstract fun checklistDao(): ChecklistDao
    abstract fun auditDao(): AuditDao

    companion object {
        @Volatile
        private var INSTANCE: RootMateDatabase? = null

        fun getDatabase(context: Context): RootMateDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RootMateDatabase::class.java,
                    "rootmate_database"
                )
                .fallbackToDestructiveMigration() // Simple for development, wipe old DB on schema change
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
