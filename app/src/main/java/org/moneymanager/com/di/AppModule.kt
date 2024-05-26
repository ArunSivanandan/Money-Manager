package org.moneymanager.com.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import org.moneymanager.com.local.AppDatabase
import org.moneymanager.com.exportcsv.ExportCsvService
import org.moneymanager.com.local.dataencryption.DatabasePassphrase
import org.moneymanager.com.local.datastore.DataStoreImpl
import org.moneymanager.com.local.datastore.DataStoreManager
import org.moneymanager.com.utils.Constants.DB_NAME
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): DataStoreImpl {
        return DataStoreManager(context)
    }

    @Provides
    @Singleton
    fun provideDatabasePassphrase(@ApplicationContext context: Context) = DatabasePassphrase(context)

    @Provides
    @Singleton
    fun provideSupportFactory(databasePassphrase: DatabasePassphrase) = SupportFactory(databasePassphrase.getPassphrase())

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context, supportFactory: SupportFactory): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration()
            .openHelperFactory(supportFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideExportCSV(@ApplicationContext context: Context): ExportCsvService {
        return ExportCsvService(appContext = context)
    }
}
