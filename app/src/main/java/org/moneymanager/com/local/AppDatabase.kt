package org.moneymanager.com.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.moneymanager.com.model.Transaction

@Database(
    entities = [Transaction::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTransactionDao(): TransactionDao
}
