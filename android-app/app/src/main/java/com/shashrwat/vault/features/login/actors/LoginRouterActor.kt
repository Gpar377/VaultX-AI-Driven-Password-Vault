package com.shashrwat.vault.features.login.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.Router
import com.shashrwat.vault.features.common.Screens.MainListScreen
import com.shashrwat.vault.features.common.navigation.RouterActor
import com.shashrwat.vault.features.login.LoginCommand
import com.shashrwat.vault.features.login.LoginCommand.GoToMainListScreen
import com.shashrwat.vault.features.login.LoginEvent

fun LoginRouterActor(router: Router): Actor<LoginCommand, LoginEvent> {
  return RouterActor<LoginCommand, GoToMainListScreen, LoginEvent>(router) {
    switchToNewRoot(MainListScreen)
  }
}
