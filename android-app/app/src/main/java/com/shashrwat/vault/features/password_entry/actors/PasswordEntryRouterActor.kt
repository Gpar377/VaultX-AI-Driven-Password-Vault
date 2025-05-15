package com.shashrwat.vault.features.password_entry.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.Router
import com.shashrwat.vault.features.common.Screens
import com.shashrwat.vault.features.common.Screens.CreatingPasswordScreen
import com.shashrwat.vault.features.common.navigation.RouterActor
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand.RouterCommand
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand.RouterCommand.GoBack
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand.RouterCommand.GoToCreatingPasswordScreen
import com.shashrwat.vault.features.password_entry.PasswordEntryCommand.RouterCommand.SwitchBackToLogin
import com.shashrwat.vault.features.password_entry.PasswordEntryEvent

fun PasswordEntryRouterActor(router: Router): Actor<PasswordEntryCommand, PasswordEntryEvent> {
  return RouterActor<PasswordEntryCommand, RouterCommand, PasswordEntryEvent>(router) { command ->
    when (command) {
      is GoToCreatingPasswordScreen -> goForward(CreatingPasswordScreen)
      GoBack -> goBack()
      SwitchBackToLogin -> switchToNewRoot(Screens.LoginScreen)
    }
  }
}
