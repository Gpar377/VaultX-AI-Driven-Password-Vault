package com.shashrwat.vault.features.note_entry.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.data.database.ObservableCachedDatabaseStorage
import com.shashrwat.vault.features.common.domain.MasterPasswordProvider
import com.shashrwat.vault.features.note_entry.NoteEntryCommand
import com.shashrwat.vault.features.note_entry.NoteEntryCommand.FetchNoteEntry
import com.shashrwat.vault.features.note_entry.NoteEntryEvent
import com.shashrwat.vault.features.note_entry.NoteEntryEvent.MasterPasswordNull
import com.shashrwat.vault.features.note_entry.NoteEntryEvent.ReceivedNoteEntry
import domain.interactors.KeePassNoteEntryInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class FetchNoteEntryActor(
  private val masterPasswordProvider: MasterPasswordProvider,
  private val storage: ObservableCachedDatabaseStorage,
  private val noteEntryInteractor: KeePassNoteEntryInteractor,
) : Actor<NoteEntryCommand, NoteEntryEvent> {
  
  @OptIn(ExperimentalCoroutinesApi::class)
  override fun handle(commands: Flow<NoteEntryCommand>): Flow<NoteEntryEvent> {
    return commands.filterIsInstance<FetchNoteEntry>()
        .mapLatest { command ->
          val masterPassword = masterPasswordProvider.provideMasterPasswordIfSet()
              ?: return@mapLatest MasterPasswordNull
          val database = storage.getDatabase(masterPassword)
          val noteEntry = noteEntryInteractor.getNoteEntry(database, command.noteId)
          ReceivedNoteEntry(noteEntry)
        }
  }
}