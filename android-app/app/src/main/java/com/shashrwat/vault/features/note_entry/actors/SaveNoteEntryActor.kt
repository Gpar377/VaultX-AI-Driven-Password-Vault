package com.shashrwat.vault.features.note_entry.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.data.database.ObservableCachedDatabaseStorage
import com.shashrwat.vault.features.common.domain.MasterPasswordProvider
import com.shashrwat.vault.features.note_entry.NoteEntryCommand
import com.shashrwat.vault.features.note_entry.NoteEntryCommand.SaveNoteEntry
import com.shashrwat.vault.features.note_entry.NoteEntryEvent
import com.shashrwat.vault.features.note_entry.NoteEntryEvent.NotifyEntryCreated
import domain.interactors.KeePassNoteEntryInteractor
import domain.model.NoteEntry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class SaveNoteEntryActor(
  private val masterPasswordProvider: MasterPasswordProvider,
  private val storage: ObservableCachedDatabaseStorage,
  private val noteEntryInteractor: KeePassNoteEntryInteractor,
) : Actor<NoteEntryCommand, NoteEntryEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<NoteEntryCommand>): Flow<NoteEntryEvent> {
    return commands.filterIsInstance<SaveNoteEntry>()
        .mapLatest { command ->
          val masterPassword = masterPasswordProvider.provideMasterPassword()
          val database = storage.getDatabase(masterPassword)
          val databaseUUIDPair = noteEntryInteractor.addNote(database, command.data)
          storage.saveDatabase(databaseUUIDPair.first)
          val createdEntry = NoteEntry(
            id = databaseUUIDPair.second,
            title = command.data.title,
            text = command.data.text,
            isFavorite = command.data.isFavorite
          )
          NotifyEntryCreated(createdEntry)
        }
  }
}
