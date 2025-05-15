package com.shashrwat.vault.features.settings.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.data.files.StorageBackupPreferences
import com.shashrwat.vault.features.common.domain.MasterPasswordProvider
import com.shashrwat.vault.features.common.domain.StorageBackupInteractor
import com.shashrwat.vault.features.settings.SettingsCommand
import com.shashrwat.vault.features.settings.SettingsCommand.BackupCommand
import com.shashrwat.vault.features.settings.SettingsCommand.BackupCommand.DisableStorageBackup
import com.shashrwat.vault.features.settings.SettingsCommand.BackupCommand.EnableStorageBackup
import com.shashrwat.vault.features.settings.SettingsCommand.BackupCommand.PerformBackup
import com.shashrwat.vault.features.settings.SettingsEvent
import com.shashrwat.vault.features.settings.SettingsEvent.PerformedBackup
import com.shashrwat.vault.features.settings.SettingsEvent.StorageBackupDisabled
import com.shashrwat.vault.features.settings.SettingsEvent.StorageBackupEnabled
import domain.DatabaseStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class StorageBackupActor(
  private val preferences: StorageBackupPreferences,
  private val masterPasswordProvider: MasterPasswordProvider,
  private val storage: DatabaseStorage,
  private val storageBackupInteractor: StorageBackupInteractor
) : Actor<SettingsCommand, SettingsEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<SettingsCommand>): Flow<SettingsEvent> {
    return commands.filterIsInstance<BackupCommand>()
        .mapLatest { command ->
          when (command) {
            is EnableStorageBackup -> {
              preferences.enableBackup(command.backupFolderUri.toString())
              StorageBackupEnabled(command.backupFolderUri)
            }
            DisableStorageBackup -> {
              preferences.disableBackup()
              StorageBackupDisabled
            }
            PerformBackup -> {
              val masterPassword = masterPasswordProvider.provideMasterPassword()
              val database = storage.getDatabase(masterPassword)
              storageBackupInteractor.forceBackup(database)
              PerformedBackup
            }
          }
        }
  }
}
