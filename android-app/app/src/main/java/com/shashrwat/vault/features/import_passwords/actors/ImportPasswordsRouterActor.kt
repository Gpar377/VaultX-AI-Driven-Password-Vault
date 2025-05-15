package com.shashrwat.vault.features.import_passwords.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.Router
import com.shashrwat.vault.features.common.Screens
import com.shashrwat.vault.features.common.navigation.RouterActor
import com.shashrwat.vault.features.import_passwords.ImportPasswordsCommand
import com.shashrwat.vault.features.import_passwords.ImportPasswordsCommand.RouterCommand
import com.shashrwat.vault.features.import_passwords.ImportPasswordsCommand.RouterCommand.GoBack
import com.shashrwat.vault.features.import_passwords.ImportPasswordsCommand.RouterCommand.GoToMainListScreen
import com.shashrwat.vault.features.import_passwords.ImportPasswordsEvent

fun ImportPasswordsRouterActor(
  router: Router
): Actor<ImportPasswordsCommand, ImportPasswordsEvent> {
  return RouterActor<ImportPasswordsCommand, RouterCommand, ImportPasswordsEvent>(
    router
  ) { command ->
    when (command) {
      GoToMainListScreen -> switchToNewRoot(Screens.MainListScreen)
      GoBack -> goBack()
    }
  }
}
