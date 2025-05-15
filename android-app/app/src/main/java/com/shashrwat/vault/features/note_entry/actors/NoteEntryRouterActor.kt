package com.shashrwat.vault.features.note_entry.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.Router
import com.shashrwat.vault.features.common.Screens
import com.shashrwat.vault.features.common.navigation.RouterActor
import com.shashrwat.vault.features.note_entry.NoteEntryCommand
import com.shashrwat.vault.features.note_entry.NoteEntryCommand.RouterCommand
import com.shashrwat.vault.features.note_entry.NoteEntryCommand.RouterCommand.GoBack
import com.shashrwat.vault.features.note_entry.NoteEntryCommand.RouterCommand.SwitchBackToLogin
import com.shashrwat.vault.features.note_entry.NoteEntryEvent

fun NoteRouterActor(
  router: Router
): Actor<NoteEntryCommand, NoteEntryEvent> {
  return RouterActor<NoteEntryCommand, RouterCommand, NoteEntryEvent>(router) { command ->
    when (command) {
      GoBack -> goBack()
      SwitchBackToLogin -> switchToNewRoot(Screens.LoginScreen)
    }
  }
}