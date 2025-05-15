package com.shashrwat.vault.features.settings.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.Router
import com.shashrwat.vault.features.common.Screens.MasterPasswordScreen
import com.shashrwat.vault.features.common.navigation.RouterActor
import com.shashrwat.vault.features.master_password.MasterPasswordScreenMode.CHANGE_EXISTING
import com.shashrwat.vault.features.settings.SettingsCommand
import com.shashrwat.vault.features.settings.SettingsCommand.RouterCommand
import com.shashrwat.vault.features.settings.SettingsCommand.RouterCommand.GoBack
import com.shashrwat.vault.features.settings.SettingsEvent

fun SettingsRouterActor(router: Router): Actor<SettingsCommand, SettingsEvent> {
  return RouterActor<SettingsCommand, RouterCommand, SettingsEvent>(router) { command ->
    when (command) {
      is RouterCommand.GoToMasterPasswordScreen -> {
        goForward(MasterPasswordScreen(CHANGE_EXISTING))
      }
      GoBack -> goBack()
    }
  }
}