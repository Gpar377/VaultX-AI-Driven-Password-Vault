package com.shashrwat.vault.features.password_entry.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.data.database.ObservableCachedDatabaseStorage
import com.shashrwat.vault.features.common.domain.MasterPasswordProvider
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand.SavePassword
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent.CreatedPasswordEntry
import domain.interactors.KeePassPasswordEntryInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class SavingPasswordEntryActor(
  private val masterPasswordProvider: MasterPasswordProvider,
  private val storage: ObservableCachedDatabaseStorage,
  private val passwordEntryInteractor: KeePassPasswordEntryInteractor
) : Actor<PasswordEntryCommand, PasswordEntryEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<PasswordEntryCommand>): Flow<PasswordEntryEvent> {
    return commands.filterIsInstance<SavePassword>()
        .mapLatest { command ->
          val masterPassword = masterPasswordProvider.provideMasterPassword()
          val database = storage.getDatabase(masterPassword)
          val databaseUUIDPair = passwordEntryInteractor
              .addPassword(database, command.passwordEntryData)
          storage.saveDatabase(databaseUUIDPair.first)
          CreatedPasswordEntry(databaseUUIDPair.second)
        }
  }
}
