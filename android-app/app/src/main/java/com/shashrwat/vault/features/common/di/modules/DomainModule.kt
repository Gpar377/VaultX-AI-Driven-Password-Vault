package com.shashrwat.vault.features.common.di.modules

import com.shashrwat.vault.BuildConfig
import com.shashrwat.vault.features.common.AppConfig
import com.shashrwat.vault.features.common.AppConstants
import com.shashrwat.vault.features.common.data.database.BasicDatabaseStorage
import com.shashrwat.vault.features.common.data.database.CachedDatabaseStorage
import com.shashrwat.vault.features.common.data.database.ObservableCachedDatabaseStorage
import com.shashrwat.vault.features.common.data.files.DefaultDatabaseFileSaver
import com.shashrwat.vault.features.common.data.files.StorageBackupDatabaseFileSaver
import com.shashrwat.vault.features.common.data.files.StorageBackupExternalFileSaverImpl
import com.shashrwat.vault.features.common.data.files.StorageBackupPreferences
import com.shashrwat.vault.features.common.domain.BackupInterceptor
import com.shashrwat.vault.features.common.domain.DatabaseChangesJournalImpl
import com.shashrwat.vault.features.common.domain.ShowUsernamesInteractor
import com.shashrwat.vault.features.common.domain.StorageBackupInteractor
import com.shashrwat.vault.features.main_list.domain.EntriesListUiMapper
import com.shashrwat.vault.features.main_list.domain.LoadEntriesInteractor
import domain.DatabaseCache
import domain.DatabaseFileSaver
import domain.DatabaseInitializer
import domain.DefaultDatabaseInitializer

interface DomainModule {
  val storageBackupPreferences: StorageBackupPreferences
  val storageBackupInteractor: StorageBackupInteractor
  val databaseFileSaver: DatabaseFileSaver
  val databaseCache: DatabaseCache
  val databaseInitializer: DatabaseInitializer
  val observableCachedDatabaseStorage: ObservableCachedDatabaseStorage
  val showUsernamesInteractor: ShowUsernamesInteractor
  val entriesListUiMapper: EntriesListUiMapper
  val loadEntriesInteractor: LoadEntriesInteractor
}

class DomainModuleImpl(
  coreModule: CoreModule,
  ioModule: IoModule,
  preferencesModule: PreferencesModule,
  backupInterceptor: BackupInterceptor,
) : DomainModule {
  
  private val passedTimeSinceLastBackupThreshold = if (BuildConfig.DEBUG) {
    AppConfig.Debug.TimePassedSinceLastBackupThreshold
  } else {
    AppConfig.Release.TimePassedSinceLastBackupThreshold
  }
  
  private val maxBackupFileCount = if (BuildConfig.DEBUG) {
    AppConfig.Debug.MaxBackupFileCount
  } else {
    AppConfig.Release.MaxBackupFileCount
  }
  
  private val databaseChangesForBackupThreshold = if (BuildConfig.DEBUG) {
    AppConfig.Debug.DatabaseChangesForBackupThreshold
  } else {
    AppConfig.Release.DatabaseChangesForBackupThreshold
  }
  
  override val storageBackupPreferences = StorageBackupPreferences(
    preferencesModule.settingsPreferences
  )
  
  override val storageBackupInteractor = StorageBackupInteractor(
    storageBackupExternalFileSaver = StorageBackupExternalFileSaverImpl(
      coreModule.application,
      coreModule.dispatchers,
      backupInterceptor
    ),
    preferences = storageBackupPreferences,
    journal = DatabaseChangesJournalImpl(preferencesModule.settingsPreferences),
    timestampProvider = coreModule.timestampProvider,
    dateTimeFormatter = coreModule.dateTimeFormatter,
    passedTimeSinceLastBackupThreshold = passedTimeSinceLastBackupThreshold,
    databaseChangesThreshold = databaseChangesForBackupThreshold,
    backupFileCountThreshold = maxBackupFileCount,
  )
  
  override val databaseFileSaver = StorageBackupDatabaseFileSaver(
    databaseFileSaver = DefaultDatabaseFileSaver(
      AppConstants.DEFAULT_INTERNAL_PASSWORDS_FILE_NAME,
      ioModule.keyFileSaver,
      coreModule.application,
      coreModule.dispatchers,
      coreModule.globalIOScope,
    ),
    databaseChangesJournal = DatabaseChangesJournalImpl(preferencesModule.settingsPreferences),
    storageBackupInteractor = storageBackupInteractor,
    scope = coreModule.globalIOScope
  )
  
  private val cachedDatabaseStorage = CachedDatabaseStorage(
    BasicDatabaseStorage(databaseFileSaver)
  )
  
  override val databaseCache = cachedDatabaseStorage
  
  override val databaseInitializer = DefaultDatabaseInitializer(databaseCache, databaseFileSaver)
  
  override val observableCachedDatabaseStorage =
      ObservableCachedDatabaseStorage(cachedDatabaseStorage)
  
  override val showUsernamesInteractor =
      ShowUsernamesInteractor(preferencesModule.settingsPreferences)
  
  override val entriesListUiMapper = EntriesListUiMapper()
  
  override val loadEntriesInteractor = LoadEntriesInteractor(
    observableCachedDatabaseStorage,
    entriesListUiMapper,
    showUsernamesInteractor
  )
}
