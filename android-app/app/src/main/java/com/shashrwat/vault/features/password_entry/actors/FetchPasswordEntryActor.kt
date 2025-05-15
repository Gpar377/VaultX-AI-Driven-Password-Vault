package com.shashrwat.vault.features.password_entry.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.data.database.ObservableCachedDatabaseStorage
import com.shashrwat.vault.features.common.domain.MasterPasswordProvider
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand.FetchPasswordEntry
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent.MasterPasswordNull
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent.ReceivedPasswordEntry
import domain.interactors.KeePassPasswordEntryInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class FetchPasswordEntryActor(
  private val masterPasswordProvider: MasterPasswordProvider,
  private val storage: ObservableCachedDatabaseStorage,
  private val passwordEntryInteractor: KeePassPasswordEntryInteractor,
) : Actor<PasswordEntryCommand, PasswordEntryEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<PasswordEntryCommand>): Flow<PasswordEntryEvent> {
    return commands.filterIsInstance<FetchPasswordEntry>()
        .mapLatest { command ->
          val masterPassword = masterPasswordProvider.provideMasterPasswordIfSet()
              ?: return@mapLatest MasterPasswordNull
          val database = storage.getDatabase(masterPassword)
          val passwordEntry = passwordEntryInteractor.getPasswordEntry(database, command.passwordId)
          ReceivedPasswordEntry(passwordEntry)
        }
  }
}