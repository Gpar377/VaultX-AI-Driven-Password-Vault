package com.shashrwat.vault.features.password_entry.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.data.database.ObservableCachedDatabaseStorage
import com.shashrwat.vault.features.common.domain.MasterPasswordProvider
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand.UpdatePasswordEntry
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand.UpdatePasswordEntry.UpdateIsFavorite
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand.UpdatePasswordEntry.UpdateNotes
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand.UpdatePasswordEntry.UpdatePassword
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand.UpdatePasswordEntry.UpdateTitle
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand.UpdatePasswordEntry.UpdateUrl
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand.UpdatePasswordEntry.UpdateUsername
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent.UpdatedPasswordEntry.UpdatedIsFavorite
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent.UpdatedPasswordEntry.UpdatedNotes
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent.UpdatedPasswordEntry.UpdatedPassword
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent.UpdatedPasswordEntry.UpdatedTitle
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent.UpdatedPasswordEntry.UpdatedUrl
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent.UpdatedPasswordEntry.UpdatedUsername
import domain.interactors.KeePassPasswordEntryInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class UpdatePasswordEntryActor(
  private val masterPasswordProvider: MasterPasswordProvider,
  private val storage: ObservableCachedDatabaseStorage,
  private val passwordEntryInteractor: KeePassPasswordEntryInteractor,
) : Actor<PasswordEntryCommand, PasswordEntryEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<PasswordEntryCommand>): Flow<PasswordEntryEvent> {
    return commands.filterIsInstance<UpdatePasswordEntry>()
        .mapLatest { command ->
          val masterPassword = masterPasswordProvider.provideMasterPassword()
          val database = storage.getDatabase(masterPassword)
          val newDatabase = passwordEntryInteractor.editPassword(database, command.passwordEntry)
          storage.saveDatabase(newDatabase)
          when (command) {
            is UpdateTitle -> UpdatedTitle(command.passwordEntry)
            is UpdateUsername -> UpdatedUsername(command.passwordEntry)
            is UpdatePassword -> UpdatedPassword(command.passwordEntry)
            is UpdateUrl -> UpdatedUrl(command.passwordEntry)
            is UpdateNotes -> UpdatedNotes(command.passwordEntry)
            is UpdateIsFavorite -> UpdatedIsFavorite(command.passwordEntry)
          }
        }
  }
}
