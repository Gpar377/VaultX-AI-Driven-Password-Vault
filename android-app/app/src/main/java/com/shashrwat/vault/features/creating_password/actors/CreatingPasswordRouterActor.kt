package com.shashrwat.vault.features.creating_password.actors

import com.shashrwat.vault.core.mvi.tea.Actor
import com.shashrwat.vault.features.common.Router
import com.shashrwat.vault.features.common.navigation.RouterActor
import com.shashrwat.vault.features.creating_password.CreatingPasswordCommand
import com.shashrwat.vault.features.creating_password.CreatingPasswordCommand.GoBack
import com.shashrwat.vault.features.creating_password.CreatingPasswordEvent

fun CreatingPasswordRouterActor(
  router: Router
): Actor<CreatingPasswordCommand, CreatingPasswordEvent> {
  return RouterActor<CreatingPasswordCommand, GoBack, CreatingPasswordEvent>(router) {
    goBack()
  }
}
