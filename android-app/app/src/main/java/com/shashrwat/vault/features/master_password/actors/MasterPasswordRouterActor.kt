package com.shashrwat.vault.features.master_password.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.Router
import com.shashrwat.vault.features.common.Screens
import com.shashrwat.vault.features.common.navigation.RouterActor
import com.shashrwat.vault.features.master_password.MasterPasswordCommand
import com.shashrwat.vault.features.master_password.MasterPasswordCommand.RouterCommand
import com.shashrwat.vault.features.master_password.MasterPasswordCommand.RouterCommand.GoBack
import com.shashrwat.vault.features.master_password.MasterPasswordCommand.RouterCommand.GoToMainListScreen
import com.shashrwat.vault.features.master_password.MasterPasswordEvent

fun CreatingMasterPasswordRouterActor(
  router: Router
): Actor<MasterPasswordCommand, MasterPasswordEvent> {
  return RouterActor<MasterPasswordCommand, RouterCommand,
      MasterPasswordEvent>(router) { command ->
    when (command) {
      GoBack -> goBack()
      GoToMainListScreen -> switchToNewRoot(Screens.MainListScreen)
    }
  }
}
